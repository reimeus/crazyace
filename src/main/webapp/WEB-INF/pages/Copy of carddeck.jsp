<html>
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
  <link rel="stylesheet" type="text/css" href="/resources/cardstyle.css">
 </head>
<body>

<div ng-app="myApp" ng-controller="cardCtrl">

<h1 class="head">
	Crazy Ace 
	<img src="/resources/crazyace2.png">
</h1>

<p>

<div class="status">
	<span class="namecell">${playerName}</span>
	
	<span class="timecell" ng-if="startTime === null">Waiting for another player to join</span>
	<span class="timecell" ng-if="startTime !== null">Game started at {{startTime}}</span>
</div>

<div class="outer">

<div>
	<div ng-repeat='(key, value) in playersMap'" ng-class="key === currentPlayer? 'seluser': 'nohilite'">
		<span class="ptrcell" ng-if="key === currentPlayer">&#10148;</span>
		<span class="ptrcell" ng-if="key !== currentPlayer">&nbsp;</span>
		<span class="cell">{{key}}</span>
		<span class="cell">{{value}}
				<ng-pluralize count="value" when="{
                     '1': 'card', 
                     'other': 'cards'}">
				</ng-pluralize>
		</span>
	</div>

<p>


<div>
	<span>
		<img src="/resources/cardback.png">
	</span>
	<span ng-if="currentCard === null">
	 	<img src="/resources/waiting.png">
	</span>
	<span ng-if="currentCard !== null">
	 	<img src="{{currentCard}}">
	</span>
</div>


<div class="infomsg">
	<!-- Current suit is now -->
	<span ng-bind="aceChangeMessage"></span>
	
	<!-- Ace change suit -->
	<span ng-bind="aceSuit" class="blockinfonb"></span>
	
	<span ng-bind="winLoseMessage" class="winlose"></span>
</div>

<div class="cards">
	<span ng-repeat="card in cards" ng-click="setSelected(card)" ng-class="card.id === selected.id? 'selected': 'unselected'">
		<img src="{{card.url}}">
	</span>
</div>
	  
<div class="buttonbar">

<span class="playstyle">
	<label for="suit" title="Suit for selected ace">Suit:</label>
	<select name="suit" ng-disabled="!aceSelected" ng-model="selectedItem" ng-options="item.name for item in suit"></select>

	<button ng-click="playCard()" title="Play the currently selected card" ng-disabled="win || lose">Play</button>
</span>	
	
	<button ng-click="requestCard()" title="Request a card from the deck" ng-disabled="win || lose">Request</button>
</div>


</div>

<div ng-bind="errorMessage" class="errors"></div>

</div>

</div>

<script>


var app = angular.module('myApp', []);
app.controller('cardCtrl',

function($scope, $http, $timeout) {
	 $scope.aceSelected = false;
	 $scope.suit = [{id: 0, name: 'Clubs'}, {id: 1, name: 'Diamonds'}, {id: 2, name: 'Hearts'}, {id: 3, name: 'Spades'}];

     (function tick() {
    	 
         $scope.setSelected = function(card) {
             $scope.selected = card;
             $scope.errorMessage = '';
             $scope.aceSelected = card.ace;
             $scope.selectedItem = $scope.suit[card.suitIndex];
         }

         $scope.checkTurn = function() {
        	 var isPlayerTurn = $scope.currentPlayer === "${playerName}";
        	 if (!isPlayerTurn) {
        	 	$scope.errorMessage = 'Cannot play: waiting for ' + $scope.currentPlayer + ' to play';
        	 }
        	 
        	 return isPlayerTurn;
         }
         
         $scope.checkStart = function() {
        	 var isStarted = $scope.startTime !== null;
        	 if (!isStarted) {
        	 	$scope.errorMessage = 'Cannot play: waiting for another player to join';
        	 }
        	 
        	 return isStarted;
         }
         
         $scope.playCard = function() {
        	 if (!$scope.checkStart() || !$scope.checkTurn()) return;
        	 
        	 if (angular.isUndefined($scope.selected)) {
        		 $scope.errorMessage = 'Please select a card';
        	 } else {
        	 	console.log("Play " + $scope.selected.id);
        	 	
        	 	var aceSuit = $scope.aceSelected? $scope.selectedItem.name: null;
        	 	
        	 	var params = { playerName: "${playerName}", cardId: $scope.selected.id, suit: aceSuit };
        	 	
        	 	var config = {'Content-Type': 'application/x-www-form-urlencoded'};
        	 	
        	 	$http({
        	 	    method: 'POST',
        	 	    url: '/playCard',
        	 	   	data: params,
        	 	    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        	 	   	transformRequest: function(obj) {
                       var str = [];
                       for(var p in obj)
                       str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                       return str.join("&");
                     }
        	 	}).
        	 	success(function(data){
        	 		   delete $scope.selected;
        	 	}).
                error(function (data, status, headers, config) {
                	console.log("Error " + status);
                	 $scope.errorMessage = 'Cannot play that card';
                });
        	 	
        	 }
         }
         
         $scope.requestCard = function() {
        	 if (!$scope.checkStart() || !$scope.checkTurn()) return;
        	
    	 	$http({
    	 	    method: 'POST',
    	 	    url: '/requestCard',
    	 	   	data: { playerName: "${playerName}"},
    	 	    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
   		 	   	transformRequest: function(obj) {
                  var str = [];
                  for(var p in obj)
                  str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                  return str.join("&");
                }
    	 	}).
    	 	success(function(data){
    	 	 	$scope.errorMessage = '';
 	 		}).
            error(function (data, status, headers, config) {
            	console.log("Error " + status);
            	 $scope.errorMessage = 'Error retrieving card ' + status;
            });
         }
         
         $http.get('/getPlayerCards', { params: { playerName: "${playerName}" }}).success(function (response) {
			  $scope.cards = response.cards;
			  $scope.startTime = response.startTime;
			  $scope.currentCard = response.currentCard;
			  $scope.currentPlayer = response.currentPlayer;
			  $scope.playersMap = response.playersMap;
			  
			  $scope.aceChangeMessage = response.aceSuit != null? "Current suit is now ": "";
			  $scope.aceSuit = response.aceSuit;
			  
			  if (response.winner !== null && response.winner === "${playerName}") {
				  $scope.win = true;
				  $scope.winLoseMessage = "You Win!";
				  $scope.aceChangeMessage = "";
				  $scope.aceSuit = "";
			  }
			  
			  if (response.winner !== null && response.winner !== "${playerName}") {
				  $scope.lose = true;
				  $scope.winLoseMessage = "You Lose!";
				  $scope.aceChangeMessage = "";
				  $scope.aceSuit = "";
			  }
			  
			  $timeout(tick, 1000);
         });
     })();
});
</script>

</body>
</html>