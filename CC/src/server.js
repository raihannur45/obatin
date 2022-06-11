require('dotenv').config();

const Hapi = require('@hapi/hapi');

// plugin untuk memuat swagger, Inert dan vision digunakan untuk memuat ui
const Inert = require('@hapi/inert');
const Vision = require('@hapi/vision');
const HapiSwagger = require('hapi-swagger');

const Jwt = require('@hapi/jwt');

// memuat custom exceptions
const ClientError = require('./exceptions/ClientError');

// Deskripsi swagger
const swaggerOption = require('./swaggerOption');

// memuat plugin talk, memanggil class service NlpService, dan validator

// const talk = require('./api/talk');
// const NlpService = require('./services/nlp/NlpService');
// const TalkValidator = require('./validator/talk');

const users = require('./api/users');
const UserService = require('./services/postgres/UsersService');
const MemcachedService = require('./services/memchached/MemchachedService');
const UserValidator = require('./validator/users');

const authentications = require('./api/authentications');
const AuthenticationService = require('./services/postgres/AuthenticationService');
const TokenManager = require('./tokenize/TokenManager');
const AuthenticationsValidator = require('./validator/authentications');
const AuthenticationError = require('./exceptions/AuthenticationError');

const ml = require('./api/ml');
const MlValidator = require('./validator/ml');

const url = process.env.ML_API;

// Direktori model
// const botDir = `${__dirname}/bot`;

(async () => {
  const date = new Date().toLocaleString('en-US', {
    timeZone: 'Asia/Jakarta',
  });
  const currentHour = date.slice(10, 17);

  // membuat instance dari Class NlpService dengan parameter direktori model (botDir)
  //  const nlpService = new NlpService(botDir);
  const memcachedService = new MemcachedService();
  const userService = new UserService(memcachedService);
  const authenticationService = new AuthenticationService();

  // Konfigurasi server
  const server = Hapi.server({
    host: process.env.HOST || 'localhost', // host, prod 0.0.0.0 set ke env
    port: process.env.PORT || 5000, // port
    routes: {
      cors: {
        origin: ['*'], // menetapkan cors agar tidak terjadi error di front-end
      },
    },
  });

  // menambahkan extension sebelum server meresponse
  server.ext('onPreResponse', (request, h) => {
    const { response } = request;
    // jika response menidikasikan ClientError
    if (response instanceof ClientError) {
      if (response instanceof AuthenticationError) {
        // response dengan statusCode 401
        return h.response({
          status: 'Unauthorized',
          message: response.message,
          data: {},
        }).code(response.statusCode); // 401
      }
      // response dengan statusCode 400
      return h.response({
        status: 'Bad Request',
        message: response.message,
        data: {},
      }).code(response.statusCode); // 400
    }
    if (response.isServer) {
      console.log(`Error : ${response.message}`);
      return h.response({
        status: 'Server Error',
        message: 'An internal server error occurred',
      }).code(500); // 500
    }
    // meneruskan response
    return response.continue || response;
  });

  // Mendaftarkan plugin Inert, Vision, dan hapi-swagger
  await server.register([
    Inert,
    Vision,
    {
      plugin: HapiSwagger,
      options: swaggerOption,
    },
    {
      plugin: Jwt,
    },
  ]);

  server.auth.strategy('obatin_api_jwt', 'jwt', {
    keys: process.env.ACCESS_TOKEN_KEY,
    verify: {
      aud: false,
      iss: false,
      sub: false,
      maxAgeSec: process.env.ACCESS_TOKEN_AGE,
    },
    validate: (artifacts) => ({
      isValid: true,
      credentials: {
        id: artifacts.decoded.payload.id,
      },
    }),
  });

  // Mendaftarkan custom plugin, yaitu talk dengan option service, dan validator
  await server.register([
    /**    {
      plugin: talk,
      options: {
        service: nlpService,
        validator: TalkValidator,
      },
    },
    */
    {
      plugin: users,
      options: {
        service: userService,
        validator: UserValidator,
      },
    },
    {
      plugin: authentications,
      options: {
        authenticationService,
        userService,
        tokenManager: TokenManager,
        validator: AuthenticationsValidator,
      },
    },
    {
      plugin: ml,
      options: {
        validator: MlValidator,
        url,
      },
    },
  ]);

  server.events.on('response', (request) => {
    console.log(`${currentHour} | ${request.response.statusCode} | ${request.info.remoteAddress} : ${request.method.toUpperCase()} ${request.path}`);
  });

  // Server dimulai
  console.log(`using ${process.env.NODE_ENV} environment`);
  console.log(`server running on ${server.info.uri}`);
  await server.start();
})();
