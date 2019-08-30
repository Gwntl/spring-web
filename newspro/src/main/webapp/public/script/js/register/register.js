mainapp.controller('registerController',['$scope','$http','alertService',function($scope, $http, alertService){
	$scope.params = {};
	
	$scope.submit=function(){
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
    	$scope.params.remark = $scope.remark;
    	console.log($scope.params);
    	$http({
    		method:"POST",
    		url:"register/user.do",
    		params:$scope.params
    	}).then(function successCallback(data){
    		console.log(data);
    		if(data.status == 200 && data.data.call_status == "0000"){
    			alertService.alert("注册成功:" + data.data.result);
    		}else{
    			alertService.alert("注册失败:" + data.data.result);
    		}
    	}, function failCallBack(data){
    		alertService.alert("注册失败: " + data.data.result);
    	});
	}
}]);