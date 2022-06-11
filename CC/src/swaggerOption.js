const swaggerOption = {
  info: {
    title: 'Obatin API Documentation',
    version: '1.0',
    description: 'Obatin REST API documentation. This documentation based on OpenAPI specification with Swagger as user interface.',
    contact: {
      name: 'Me',
      email: 'rustwell77@gmail.com',
    },
  },
  grouping: 'tags',
  basePath: '/',
  tags: [
    /**
    {
      name: 'talk',
      description: 'This talk tag',
    },
    */
    {
      name: 'authentications',
      description: 'This authentication tag',
    },
    {
      name: 'users',
      description: 'This users tag',
    },
    {
      name: 'bot',
      description: 'This bot tag',
    },
  ],
  payloadType: 'json',
  documentationPath: '/docs',
  schemes: ['http'],
  cors: true,
  securityDefinitions: {
    Bearer: {
      type: 'Bearer',
      name: 'Authorization',
      in: 'header',
    },
  },
};

module.exports = swaggerOption;
