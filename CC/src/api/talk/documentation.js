const Joi = require('joi');
//
const doc = {
  postTalkDoc: {
    auth: 'obatin_api_jwt',
    description: 'Talk to BOT',
    notes: 'This is endpoint where you can talk with Obatin BOT. Payload only contains message field, after you fill message field and send request to server, server will response 200 and data contains response from BOT.',
    tags: ['api', 'talk'],
    validate: {
      payload: Joi.object({
        message: Joi.string().required(),
      }),
    },
    response: {
      status: {
        200: Joi.object({
          status: Joi.string(),
          data: {
            response: Joi.string(),
          },
        }),
        400: undefined,
        401: undefined,
      },
    },
  },
};

module.exports = doc;
