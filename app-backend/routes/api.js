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

    //ROUTES THAT DO NOT REQUIRE AN ACCOUNT OR AUTHORIZATION

    //creating a user
    apiRouter.route('/users').post(userRoutes.create);

    //loading the list of profiles
    apiRouter.route('/profiles').get(profileRoutes.list);

    //loading the list of member profiles
    apiRouter.route('/profiles/members').get(profileRoutes.listMembers);

    //loading the list of member profiles
    apiRouter.route('/profiles/partners').get(profileRoutes.listPartners);

    //get the profile with given id
    apiRouter.route('/profiles/:profile_id').get(profileRoutes.retrieve);

    //loading the list of meetings
    apiRouter.route('/meetings').get(meetingRoutes.list);

    //get the meeting with given id
    apiRouter.route('/meetings/:meeting_id').get(meetingRoutes.retrieve);

    //loading the list of programs
    apiRouter.route('/programs').get(programRoutes.list);

    //get the program with given id
    apiRouter.route('/programs/:program_id').get(programRoutes.retrieve);

  //ROUTE TO CHECK FOR MEMBER (OR ABOVE) ACCOUNT
  apiRouter.use(authRoutes.checkMember);

    //ROUTES THAT REQUIRE (AT LEAST) A MEMBER ACCOUNT

    //modifying an account with given id
    apiRouter.route('/users/:user_id').put(userRoutes.modify);

    //get the account with given id
    //must match the user's own account, or user must be a moderator/admin
    apiRouter.route('/users/:user_id').get(userRoutes.retrieve);

    //create a meeting listing
    apiRouter.route('/meetings').post(meetingRoutes.create);

    //update the meeting with given id
    apiRouter.route('/meetings/:meeting_id').put(meetingRoutes.modify);

    //update the program with given id
    //must match institution of user's associated profile, or user must be a moderator/admin
    apiRouter.route('/programs/:program_id').put(programRoutes.modify);

    //update the profile with given id
    //must match the user's associated profile, or user must be a moderator/admin
    apiRouter.route('/profiles/:profile_id').put(profileRoutes.modify);

  //ROUTE TO CHECK FOR MODERATOR (OR ABOVE) ACCOUNT
  apiRouter.use(authRoutes.checkModerator);

    //ROUTES THAT REQUIRE (AT LEAST) A MODERATOR ACCOUNT

    //loading the list of users
    apiRouter.route('/users').get(userRoutes.list);

    //getting list of users that have not been activated
    apiRouter.route('/users/inactive').get(userRoutes.listInactive);

    //modifying an account with given id
    apiRouter.route('/users/:user_id/activate').post(userRoutes.activate);

    //create a new profile
    apiRouter.route('/profiles').post(profileRoutes.create);

    //create a program
    apiRouter.route('/programs').post(programRoutes.create);

  //ROUTE TO CHECK FOR ADMINISTRATOR ACCOUNT
  apiRouter.use(authRoutes.checkAdministrator);

    //DELETING ENTRIES IN THE DATABASE, REQUIRES AN ADMIN USER ACCOUNT

    //delete the user with given id
    apiRouter.route('/users/:user_id').delete(userRoutes.expunge);

    //delete the profile with given id
    apiRouter.route('/profiles/:profile_id').delete(profileRoutes.expunge);

    //delete the meeting with given id
    apiRouter.route('/meetings/:meeting_id').delete(meetingRoutes.expunge);

    //delete the program with given id
    apiRouter.route('/programs/:program_id').delete(programRoutes.expunge);




  return apiRouter;
};
