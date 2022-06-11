const routes = (handler, doc) => [
  {
    method: 'POST',
    path: '/bot',
    handler: handler.postPredictHandler,
    options: doc.postPredictDoc,
  },
  {
    method: 'POST',
    path: '/bot/message',
    handler: handler.postGetResponseHandler,
    options: doc.postGetResponseDoc,
  },
  {
    method: 'POST',
    path: '/bot/message/reply',
    handler: handler.postGetMessageHandler,
    options: doc.postGetMessageDoc,
  },
];

module.exports = routes;
