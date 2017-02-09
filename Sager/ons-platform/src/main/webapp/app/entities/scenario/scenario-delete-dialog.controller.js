(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ScenarioDeleteController',ScenarioDeleteController);

    ScenarioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Scenario'];

    function ScenarioDeleteController($uibModalInstance, entity, Scenario) {
        var vm = this;

        vm.scenario = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Scenario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
