//Database functions for user accounts

var User = require('mongoose').model('User');

//Create the user accounts
exports.create = function(req, res) {

  var user = new User();

  //setting user information given by request
  user.name = req.body.name;
  user.type = req.body.type;
  user.username = req.body.username;
  user.password = req.body.password;

  user.save(function(err) {
    if(err) {
      if(err.code == 11000) {
        return res.json({ success: false, message: 'That username already exists.'});
      } else {
        res.send(err);
        }
    }

    //return a message
    res.json({ _id: user._id, message: 'User account created!'});
  });
};

//Retrieving the list of user accounts
exports.list = function(req, res) {
  User.find(function (err, users) {
    if(err) res.send(err);

    res.json(users);
  });
};

//Retrieving a specific user account
exports.retrieve = function(req, res) {
  User.findById(req.params.user_id, function(err, user) {
    if(err) res.send(err);

    //return that liaison
    res.json(user);
  });
};

exports.listInactive = function(req, res) {
  User.find({ active: false}, function(err, users) {
    if(err) res.send(err);

    res.json(users);
  });
};

//Modifying a user account
exports.modify = function(req, res) {
  User.findById(req.params.user_id, function(err, user) {

    if(err) res.send(err);

    //setting user information given by request
    if(req.body.name) user.name = req.body.name;
    if(req.body.type) user.type = req.body.type;
    if(req.body.active) user.active = req.body.active;
    if(req.body.username) user.username = req.body.username;
    if(req.body.password) user.password = req.body.password;
    if(req.body.profile_id) user._profileId = req.body.profile_id;
    if(req.body.program_id) user._programId = req.body.program_id;

    user.save(function(err) {
      if(err) res.send(err);

      //return a message
      res.json({ _id: user._id, message: 'User account updated!'});
    });
  });
};

exports.activate = function(req, res) {
  User.findById(req.params.user_id, function(err, user) {

    if(err) res.send(err);

    //Activates the user account.
    user.active = true;

    user.save(function(err) {
      if(err) res.send(err);

      //return a message
      res.json({ _id: user._id, message: 'User account activated!'});
    });
  });
};

//Removing a user account
exports.expunge = function(req, res) {
  User.remove({
    _id: req.params.user_id
  }, function(err) {
    if(err) res.send(err);

    res.json({ message: 'Successfully deleted user account.' });
  });
};
