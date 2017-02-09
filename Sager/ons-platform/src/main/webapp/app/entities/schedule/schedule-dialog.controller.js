(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ScheduleDialogController', ScheduleDialogController);

    ScheduleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Schedule'];

    function ScheduleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Schedule) {
        var vm = this;

        vm.schedule = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.schedule.id !== null) {
                Schedule.update(vm.schedule, onSaveSuccess, onSaveError);
            } else {
                Schedule.save(vm.schedule, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('onsPortalApp:scheduleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.scheduleDate = false;
        vm.datePickerOpenStatus.creationDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
