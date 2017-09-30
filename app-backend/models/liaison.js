//SCHEMA FOR LIAISON PROFILES

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var LiaisonSchema = new Schema({
  name: String,
  phone: String,
  email: String,
  institution: String,
  position: String,
  brief: String,
  picUrl: String
});

module.exports = mongoose.model('Liaison', LiaisonSchema);
