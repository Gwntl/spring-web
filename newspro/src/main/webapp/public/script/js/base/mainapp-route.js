mainapp.config(['$routeProvider',function($routeProvider) {
    $routeProvider.when('/', {
        controller:'myCtrl',
        templateUrl:'public/view/login.html'
    }).when('/trade',{
        controller:'emailDetail',
        templateUrl:'public/view/emailDetail.html'
    });
}]);
