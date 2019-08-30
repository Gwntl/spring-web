(function (window) {
	var ng = angular.module("ng");
	ng.config(["$provide", "$compileProvider", "$filterProvider", function ($provide, $compileProvider, $filterProvider) {
		
		//定义$remote=function $remoteProvider
		$provide.provider({
			$remote: $remoteProvider
		});
		$compileProvider.directive({
			
		});
	}]);
	
	function $remoteProvider() {
		var service = {
				errorcode:"",
				config: {},
				failCallBack: null,
				setErrorTag: function (tag) {
					this.errorcode = tag;
				},
				setErrorCallback: function (fn) {
					this.failCallBack = fn;
				}
		};
		angular.extend(this, service);
		
		this.$get=['$http', function($http){
			var errorcode = this.errorcode;
			failCallBack = this.failCallBack;
			
			function proxy(method, url, data, successFunction, errorFn, config){
				config = angular.extend(config || {}, {
					method: method,
					url: url,
					data: data,
				});
				
		    	$http({
		    		method:method,
		    		url:url,
		    		params:data
		    	}).then(function successCallback(response){
					if (response.data && response.status >= 200 && 300 > response.status) {
						success(response.data, response.status, response.headers, config);
					} else {
						fail(response.data, response.status, response.headers, config);
					}
		    	}, function failCallBack(data){
		    		fail(response.data, response.status, response.headers, config);
		    	});
				
				function success(data, status, headers, config){
					var error = false;
					if(!("undefined" == typeof data[errorcode])){
						failCallBack && failCallBack(data, status, headers, config);
						error = true;
					}
					if (!error) {
						successFunction && successFunction(data);
					}
				};
				
				function fail(data, status, headers, config){
					if(errorFn){
						errorFn(data, status, headers, config);
					}else{
						failCallBack && failCallBack(data, status, headers, config);
					}
				};
			};
			
			return {
				GET: function (url, params, okFn, errFn, config) {
					proxy("GET", url, params, okFn, errFn, config);
				},
				POST: function (url, params, okFn, errFn, config) {
					proxy("POST", url, params, okFn, errFn, config);
				},
				PUT: function (url, params, okFn, errFn, config) {
					proxy("PUT", url, params, okFn, errFn, config);
				},
				DELETE: function (url, params, okFn, errFn, config) {
					proxy("DELETE", url, params, okFn, errFn, config);
				}
			};
		}];
	}
	
})(window);

