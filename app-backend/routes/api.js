//ROUTES FOR THE API

//initializing variables
var Liaison = require('../models/liaison');

//route subfiles
//var liaisonRoutes = require('./liaisonRoutes');

module.exports = function(app, express) {

  var apiRouter = express.Router();

  apiRouter.use(function(req, res, next) {
    console.log('Someone came to our app!');
    next();
  });

  //Test Route
  apiRouter.get('/', function(req, res) {
    res.json({ message: 'Stay positive.'});
  });

  return apiRouter;
};
