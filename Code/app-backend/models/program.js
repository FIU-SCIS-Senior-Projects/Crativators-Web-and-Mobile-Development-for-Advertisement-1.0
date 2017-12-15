//SCHEMA FOR PROGRAM ENTRIES

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ProgramSchema = new Schema({
  name: String,
  brief: String,
  picUrl: String
});

module.exports = mongoose.model('Program', ProgramSchema);
