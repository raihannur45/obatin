const Joi = require('joi');
const { joiPassword } = require('joi-password');

const predictPayloadSchema = Joi.object({
  text: Joi.string().required(),
});

const getMessagePayloadSchema = Joi.object({
  label: joiPassword.string().noWhiteSpaces().required(),
  namaobat: Joi.string().required(),
});

const getResponsePayloadSchema = Joi.object({
  label: joiPassword.string().noWhiteSpaces().required(),
});

module.exports = { predictPayloadSchema, getMessagePayloadSchema, getResponsePayloadSchema };
