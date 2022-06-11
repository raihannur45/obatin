const fetch = (...args) => import('node-fetch').then(({ default: fetchx }) => fetchx(...args));

class MlHandler {
  constructor(validator, url) {
    this.url = url;
    this._validator = validator;

    this.postPredictHandler = this.postPredictHandler.bind(this);
    this.postGetMessageHandler = this.postGetMessageHandler.bind(this);
    this.postGetResponseHandler = this.postGetResponseHandler.bind(this);
  }

  async postPredictHandler({ payload }) {
    this._validator.validatePredictPayload(payload);
    let response;
    try {
      response = await fetch(`${this.url}/bot?text=${payload.text}`);
      response = await response.json();
    } catch (error) {
      console.log(error);
      throw new Error();
    }
    return {
      status: 'success',
      data: {
        response,
      },
    };
  }

  async postGetMessageHandler({ payload }) {
    this._validator.validateGetMessagePayload(payload);
    let response = {};
    try {
      response = await fetch(`${this.url}/database?label=${payload.label}&namaobat=${payload.namaobat}`);
      response = await response.json();
    } catch (error) {
      console.log(error);
      throw new Error();
    }
    return {
      status: 'success',
      data: {
        response,
      },
    };
  }

  async postGetResponseHandler({ payload }) {
    this._validator.validateGetResponsePayload(payload);
    let response = {};
    try {
      response = await fetch(`${this.url}/respon?label=${payload.label}`);
      response = await response.json();
    } catch (error) {
      console.log(error);
      throw new Error();
    }
    return {
      status: 'success',
      data: {
        response,
      },
    };
  }
}

module.exports = MlHandler;
