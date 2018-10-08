todoApp.controller('loginController', ['$scope', 'appService', '$http', '$localStorage', '$state', '$rootScope', function ($scope, appService, $http, $localStorage, $state, $rootScope) {

    redirectToHomePage();

    $scope.login = function (userDetails) {
        appService.doPost('/auth/login', userDetails).then(
            function (response) {
                $localStorage.loginDetails = { userName : response.data.userName, userId : response.data.userId, authToken: response.data.token, isLoggedIn: true, authorities: response.data.authorities };
                redirectToHomePage();
            },
            function (response) {
                ngNotify.set(response.data.messageKey || response.data.message, 'error');
                $scope.error = response.data;
            }
        );
    }

    function redirectToHomePage() {
        if ($localStorage.loginDetails && $localStorage.loginDetails.isLoggedIn) {
            $state.go($rootScope.hasAuthority('ADMIN') ? 'admin-todos' : 'todos');
        }
    }

}]);