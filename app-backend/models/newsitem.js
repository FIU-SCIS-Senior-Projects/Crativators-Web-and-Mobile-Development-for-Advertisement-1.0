//SCHEMA FOR NEWS FEED ITEM

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var NewsItemSchema = new Schema({
  title: String,
  body: String,
  date: String //The date this item was created or last modified.
});

module.exports = mongoose.model('NewsItem', NewsItemSchema);
