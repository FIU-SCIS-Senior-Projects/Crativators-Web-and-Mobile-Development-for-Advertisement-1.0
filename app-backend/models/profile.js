//SCHEMA FOR PROFILE ENTRIES

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ProfileSchema = new Schema({
  name: String,
  phone: String,
  type: String,
  email: String,
  institution: String,
  position: String,
  brief: String,
  picUrl: String
});

module.exports = mongoose.model('Profile', ProfileSchema);
