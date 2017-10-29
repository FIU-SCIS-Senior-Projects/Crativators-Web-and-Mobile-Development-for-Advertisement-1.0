//Profile Service

angular.module('profileService', [])

.factory('Profile', function($http) {

  //create a new object
  var profileFactory = {};

  //get a single profile
  profileFactory.get = function(id) {
    return $http.get('/api/profiles/' + id);
  };

  //get all profiles
  profileFactory.all = function() {
    return $http.get('/api/profiles/');
  };

  //create a profile
  profileFactory.create = function(profileData) {
    return $http.post('/api/profiles/', profileData);
  };

  //update profile information
  profileFactory.update = function(id, profileData) {
    return $http.put('/api/profiles/' + id, profileData);
  };

  //delete a profile
  profileFactory.delete = function(id) {
    return $http.delete('/api/profiles/' + id);
  };

  //return the profile factory object
  return profileFactory;

});
