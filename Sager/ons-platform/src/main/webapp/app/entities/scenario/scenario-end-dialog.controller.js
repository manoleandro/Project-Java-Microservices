(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ScenarioEndController',ScenarioEndController);

    ScenarioEndController.$inject = ['$uibModalInstance', 'entity', 'items', 'Scenario', '$log'];

    function ScenarioEndController($uibModalInstance, entity, items, Scenario, $log) {
        var vm = this;

        vm.scenario = entity;
        vm.clear = clear;
        vm.confirmEnd = confirmEnd;
        
        vm.makeDefault = items.makeDefault;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmEnd (id, makeDefault) {
            Scenario.end({id: id, makeDefault: makeDefault}, {},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
