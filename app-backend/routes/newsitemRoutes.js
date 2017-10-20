//Database functions for news items.

var NewsItem = require('mongoose').model('NewsItem');

//creating a news item
exports.create = function(req, res) {

  var item = new NewsItem();

  if(req.body.title)
    item.title = req.body.title;
  else
    item.title = "(News Item Title)";

  if(req.body.body)
    item.body = req.body.body;
  else
    item.body = "(Post Text Body)";

  item.date = Date("<YYYY-mm-ddTHH:MM:ss>");

  item.save(function(err) {
    if(err) res.send(err);

    //return a message
    res.json({ _id: item._id, message: 'News Item created!'});
  });
};

//Retrieving the list of news items
exports.list = function(req, res) {
  NewsItem.find(function (err, items) {
    if(err) res.send(err);

    res.json(items);
  });
};

//Retrieving a specific news item
exports.retrieve = function(req, res) {
  NewsItem.findById(req.params.item_id, function(err, item) {
    if(err) res.send(err);

    //return that liaison
    res.json(item);
  });
};

//Modifying a news item
exports.modify = function(req, res) {
  NewsItem.findById(req.params.item_id, function(err, item) {

    if(err) res.send(err);

    var change = false;

    if(req.body.title) {
      item.title = req.body.title;
      change = true;
    }
    if(req.body.body) {
      item.body = req.body.body;
      change = true;
    }
    if(change)
      item.date = Date("<YYYY-mm-ddTHH:MM:ss>");

    item.save(function(err) {
      if(err) res.send(err);

      //return a message
      res.json({ _id: item._id, message: 'News Item updated!'});
    });
  });
};

//Removing a news item
exports.expunge = function(req, res) {
  NewsItem.remove({
    _id: req.params.item_id
  }, function(err) {
    if(err) res.send(err);

    res.json({ message: 'Successfully deleted News Item.' });
  });
};
