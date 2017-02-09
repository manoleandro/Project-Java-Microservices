(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('RecordDialogController', RecordDialogController);

    RecordDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Record', 'Timeline'];

    function RecordDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Record, Timeline) {
        var vm = this;

        vm.record = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.timelines = Timeline.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.record.id !== null) {
                Record.update(vm.record, onSaveSuccess, onSaveError);
            } else {
                Record.save(vm.record, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('onsPortalApp:recordUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.timelineDate = false;
        vm.datePickerOpenStatus.creationDate = false;

        vm.setContent = function ($file, record) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        record.content = base64Data;
                        record.contentContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
