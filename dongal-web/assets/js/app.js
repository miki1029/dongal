var app = angular.module('dongal', ["ngResource", "ngRoute", "frapontillo.bootstrap-switch"]);

app.config(function($routeProvider, $locationProvider) {
	$routeProvider
		.when("/", {
			templateUrl: "views/home.html",
			controller: "homeCtrl"
		})

		.when("/home", {
			templateUrl: "views/home.html",
			controller: "homeCtrl"
		})

		.when("/list", {
		    templateUrl: "views/list.html",
		    controller: "listCtrl"
	    })

		.when("/favorite", {
		    templateUrl: "views/favorite.html",
		    controller: "favoriteCtrl"
	    })
		
		.when("/more", {
			templateUrl: "views/more.html",
			controller: "moreCtrl"
		})

		.when("/more/settings", {
			templateUrl: "views/settings.html",
			controller: "settingsCtrl"
		})

		;

	// use the HTML5 History API
	$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});
});
