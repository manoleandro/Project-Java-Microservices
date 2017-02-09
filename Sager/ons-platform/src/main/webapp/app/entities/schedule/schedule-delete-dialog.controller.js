(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ScheduleDeleteController',ScheduleDeleteController);

    ScheduleDeleteController.$inject = ['$uibModalInstance', 'entity', 'Schedule'];

    function ScheduleDeleteController($uibModalInstance, entity, Schedule) {
        var vm = this;

        vm.schedule = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Schedule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
