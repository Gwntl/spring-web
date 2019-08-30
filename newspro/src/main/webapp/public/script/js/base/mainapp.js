//var mainapp = angular.module('mainapp', ['ngRoute','ngDialog']);

var mainapp = angular.module('mainapp', ['ui.router','ngDialog','ngStorage']);

(function (window) {
	'use strict';
	var mod = angular.module('mainapp');

	mod.config(['$controllerProvider',function($controllerProvider) {
		mainapp.register={
				controller: $controllerProvider.register
		};
	}]);
	
	configRemote.$inject = ['$remoteProvider'];
	function configRemote($remoteProvider,ngDialog,$rootScope,alertService) {
		$remoteProvider.setErrorTag("errorcode");
		/**
		 * 请求参数配置
		 */
		$remoteProvider.config = {
			headers: {
				'Content-Type': 'application/json'
			},
			timeout: 120000, // 5min
		};
		$remoteProvider.setErrorCallback(function (data, status, headers, config) {
			//获取$rootScope
			var $rootScope = angular.element("body").scope();
			var ngDialog = $rootScope.ngDialog;
			if (status == "520") {
				
			}else if(status == "200" && data && data.errorcode){
				$rootScope.alert(data.result,"温馨提示");
			}else {
				var title = '';
				var msg = '';
				if (status == "518") {
					title = "错误提示";
					msg = "页面已失效，请重新登录!";
				} else if (status == '-1') {
					title = "错误提示";
					msg = "请求超时！";
				} else {
					title = "错误提示";
					msg = data.result;
				}
				$rootScope.alert(msg,title);
			}
		});
	}
	
	mod.config(configRemote);

	
	mod.service('alertService',function(ngDialog,$rootScope){
		this.alert=function(msg,title){
			if(title == undefined || title == ""){
				$rootScope.title = "温馨提示";
			}else{
				$rootScope.title=title;
			}
			$rootScope.msg=msg;
			ngDialog.open({
	    		template: 'public/view/alert.html',
//				className: 'ngdialog-theme-default ngdialog-theme-custom',
	    		className: 'ngdialog-theme-default',
				//dialog框中隐藏重叠div, false-隐藏
				overlay: false,
				plain: false,
				showClose: false,
				closeByDocument: false,
				closeByEscape: false,
				appendTo: false,
				preCloseCallback: function () {
					console.log('default pre-close callback');
				},
				controller:['$scope',function($scope){
					$(".errorlayerdiv .errorlayerbuttondiv button.button1").focus();
					$scope.closeDialog = function () {
						$scope.closeThisDialog();
					};
				}]
	    	});
		}
	});
	
	/**
	 * 运行配置 runRootScope
	 */
	runRootScope.$inject = ['$rootScope', '$timeout','$location', '$remote', 'ngDialog','$window'];
	function runRootScope($rootScope,$timeout,$location,$remote,ngDialog,$window){
		// 封装打开窗口方法
		$rootScope.ngDialog = ngDialog;
		
		$rootScope.alert = function (msg, title) {
			if(title == undefined || title == ""){
				$rootScope.title = "温馨提示";
			}else{
				$rootScope.title=title;
			}
			$rootScope.msg=msg;
			ngDialog.open({
	    		template: 'public/view/alert.html',
//				className: 'ngdialog-theme-default ngdialog-theme-custom',
	    		className: 'ngdialog-theme-default',
				//dialog框中隐藏重叠div, false-隐藏
				overlay: false,
				plain: false,
				showClose: false,
				closeByDocument: false,
				closeByEscape: false,
				appendTo: false,
				preCloseCallback: function () {
					console.log('default pre-close callback');
				},
				controller:['$scope',function($scope){
					$(".errorlayerdiv .errorlayerbuttondiv button.button1").focus();
					$scope.closeDialog = function () {
						$scope.closeThisDialog();
					};
				}]
	    	});
		};
		
		$rootScope.isEmpty = function(value) {
			return angular.isUndefined(value) || "" === value || null === value || value !== value;
		};
		
		//通过$on为$rootScope添加路由事件
//        $rootScope.$on('$routeChangeSuccess',function(event, current, previous){
//            console.log('successfully changed routes');
//            console.log(event);
//            console.log(current);
//            console.log("路径" + current.$$route.originalPath);
//        });
        
        $rootScope.$on('$routeChangeStart', function(event, current, previous){
        	var sessionValue = $window.sessionStorage['test_success'];
        	var reqUrl = current.$$route.originalPath;
			if(reqUrl != '/' && $rootScope.isEmpty(sessionValue)){
				$rootScope.alert('请先登陆');
				$location.path('/');
			} 
//			else if(reqUrl === '/' && !$rootScope.isEmpty(sessionValue)){
//				$window.sessionStorage.removeItem('test_success');
//			}
		});
        
//		$rootScope.$on('$routeChangeError', function(event, current, previous, rejection){
//			 console.log('error changing routes');
//        });
	}
	mainapp.run(runRootScope);
})(window);
