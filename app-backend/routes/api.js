//ROUTES FOR THE API

//initializing variables
var Liaison = require('../models/liaison');

//route subfiles
var liaisonRoutes = require('./liaisonRoutes');

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

  //Liaison Routing
  apiRouter.route('/liaisons')

    //create a new liaison
    .post(liaisonRoutes.create)

    //loading the list of liaisons
    .get(liaisonRoutes.list);

  apiRouter.route('liaisons/:liaison_id')

    //get the liaison with given id
    .get(liaisonRoutes.retrieve)

    //update the liaison with given id
    .put(liaisonRoutes.modify)

    //delete the liaison with given id
    .delete(liaisonRoutes.expunge);

  return apiRouter;
};
