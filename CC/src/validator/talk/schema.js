const Joi = require('joi');

const talkPayloadSchema = Joi.object({
  message: Joi.string().required(), // key message bertipe string
});

module.exports = talkPayloadSchema;
