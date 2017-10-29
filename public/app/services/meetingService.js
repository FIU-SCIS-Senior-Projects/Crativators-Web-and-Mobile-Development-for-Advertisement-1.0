//Meeting Service

angular.module('meetingService', [])

.factory('Meeting', function($http) {

  //create a new object
  var meetingFactory = {};

  //get a single meeting
  meetingFactory.get = function(id) {
    return $http.get('/api/meetings/' + id);
  };

  //get all meetings
  meetingFactory.all = function() {
    return $http.get('/api/meetings/');
  };

  //create a meeting
  meetingFactory.create = function(meetingData) {
    return $http.post('/api/meetings/', meetingData);
  };

  //update meeting information
  meetingFactory.update = function(id, meetingData) {
    return $http.put('/api/meetings/' + id, meetingData);
  };

  //delete a meeting
  meetingFactory.delete = function(id) {
    return $http.delete('/api/meetings/' + id);
  };

  //return the meeting factory object
  return meetingFactory;

});
