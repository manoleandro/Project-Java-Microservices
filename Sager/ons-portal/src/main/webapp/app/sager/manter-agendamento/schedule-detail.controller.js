(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ScheduleDetailController', ScheduleDetailController);

    ScheduleDetailController.$inject = ['$state', '$scope', '$rootScope', '$stateParams', 'entity', 'Schedule', '$uibModalInstance', 'ScheduleService'];

    function ScheduleDetailController($state, $scope, $rootScope, $stateParams, entity, Schedule, $uibModalInstance, ScheduleService) {
        var vm = this;

        vm.clear = clear;
        vm.schedule = entity;
        vm.agendamento = $stateParams.instalacoesReq;
        vm.update = update;
        $scope.listaErro = [];
        $scope.listaSucesso = [];
        
        vm.horaPopUp = new Date(vm.agendamento.dataAgendamento);
        
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
        	
        	newDate.setHours(vm.horaPopUp.getHours());
        	newDate.setMinutes(vm.horaPopUp.getMinutes());
        	newDate.setSeconds(vm.horaPopUp.getSeconds());
        	
        	var agendamento = new Object();
        	agendamento.dataAgendamento = newDate;
        	agendamento.id = vm.agendamento.id;
        	agendamento.jobId = vm.agendamento.jobId;
        	agendamento.solicitante = vm.agendamento.solicitante;
        	
        	ScheduleService.save(acao, agendamento,onSuccess, onError);
        	
        	function onSuccess(data, headers) {
//            	$scope.gridOptions.data = 	;
        		$scope.listaSucesso.push({"SucessoCod":"RS_MENS_001","critica":"", "mensagem":"Operação realizada com sucesso"});
        		$stateParams.newDate = data;
        		$uibModalInstance.dismiss('cancel');
    		}

            function onError(error) {
    			$scope.listaErro.push({"erroCod":"","critica":"", "mensagem":""+error.data.message});
    		}
            
        }
        
        $scope.$on('$destroy', unsubscribe);
    }
})();
