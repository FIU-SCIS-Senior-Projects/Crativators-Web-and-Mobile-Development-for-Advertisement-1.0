//Liaison Service

angular.module('liaisonService', [])

.factory('Liaison', function($http) {

  //create a new object
  var liaisonFactory = {};

  //get a single liaison
  liaisonFactory.get = function(id) {
    return $http.get('/api/liaisons/' + id);
  };

  //get all liaisons
  liaisonFactory.all = function() {
    return $http.get('/api/liaisons/');
  };

  //create a liaison
  liaisonFactory.create = function(liaisonData) {
    return $http.post('/api/liaisons/' + id, liaisonData);
  };

  //update liaison information
  liaisonFactory.update = function(id, liaisonData) {
    return $http.put('/api/liaisons/' + id, liaisonData);
  };

  //delete a liaison
  liaisonFactory.delete = function(id) {
    return $http.delete('/api/liaisons/' + id);
  };

  //return the liaison factory object
  return liaisonFactory;

});
