const routes = require('./routes');
const MlHandler = require('./handler');
const doc = require('./documentations');

module.exports = {
  name: 'ml', // nama plugin
  version: '1.0', // versi
  register: async (server, { validator, url }) => {
    // membuat instance talkHander dengan parameter service dan payload
    const mlHandler = new MlHandler(validator, url);
    // mendaftarkan route, fungsi routes berisikan handler dan dokumentasi
    server.route(routes(mlHandler, doc));
  },
};
