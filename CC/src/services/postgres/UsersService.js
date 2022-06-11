const bcrypt = require('bcrypt');
const { nanoid } = require('nanoid');
const { Op } = require('sequelize');
const { users } = require('../../models');

const InvariantError = require('../../exceptions/InvariantError');
const AuthenticationError = require('../../exceptions/AuthenticationError');
const NotFoundError = require('../../exceptions/NotFoundError');

class UsersService {
  constructor(memcachedService) {
    this._model = users;
    this._memcachedService = memcachedService;
  }

  // register
  async addUser({
    email,
    password,
    username,
    fullname,
  }) {
    let result = null;
    const hashedPassword = await bcrypt.hash(password, 10); // enkripsi password dengan bycrypt
    const id = `user-${nanoid(12)}`;
    await this.verifyNewUserName(username);
    await this.verifyNewUserEmail(email);
    result = await this._model.create({
      id,
      email,
      password: hashedPassword,
      username,
      fullname,
    }, { raw: true });
    if (!result.id) {
      throw new InvariantError('registration fail');
    }
    return result.id;
  }

  async verifyNewUserName(username) {
    const result = await this._model.findOne({
      where: { username },
      attributes: ['id'],
    });
    if (result) {
      throw new InvariantError('username already used');
    }
  }

  async verifyNewUserEmail(email) {
    const result = await this._model.findOne({
      where: { email },
      attributes: ['id'],
    });
    if (result) {
      throw new InvariantError('email already used');
    }
  }

  // login
  async verifyUserCredential({ username, password }) {
    const result = await this._model.findOne({
      where: {
        [Op.or]: [{ username }, { email: username }],
      },
    });
    if (result === null) {
      throw new AuthenticationError('wrong credentials');
    }

    const { id, password: hashedPassword } = result;
    const match = await bcrypt.compare(password, hashedPassword);
    if (!match) {
      throw new AuthenticationError('wrong credentials');
    }
    return id;
  }

  async getUser({ username, email }) {
    const itsme = username || email;
    let result = await this._memcachedService.get(itsme);
    if (!result) {
      // cari user berdasarkan username atau email
      result = await this._model.findOne({
        where: {
          [Op.or]: [{ username: itsme }, { email: itsme }],
        },
        attributes: ['id', 'username', 'fullname', 'email'],
        raw: true,
      });
      if (!result) {
        throw new NotFoundError(`user ${username || email} not found`);
      }
      await this._memcachedService.set(itsme, result);
    }
    return result;
  }
}

module.exports = UsersService;
