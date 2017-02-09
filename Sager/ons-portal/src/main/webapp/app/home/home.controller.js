(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal'];

    function HomeController ($scope, Principal) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
    }
})();
