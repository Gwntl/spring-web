mainapp.controller('myCtrl', ['$scope','$routeParams','$http','$location','ngDialog','alertService',function($scope,$routeParams,$http, $location, ngDialog,alertService) {
	$scope.params = {};
    $scope.login=function(){
    	var username = $scope.username;
    	var password = $scope.password;
    	console.log(username);
    	if(username == undefined || username == null || username == ""){
    		alertService.alert("用户名不能位空");
    		return;
    	}
    	if(password == undefined || password == null || password == ""){
    		alertService.alert("密码不能位空");
    		return;
    	}
    	
    	$scope.params.username = username;
    	$scope.params.password = password;
    	console.log($scope.params);
    	$http({
    		method:"GET",
    		url:"login.do",
    		params:$scope.params
    	}).then(function successCallback(data){
    		console.log(data);
    		if(data.status == 200 && data.data.result == "success"){
    			$location.path("/trade");
    		}else{
    			alertService.alert("登陆失败:" + data.data.result);
    		}
    	}, function failCallBack(data){
    		alertService.alert("登陆失败: " + data.data.result);
    	});
    }
}]);	