class TalkHandler {
  constructor(service, validator) {
    this._service = service;
    this._validator = validator;
    this.postTalkHandler = this.postTalkHandler.bind(this);
  }

  // eslint-disable-next-line class-methods-use-this
  async postTalkHandler({ payload }, h) {
    // memvalidasi payload yang masuk
    this._validator.validateTalkPayload(payload);

    // mendapatkan data dari service
    const response = await this._service.sendMessage(payload);

    // response berhasil
    return h.response({
      status: 'success',
      data: {
        response,
      },
    }).code(200);
  }
}

module.exports = TalkHandler;
