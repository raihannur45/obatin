const InvariantError = require('../../exceptions/InvariantError');
const { authentications } = require('../../models');

class AuthenticationService {
  constructor() {
    this._model = authentications;
  }

  async addRefreshToken(refreshToken) {
    await this._model.create({ token: refreshToken });
  }

  async verifyRefreshToken({ refreshToken }) {
    const result = await this._model.findOne({ where: { token: refreshToken } });

    if (result === null) {
      throw new InvariantError('Invalid refresh token');
    }
  }

  async deleteRefreshToken({ refreshToken }) {
    await this._model.destroy({ where: { token: refreshToken } });
  }
}

module.exports = AuthenticationService;
