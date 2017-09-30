var Liaison =  require('mongoose').model('Liaison');

exports.create = function(req, res) {

  //Creating new liaison profile
  var liaison = new Liaison();
  liaison.name = "(Name)";
  liaison.phone = "(Phone Number)";
  liaison.email = "(Email)";
  liaison.institution = "(Institution)";
  liaison.position = "(Position)";
  liaison.brief = "(Liaison Brief)";
  liaison.picUrl = "(Picture URL here)";

  liaison.save(function(err) {
    if(err) res.send(err);

    //return a message
    res.json({ _id: liaison._id, message: 'Liaison profile created!'});
  });
};

exports.list = function(req, res) {
  Character.find(function (err, liaisons) {
    if(err) res.send(err);

    res.json(liaisons);
  });
};

exports.retrieve = function(req, res) {
  Liaison.findById(req.params.char_id, function(err, char) {
    if(err) res.send(err);

    //return that character
    res.json(char);
  });

  //NEEDS MORE
};
