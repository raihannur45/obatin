class UserHandler {
  constructor(service, validator) {
    this._service = service;
    this._validator = validator;

    this.postUserHandler = this.postUserHandler.bind(this);
    this.getUserHandler = this.getUserHandler.bind(this);
  }

  async postUserHandler({ payload }, h) {
    this._validator.validateUserPayload(payload);

    const id = await this._service.addUser(payload);

    return h.response({
      status: 'success',
      message: 'user added',
      data: {
        id,
      },
    }).code(201);
  }

  async getUserHandler({ query }) {
    this._validator.validateUserQuery(query);

    const user = await this._service.getUser(query);

    return {
      status: 'success',
      data: user,
    };
  }
}

module.exports = UserHandler;
