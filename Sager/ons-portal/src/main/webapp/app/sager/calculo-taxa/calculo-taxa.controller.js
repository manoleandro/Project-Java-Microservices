(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('CalculoTaxaController', CalculoTaxaController);

    CalculoTaxaController.$inject = ['$scope', '$state', '$window'];
    
    function CalculoTaxaController ($scope, $state, $window) {
        var vm = this;
        vm.instalacoes = [];
        vm.validarBtnAgendarCalculo = validarBtnAgendarCalculo;
        $scope.listaErro = [];
        
        vm.instalacoesSelecionada = [];
        
        $scope.mesInicial = $("#mesInicial").val();
        $scope.mesFinal = $("#mesFinal").val();

        // pegando o value do input month
    	if($("#mesInicial").val() != ""){
    		$scope.mesInicial = $("#mesInicial").val();
    	}
    	if($("#mesFinal").val() != ""){
    		$scope.mesFinal = $("#mesFinal").val();
    	}
    	
    	
    	$scope.mesInicial = new Date();
        $scope.mesFinal = new Date();
        
    	vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.teste = false;
        vm.datePickerOpenStatus.mesInicial = false;
        vm.datePickerOpenStatus.mesFinal = false;
        vm.datePickerOpenStatus.creationDate = false;
        
        vm.datepickerOptionsInicial= {
            mode: 'month',
            minMode: 'month',
            maxMode: 'year',
            minDate: new Date("01/01/2001"),
            maxDate: $scope.mesFinal
        };
        vm.datepickerOptionsFinal= {
            mode: 'month',
            minMode: 'month',
            maxMode: 'year',
            //minDate: new Date("01/01/2001"),
            minDate: $scope.mesInicial,
        	maxDate: new Date()
        };
        
        $scope.changeDate = function () {
            // min max datesc
        	if($scope.mesInicial){
        		vm.datepickerOptionsFinal.minDate = $scope.mesInicial;
        		vm.datepickerOptionsInicial.maxDate = $scope.mesFinal;
        	}else{
        		vm.datepickerOptionsFinal.minDate = $scope.mesFinal;
        	}
            if($scope.mesFinal){
            	$scope.mesFinal.setHours(23,59,59,999);
            }else{
            	$scope.mesFinal.setHours(23,59,59,999);
            }
            
        }
        
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    	
        
        function validarBtnAgendarCalculo(){
        	$scope.listaErro =[];
        	var flagErro = false;
        	
        	
        	
        	if(vm.instalacoesSelecionada.length < 1){
        		$scope.listaErro.push({"erroCod":"RS_MENS_002","critica":"Instalação", "mensagem":"Informações obrigatórias"});
        		flagErro = true;
        	}
        	
        	
        	
      	  var diaHoje = new Date($scope.mesInicial);
      	  diaHoje.setDate(1);
    	  diaHoje.setHours(0);
    	  diaHoje.setMinutes(0);
    	  diaHoje.setSeconds(0);
    	  diaHoje.setMilliseconds(0);
    	  
          var instalacoesReq = [];
          var instalacao;
          for (var i = 0; i < vm.instalacoesSelecionada.length; i++) {
	 		var instalacaoSelecionada = vm.instalacoesSelecionada[i];
	 		instalacao = {};
			instalacao.nome = instalacaoSelecionada.nome;
			instalacao.id = instalacaoSelecionada.id;
			instalacao.equipamentos = instalacaoSelecionada.equipamentos;
			instalacao.minorVersion = instalacaoSelecionada.minorVersion
			instalacoesReq.push(instalacao);	
          }
          
          if(!flagErro){
        	  $scope.mesToSend = new Date($scope.mesFinal); 
        	  $scope.mesToSend.setMonth($scope.mesFinal.getMonth() + 1);
        	  $scope.mesToSend.setDate(1);
        	  $scope.mesToSend.setHours(0);
        	  $scope.mesToSend.setMinutes(0);
        	  $scope.mesToSend.setSeconds(0);
        	  $scope.mesToSend.setMilliseconds(0);
        	  $scope.mesToSend.setTime($scope.mesToSend.getTime() - 1);
        	  
        	  $state.go('calculo-taxa.new',{usinasSelecionadas: instalacoesReq, mesInicial: diaHoje, mesFinal: $scope.mesToSend });
          } else {
        	  $window.scrollTo(0, angular.element('mensagemErro').offsetTop);
          }
    	}
    }
})();
