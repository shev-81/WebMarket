angular.module('market-front').controller('orderController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadOrders = function () {
        $http.get(contextPath + 'api/v1/orders')
            .then(function (response) {
                 $scope.MyOrders = response.data;
            });
    }

    $scope.isOrdersHaveItems = function(){
//             console.log($scope.MyOrders);
            if($scope.MyOrders != 0){
                return true;
            }else{
                return false;
            }
        };

    $scope.goToPay = function (orderId) {
        $location.path('/order_pay/' + orderId);
    }

    $scope.isUserPay = function (payment) {
        if (payment == 'PAID') {
            return true;
        } else {
            return false;
        }
    };

    $scope.disabledCheckOut = function () {
        alert("Ваш заказ оплачен.");
    }

    $scope.loadOrders();
    });