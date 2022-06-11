const talkPayloadSchema = require('./schema');
const InvariantError = require('../../exceptions/InvariantError');

const TalkValidator = {
  // fungsi untuk memvalidasi payload talk
  validateTalkPayload: (payload) => {
    const validationResult = talkPayloadSchema.validate(payload);
    // jika divalidasi hasilnya error maka panggil InvariantError
    if (validationResult.error) {
      throw new InvariantError(validationResult.error.message);
    }
  },
};

module.exports = TalkValidator;
