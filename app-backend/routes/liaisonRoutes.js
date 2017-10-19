//Database functions for liaison profiles.

var Liaison =  require('mongoose').model('Liaison');

//Creating new liaison profile
exports.create = function(req, res) {

  var liaison = new Liaison();

  if(req.body.name)
    liaison.name = req.body.name;
  else
    liaison.name = "(Name)";

  if(req.body.phone)
    liaison.phone = req.body.phone;
  else
    liaison.phone = "(Phone Number)";

  if(req.body.email)
    liaison.email = req.body.email;
  else
    liaison.email = "(Email)";

  if(req.body.institution)
    liaison.institution = req.body.institution;
  else
    liaison.institution = "(Institution)";

  if(req.body.position)
    liaison.position = req.body.position;
  else
    liaison.position = "(Position)";

  if(req.body.brief)
    liaison.brief = req.body.brief;
  else
    liaison.brief = "(Liaison Brief)";

  if(req.body.picUrl)
    liaison.picUrl = req.body.picUrl;
  else
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
  }, function(err) {
    if(err) res.send(err);

    res.json({ message: 'Successfully deleted Liaison profile.' });
  });
};
