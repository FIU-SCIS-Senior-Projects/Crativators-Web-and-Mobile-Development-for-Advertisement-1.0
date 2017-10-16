//ROUTES FOR THE API

//initializing variables
var Liaison = require('../models/liaison');
var NewsItem = require('../models/newsitem');

//route subfiles
var liaisonRoutes = require('./liaisonRoutes');
var newsitemRoutes = require('./newsitemRoutes');

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

  apiRouter.route('/liaisons/:liaison_id')

    //get the liaison with given id
    .get(liaisonRoutes.retrieve)

    //update the liaison with given id
    .put(liaisonRoutes.modify)

    //delete the liaison with given id
    .delete(liaisonRoutes.expunge);

  //News Item Routing
  apiRouter.route('/newsitem')

    //create a news item
    .post(newsitemRoutes.create)

    //loading the list of news items
    .get(newsitemRoutes.list);

  apiRouter.route('/newsitem/:item_id')

  //get the news item with given id
  .get(newsitemRoutes.retrieve)

  //update the news item with given id
  .put(newsitemRoutes.modify)

  //delete the news item with given id
  .delete(newsitemRoutes.expunge);

  return apiRouter;
};
