(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ScenarioDialogController', ScenarioDialogController);

    ScenarioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Scenario', 'CadastroUsina'];

    function ScenarioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Scenario, CadastroUsina) {
        var vm = this;

        vm.scenario = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        vm.cadastroUsinas = [];
        loadAllUsinas();
        vm.selectUsina = selectUsina;
        
        function loadAllUsinas() {
            CadastroUsina.query(function(result) {
                vm.cadastroUsinas = result;
            });
        }
       
        function selectUsina() {
        	vm.scenario.aggregateId = vm.selectedUsina.id;
        	vm.scenario.aggregateName = vm.selectedUsina.nomeCurto;
        }
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.scenario.id !== null) {
                Scenario.update(vm.scenario, onSaveSuccess, onSaveError);
            } else {
                Scenario.save(vm.scenario, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('onsPortalApp:scenarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.creationDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
