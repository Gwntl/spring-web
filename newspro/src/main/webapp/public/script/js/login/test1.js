mainapp.controller('emailDetail',['$scope','$routeParams','messages',function($scope,$routeParams){
    $scope.email=messages[$routeParams.id];
    $scope.call=function(){
    	console.log($scope.email.input);
    }
}]);