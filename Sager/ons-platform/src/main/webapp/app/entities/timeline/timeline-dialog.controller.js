(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('TimelineDialogController', TimelineDialogController);

    TimelineDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Timeline', 'Record'];

    function TimelineDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Timeline, Record) {
        var vm = this;

        vm.timeline = entity;
        vm.clear = clear;
        vm.save = save;
        vm.records = Record.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.timeline.id !== null) {
                Timeline.update(vm.timeline, onSaveSuccess, onSaveError);
            } else {
                Timeline.save(vm.timeline, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('onsPortalApp:timelineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
