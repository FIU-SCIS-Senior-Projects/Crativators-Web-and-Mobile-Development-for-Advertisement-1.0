//Database functions for programs.

var Program =  require('mongoose').model('Program');

//Creating new program
exports.create = function(req, res) {

  var program = new Program();

  if(req.body.name)
    program.name = req.body.name;
  else
    program.name = "(Name)";

  if(req.body.brief)
    program.brief = req.body.brief;
  else
    program.brief = "(Program Brief)";

  if(req.body.picUrl)
    program.picUrl = req.body.picUrl;
  else
    program.picUrl = "(Picture URL here)";

  program.save(function(err) {
    if(err) res.send(err);

    //return a message
    res.json({ _id: program._id, message: 'Program created!'});
  });
};

//Retrieving the list of programs
exports.list = function(req, res) {
  Program.find(function (err, programs) {
    if(err) res.send(err);

    res.json(programs);
  });
};

//Retrieving a specific program
exports.retrieve = function(req, res) {
  Program.findById(req.params.program_id, function(err, program) {
    if(err) res.send(err);

    //return that program
    res.json(program);
  });
};

//Modifying a program
exports.modify = function(req, res) {
  Program.findById(req.params.program_id, function(err, program) {

    if(err) res.send(err);

    if(req.body.name) program.name = req.body.name;
    if(req.body.brief) program.brief = req.body.brief;
    if(req.body.picUrl) program.picUrl = req.body.picUrl;

    program.save(function(err) {
      if(err) res.send(err);

      //return a message
      res.json({ _id: program._id, message: 'Program updated!'});
    });
  });
};

//Removing a program
exports.expunge = function(req, res) {
  Program.remove({
    _id: req.params.program_id
  }, function(err) {
    if(err) res.send(err);

    res.json({ message: 'Successfully deleted program.' });
  });
};
