angular.module('app.route', ['ngRoute'])

.config(function($routeProvider, $locationProvider) {

  $routeProvider

  //COMPLETE ROUTES HERE AS WE HAVE VIEWS

  $locationProvider.html5Mode(true);
});
