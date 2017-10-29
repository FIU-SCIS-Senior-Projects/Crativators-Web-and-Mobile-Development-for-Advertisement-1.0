//Database functions for profiles.

var Profile =  require('mongoose').model('Profile');

//Creating new profile
exports.create = function(req, res) {

  var profile = new Profile();

  if(req.body.name)
    profile.name = req.body.name;
  else
    profile.name = "(Name)";

  if(req.body.phone)
    profile.phone = req.body.phone;
  else
    profile.phone = "(Phone Number)";

  if(req.body.email)
    profile.email = req.body.email;
  else
    profile.email = "(Email)";

  if(req.body.institution)
    profile.institution = req.body.institution;
  else
    profile.institution = "(Institution)";

  if(req.body.position)
    profile.position = req.body.position;
  else
    profile.position = "(Position)";

  if(req.body.brief)
    profile.brief = req.body.brief;
  else
    profile.brief = "(Profile Brief)";

  if(req.body.picUrl)
    profile.picUrl = req.body.picUrl;
  else
    profile.picUrl = "(Picture URL here)";

  profile.save(function(err) {
    if(err) res.send(err);

    //return a message
    res.json({ _id: profile._id, message: 'Profile created!'});
  });
};

//Retrieving the list of profiles
exports.list = function(req, res) {
  Profile.find(function (err, profiles) {
    if(err) res.send(err);

    res.json(profiles);
  });
};

exports.listMembers = function(req, res) {
  User.find({ type: 'Member' }, function(err, members) {
    if(err) res.send(err);

    res.json(members);
  });
};

exports.listPartners = function(req, res) {
  User.find({ type: 'Partner' }, function(err, partners) {
    if(err) res.send(err);

    res.json(partners);
  });
};

//Retrieving a specific profile
exports.retrieve = function(req, res) {
  Profile.findById(req.params.profile_id, function(err, profile) {
    if(err) res.send(err);

    //return that profile
    res.json(profile);
  });
};

//Modifying a profile
exports.modify = function(req, res) {
  Profile.findById(req.params.profile_id, function(err, profile) {

    if(err) res.send(err);

    if(req.body.name) profile.name = req.body.name;
    if(req.body.phone) profile.phone = req.body.phone;
    if(req.body.email) profile.email = req.body.email;
    if(req.body.institution) profile.institution = req.body.institution;
    if(req.body.position) profile.position = req.body.position;
    if(req.body.brief) profile.brief = req.body.brief;
    if(req.body.picUrl) profile.picUrl = req.body.picUrl;

    profile.save(function(err) {
      if(err) res.send(err);

      //return a message
      res.json({ _id: profile._id, message: 'Profile updated!'});
    });
  });
};

//Removing a profile
exports.expunge = function(req, res) {
  Profile.remove({
    _id: req.params.profile_id
  }, function(err) {
    if(err) res.send(err);

    res.json({ message: 'Successfully deleted profile.' });
  });
};
