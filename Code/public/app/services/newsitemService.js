//Liaison Service

angular.module('newsitemService', [])

.factory('NewsItem', function($http) {

  //create a new object
  var newsitemFactory = {};

  //get a single liaison
  newsitemFactory.get = function(id) {
    return $http.get('/api/newsitem/' + id);
  };

  //get all newsitem
  newsitemFactory.all = function() {
    return $http.get('/api/newsitem/');
  };

  //create a liaison
  newsitemFactory.create = function(liaisonData) {
    return $http.post('/api/newsitem/', liaisonData);
  };

  //update liaison information
  newsitemFactory.update = function(id, liaisonData) {
    return $http.put('/api/newsitem/' + id, liaisonData);
  };

  //delete a liaison
  newsitemFactory.delete = function(id) {
    return $http.delete('/api/newsitem/' + id);
  };

  //return the liaison factory object
  return newsitemFactory;

});
