//Routes for authenticating users

var config = require('../../config');
var jwt = require('jsonwebtoken');
var User = require('../models/user');

exports.authentication = function(req, res) {

  User.findOne({
    username: req.body.username
  }).select('type active username password').exec(function(err, user) {

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
      } else if(user.active) {  //if account is not active,

        //user has been found and password is correct, so create token
        var token = jwt.sign({
          type: user.type,
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
      } else {
          res.json({
            success: false,
            message: 'Authentication failed, account not active.'
          });
      }
    }
  });
};

//Function that gives an integer value to the priority of user accounts.
//Members have a value of 1, Moderators have a value of 2,
//Administrators have a value of 3.
determineType = function(userType) {
  if(userType.valueOf() == new String('Member').valueOf())
    return 1;
  if(userType.valueOf() == new String('Moderator').valueOf())
    return 2;
  if(userType.valueOf() == new String('Administrator').valueOf())
    return 3;

  return -1;
}

//Checks if the account is at least Member level before continuing.
exports.checkMember = function(req, res, next) {
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
          if(determineType(decoded.type) > 0) {
            //save token for use in other routes
            req.decoded = decoded;

            next();
          }
          else {
          return res.status(403).send({
            success: false,
            message: 'This account is not authorized for this action.'
          });
        }
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

//Checks if the account is at least Moderator level before continuing.
//Virtually similar to previous function.
exports.checkModerator = function(req, res, next) {
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
          if(determineType(decoded.type) > 1) {
            //save token for use in other routes
            req.decoded = decoded;

            next();
          }
          else {
          return res.status(403).send({
            success: false,
            message: 'This account is not authorized for this action.'
          });
        }
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

//Checks if the account is at least Moderator level before continuing.
//Virtually similar to previous function.
exports.checkAdministrator = function(req, res, next) {
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
          if(determineType(decoded.type) > 2) {
            //save token for use in other routes
            req.decoded = decoded;

            next();
          }
          else {
          return res.status(403).send({
            success: false,
            message: 'This account is not authorized for this action.'
          });
        }
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
