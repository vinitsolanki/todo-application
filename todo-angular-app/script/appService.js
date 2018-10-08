todoApp.factory('appService', ['$http', '$q', 'environment', function ($http, $q, environment) {

    return {

        doPost: function (url, data) {
            return $http.post(environment.baseUrl + url, data);
        },
        doUpdate: function (url, data) {
            return $http.put(environment.baseUrl + url + "/" + data.id, data);
        },
        doGet: function (url) {
            return $http.get(environment.baseUrl + url);
        },
        delete: function (url) {
            return $http.delete(environment.baseUrl + url);
        }
    }
}]);