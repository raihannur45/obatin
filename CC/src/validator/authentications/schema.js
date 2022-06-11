const Joi = require('joi');
const { joiPassword } = require('joi-password');

const postAuthenticationPayloadSchema = Joi.object({
  username: Joi.string().trim().min(3).required(),
  password: joiPassword
    .string()
    .required(),
});

const putAuthenticationPayloadSchema = Joi.object({
  refreshToken: Joi.string().required(),
});

const deleteAuthenticationPayloadSchema = Joi.object({
  refreshToken: Joi.string().required(),
});

module.exports = {
  postAuthenticationPayloadSchema,
  putAuthenticationPayloadSchema,
  deleteAuthenticationPayloadSchema,
};
