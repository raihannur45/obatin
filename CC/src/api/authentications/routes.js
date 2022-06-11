const routes = (handler, doc) => [
  {
    method: 'POST',
    path: '/auth',
    handler: handler.postAuthenticationHandler,
    options: doc.postAuthenticationDoc,
  },
  {
    method: 'PUT',
    path: '/auth',
    handler: handler.putAuthenticationHandler,
    options: doc.putAuthenticationDoc,
  },
  {
    method: 'DELETE',
    path: '/auth',
    handler: handler.deleteAuthenticationHandler,
    options: doc.deleteAuthenticationDoc,
  },
];

module.exports = routes;
