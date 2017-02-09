(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('TimelineDeleteController',TimelineDeleteController);

    TimelineDeleteController.$inject = ['$uibModalInstance', 'entity', 'Timeline'];

    function TimelineDeleteController($uibModalInstance, entity, Timeline) {
        var vm = this;

        vm.timeline = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Timeline.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
