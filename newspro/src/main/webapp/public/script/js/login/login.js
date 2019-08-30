mainapp.controller('myCtrl', ['$scope','$http','$remote','$rootScope','$window','$state','$localStorage',function($scope,$http,$remote,$rootScope,$window,$state,$localStorage) {
	$scope.params = {};
    $scope.login=function(){
    	var username = $scope.username;
    	var password = $scope.password;
    	if($rootScope.isEmpty(username)){
    		$rootScope.alert("用户名不能位空");
    		return;
    	}
    	if($rootScope.isEmpty(password)){
    		$rootScope.alert("密码不能位空");
    		return;
    	}
    	
    	$scope.app = {
    		name : 'Angulr',
			version : '1.3.3',
			// for chart colors
			color : {
				primary : '#7266ba',
				info : '#23b7e5',
				success : '#27c24c',
				warning : '#fad733',
				danger : '#f05050',
				light : '#e8eff0',
				dark : '#3a3f51',
				black : '#1c2b36'
			},
			settings : {
				themeID : 1,
				navbarHeaderColor : 'bg-black',
				navbarCollapseColor : 'bg-white-only',
				asideColor : 'bg-black',
				headerFixed : true,
				asideFixed : false,
				asideFolded : false,
				asideDock : false,
				container : false
			}
    	}
    	
    	// save settings to local storage
        if ( angular.isDefined($localStorage.settings) ) {
          $scope.app.settings = $localStorage.settings;
        } else {
          $localStorage.settings = $scope.app.settings;
        }
        $scope.$watch('app.settings', function(){
          if( $scope.app.settings.asideDock  &&  $scope.app.settings.asideFixed ){
            // aside dock and fixed must set the header fixed.
            $scope.app.settings.headerFixed = true;
          }
          // save to local storage
          $localStorage.settings = $scope.app.settings;
        }, true);
    	
    	
    	
    	$scope.params.username = username;
    	$scope.params.password = password;
    	$remote.GET("login.do",$scope.params,function(){
    		$window.sessionStorage['test_success']='test_success';
    		$state.go('mainapp.mainPage');
    	});
    }
    
    $scope.register=function(){
//    	ngDialog.open({
//    		//Esc键关闭对话框, false-不可以
//    		showClose:false,
//    		//隐藏dialog框关闭按钮, false-隐藏
//    		closeByEscape :false,
//    		width:'40%',
//    		height:'35%',
//    		template: 'public/view/register.html',
//			className: 'ngdialog-theme-default',
//			controller: 'registerController'
//    	});
//    	$location.path("/register");
    }
}]);	