'use strict';
var mainapp = angular.module('mainapp', ['ngRoute']);

//邮件
var messages=[{
    id:0,
    sender:"王经理",
    subject:"项目总结",
    date:"2015-4-2 09:00:422222",
    recipient:"小李",
    message:"记得明天上午开会要收项目总结，不要搞砸了。"
},{
    id:1,
    sender:"小姨子",
    subject:"明天吃饭",
    date:"2015-4-2 23:12:56",
    recipient:"小李",
    message:"姐夫明天请我吃饭啦。"
}];

mainapp.value('messages',messages);

mainapp.controller('emailList', ['$scope', function($scope){
    $scope.emails=messages;
}]);

mainapp.config(['$routeProvider','$controllerProvider',function($routeProvider,$controllerProvider) {
	mainapp.register={
			controller: $controllerProvider.register
	}
	
    $routeProvider.when('/', {
        controller:'myCtrl',
        templateUrl:'public/view/login.html'
    }).when('/view/:id',{
        controller:'emailDetail',
        templateUrl:'public/view/emailDetail.html'
    });
}]);