const Joi = require('joi');
const { joiPassword } = require('joi-password');

const userPayloadSchema = Joi.object({
  email: Joi.string().email().required(),
  password: joiPassword
    .string()
    .minOfSpecialCharacters(1)
    .minOfLowercase(1)
    .minOfUppercase(1)
    .minOfNumeric(1)
    .noWhiteSpaces()
    .required()
    .messages({
      'password.minOfUppercase': '{#label} should contain at least {#min} uppercase character',
      'password.minOfSpecialCharacters':
            '{#label} should contain at least {#min} special character',
      'password.minOfLowercase': '{#label} should contain at least {#min} lowercase character',
      'password.minOfNumeric': '{#label} should contain at least {#min} numeric character',
      'password.noWhiteSpaces': '{#label} should not contain white spaces',
    }),
  passwordConfirmation: Joi.any().valid(Joi.ref('password')).required(),
  username: joiPassword
    .string()
    .noWhiteSpaces()
    .required()
    .messages({
      'username.noWhiteSpaces': '{#label} should not contain white spaces',
    }),
  fullname: Joi.string().required(),
});

const userQuerySchema = Joi.object({
  username: Joi.string(),
  email: Joi.string(),
});

module.exports = { userPayloadSchema, userQuerySchema };
