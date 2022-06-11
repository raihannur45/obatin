const { Model } = require('sequelize');

module.exports = (sequelize, DataTypes) => {
  class Users extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  }
  Users.init({
    id: {
      type: DataTypes.STRING,
      primaryKey: true,
    },
    email: DataTypes.STRING,
    password: DataTypes.TEXT,
    username: DataTypes.STRING,
    fullname: DataTypes.TEXT,
  }, {
    sequelize,
    modelName: 'users',
  });
  return Users;
};
