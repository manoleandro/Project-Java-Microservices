(function(){
	'use strict';

    angular
        .module('onsPortalApp')
        .controller('CalculoTaxaDialogController', CalculoTaxaDialogController);
    
    CalculoTaxaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity','ScheduleService','Principal','DateUtils', 'ScheduleServiceByInstalacao', 'uiGridConstants', '$interval', 'AlertService', 'VerificarInstalacaoByDate'];
    
    function CalculoTaxaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity,ScheduleService,Principal,DateUtils, ScheduleServiceByInstalacao, uiGridConstants, $interval, AlertService, VerificarInstalacaoByDate) {
        var vm = this;

        vm.calculoTaxa = entity;
        vm.clear = clear;
        vm.today = new Date();
        vm.newAdded;
        vm.usinasSelecionadas = $stateParams.usinasSelecionadas;
        vm.mesInicial = $stateParams.mesInicial;
        vm.mesFinal = $stateParams.mesFinal;
        $scope.agendado = [];
        vm.comentarios = [];
        
        $scope.listaErro =[];
        $scope.listaSucesso = [];
        vm.validarBtnAgendar = validarBtnAgendar;
        vm.settingsAccount;
        $scope.hasComments = false;
        vm.usinasPossiveis = [];
        
        var dates = {
				dtInicio: vm.mesInicial,
				dtFim: vm.mesFinal
		};
		VerificarInstalacaoByDate.verificar(dates, vm.usinasSelecionadas, onSuccessVerificar, onErrorVerificar );
		
		function onSuccessVerificar (data){
			for (var i=0; i < data.length; i++){
				if(data[i].comentarios != null){
					vm.comentarios = vm.comentarios.concat(data[i].comentarios);
					$scope.hasComments = true;
				} 
			}
			
			data.filter(function(dt){
				if(dt.comentarios == null || dt.comentarios == undefined){
					vm.usinasPossiveis = vm.usinasPossiveis.concat(dt.usina);
				}
			});
			ScheduleServiceByInstalacao.getAgendamentos(vm.usinasPossiveis, onSuccess, onError);
	        
	        function onSuccess(data, headers) {
	        	for(var i = 0; i < data.length; i++){
	        		data[i].subGridOptions = {
        				columnDefs: [ 
        					{name:"Instalação", field:"nome"},
        					{name:"Cenários", cellTemplate: "<div>VIGENTE</div>"},
        					{name:"Situação", cellTemplate: "<div>"+situacao+"</div>"},
        					{name:"Resultado", cellTemplate: "<div>"+resultado+"</div>"}
    					],
						data: data[i].instalacoesCenarios
	        		}
		      }
	        	$scope.gridOptions.data = data;
			}

	        function onError(error) {
				AlertService.error(error.data.message);
			}
		}
		
		function onErrorVerificar (error){
			console.log(error);
		}
        
    	$scope.gridOptions = {
            enableRowSelection: false,
            enableSelectAll: false,
            selectionRowHeaderWidth: 35,
            expandableRowTemplate: 'app/sager/calculo-taxa/repeatInstalacao.html',
            rowHeight: 35,
            showGridFooter: true,
            enableFiltering: false,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                $interval(function() {
                    $scope.gridApi.core.handleWindowResize();
                }, 10, 500);
            },
            columnDefs: [
            { field: 'protocoloStr', displayName: 'Protocolo' },
//            { field: 'instalacoes', displayName: 'Instalações' },
            { field: 'dataAgendamento', displayName: 'Data Agendamento', cellFilter: 'date: "dd/MM/yyyy HH:mm"' },
            { field: 'solicitante' },
            { field: 'mesInicial', displayName: 'Mês Inicial', cellFilter: 'date: "MM/yyyy"' },
            { field: 'mesFinal', displayName: 'Mês Final', cellFilter: 'date: "MM/yyyy"' },
            { field: 'situacao', displayName: 'Situação' },
            { field: 'resultado' }
            ]
        };
        
        $scope.checkSelections = function() {
            return $scope.gridApi.selection.getSelectedCount() > 0;
        };

        $scope.unselect = function(row) {
            $scope.gridApi.selection.toggleRowSelection(row);
        }
            
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
        
        /* calendar */
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.horaAgendamento = false;
        
        vm.startDate = new Date();
        vm.startDate.setHours(0,0,0,0);
        vm.dtFim = new Date();
        vm.dtFim.setHours(23,59,59,999);
        
        vm.datepickerOptionsInicial = {
        	showWeeks: false,
        	minDate: new Date(),
            /*maxDate: new Date()*/
        };
        vm.datepickerOptionsHoraAgendamento= {
        	isMeridian: false,
			readonlyInput: false,
			showMeridian: true,
			mousewheel : true
        };
        
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        /*--end calendar--*/

	  function validarBtnAgendar() {

			var diaHoje = new Date();
			diaHoje.setHours(0);
			diaHoje.setMinutes(0);
			diaHoje.setSeconds(0);
			diaHoje.setMilliseconds(0);

			var flagErro = false;
			if (diaHoje.getTime() > vm.startDate) {
				$scope.listaErro
						.push({ "erroCod" : "RS_MENS_025", "critica" : "", "mensagem" : "A data do agendamento não pode ser anterior a data corrente" });
				flagErro = true;
			}
			if ($("#dataAgendamento").val() == "") {
				$scope.listaErro.push({ "erroCod" : "RS_MENS_002", "critica" : "Data de agendamento", "mensagem" : "Informações obrigatórias" });
				flagErro = true;
			}
			if ($("#horaAgendamento").val() == "") {
				$scope.listaErro.push({ "erroCod" : "RS_MENS_002", "critica" : "Hora de agendamento", "mensagem" : "Informações obrigatórias" });
				flagErro = true;
			}

			vm.onSuccessAgendado = function(data) {
				$scope.listaSucesso.push({"SucessoCod" : "RS_MENS_001", "critica" : "", "mensagem" : "Operação realizada com sucesso"});
				vm.newAdded = data;
				$scope.gridOptions.data.push(vm.newAdded);
			}

			vm.onErrorAgendado = function(error) {
				$scope.listaErro.push({"erroCod" : "", "critica" : "", "mensagem" : "" + error.data.message });
			}
			
			if (!flagErro) {
				var body = {};

				var dataAgend = new Date(vm.startDate);
				dataAgend.setHours(vm.today.getHours());
				dataAgend.setMinutes(vm.today.getMinutes());
				dataAgend.setSeconds(vm.today.getSeconds());
				vm.mesFinal.setHours(20,59,59,999);
				body.dataAgendamento = dataAgend;
				body.instalacoesCenarios = vm.usinasPossiveis;
				body.mesInicial = vm.mesInicial;
				body.mesFinal = vm.mesFinal;
				body.situacao = 'EM AGENDAMENTO';
				body.resultado = 'NA';
				body.solicitante = vm.settingsAccount.login;
				body.dataCriacao = new Date();
				
				var acao = { acao : 'SALVAR' }
				ScheduleService.save(acao, body, vm.onSuccessAgendado, vm.onErrorAgendado);
			}
		}

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();