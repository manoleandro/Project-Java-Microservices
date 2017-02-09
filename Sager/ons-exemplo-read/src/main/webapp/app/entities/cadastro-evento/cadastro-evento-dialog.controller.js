(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('CadastroEventoDialogController', CadastroEventoDialogController);

    CadastroEventoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'minDate'];

    function CadastroEventoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, minDate) {
        var vm = this;

        vm.cadastroEvento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        
        vm.datepickerOptions = {minDate: minDate};

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.cadastroEvento.dirty = true;
            $uibModalInstance.close(vm.cadastroEvento);
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataVerificada = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
