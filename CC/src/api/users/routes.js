const routes = (handler, doc) => [
  {
    method: 'POST',
    path: '/users',
    handler: handler.postUserHandler,
    options: doc.postUsersDoc,
  },
  {
    method: 'GET',
    path: '/users/profiles',
    handler: handler.getUserHandler,
    options: doc.getUserDoc,
  },
];

module.exports = routes;
