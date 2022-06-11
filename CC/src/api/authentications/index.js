const routes = require('./routes');
const AuthenticationsHandler = require('./handler');
const doc = require('./documentations');

module.exports = {
  name: 'authentications',
  version: '1.0',
  register: async (server, {
    authenticationService, userService, tokenManager, validator,
  }) => {
    const authenticationsHandler = new AuthenticationsHandler(
      authenticationService,
      userService,
      tokenManager,
      validator,
    );
    server.route(routes(authenticationsHandler, doc));
  },
};
