angular.module('market-front').controller('userController', function ($scope, $http, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:5555/auth/';

    $scope.showCurrentUserInfo = function () {
        $http.get(contextPath + 'api/v1/user/profile')
            .then(function successCallback(response) {
                alert('MY NAME IS: ' + response.data.username);
            }, function errorCallback(response) {
                alert('UNAUTHORIZED');
            });
    }

    $scope.loadUsers = function () {
        $http.get(contextPath + 'api/v1/user'
        ).then(function (response) {
            $scope.usersList = response.data;
        });
    };

    $scope.createUser = function (){
       // console.log($scope.newUser.name);
    $http.post(contextPath + 'api/v1/user/new', $scope.newUser
    ).then(function (response) {
        $scope.loadUsers();
    });
}

    $scope.deleteUser = function (userId) {
        $http.delete(contextPath + 'api/v1/' + userId)
            .then(function (response) {
                $scope.loadUsers();
            });
    }
    $scope.loadUsers();      //  запуск функции при загрузке страницы
});