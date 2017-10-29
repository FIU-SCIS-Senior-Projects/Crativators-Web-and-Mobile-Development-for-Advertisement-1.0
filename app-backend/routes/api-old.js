//OLD FILE, PRESENT FOR BACKUP
//ROUTES FOR THE API

//initializing variables
var Profile = require('../models/profile');
var Meeting = require('../models/meeting');
var User = require('../models/user');
var Program = require('../models/Program');

//route subfiles
var profileRoutes = require('./profileRoutes');
var meetingRoutes = require('./meetingRoutes');
var userRoutes = require('./userRoutes');
var programRoutes = require('./programRoutes');
var authRoutes = require('./authRoutes');

module.exports = function(app, express) {

  var apiRouter = express.Router();

  //Test Route
  apiRouter.get('/', function(req, res) {
    res.json({ message: 'Stay positive.'});
  });

  //AUTHENTICATION ROUTE
  apiRouter.post('/authenticate', authRoutes.authentication);

  apiRouter.use(authRoutes.checkToken);

  //User Account Routing
  apiRouter.route('/users')

    //create a new user
    .post(userRoutes.create)

    //loading the list of users
    .get(userRoutes.list);

  apiRouter.route('/users/:user_id')

    //get the user with given id
    .get(userRoutes.retrieve)

    //activate user account
    .post(userRoutes.activate)

    //update the user with given id
    .put(userRoutes.modify)

    //delete the user with given id
    .delete(userRoutes.expunge);

  //Profile Routing
  apiRouter.route('/profiles')

    //create a new profile
    .post(profileRoutes.create)

    //loading the list of profiles
    .get(profileRoutes.list);

  apiRouter.route('/profiles/:profile_id')

    //get the profile with given id
    .get(profileRoutes.retrieve)

    //update the profile with given id
    .put(profileRoutes.modify)

    //delete the profile with given id
    .delete(profileRoutes.expunge);

  //Meeting Routing
  apiRouter.route('/meetings')

    //create a meeting
    .post(meetingRoutes.create)

    //loading the list of meetings
    .get(meetingRoutes.list);

  apiRouter.route('/meetings/:meeting_id')

    //get the meeting with given id
    .get(meetingRoutes.retrieve)

    //update the meeting with given id
    .put(meetingRoutes.modify)

    //delete the meeting with given id
    .delete(meetingRoutes.expunge);

  //Program Routing
  apiRouter.route('/programs')

    //create a program
    .post(programRoutes.create)

    //loading the list of programs
    .get(programRoutes.list);

  apiRouter.route('/programs/:program_id')

    //get the program with given id
    .get(programRoutes.retrieve)

    //update the program with given id
    .put(programRoutes.modify)

    //delete the program with given id
    .delete(programRoutes.expunge);



    //MODIFYING AND DELETING ENTRIES IN THE DATABASE
    //REQUIRES AN ADMIN USER ACCOUNT



  return apiRouter;
};
