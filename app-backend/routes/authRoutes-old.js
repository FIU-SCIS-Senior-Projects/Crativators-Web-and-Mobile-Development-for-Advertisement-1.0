//OLD FILE, PRESENT FOR BACKUP
//Routes for authenticating users

var config = require('../../config');
var jwt = require('jsonwebtoken');
var User = require('../models/user');

exports.authentication = function(req, res) {

  User.findOne({
    username: req.body.username
  }).select('name username password').exec(function(err, user) {

    if(err) throw err;

    //if username not found
    if(!user) {
      res.json({
        success: false,
        message: 'Authentication failed, user not found.'
      });
    } else {

      //check if given password matches stored password
      var validPassword = user.comparePassword(req.body.password);
      if(!validPassword) {
        res.json({
          success: false,
          message: 'Authentication failed, wrong password.'
        });
      } else {

        //user has been found and password is correct, so create token
        var token = jwt.sign({
          name: user.name,
          username: user.username
        }, config.secret, {
          expiresIn: '24h'  //token expires in 24 hours
        });

        //return information, including token
        res.json({
          success: true,
          message: 'Authentication success, token passed.',
          token: token
        });
      }
    }
  });
};

exports.checkToken = function(req, res, next) {
  //console.log('Someone came to our app!');

  //check for a token
  var token = req.body.token || req.query.token || req.headers['x-access-token'];

  if(token) {

    jwt.verify(token, config.secret, function(err, decoded) {
      if(err) {
        return res.status(403).send({
          success: false,
          message: 'Failed to authenticate token.'
        });
      } else {
        //save token for use in other routes

        console.log(decoded.name);
        req.decoded = decoded;

        next();
      }
    });

  } else {

    //if there is no token, return 403
    return res.status(403).send({
      success: false,
      message: 'No token provided.'
    });
  }
};
