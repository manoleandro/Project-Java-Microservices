(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('RetificacaoScheduleController', RetificacaoScheduleController);
    
    RetificacaoScheduleController.$inject = ['$scope', '$rootScope', '$interval', '$stateParams', 'entity', 'Schedule', 'ScheduleRetificacoesService', 'AlertService', 'ParseLinks', '$mdDialog'];

    function RetificacaoScheduleController($scope, $rootScope, $interval, $stateParams, entity, Schedule, ScheduleRetificacoesService, AlertService, ParseLinks, $mdDialog) {
    	var vm = this;
    	
    	vm.schedule = entity;
    	vm.search = search;
		vm.datesParam = $stateParams.dates;
		
		$scope.showCenario = false;
    	$scope.listaErro = [];
		$scope.listaSucesso = [];
		
		vm.validateBeforeAction = validateBeforeAction;
		
    	search();
		
	 	function cleanStatusList() {
			$scope.listaErro = [];
			$scope.listaSucesso = [];
		}
	 	$scope.agendamentos = [];
	 	
	 	vm.verLogs = function(comentarios){
			if( comentarios.length == 0)
				return;
			var comentari = [
				{"tipo":"Error","descricao":"sem motivo","dataInsercao":new Date(),"origem":"origem 1"},
				{"tipo":"Error","descricao":"sem motivo","dataInsercao":new Date(),"origem":"origem 1"},
				{"tipo":"Error","descricao":"sem motivo","dataInsercao":new Date(),"origem":"origem 1"},
				{"tipo":"Error","descricao":"sem motivo","dataInsercao":new Date(),"origem":"origem 1"},
				{"tipo":"Error","descricao":"sem motivo","dataInsercao":new Date(),"origem":"origem 1"},
				{"tipo":"Error","descricao":"sem motivo","dataInsercao":new Date(),"origem":"origem 1"},
				{"tipo":"Error","descricao":"sem motivo","dataInsercao":new Date(),"origem":"origem 1"}
				];
			$scope.showAdvanced = function(ev) {
		  	    $mdDialog.show({
		  	      controller: function () { 
		  	    	$scope.hide = function() {
		  	  	      $mdDialog.hide();
		  	  	    };

		  	  	    this.cancel = function() {
		  	  	      $mdDialog.cancel();
		  	  	    };
		  	  	    this.comentarios = comentarios;
		  	    	
		  	    },
		  	      controllerAs: 'ctrl',
		  	      templateUrl: 'app/sager/manter-agendamento/logCalculoAgendamento.html',
		  	      parent: angular.element(document.body),
		  	      targetEvent: ev,
		  	      clickOutsideToClose:true,
		  	      fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
		  	      bindToController: true,
		  	    })
		  	    .then(function(answer) {
		  	      $scope.status = 'You said the information was "' + answer + '".';
		  	    }, function() {
		  	      $scope.status = 'You cancelled the dialog.';
		  	    });
		  	  };
		  	  
		  	 var event; 
		  	$scope.showAdvanced(event);
		}
	 	
	 	 var resultado = '<span ng-show="COL_FIELD == \'NA\'" class="glyphicon glyphicon-minus" style="font-size: 15pt;"></span>' +
			'<span ng-show="COL_FIELD == \'ERRO\'" ng-click="grid.appScope.verLogs(row.entity.comentario)" class="glyphicon glyphicon-remove" style="color: red; font-size: 15pt;"></span>' +
			'<span ng-show="COL_FIELD == \'OK\'" ng-click="grid.appScope.verLogs(row.entity.comentario)" class="glyphicon glyphicon-ok" style="color: green; font-size: 15pt;"></span>';


	 	
	 	 /*- gridTable -*/
	    $scope.gridOptions = {
	    		enableRowSelection: true,
	    		enableSelectAll: false,
	    		selectionRowHeaderWidth: 35,
	    		rowHeight: 35,
	    		expandableRowTemplate: 'app/sager/manter-agendamento/expandableRowTemplate.html',
	            expandableRowHeight: 150,
	            expandableRowScope: { 
	                clickMeSub: function(){
	                  console.log('hi');
	                },
	                verLogs: vm.verLogs,
	            },
	            enableExpandableRowHeader: true,
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
	    			{ field: 'numeroTarefa', displayName: 'Número da Tarefa' },
	    			{ field: 'dataAgendamento', displayName: 'Data Agendamento', cellFilter: 'date: "dd/MM/yyyy HH:mm"'},
	    			{ field: 'situacao', displayName: 'Situação' },
//	    			{ field: 'situacao', displayName: 'Situação', cellTemplate: situacao },
	    			{ field: 'resultado', displayName: 'Resultado',	cellTemplate: resultado},
	    			{ field: 'solicitante', displayName: 'Solicitante' }
				]
	    };
	 	
    	function search(){
		 	ScheduleRetificacoesService.getAgendamentos(vm.datesParam, null, onSuccess, onError);
		 	
		 	function onSuccess(data, headers) {
		 		
		 		 for(var i = 0; i < data.length; i++){
			        data[i].subGridOptions = {
			          columnDefs: [ 
			        	  	{name:"Nome da Tarefa", field:"nomeTarefa"},
			        	  	{name:"Situacao", field:"situacao"},
			        	  	{field: 'resultado', displayName: 'Resultado', cellTemplate: resultado, cellTooltip: true } 
			          ],
			          data: data[i].comandos
			        }
			      }
	 		
				$scope.showRetificacao = true;
				$scope.agendamentos = data;
				$scope.gridOptions.data = data;
//				vm.links = ParseLinks.parse(headers('link'));
	            vm.totalItems = headers('X-Total-Count');
	            vm.queryCount = vm.totalItems;
	        }
			
			function onError(error) {
				AlertService.error(error.data.message);
			}
				
//		    var resultado = '<span ng-show="COL_FIELD == \'NA\'" class="glyphicon glyphicon-minus" style="font-size: 15pt;"></span>' +
//		    '<span ng-show="COL_FIELD == \'ERRO\'" class="glyphicon glyphicon-remove" style="color: red; font-size: 15pt;"></span>' +
//		    '<span ng-show="COL_FIELD == \'OK\'" class="glyphicon glyphicon-ok" style="color: green; font-size: 15pt;"</span>';
		    
		   
		    var situacao = '<span ng-show="COL_FIELD == \'NA\'" class="glyphicon glyphicon-minus" style="font-size: 15pt;"></span>' +
		    '<span ng-show="COL_FIELD == \'ERRO\'" class="label label-danger">Erro!</span>' +
		    '<span ng-show="COL_FIELD == \'FINALIZADO\'" class="label label-success">Finalizado</span>';
		   
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
      	  } else if ($scope.agendamentoSelected[0].situacao != "AGENDADO"){
      		  $scope.listaErro.push({"erroCod":"","critica":"", "mensagem":"Esta operação só é permitida para Agendamentos com " +
      		  	"status de Agendado"});
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