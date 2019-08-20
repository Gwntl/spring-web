mainapp.controller('myCtrl', ['$scope','$routeParams','$http','$location','ngDialog','alertService',function($scope,$routeParams,$http, $location, ngDialog,alertService) {
	$scope.params = {};
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
    	
    	$scope.params.username = username;
    	$scope.params.password = password;
    	console.log($scope.params);
    	$http({
    		method:"POST",
    		url:"login.do",
    		params:$scope.params
    	}).then(function successCallback(data){
    		console.log(data);
    		if(data.status == 200 && data.data.result == "success"){
    			$location.path("/trade");
    		}else{
    			alert("登陆失败");
    		}
    	}, function failCallBack(data){
    		
    	});
    }
    
    $scope.open=function(){
    	alertService.alert("test");
    }
}]);	