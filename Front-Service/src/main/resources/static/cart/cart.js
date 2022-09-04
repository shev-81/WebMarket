angular.module('market-front').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/cart/';

    $scope.loadCart = function () {
        // console.log("Загрузка продуктов из корзины");
        $http({
            url: contextPath + 'api/v1/cart/' + $localStorage.springWebGuestCartId,
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.isCartHaveItems = function(x){
        if(x > 0){
            return true;
        }else{
            return false;
        }
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.clearCart = function () {
        $http.get(contextPath + 'api/v1/cart/'+ $localStorage.springWebGuestCartId +'/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.checkOut = function () {
        $http({
            url: 'http://localhost:5555/core/api/v1/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function successCallback(response) {
            $scope.loadCart();
            $scope.orderDetails = null
        }, function errorCallback(response) {
            console.log("Сервис лежит");
            let exceptionObj = response.data.message;
            alert("Ошибка " + exceptionObj);
        });
    };

    $scope.loadCart();
});