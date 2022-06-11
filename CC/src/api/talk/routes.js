const routes = (handler, doc) => [
  {
    method: 'POST',
    path: '/talk',
    handler: handler.postTalkHandler, // load handler by passing parameter handler
    options: doc.postTalkDoc, // load dokumentasi by passing parameter doc
  },
];

module.exports = routes;
