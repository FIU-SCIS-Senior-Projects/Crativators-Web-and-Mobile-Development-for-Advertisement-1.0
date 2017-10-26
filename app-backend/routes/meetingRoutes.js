//Database functions for meetings.

var Meeting = require('mongoose').model('Meeting');

//creating a meeting
exports.create = function(req, res) {

  var meeting = new Meeting();

  if(req.body.title)
    meeting.title = req.body.title;
  else
    meeting.title = "(Meeting Title)";

  if(req.body.agenda)
    meeting.agenda = req.body.agenda;
  else
    meeting.agenda = "(Post Agenda Body)";

  if(req.body.minutes)
    meeting.minutes = req.body.minutes;
  else
    meeting.minutes = "(Post Minutes Body)";

  meeting.date = Date("<YYYY-mm-ddTHH:MM:ss>");

  meeting.save(function(err) {
    if(err) res.send(err);

    //return a message
    res.json({ _id: meeting._id, message: 'Meeting created!'});
  });
};

//Retrieving the list of meetings
exports.list = function(req, res) {
  Meeting.find(function (err, meetings) {
    if(err) res.send(err);

    res.json(meetings);
  });
};

//Retrieving a specific meeting
exports.retrieve = function(req, res) {
  Meeting.findById(req.params.meeting_id, function(err, meeting) {
    if(err) res.send(err);

    //return that liaison
    res.json(meeting);
  });
};

//Modifying a meeting
exports.modify = function(req, res) {
  Meeting.findById(req.params.meeting_id, function(err, meeting) {

    if(err) res.send(err);

    var change = false;

    if(req.body.title) {
      meeting.title = req.body.title;
      change = true;
    }
    if(req.body.agenda) {
      meeting.agenda = req.body.agenda;
      change = true;
    }
    if(req.body.minutes) {
      meeting.minutes = req.body.minutes;
      change = true;
    }
    if(change)
      meeting.date = Date("<YYYY-mm-ddTHH:MM:ss>");

    meeting.save(function(err) {
      if(err) res.send(err);

      //return a message
      res.json({ _id: meeting._id, message: 'Meeting updated!'});
    });
  });
};

//Removing a meeting
exports.expunge = function(req, res) {
  Meeting.remove({
    _id: req.params.meeting_id
  }, function(err) {
    if(err) res.send(err);

    res.json({ message: 'Successfully deleted meeting.' });
  });
};
