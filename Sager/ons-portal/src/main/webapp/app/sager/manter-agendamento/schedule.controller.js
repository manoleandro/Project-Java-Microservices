(function() {
    'use strict';

	angular
		.module('onsPortalApp')
		.controller('ScheduleController', ScheduleController);

	ScheduleController.$inject = ['$window', '$scope', '$state', 'Schedule', 'AgendamentoCalculoTaxaByDate', 'AlertService', 'ScheduleService', 'Principal', '$mdDialog', 'pagingParams', 'paginationConstants', 'ParseLinks', '$stateParams'];

	function ScheduleController($window, $scope, $state, Schedule, AgendamentoCalculoTaxaByDate, AlertService, ScheduleService, Principal, $mdDialog, pagingParams, paginationConstants, ParseLinks, $stateParams) {
		var vm = this;
		var page = "";
		var instalacao;
		var filtersBody;
		var datesToGo;
		
		$scope.listaErro = [];
		$scope.listaSucesso = [];
		
		vm.cleanStatusList = cleanStatusList;
		vm.calculoSchedules = [];
		vm.reprocessSchedules = [];
		vm.scenarioSchedules = [];
		vm.commands = [];
		$scope.instalacoesSelected = [];
		$scope.agendamentoSelected = [];
		vm.settingsAccount;
		vm.search = search;
		
		 /*- calendar - */
		vm.dtInicio = new Date();
        vm.dtInicio.setHours(0,0,0,0);
        vm.dtFim = new Date();
        vm.dtFim.setHours(23,59,59,999);
        
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.creationDate = false;
        
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login
            };
        };

        Principal.identity().then(function(account) {
            vm.settingsAccount = copyAccount(account);
        });
        
        vm.datepickerOptionsInicial= {
        	showWeeks: false,
        	minDate: new Date("01/01/2001"),
        	maxDate: vm.dtFim
        };
        vm.datepickerOptionsFinal= {
        	showWeeks: false,
        	minDate: vm.dtInicio
        };
        
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        $scope.changeDate = function () {
            // min max datesc
        	if(vm.dtInicio){
        		vm.datepickerOptionsFinal.minDate = vm.dtInicio;
        		vm.datepickerOptionsInicial.maxDate = vm.dtFim;
        	}else{
        		vm.datepickerOptionsFinal.minDate = vm.dtFim;
        	}
            if(vm.dtFim){
            	vm.dtFim.setHours(23,59,59,999);
            }else{
            	vm.dtFim.setHours(23,59,59,999);
            }
            
        }
        /* - calendar -*/
        loadCommands();
        
		function cleanStatusList(){
			$scope.listaErro = [];
			$scope.listaSucesso = [];
		}

		function search() {
			datesToGo = {
					dtInicio: vm.dtInicio,
					dtFim: vm.dtFim,
				};
			vm.cleanStatusList;
			if (page != ""){
				$scope.listaErro = [];
				switch (page) {
				case "retificacao-schedule":
					$state.go(page, {dates: datesToGo});
					$scope.page = " - Retificação";
					break;
				case "cenario-schedule":
					$state.go(page, {dates: datesToGo});
					$scope.page = " - Cenário";
					break;
				default:
					$scope.page = "";
					break;
				};
			} else {
				$scope.listaErro.push({"erroCod":"RS_MENS_002","critica":"Origem do Agendamento", "mensagem":"Informações obrigatórias não informadas: Origem do Agendamento"});
				$window.scrollTo(0, angular.element('mensagemErro').offsetTop);
			}
		}
		
		
		function loadCommands() {
			vm.commands = [ {
				url: 'calculo-taxa-schedule',
				description : 'Cálculo de Taxas',
				//className : 'br.org.ons.sager.CalcularTaxasCommand'
				className : 'calculo-taxa-schedule'
			}, {
				url: 'retificacao-schedule',
				description : 'Retificação',
//				className : 'br.org.ons.platform.ReprocessCommand'
				className : 'retificacao-schedule'
			}, {
				url: 'cenario-schedule',
				description : 'Construção de Cenário',
				className : 'cenario-schedule'
			} ];
			$scope.url = vm.commands.url;
		}
		
		// hide search button
		page = $("#searchCommand").val();
		if(page == null){
			$scope.calcTaxa = true;
		}
		//****
		
		
		// change page according to select
		$('#searchCommand').change(function() {
			page = $("#searchCommand").val();
			if(page == "calculo-taxa-schedule"){
				$state.go(page);
				$scope.calcTaxa = true;
				$scope.page = " - Cálculo de Taxa";
			} else {
				$state.go("manter-agendamento");
				$scope.calcTaxa = false;
				$scope.page = "";
			};
		});
		
		if($stateParams.idBusca){
			console.log("Id busca");
			vm.searchCommand = "calculo-taxa-schedule";
			$state.go(vm.searchCommand,{nstalacoesReq: $scope.agendamentoSelected[0], dates: datesToGo, idBusca: $stateParams.idBusca});
		}
	}
})();
