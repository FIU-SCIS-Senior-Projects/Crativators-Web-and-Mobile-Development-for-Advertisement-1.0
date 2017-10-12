//SERVER SETUP

//PACKAGES
var express     = require('express'); //call express
var app         = express(); //define the app using express
var bodyParser  = require('body-parser'); //get body-parser
var morgan      = require('morgan'); //used to see requests
var mongoose    = require('mongoose');
var config      = require('./config');
var path        = require('path');

//APP CONFIGURATION
//use body parser to grab information from POST requests
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

//configure app to handle CORS requests
app.use(function(req, res, next) {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET', 'POST');
  res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With', 'content-type');
  next();
});

//log all requests to the console
app.use(morgan('dev'));

//variable for accepting promises
mongoose.Promise = global.Promise;

//connect to our database (mlab)
mongoose.connect(config.database);

//set static files location
//used for requests that our frontend will make
app.use(express.static(__dirname + '/'));

//ROUTES FOR THE API
//COMPLETE ROUTES ON RELEVANT FILE
var apiRoutes = require('./app-backend/routes/api')(app, express);
app.use('/api', apiRoutes);

//MAIN CATCHALL ROUTE - SEND USERS TO FRONTEND
app.get('/', function(req, res) {
  res.sendFile(path.join(__dirname + '/index.html'));
});

app.listen(config.port);
console.log('Positive Pathways running on port ' + config.port);
