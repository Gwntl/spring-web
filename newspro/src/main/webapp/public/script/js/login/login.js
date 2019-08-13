//var myApp = angular.module('myApp', []);

mainapp.controller('myCtrl', ['$scope','$routeParams',function($scope,$routeParams) {
    $scope.login=function(){
    	var username = $scope.username;
    	var password = $scope.password;
    	console.log(username);
    	if(username == undefined || username == null || username == ""){
    		alert("用户名不能位空");
    		return;
    	}
    	if(password == undefined || password == null || password == ""){
    		alert("密码不能位空");
    		return;
    	}
    }
}]);	