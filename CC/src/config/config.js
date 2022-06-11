require('dotenv').config();

module.exports = {
  development: {
    // database di setup disini jika pake environment 'development'
    username: 'root',
    password: '',
    database: 'mydb',
    host: '127.0.0.1',
    dialect: 'mysql',
  },
  production: {
    username: process.env.DB_USERNAME,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_DATABASE,
    host: process.env.DB_HOST,
    dialect: process.env.DB_DIALECT,
  },
};
