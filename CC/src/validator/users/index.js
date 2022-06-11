const InvariantError = require('../../exceptions/InvariantError');
const { userPayloadSchema, userQuerySchema } = require('./schema');

const UserValidator = {
  validateUserPayload: (payload) => {
    const validationResult = userPayloadSchema.validate(payload);
    if (validationResult.error) {
      throw new InvariantError(validationResult.error.message);
    }
  },
  validateUserQuery: (query) => {
    const validationResult = userQuerySchema.validate(query);
    if (validationResult.error) {
      throw new InvariantError(validationResult.error.message);
    }
  },
};

module.exports = UserValidator;
