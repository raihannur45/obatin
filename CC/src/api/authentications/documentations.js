const Joi = require('joi');
const { joiPassword } = require('joi-password');
//
const doc = {
  postAuthenticationDoc: {
    description: 'User login',
    notes: 'This endpoint used for user login. Payload contains username and password field, you can fill of username with username or email, after you fill payload and send request to server, server will response 201 and data contains accessToken and refreshToken.',
    tags: ['api', 'authentications'],
    validate: {
      payload: Joi.object({
        username: Joi.string().trim().min(3).required(),
        password: joiPassword
          .string()
          .required(),
      }),
    },
    response: {
      status: {
        201: Joi.object({
          status: Joi.string(),
          message: Joi.string(),
          data: {
            accessToken: Joi.string(),
            refreshToken: Joi.string(),
          },
        }),
        400: undefined,
        401: undefined,
      },
    },
  },
  putAuthenticationDoc: {
    description: 'Update access token',
    notes: 'This endpoint used for update access token, example when accessToken has expired. Payload contain refreshToken field, after you fill payload and send request to server, server will response 200 and data contains new accessToken.',
    tags: ['api', 'authentications'],
    validate: {
      payload: Joi.object({
        refreshToken: Joi.string().required(),
      }),
    },
    response: {
      status: {
        200: Joi.object({
          status: Joi.string(),
          message: Joi.string(),
          data: {
            accessToken: Joi.string(),
          },
        }),
        400: undefined,
      },
    },
  },
  deleteAuthenticationDoc: {
    description: 'User logout',
    notes: 'This endpoint used when user logout. Payload contains refreshToken fields, after you fill payload and send request to server, server will response 200 with status success.',
    tags: ['api', 'authentications'],
    validate: {
      payload: Joi.object({
        refreshToken: Joi.string().required(),
      }),
    },
    response: {
      status: {
        200: Joi.object({
          status: Joi.string(),
          message: Joi.string(),
        }),
        400: undefined,
      },
    },
  },
};

module.exports = doc;
