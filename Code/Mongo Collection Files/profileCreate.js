var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/pospathways";

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  db.createCollection("profiles", function(err, res) {
    if (err) throw err;
    console.log("Collection created!");
    db.close();
  });
});