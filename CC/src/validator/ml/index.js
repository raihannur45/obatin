const InvariantError = require('../../exceptions/InvariantError');
const {
  predictPayloadSchema,
  getMessagePayloadSchema,
  getResponsePayloadSchema,
} = require('./schema');

const MlValidator = {
  validatePredictPayload: (payload) => {
    const validationResult = predictPayloadSchema.validate(payload);
    if (validationResult.error) {
      throw new InvariantError(validationResult.error.message);
    }
  },
  validateGetMessagePayload: (payload) => {
    const validationResult = getMessagePayloadSchema.validate(payload);
    if (validationResult.error) {
      throw new InvariantError(validationResult.error.message);
    }
  },
  validateGetResponsePayload: (payload) => {
    const validationResult = getResponsePayloadSchema.validate(payload);
    if (validationResult.error) {
      throw new InvariantError(validationResult.error.message);
    }
  },
};

module.exports = MlValidator;
