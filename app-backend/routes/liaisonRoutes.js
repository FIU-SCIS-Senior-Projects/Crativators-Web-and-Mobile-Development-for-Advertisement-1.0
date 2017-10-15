var Liaison =  require('mongoose').model('Liaison');

//Creating new liaison profile
exports.create = function(req, res) {

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

//Retrieving the list of liaisons
exports.list = function(req, res) {
  Liaison.find(function (err, liaisons) {
    if(err) res.send(err);

    res.json(liaisons);
  });
};

//Retrieving a specific liaison profile
exports.retrieve = function(req, res) {
  Liaison.findById(req.params.liaison_id, function(err, liaison) {
    if(err) res.send(err);

    //return that liaison
    res.json(liaison);
  });
};

//Modifying a liaison profile
exports.modify = function(req, res) {
  Liaison.findById(req.params.liaison_id, function(err, liaison) {

    if(err) res.send(err);

    if(req.body.name) liaison.name = req.body.name;
    if(req.body.phone) liaison.phone = req.body.phone;
    if(req.body.email) liaison.email = req.body.email;
    if(req.body.institution) liaison.institution = req.body.institution;
    if(req.body.position) liaison.position = req.body.position;
    if(req.body.brief) liaison.brief = req.body.brief;
    if(req.body.picUrl) liaison.picUrl = req.body.picUrl;

    liaison.save(function(err) {
      if(err) res.send(err);

      //return a message
      res.json({ _id: liaison._id, message: 'Liaison updated!'});
    });
  });
};

//Removing a liaison profile
exports.expunge = function(req, res) {
  Liaison.remove({
    _id: req.params.liaison_id
  }, function(err, char) {
    if(err) res.send(err);

    res.json({ message: 'Successfully deleted' });
  });
};
