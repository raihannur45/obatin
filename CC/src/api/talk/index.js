const routes = require('./routes');
const TalkHandler = require('./handler');
const doc = require('./documentation');

module.exports = {
  name: 'Talk', // nama plugin
  version: '1.0', // versi
  register: async (server, { service, validator }) => {
    // membuat instance talkHander dengan parameter service dan payload
    const talkHandler = new TalkHandler(service, validator);
    // mendaftarkan route, fungsi routes berisikan handler dan dokumentasi
    server.route(routes(talkHandler, doc));
  },
};
