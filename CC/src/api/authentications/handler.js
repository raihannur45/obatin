class AuthenticationsHandler {
  constructor(authenticationService, userService, tokenManager, validator) {
    this._authenticationService = authenticationService;
    this._userService = userService;
    this._tokenManager = tokenManager;
    this._validator = validator;

    this.postAuthenticationHandler = this.postAuthenticationHandler.bind(this);
    this.putAuthenticationHandler = this.putAuthenticationHandler.bind(this);
    this.deleteAuthenticationHandler = this.deleteAuthenticationHandler.bind(this);
  }

  async postAuthenticationHandler({ payload }, h) {
    this._validator.validatePostAuthenticationPayload(payload);
    const id = await this._userService.verifyUserCredential(payload);

    const accessToken = this._tokenManager.generateAccessToken({ id });
    const refreshToken = this._tokenManager.generateRefreshToken({ id });

    await this._authenticationService.addRefreshToken(refreshToken);

    return h.response({
      status: 'success',
      message: 'Authentication success',
      data: {
        accessToken,
        refreshToken,
      },
    }).code(201);
  }

  async putAuthenticationHandler({ payload }) {
    this._validator.validatePutAuthenticationPayload(payload);

    await this._authenticationService.verifyRefreshToken(payload);
    const { id } = this._tokenManager.verifyRefreshToken(payload);

    const accessToken = this._tokenManager.generateAccessToken({ id });

    return {
      status: 'success',
      message: 'access token generated',
      data: {
        accessToken,
      },
    };
  }

  async deleteAuthenticationHandler({ payload }) {
    this._validator.validateDeleteAuthenticationPayload(payload);
    await this._authenticationService.verifyRefreshToken(payload);
    await this._authenticationService.deleteRefreshToken(payload);

    return {
      status: 'success',
      message: 'refresh token has been deleted',
    };
  }
}

module.exports = AuthenticationsHandler;
