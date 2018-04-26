<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Example - example-example109-production</title>
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.0-beta.1/angular.min.js"></script>
  <link rel="stylesheet" type="text/css" href="mystyle.css">
</head>



<div ng-app="myApp" ng-controller="signCtrl">

<h1>{{serverTime}}</h1>

<ul>
  <li ng-repeat="x in names">
    {{ x.id + ', ' + x.content }}
  </li>
</ul>

</div>


<script>

 function dataCtrl($scope, $http, $timeout) {
     $scope.data = [];

     (function tick() {
         $http.get('http://localhost:8080/greetingAll').success(function (data) {
			  $scope.names = response.greetings;
			  $scope.serverTime = response.serverTime;
			  $timeout(tick, 1000);
         });
     })();
};

</script>

</body>
</html>