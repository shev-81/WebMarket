angular.module('market-front').controller('welcomeController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/analit/';

    $scope.loadProductsAnalytic = function (){
        // console.log("загружаем Аналитику");
        $http.get(contextPath +'api/v1/analit')
            .then(function (response) {
                $scope.ProductList = response.data;
            });
    }
    $scope.loadProductsAnalytic();
});