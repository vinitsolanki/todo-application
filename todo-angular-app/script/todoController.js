todoApp.controller('todoController', function ($scope, appService, $stateParams, ngNotify, $rootScope) {
    $scope.todo = {};

    $scope.saveTodo = function () {
        appService.doPost('/todos', $scope.todo).then(
            function (response) {
                ngNotify.set(response.data.messageKey || response.data.message, 'success');
                $scope.todo = {};
                $scope.getTodos();

            },
            function (response) {
                ngNotify.set(response.data.messageKey || response.data.message, 'error');
            }
        );

    }


    $scope.updateTodo = function (todo) {
        appService.doUpdate('/todos', todo).then(
            function (response) {
                ngNotify.set(response.data.messageKey || response.data.message, 'success');

            },
            function (response) {
                ngNotify.set(response.data.messageKey || response.data.message, 'error');
            }
        );
    }

    
    $scope.deleteTodo = function (todoId) {
        appService.delete('/todos/' + todoId).then(
            function (response) {
                ngNotify.set(response.data.messageKey || response.data.message, 'success');
                $scope.getTodos();

            },
            function (response) {
                ngNotify.set(response.data.messageKey || response.data.message, 'error');
            }
        );
    }

    $scope.getUser = function (userId) {
        appService.doGet('/admin/users/' + userId).then(
            function (response) {
                $scope.user = response.data.data;
            },
            function (response) {
                ngNotify.set(response.data.messageKey || response.data.message, 'error');
            }
        );
    };
    var todoUrl = "/todos";
    if ($stateParams.userId) {
        if ($stateParams.userId != $rootScope.loginDetails.userId) {
            $scope.getUser($stateParams.userId);
            todoUrl = "/admin/users/" + $stateParams.userId + "/todos";
        }
    }

    $scope.getTodos = function () {
        appService.doGet(todoUrl).then(
            function (response) {
                $scope.todos = response.data.data;

            },
            function (response) {
                ngNotify.set(response.data.messageKey || response.data.message, 'error');
            }
        );
    };

    $scope.getTodos();
});