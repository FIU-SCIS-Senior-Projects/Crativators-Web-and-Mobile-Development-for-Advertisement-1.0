//ROUTES FOR THE API

//initializing variables
var Liaison = require('../models/liaison');
var NewsItem = require('../models/newsitem');
var User = require('../models/user');

//route subfiles
var liaisonRoutes = require('./liaisonRoutes');
var newsitemRoutes = require('./newsitemRoutes');
var userRoutes = require('./userRoutes');

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

  //User Account Routing
  apiRouter.route('/users')

    //create a new user
    .post(userRoutes.create)

    //loading the list of users
    .get(userRoutes.list);

  apiRouter.route('/users/:user_id')

    //get the user with given id
    .get(userRoutes.retrieve)

    //update the user with given id
    .put(userRoutes.modify)

    //delete the user with given id
    .delete(userRoutes.expunge);

  //Liaison Routing
  apiRouter.route('/liaisons')

    //create a new liaison
    .post(liaisonRoutes.create)

    //loading the list of liaisons
    .get(liaisonRoutes.list);

  apiRouter.route('/liaisons/:liaison_id')

    //get the liaison with given id
    .get(liaisonRoutes.retrieve)

    //update the liaison with given id
    .put(liaisonRoutes.modify)

    //delete the liaison with given id
    .delete(liaisonRoutes.expunge);

  //News Item Routing
  apiRouter.route('/newsitems')

    //create a news item
    .post(newsitemRoutes.create)

    //loading the list of news items
    .get(newsitemRoutes.list);

  apiRouter.route('/newsitems/:item_id')

    //get the news item with given id
    .get(newsitemRoutes.retrieve)

    //update the news item with given id
    .put(newsitemRoutes.modify)

    //delete the news item with given id
    .delete(newsitemRoutes.expunge);

  return apiRouter;
};
