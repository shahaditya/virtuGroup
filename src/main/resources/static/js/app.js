(function() {

    var app = angular.module('notesApp', ['ngRoute', 'ngMaterial']);

    app.config(['$locationProvider', '$routeProvider',
        function($locationProvider, $routeProvider) {

            $routeProvider
                .when('/', {
                    templateUrl: '/partials/notes-view.html',
                    controller: 'notesController'
                })
                .when('/login', {
                    templateUrl: '/partials/login.html',
                    controller: 'loginController',
                })
                .otherwise('/');
        }
    ]);

    app.run(['$rootScope', '$location', 'AuthService', function($rootScope, $location, AuthService) {
        $rootScope.$on('$routeChangeStart', function(event) {

            if ($location.path() == "/login") {
                return;
            }

            if (!AuthService.isLoggedIn()) {
                console.log('DENY');
                event.preventDefault();
                $location.path('/login');
            }
        });
    }]);


    app.service('AuthService', function($http) {
        var loggedUser = null;

        function login(username, password) {
            return $http.post("api/login", {
                username: username,
                password: password
            }).then(function(user) {
                loggedUser = user;
            }, function(error) {
                loggedUser = null;
            })
        }

        function isLoggedIn() {
            return loggedUser != null;
        }
        return {
            login: login,
            isLoggedIn: isLoggedIn
        }
    });

    app.controller('loginController', function($scope, AuthService, $location) {

        $scope.invalidCreds = false;
        $scope.login = {
            username: null,
            password: null
        };

        $scope.login = function() {
            AuthService.login($scope.login.username, $scope.login.password).then(function(user) {
                console.log(user);
                $location.path("/");
            }, function(error) {
                console.log(error);
                $scope.invalidCreds = true;
            });
        };
    });


    app.controller('notesController', function($scope, $http) {

        $scope.isEditCreateView = false;
        $scope.notes = [];
        $scope.note = {
            name: null,
            summary: null
        };
        $scope.newNoteView = function() {
            $scope.isEditCreateView = true;
        };
        _getAllNotes();

        $scope.save = function() {
            $http({
                method: "POST",
                url: "addNote",
                data: angular.toJson($scope.note),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(_success, _error);
        }
        $scope.deleteNote = function(i) {
            var r = confirm("Are you sure you want to delete this note?");
            if (r == true) {
                //TODO delete the note
            }
        };

        $scope.viewNote = function() {


        };

        function _getAllNotes() {
            $http({
                method: 'GET',
                url: 'http://localhost:8080/getAllNotes'
            }).then(function successCallback(response) {
                $scope.notes = response.data;
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        }

        function _success(response) {
            _getAllNotes();
            _clearFormData()
        }

        function _error(response) {
            console.log(response.statusText);
        }
        //Clear the form
        function _clearFormData() {
            $scope.note.name = "";
            $scope.note.summary = "";
        };
    });

})();