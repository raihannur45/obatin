const Joi = require('joi');
const { joiPassword } = require('joi-password');

const doc = {
  postPredictDoc: {
    auth: 'obatin_api_jwt',
    description: 'Talk to BOT',
    notes: 'This is endpoint where you can talk with Obatin BOT. Payload only contains text field, after you fill text field and send request to server, if success, server will response 200 and data contains response from BOT, else server will response 400 if you send bad payload or server will response 401 that mean you need to login first',
    tags: ['api', 'bot'],
    validate: {
      payload: Joi.object({
        text: Joi.string().required(),
      }),
    },
    response: {
      status: {
        200: Joi.object({
          status: Joi.string(),
          data: {
            response: Joi.object({
              label: Joi.string(),
              namaobat: Joi.string().allow(''),
              status: Joi.number(),
            }),
          },
        }),
        400: undefined,
        401: undefined,
      },
    },
  },
  postGetResponseDoc: {
    auth: 'obatin_api_jwt',
    description: 'Talk to BOT',
    notes: 'This endpoint similar to /respon and payload only contains label field',
    tags: ['api', 'bot'],
    validate: {
      payload: Joi.object({
        label: joiPassword.string().noWhiteSpaces().required(),
      }),
    },
    response: {
      status: {
        200: Joi.object({
          status: Joi.string(),
          data: {
            response: Joi.object({
              jawab: Joi.string(),
            }),
          },
        }),
        400: undefined,
        401: undefined,
      },
    },
  },
  postGetMessageDoc: {
    auth: 'obatin_api_jwt',
    description: 'Talk to BOT',
    notes: 'This endpoint similar to /database, payload only contains label and namaobat field',
    tags: ['api', 'bot'],
    validate: {
      payload: Joi.object({
        label: joiPassword.string().noWhiteSpaces().required(),
        namaobat: Joi.string().required(),
      }),
    },
    response: {
      status: {
        200: Joi.object({
          status: Joi.string(),
          data: {
            response: Joi.object({
              jawab: Joi.string(),
            }),
          },
        }),
        400: undefined,
        401: undefined,
      },
    },
  },
};

module.exports = doc;
