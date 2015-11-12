app.controller('homeCtrl', function($scope, $http) {
    $http.get("http://dna.dongguk.ac.kr/~felika/dongal/dongal-backend/home.php")
    .success(function(response) {
		$scope.userInfo = response.data.userInfo;
		$scope.alarms = response.data.alarms;

		$scope.collapse = function($event) {
			var that = $event.currentTarget;
			$($(that).data('target')).collapse('toggle');
		};
	});
});

app.controller('listCtrl', function($scope, $http) {
    $http.get("http://dna.dongguk.ac.kr/~felika/dongal/dongal-backend/home.php")
    .success(function(response) {
		$scope.userInfo = response.data.userInfo;
		$scope.alarms = response.data.alarms;

		$scope.collapse = function($event) {
			var that = $event.currentTarget;
			$($(that).data('target')).collapse('toggle');
		};
	});
});

app.controller('favoriteCtrl', function($scope, $http) {
    $http.get("http://dna.dongguk.ac.kr/~felika/dongal/dongal-backend/favorite.php")
    .success(function(response) {
		$scope.userInfo = response.data.userInfo;
		$scope.favorites= response.data.favorites;

		$scope.collapse = function($event) {
			var that = $event.currentTarget;
			$($(that).data('target')).collapse('toggle');
		};
	});
});

app.controller('moreCtrl', function($scope, $http) {
});

app.controller('settingsCtrl', function($scope, $http) {
    $http.get("http://dna.dongguk.ac.kr/~felika/dongal/dongal-backend/settings.php")
    .success(function(response) {
		$scope.categories = response.data.categories;
	});
});

app.controller('footerCtrl', function($scope, $location) {
	$scope.isActive = function (viewLocation) { 
		var count = ($location.path().match(/\//g) || []).length;
		if(count == 1) {
			return viewLocation === $location.path();
		} else {
			return ($location.path().indexOf(viewLocation.substring(1, viewLocation.length)) > 0);
		}
	};
});



