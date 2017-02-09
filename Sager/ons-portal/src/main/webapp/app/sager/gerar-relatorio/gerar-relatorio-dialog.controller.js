(function(){
	'use strict';

    angular
        .module('onsPortalApp')
        .controller('GerarRelatorioDialogController', GerarRelatorioDialogController);
    
    GerarRelatorioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity','ScheduleService','Principal','DateUtils'];
    
    function GerarRelatorioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity,ScheduleService,Principal,DateUtils) {
        var vm = this;

        vm.calculoTaxa = entity;
        vm.clear = clear;
        vm.save = save;
        vm.today = new Date();
        
        vm.usinasSelecionadas = $stateParams.usinasSelecionadas;
        vm.mesInicial = $stateParams.mesInicial;
        vm.mesFinal = $stateParams.mesFinal;
        
        $scope.listaErro =[];
        $scope.listaSucesso = [];
        vm.validarBtnAgendar = validarBtnAgendar;
        vm.settingsAccount;
        
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
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.creationDate = false;
        vm.datePickerOpenStatus.horaAgendamento = false;
        
        vm.datepickerOptionsInicial= {
        	showWeeks: false,
        	minDate: null,
            maxDate: new Date()
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
        
        function validarBtnAgendar(){
        	
        	var diaHoje = new Date();
        	diaHoje.setHours(0);
        	diaHoje.setMinutes(0);
        	diaHoje.setSeconds(0);
        	diaHoje.setMilliseconds(0);

        	var dataAgend = new Date($("#dataAgendamento").val());
        	dataAgend.setDate(dataAgend.getDate()+1);
        	dataAgend.setHours(0);
        	dataAgend.setMinutes(0);
        	dataAgend.setSeconds(0);
        	dataAgend.setMilliseconds(0);
        	
        	var flagErro = false;

            $scope.dataAgendamento = $("#dataAgendamento").val();
            $scope.horaAgendamento = $("#horaAgendamento").val();     
            
            if(diaHoje.getTime() > dataAgend.getTime()){
    	          $scope.listaErro.push({"erroCod":"RS_MENS_025","critica":"", "mensagem":"A data do agendamento não pode ser anterior a data corrente"});
    	          flagErro = true;
            }            
            if($("#dataAgendamento").val() == ""){
          	  $scope.listaErro.push({"erroCod":"RS_MENS_002","critica":"Data de agendamento", "mensagem":"Informações obrigatórias"});
          	  flagErro = true;
            }
            if($("#horaAgendamento").val() == ""){
          	  $scope.listaErro.push({"erroCod":"RS_MENS_002","critica":"Hora de agendamento", "mensagem":"Informações obrigatórias"});
          	  flagErro = true;
            }          
                        
          if( !flagErro){
        	  //Se não houve problema, então agendar
        	  
//        	  var body =  " { "+
//						  " \"dataAgendamento\": \"+vm.dataAgendamento+"\", "+
//						  " \"id\": \"110\", "+
//						  " \"idInstalacao\": \"101\", "+
//						  " \"mesFinal\": \"2016-10-20T16:53:26.869Z\", "+
//						  " \"mesInicial\": \"2016-10-20T16:53:26.869Z\", "+
//						  " \"protocolo\": \"cnos1\", "+
//						  " \"resultado\": \"OK\", "+
//						  " \"situacao\": \"situacao1\", "+
//						  " \"solicitante\": \"solicitante1\" "+
//						  " }";
        	  
        	  //setando as horas,minutos e segundos da página
        	  dataAgend.setHours($("#horaAgendamento").val().split(":")[0]);
        	  dataAgend.setMinutes($("#horaAgendamento").val().split(":")[1]);
        	  dataAgend.setSeconds($("#horaAgendamento").val().split(":")[2]);
        	  
        	  console.log('mat');
        	  console.log(vm.usinasSelecionadas);
        	  console.log(vm.usinasSelecionadas.length);
			  console.log(vm.usinasSelecionadas[0]);
        	  
        	  for (var i = 0; i < vm.usinasSelecionadas.length; i++) {

	        	  var body = new Object();
	        	  body.dataAgendamento = dataAgend;
	//        	  body.id = "" + Math.floor((Math.random() * 10) + 1); // entre 0 e 10
	        	  body.idInstalacao = ""+vm.usinasSelecionadas[i].id;
	        	  body.nomeInstalacao = ""+vm.usinasSelecionadas[i].nome;
	        	  body.mesInicial = vm.mesInicial;
	        	  body.mesFinal = vm.mesFinal;
	        	  body.situacao = 'AGENDADO';
	        	  body.resultado = 'NA';
	        	  body.solicitante = vm.settingsAccount.login;
	        	  body.dataCriacao = new Date();
        	    
	        	  var acao = {acao: 'SALVAR'}
	        	  
	        	  ScheduleService.save(acao, body,onSuccess, onError);
	      		  
        	  }
	            
	            function onSuccess(data, headers) {
//	            	$scope.gridOptions.data = 	;
	    		}
	
	            function onError(error) {
	    			$scope.listaErro.push({"erroCod":"","critica":"", "mensagem":""+error.data.message});
	    		}
	            
        	  
              if(true){
    	          $scope.listaSucesso.push({"SucessoCod":"RS_MENS_001","critica":"", "mensagem":"Operação realizada com sucesso"});
              }
              
              //agendamento duplicado
//                  if(true){
//        	          $scope.listaErro.push({"erroCod":"RS_MENS_018","critica":"Instalação: U.SOBRADINHO; Mês inicial: Março 2016; Mês final: Março 2016", "mensagem":"Agendamento duplicado para a Intalação U.SOBRADINHO no período Março 2016 - Março 2016."});
//        	          flagErro = true;
//                  }                  
          }
          
      	}        

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
//            vm.isSaving = true;
//                CalculoTaxa.update(vm.calculoTaxa, onSaveSuccess, onSaveError);
//            } else {
//                CalculoTaxa.save(vm.calculoTaxa, onSaveSuccess, onSaveError);
//            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('onsPortalApp:gerarRelatorioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();