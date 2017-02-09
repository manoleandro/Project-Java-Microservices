(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('CenarioDetailController', CenarioDetailController);

    CenarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Schedule', '$uibModalInstance', 'ScheduleService'];

    function CenarioDetailController($scope, $rootScope, $stateParams, entity, Schedule, $uibModalInstance, ScheduleService) {
        var vm = this;

        vm.clear = clear;
        vm.schedule = entity;
        vm.agendamento = $stateParams.instalacoesReq;
        vm.update = update;
        $scope.listaErro = [];
        $scope.listaSucesso = [];
        
        console.log(vm.agendamento);
        var unsubscribe = $rootScope.$on('onsPortalApp:scheduleUpdate', function(event, result) {
            vm.schedule = result;
        });
        
        /* - calendar - */
		vm.dataPopUp = new Date(vm.agendamento.dataAgendamento);
        vm.dataPopUp.setHours(0,0,0,0); 	
        
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.creationDate = false;
        
        vm.datepickerOptionsPop= {
        	showWeeks: false,
        	minDate: new Date()
        };
        
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        
        
        function update(){
        	var acao = { acao: "REAGENDAR"};
        	var newDate = vm.dataPopUp;
        	
        	var agendamento = new Object();
        	agendamento.dataAgendamento = newDate;
        	agendamento.id = vm.agendamento.id;
        	agendamento.jobId = vm.agendamento.jobId;
        	
        	ScheduleService.save(acao, agendamento,onSuccess, onError);
        	
        	function onSuccess(data, headers) {
        		$scope.listaSucesso.push({"SucessoCod":"RS_MENS_001","critica":"", "mensagem":"Operação realizada com sucesso"});
        		$uibModalInstance.dismiss('cancel');
    		}

            function onError(error) {
    			$scope.listaErro.push({"erroCod":"","critica":"", "mensagem":""+error.data.message});
    		}
        }
        
        $scope.$on('$destroy', unsubscribe);
    }
})();
