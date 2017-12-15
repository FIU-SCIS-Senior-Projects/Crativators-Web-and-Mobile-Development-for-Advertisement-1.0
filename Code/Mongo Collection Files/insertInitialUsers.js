var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/ppathways";

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var myobj = [
	{name: 'SampleAdmin',type: '3', active: true,username: 'SampleAdmin',password: 'administrator'},
	{name: 'SampleMod',type: '2', active: true,username: 'SampleMod',password: 'moderator'},
	{name: 'SampleMember',type: '1', active: true,username: 'SampleMember',password: 'member'}
	]
  db.collection("User").insertMany(myobj, function(err, res) {
    if (err) throw err;
    console.log("Number of documents inserted: " + res.insertedCount);
    db.close();
  });
});