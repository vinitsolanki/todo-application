todoApp.controller('adminTodoController', function ($scope, $localStorage, appService, ngNotify) {
    $scope.todo = {};

    $scope.updateTodo = function (todo) {
        appService.doUpdate('/todos', todo).then(
            function (response) {
                ngNotify.set(response.data.messageKey || response.data.message, 'success');

            },
            function (response) {
                todo.completed = !todo.completed;
                ngNotify.set(response.data.messageKey || response.data.message, 'error');
            }
        );

    }



    $scope.getTodos = function () {
        appService.doGet('/admin/todos').then(
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