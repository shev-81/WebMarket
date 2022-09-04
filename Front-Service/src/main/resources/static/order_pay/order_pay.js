angular.module('market-front').controller('orderPayController', function ($scope, $http, $location, $localStorage, $routeParams) {

    $scope.loadOrder = function () {
        $http({
            url: 'http://localhost:5555/core/api/v1/orders/' + $routeParams.orderId,
            method: 'GET'
        }).then(function (response) {
            $scope.order = response.data;
            $scope.renderPaymentButtons();
        });
    };

    $scope.renderPaymentButtons = function() {
        paypal.Buttons({
            createOrder: function(data, actions) {
                return fetch('http://localhost:5555/core/api/v1/paypal/create/' + $scope.order.id, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function(response) {
                    return response.text();
                });
            },

            onApprove: function(data, actions) {
                return fetch('http://localhost:5555/core/api/v1/paypal/capture/' + data.orderID, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function(response) {
                    response.text().then(msg => alert(msg));
                    window.location.href = '../front/#!/orders';
                    console.log("The order is paid");

                });
            },

            onCancel: function (data) {
                console.log("Order canceled: " + data);
            },

            onError: function (err) {
                console.log(err);
            }
        }).render('#paypal-buttons');
    }

    $scope.loadOrder();
});