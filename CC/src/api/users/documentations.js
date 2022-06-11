const Joi = require('joi');
const { joiPassword } = require('joi-password');
//
const doc = {
  postUsersDoc: {
    description: 'User register',
    notes: 'This endpoint used for user register. Payload contains email, password, passwordConfirmation, username, and fullname field, after you fill payload and send request to server, server will response 201 and data contains user id.',
    tags: ['api', 'users'],
    validate: {
      payload: Joi.object({
        email: Joi.string().email().required(),
        password: joiPassword
          .string()
          .required(),
        passwordConfirmation: joiPassword
          .string()
          .required(),
        username: joiPassword
          .string()
          .required(),
        fullname: Joi.string().required(),
      }),
    },
    response: {
      status: {
        201: Joi.object({
          status: Joi.string(),
          message: Joi.string(),
          data: {
            id: Joi.string(),
          },
        }),
        400: undefined,
      },
    },
  },
  getUserDoc: {
    auth: 'obatin_api_jwt',
    description: 'Get User',
    notes: 'This endpoint is used to get user profile information based on user name or user email. You must attach email or username in the query parameter, after you fill it and send request to server, server will response 200 and data contains user profiles.',
    tags: ['api', 'users'],
    validate: {
      query: Joi.object({
        email: Joi.string(),
        username: Joi.string(),
      }),
    },
    response: {
      status: {
        200: Joi.object({
          status: Joi.string(),
          data: {
            id: Joi.string(),
            username: Joi.string(),
            fullname: Joi.string(),
            email: Joi.string(),
          },
        }),
        400: undefined,
        401: undefined,
      },
    },
  },
};

module.exports = doc;
