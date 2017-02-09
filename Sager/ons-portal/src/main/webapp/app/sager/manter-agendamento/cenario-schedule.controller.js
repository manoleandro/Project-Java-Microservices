	(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('CenarioScheduleController', CenarioScheduleController);
    
    CenarioScheduleController.$inject = ['$scope', '$state', '$rootScope', '$stateParams', '$interval', 'entity', 'ParseLinks', 'Schedule', 'ScheduleCenariosService', 'AlertService'];

    function CenarioScheduleController($scope, $state, $rootScope, $stateParams, $interval, entity, ParseLinks, Schedule, ScheduleCenariosService, AlertService) {
    	var vm = this;
    	
    	vm.schedule = entity;
    	
    	$scope.showCenario = false;
    	$scope.listaErro = [];
		$scope.listaSucesso = [];
		vm.search = search;
		vm.datesParam = $stateParams.dates;
		$scope.agendamentos = [];
		vm.validateBeforeAction = validateBeforeAction;
		
		search();
		
	 	function cleanStatusList() {
			$scope.listaErro = [];
			$scope.listaSucesso = [];
		}
	 	function search(){
		 	ScheduleCenariosService.getAgendamentos(vm.datesParam, null, onSuccess, onError);
		 	
		 	function onSuccess(data, headers) {
				$scope.showCenario = true;
				$scope.agendamentos = data;
				$scope.gridOptions.data = data;
				vm.links = ParseLinks.parse(headers('link'));
	            vm.totalItems = headers('X-Total-Count');
	            vm.queryCount = vm.totalItems;
	        }
			function onError(error) {
				AlertService.error(error.data.message);
			}
				
		    var resultado = '<span ng-show="COL_FIELD == \'NA\'"  class="glyphicon glyphicon-minus" style="font-size: 15pt;"></span>' +
		    '<span ng-show="COL_FIELD == \'ERRO\'"  class="glyphicon glyphicon-remove" style="color: red; font-size: 15pt;"></span>' +
		    '<span ng-show="COL_FIELD == \'OK\'"  class="glyphicon glyphicon-ok" style="color: green; font-size: 15pt;"</span>';
		    
		    var situacao = '<span ng-show="COL_FIELD == \'NA\'" class="glyphicon glyphicon-minus" style="font-size: 15pt;"></span>' +
		    '<span ng-show="COL_FIELD == \'ERRO\'" class="label label-danger">Erro!</span>' +
		    '<span ng-show="COL_FIELD == \'CONCLUÍDA\'" class="label label-success">Concluída</span>';
		    
		    /*- gridTable -*/
		    $scope.gridOptions = {
		    		enableRowSelection: true,
		    		enableSelectAll: false,
		    		selectionRowHeaderWidth: 35,
		    		rowHeight: 35,
		    		multiSelect: false,
		    		enableColumnResizing: true,
		    		showGridFooter: true,
		    		enableFiltering: false,
		    		paginationPageSizes: [30, 40, 70, 80],
		    		paginationPageSize: 20,
		    		enablePaginationControls: true,
		    		onRegisterApi: function(gridApi) {
		    			$scope.gridApi = gridApi;
		    			$interval(function() {
		    				$scope.gridApi.core.handleWindowResize();
		    			}, 10, 500);
		    			$scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {  
		    				if (row.isSelected) {
		    					$scope.agendamentoSelected = [];
		    					$scope.agendamentoSelected.push(row.entity);
		    				} else {
		    					var index = $scope.agendamentoSelected.lastIndexOf(row.entity);
		    					$scope.agendamentoSelected.splice(index,1);
		    				}
		    			});
		    		},
		    		columnDefs: [
		    			{ field: 'nomeCenario', displayName: 'Cenário'},
		    			{ field: 'nomeInstalacao', displayName: 'Instalação' },
		    			{ field: 'dataCriacao', displayName: 'Data Início de Vigência', cellFilter: 'date: "MM/yyyy"'},
		    			{ field: 'dataTermino', displayName: 'Data Termino de Vigência', cellFilter: 'date: "MM/yyyy"'},
		    			{ field: 'justificativa', displayName: 'Justificativa' },
		    			{ field: 'dataAgendamento', displayName: 'Data / Hora', cellFilter: 'date: "dd/MM/yyyy HH:mm"'},
	    				{ field: 'situacao', displayName: 'Situação', cellTemplate: situacao},
		    			{ field: 'resultado', displayName: 'Resultado', cellTemplate: resultado}
    				]
		    };
	 	}
	    
	 	
	    $scope.checkSelections = function() {
	    	return $scope.gridApi.selection.getSelectedCount() > 0;
	    };
	    
	    $scope.unselect = function(row) {
	    	$scope.gridApi.selection.toggleRowSelection(row);
	    }
	        
	    /*- validar campos -*/
        function validateBeforeAction(action){
      	  vm.cleanStatusList;
      	  if ($scope.agendamentoSelected[0] == null){
      		  $scope.listaErro.push({"erroCod":"","critica":"", "mensagem":"Selecione ao menos um Agendamento"});
      	 /* } else if ($scope.agendamentoSelected[0].situacao != "AGENDADO"){
      		  $scope.listaErro.push({"erroCod":"","critica":"", "mensagem":"Esta operação só é permitida para Agendamentos com " +
      		  	"status de Agendado"});*/
      	  } else {
      		  switch (action){
      		  case "goEdit":
      			  goEdit();
      			  break;
      		  case "runNow":
      			  runNow();
      			  break;
      		  case "cancelar":
      			  cancelar();
      			  break;
      		  }
      	  }
        }
        
        function goEdit(){
        	$state.go('cenario-detail', {instalacoesReq: $scope.agendamentoSelected[0]})
        }
        
        function runNow(){
        	var acao = { acao: "REAGENDAR"};
        	var newDate = new Date();
        	
        	var agendamento = new Object();
        	agendamento.dataAgendamento = newDate;
        	agendamento.id = $scope.agendamentoSelected[0].id;
        	agendamento.jobId = $scope.agendamentoSelected[0].jobId;
        	
        	ScheduleService.save(acao, agendamento,onSuccess, onError);
        	
        	function onSuccess(data, headers) {
        		$scope.listaSucesso.push({"SucessoCod":"RS_MENS_001","critica":"", "mensagem":"Operação realizada com sucesso"});
    		}

            function onError(error) {
    			$scope.listaErro.push({"erroCod":"","critica":"", "mensagem":""+error.data.message});
    		}
            
        }
        
        function cancelar(){
	      	vm.cleanStatusList;
	      	if($scope.agendamentoSelected[0].solicitante == "cnos" && vm.settingsAccount.solicitante == "cors"){
	      		$scope.listaErro.push({"erroCod":"","critica":"", "mensagem":"Você não tem permissão para executar esta ação"});
	      	} else {
			      	var acao = { acao: "CANCELAR"};
			      	ScheduleService.getAgendamentos(acao, $scope.agendamentoSelected[0], onSuccess, onError);
			      	function onSuccess(data, headers) {
			      		$scope.listaSucesso.push({"SucessoCod":"RS_MENS_001","critica":"", "mensagem":"Operação realizada com sucesso"});
			      		var temp = [];
			      		for (var i = 0; i < $scope.agendamentos.length; i++) {
			      			if($scope.agendamentoSelected[0].id == $scope.agendamentos[i].id){
			      				$scope.agendamentos[i].situacao = "CANCELADO";
			      			}
			      			temp.push($scope.agendamentos[i]);
			      		}
			      		$scope.agendamentos = [];
			      		$scope.agendamentos = temp;
			  		}
			      	function onError(error) {
			      		$scope.listaErro.push({"erroCod":"","critica":"", "mensagem":""+error.data.message});
			  		}
	      	}
       }
    }
})();