(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ScenarioController', ScenarioController);

    ScenarioController.$inject = ['$scope', '$state', 'Scenario'];

    function ScenarioController ($scope, $state, Scenario) {
        var vm = this;
        
        vm.scenarios = [];

        loadAll();

        function loadAll() {
            Scenario.query(function(result) {
                vm.scenarios = result;
            });
        }
    }
})();
