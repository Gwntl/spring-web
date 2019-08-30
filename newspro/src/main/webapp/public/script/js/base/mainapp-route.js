//mainapp.config(['$routeProvider','$locationProvider',function($routeProvider, $locationProvider) {	
//    $routeProvider.when('/', {
//        controller:'myCtrl',
//        templateUrl:'public/view/login.html'
//    }).when('/register',{
//        controller:'registerController',
//        templateUrl:'public/view/register.html'
//    }).when('/trade',{
//        controller:'tradeController',
//        templateUrl:'public/view/trade.html'
//    }).otherwise('/');
//}]);

mainapp.config(['$stateProvider','$urlRouterProvider',function($stateProvider,$urlRouterProvider) {
	$urlRouterProvider.otherwise("/access");
	
//	$urlRouterProvider.when("", "/login");
	
	$stateProvider.state('access', {
		url: '/login',
        templateUrl:'public/view/login.html',
        controller:'myCtrl'
    }).state('register',{
    	url: '/register',
        templateUrl:'public/view/register.html',
        controller:'registerController'
    }).state('trade',{
    	url: '/trade',
        templateUrl:'public/view/trade.html',
        controller:'tradeController'
    }).state('mainapp',{
    	abstract: true,
        url: '/app',
        templateUrl: 'public/view/base/mainapp.html'
    }).state('mainapp.mainPage',{
        url: '/mainPage',
        templateUrl: 'public/view/base/welcome.html'
    });
	$urlRouterProvider.otherwise("login");
}]);

