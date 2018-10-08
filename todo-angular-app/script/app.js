var todoApp = angular.module('todoApp', ['ui.router', 'ngStorage', 'ngNotify'])
    .config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/todos');
        $stateProvider
            .state('todos', {
                url: '/todos/:userId',
                templateUrl: 'pages/todos.html',
                controller: 'todoController'
            })
            .state('admin-todos', {
                url: '/admin-todos',
                templateUrl: 'pages/admin-todos.html',
                controller: 'adminTodoController'
            })
            .state('about', {
                url: '/about',
                templateUrl: 'pages/partial-home.html',
                controller: 'todoController'
            })
            .state('login', {
                url: '/login',
                templateUrl: 'pages/login.html',
                controller: 'loginController'
            });
    }).run(function ($localStorage, $rootScope, $state, $http) {
        if (!$localStorage.loginDetails) {
            $localStorage.loginDetails = { authToken: undefined, isLoggedIn: false };
        }

        $rootScope.$on('$stateChangeStart', function (event, toState) {
            $rootScope.loginDetails = $localStorage.loginDetails;
            $http.defaults.headers.common['Authorization'] = $localStorage.loginDetails.authToken;
            if (toState.name != "login") {

                if (!$localStorage.loginDetails || !$localStorage.loginDetails.isLoggedIn) {
                    event.preventDefault();
                    $state.go('login');
                    return;
                }
            }
        });
        $rootScope.logout = function () {
            $rootScope.loginDetails = $localStorage.loginDetails = {};
            $state.go('login');
        }

        $rootScope.hasAuthority = function (authority) {
            return $localStorage.loginDetails.authorities.indexOf(authority) !== -1;
        }

    }).constant('environment', {
        baseUrl: 'http://172.25.54.108:8081'
    });



