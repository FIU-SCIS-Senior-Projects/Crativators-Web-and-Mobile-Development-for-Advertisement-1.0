//SCHEMA FOR NEWS FEED ITEM

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var MeetingSchema = new Schema({
  title: String,
  agenda: String,
  minutes: String,
  past: { type: Boolean, default: false},
  date: String //The date this item was created or last modified.
});

module.exports = mongoose.model('Meeting', MeetingSchema);
