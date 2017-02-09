(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('CalcTaxaScheduleController', CalcTaxaScheduleController);
    
    CalcTaxaScheduleController.$inject = ['$scope', '$rootScope', '$state', '$window', '$stateParams', 'entity', 'Principal','Schedule', 'paginationConstants', 'ParseLinks', '$mdDialog', 'AgendamentoCalculoTaxaByDate', 'AlertService', '$interval', 'ScheduleService', 'CalculoTaxaService', 'i18nService', '$translate', 'AgendamentoCalculoByProtocolo'];
    
    function CalcTaxaScheduleController($scope, $rootScope, $state, $window, $stateParams, entity, Principal, Schedule, paginationConstants, ParseLinks, $mdDialog, AgendamentoCalculoTaxaByDate, AlertService, $interval, ScheduleService, CalculoTaxaService, i18nService, $translate, AgendamentoCalculoByProtocolo) {
    	var vm = this;
    	
    	if(!$stateParams.idBusca){
    		loadCalcTaxa();
    	}
    	
    	$scope.langs = i18nService.getAllLangs();
        $scope.lang = 'pt-br';
    	
    	vm.schedule = entity;
    	$scope.calcTaxaAgenda = false;
		vm.searchCalcTaxa = searchCalcTaxa;
		vm.validateBeforeAction = validateBeforeAction;
		
		vm.cleanStatusList = cleanStatusList;
		vm.calculoSchedules = [];
		vm.reprocessSchedules = [];
		vm.scenarioSchedules = [];
		vm.commands = [];
		$scope.instalacoesSelected = [];
		$scope.agendamentoSelected = [];
		$scope.message = false;
		vm.settingsAccount;
		
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
						var listaComentarios = [];
			  	    	$scope.hide = function() {
			  	  	      $mdDialog.hide();
			  	  	    };

			  	  	    this.cancel = function() {
			  	  	      $mdDialog.cancel();
			  	  	    };
			  	  	    for (var c = 0; c < comentarios.length; c ++){
			  	  	    	listaComentarios = listaComentarios.concat(comentarios[c].listaComentarios);
			  	  	    }
			  	  	    this.listaComentarios = listaComentarios;
			  	  	    this.comentarios = comentarios;
					},
					controllerAs: 'ctrl',
					templateUrl: 'app/sager/manter-agendamento/logCalculo.html',
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
		vm.verCenarioLogs = function(comentarios){
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
		
		var resultadoCenario = '<span ng-show="COL_FIELD == \'NA\'" class="glyphicon glyphicon-minus" style="font-size: 15pt;"></span>' +
		'<span ng-show="COL_FIELD == \'ERRO\'" ng-click="grid.appScope.verCenarioLogs(row.entity.comentario)" class="glyphicon glyphicon-remove" style="color: red; font-size: 15pt;"></span>' +
		'<span ng-show="COL_FIELD == \'OK\'" ng-click="grid.appScope.verCenarioLogs(row.entity.comentario)" class="glyphicon glyphicon-ok" style="color: green; font-size: 15pt;"></span>';
		
		$scope.gridOptions = {
			appScopeProvider: vm,
			enableRowSelection: true,
			/*enableFullRowSelection: true,*/
            enableSelectAll: false,
            expandableRowTemplate: 'app/sager/manter-agendamento/expandableRowTemplate.html',
            expandableRowHeight: 150,
            expandableRowScope: { 
                clickMeSub: function(){
                },
                verLogs: vm.verLogs,
                verCenarioLogs: vm.verCenarioLogs,
            },
            enableExpandableRowHeader: true,
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
	            { field: 'protocoloStr', displayName: 'Protocolo', cellTooltip: true },
//	            { field: 'nomeInstalacao', displayName: 'Instalação' },
	            { field: 'dataAgendamento', cellFilter: 'date: "dd/MM/yyyy HH:mm"', cellTooltip: true },
	            { field: 'solicitante', cellTooltip: true },
	            { field: 'mesInicial', displayName: 'Mês Inicial', cellFilter: 'date: "MM/yyyy"', cellTooltip: true },
	            { field: 'mesFinal', displayName: 'Mês Final', cellFilter: 'date: "MM/yyyy"', cellTooltip: true },
	            { field: 'situacao', displayName: 'Situação', cellTooltip: true },
	        	{ field: 'resultado', displayName: 'Resultado', cellTemplate: resultado, cellTooltip: true }
            ]
        };

        $scope.checkSelections = function() {
            return $scope.gridApi.selection.getSelectedCount() > 0;
        };

        $scope.unselect = function(row) {
            $scope.gridApi.selection.toggleRowSelection(row);
        }
        
    	function cleanStatusList(){
			$scope.listaErro = [];
			$scope.listaSucesso = [];
		}
    	
		var copyAccount = function(account) {
			return {
				activated : account.activated,
				email : account.email,
				firstName : account.firstName,
				langKey : account.langKey,
				lastName : account.lastName,
				login : account.login
			};
		};

		Principal.identity().then(function(account) {
			vm.settingsAccount = copyAccount(account);
		});
		
		function loadCalcTaxa(){
			CalculoTaxaService.query(null, onSuccess, onError);
			
			function onSuccess(data, headers) {
				for(var i = 0; i < data.length; i++){
			        data[i].subGridOptions = {
		        	  enableFiltering: true,
			          columnDefs: [
			        	  {name:"Instalação", field:"nome"},
			        	  {name:"Nome do Cenário", field:"nomeCenario"},
			        	  {name:"Situacao", displayName: 'Situação', field:"situacao"},
			        	  {field: 'resultado', displayName: 'Resultado', cellTemplate: resultadoCenario, cellTooltip: true }
		        	  ],
		        	  data: data[i].instalacoesCenarios
	        	  }
				}
				$scope.calcTaxaAgenda = true;
				$scope.agendamentos = data;
				$scope.gridOptions.data = data;
				$scope.message = true;
				vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
            }
			
			function onError(error) {
				AlertService.error(error.data.message);
				$scope.listaErro.push({"erroCod":"","critica":"", "mensagem":"Erro ao buscar agendamentos"});
				$window.scrollTo(0, angular.element('mensagemErro').offsetTop);
			}
		}
		
		function searchCalcTaxa() {
			$scope.message = false;
			$scope.agendamentoSelected = [];
			vm.cleanStatusList;
			
			var instalacoesReq = [];
		 	var instalacao;
		 	var instalacao;
		 	for (var i = 0; i < vm.instalacoesSelecionada.length; i++) {
		 		var instalacaoSelecionada = vm.instalacoesSelecionada[i];
		 		instalacao = new Object();
				instalacao.nome = instalacaoSelecionada.nome;
				instalacao.id = instalacaoSelecionada.id;
				instalacao.equipamentos = instalacaoSelecionada.equipamentos;
				instalacao.minorVersion = instalacaoSelecionada.minorVersion
				instalacoesReq.push(instalacao);	
		 	}
		 	
		 	/* - tratando datas -*/
		 	var dtInicioFrom = $('#field_startDate').val().split("/");
		 	var dtFimFrom = $('#field_endDate').val().split("/");
		 	var dtInicio = new Date(dtInicioFrom[2], dtInicioFrom[1]-1, dtInicioFrom[0]);
		 	var dtFim = new Date(dtFimFrom[2], dtFimFrom[1]-1, dtFimFrom[0]);
		 	dtFim.setHours(23,59,59,999);
		 	/*--*/
		 	
		 	var dates = {
				dtInicio: dtInicio,
				dtFim: dtFim
			}; 
		 	/*- request -*/
			if(instalacoesReq.length != 0) {
				AgendamentoCalculoTaxaByDate.getByInstalacaoPeriodo(dates, instalacoesReq, onSuccess, onError);
				function onSuccess(data, headers) {
					 for(i = 0; i < data.length; i++){
				        data[i].subGridOptions = {
				          columnDefs: [ 
				        	  	{name:"Nome do Cenário", field:"nomeCenario"},
				        	  	{name:"Instalação", field:"nome"},
				        		{name:"Situacao", displayName: 'Situação', field:"situacao"},
				        	  	{field: 'resultado', displayName: 'Resultado', cellTemplate: resultadoCenario, cellTooltip: true } 
				          ],
				          data: data[i].instalacoesCenarios
				        }
				      }
					
					$scope.calcTaxaAgenda = true;
					$scope.agendamentos = data;
					$scope.gridOptions.data = data;
					vm.links = ParseLinks.parse(headers('link'));
	                vm.totalItems = headers('X-Total-Count');
	                vm.queryCount = vm.totalItems;
	            }
				
				function onError(error) {
					AlertService.error(error.data.message);
				}
				
			} else {
				$scope.listaErro.push({"erroCod":"RS_MENS_002","critica":"Instalação", "mensagem":"Informações obrigatórias não informadas: Instalação"});
			}
		}
          
          /*- validar campos -*/
          function validateBeforeAction(action){
        	  if ($scope.agendamentoSelected[0] == null){
        		  $scope.listaErro.push({"erroCod":"","critica":"", "mensagem":"Selecione ao menos um Agendamento"});
        		  $window.scrollTo(0, angular.element('mensagemErro').offsetTop);
        	  } else if ($scope.agendamentoSelected[0].situacao != "AGENDADO"){
        		  $scope.listaErro.push({"erroCod":"","critica":"", "mensagem":"Esta operação só é permitida para Agendamentos com " +
        		  	"status de Agendado"});
        		  $window.scrollTo(0, angular.element('mensagemErro').offsetTop);
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
        	  $state.go('schedule-detail', {instalacoesReq: $scope.agendamentoSelected[0]});
          }
          
          function runNow(){
          	var acao = { acao: "REAGENDAR"};
          	var newDate = new Date();
          	
          	var agendamento = new Object();
          	agendamento.dataAgendamento = newDate;
          	agendamento.id = $scope.agendamentoSelected[0].id;
          	agendamento.jobId = $scope.agendamentoSelected[0].jobId;
          	agendamento.solicitante = vm.settingsAccount.login;
          	
          	ScheduleService.save(acao, agendamento,onSuccess, onError);
          	
          	function onSuccess(data, headers) {
          		$scope.listaSucesso.push({"SucessoCod":"RS_MENS_001","critica":"", "mensagem":"Operação realizada com sucesso"});
          		$window.scrollTo(0, angular.element('mensagemSucesso').offsetTop);
          		var temp = [];
	      		for (var i = 0; i < $scope.agendamentos.length; i++) {
	      			if($scope.agendamentoSelected[0].id == $scope.agendamentos[i].id){
	      				$scope.agendamentos[i].situacao = "EM EXECUÇÃO";
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
          
          function cancelar(){
        	vm.cleanStatusList;
        	if($scope.agendamentoSelected[0].solicitante == "cnos" && vm.settingsAccount.solicitante == "cors"){
        		$scope.listaErro.push({"erroCod":"","critica":"", "mensagem":"Você não tem permissão para executar esta ação"});
        	} else {
		      	var acao = { acao: "CANCELAR"};
		      	
		      	ScheduleService.getAgendamentos(acao, $scope.agendamentoSelected[0], onSuccess, onError);
		      	
		      	function onSuccess(data, headers) {
		      		$scope.listaSucesso.push({"SucessoCod":"RS_MENS_001","critica":"", "mensagem":"Operação realizada com sucesso"});
		      		$window.scrollTo(0, angular.element('mensagemSucesso').offsetTop);
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
		      		$window.scrollTo(0, angular.element('mensagemErro').offsetTop);
	      		}
        	}
         }
		/*- END Agendamento Calculo Taxa -*/
          
//          Alteração do Notificação          
          if($stateParams.idBusca){
        	 var param = {protocoloStr:$stateParams.idBusca}
        	 AgendamentoCalculoByProtocolo.agendamentosCalculoByProtocolo(param, onSuccess, onError);
  			function onSuccess(data, headers) {
  				console.log(data);
//  				for(var i = 0; i < data.length; i++){
			        data.subGridOptions = {
			          columnDefs: [ 
			        	  	{name:"Nome do Cenário", field:"nomeCenario"},
			        	  	{name:"Instalação", field:"nome"},
			        		{name:"Situacao", displayName: 'Situação', field:"situacao"},
			        	  	{field: 'resultado', displayName: 'Resultado', cellTemplate: resultadoCenario, cellTooltip: true } 
			          ],
			          data: data.instalacoesCenarios	
			        }
//			      }
  				
				$scope.calcTaxaAgenda = true;
				$scope.agendamentos = [];
				$scope.agendamentos.push(data);
				$scope.gridOptions.data = [];
				$scope.gridOptions.data.push(data);
            }
			function onError(error) {
				AlertService.error(error.data.message);
			}
          }
//          Fim do Notificação
	}
})();