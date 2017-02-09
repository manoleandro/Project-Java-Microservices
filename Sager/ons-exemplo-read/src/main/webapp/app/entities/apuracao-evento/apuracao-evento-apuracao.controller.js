(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ApuracaoEventoApuracaoController', ApuracaoEventoApuracaoController);

    ApuracaoEventoApuracaoController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CadastroEvento'];

    function ApuracaoEventoApuracaoController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CadastroEvento) {
        var vm = this;

        vm.selectedUsina = entity;
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
            CadastroEvento.saveApuracao({ usinaId: vm.selectedUsina.id, usinaVersion: vm.selectedUsina.version, mesAnoApuracao: vm.mesAnoApuracao }, {}, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('onsPortalApp:apuracaoEventoApuracao', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.mesAnoApuracao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
