'use strict';
const { Model } = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class authentications extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  }
  authentications.init({
    token: DataTypes.TEXT,
  }, {
    updatedAt: false,
    sequelize,
    modelName: 'authentications',
  });
  authentications.removeAttribute('id');
  return authentications;
};
