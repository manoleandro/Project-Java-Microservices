(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('GerarRelatorioController', GerarRelatorioController);

    GerarRelatorioController.$inject = ['$scope', '$state', '$http', '$filter','Usinas','Interligacoes','GerarRelatorioService'];
    
    function GerarRelatorioController ($scope, $state, $http, $filter,Usinas,Interligacoes,GerarRelatorioService) {
        var vm = this;
        vm.instalacoes = [];
        vm.validarBtnGerarRelatorio = validarBtnGerarRelatorio;
        $scope.listaErro = [];
        vm.instalacoesSelecionada = [];
        
    	vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.teste = false;
        vm.datePickerOpenStatus.mesInicial = false;
        vm.datePickerOpenStatus.mesFinal = false;
        vm.datePickerOpenStatus.creationDate = false;
        
        vm.dtInicio = new Date();
        vm.dtFim = new Date();
        
        vm.datepickerOptionsInicial= {
    		mode: 'month',
            minMode: 'month',
            maxMode: 'year',
            minDate: new Date("01/01/2001"),
            maxDate: vm.dtFim
        };
        vm.datepickerOptionsFinal= {
    		 mode: 'month',
             minMode: 'month',
             maxMode: 'year',
             minDate: vm.dtInicio,
         	 maxDate: new Date()
        };
        // view mode picker
        
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
        
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    	
		$scope.radioTipoRelat = function(value) {
			$scope.tipoRelatorio = value;
		}

		$scope.radioTipoArquivo = function(value) {
			$scope.tipoArquivo = value;
		}
		
    	function loadAll (filtersParam, filtersBody) {
        	/*
        	var params = {
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
            };
            */
/*     	
        	var params = {
            };

        	if(filtersParam!=null) {
        		jQuery.extend(params, filtersParam);
        	}
*/  
    		
        	GerarRelatorioService.getGerarRelatorio(filtersParam, filtersBody, onSuccess, onError);
        	// DispService.getDisps(params, filtersBody, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) { 
            	for (var i=0;i<data.length;i++) 
        		{
            		var erroCod = data[i].message;
            		var mensagem = data[i].description;
            		$scope.listaErro.push({"erroCod":erroCod, "critica":"Instalação", "mensagem":mensagem});
        		}
            	if (data.length==0) $scope.listaErro.push({"erroCod":"Sem Dados", "critica":"Usinas", "mensagem":"Não há dados nas datas selecionadas"});
            	
            }
            function onError(error) {
            	alert('Erro');
            	alert(error);
            }
        }
    	
        function validarBtnGerarRelatorio(){

            // pegando o value do input month
        	if(vm.dtInicio != ""){
        		$scope.mesInicial = vm.dtInicio;
        	}
        	if(vm.dtFim != ""){
        		$scope.mesFinal = vm.dtFim;
        	}
        	
        	$scope.listaErro =[];
        	var flagErro = false;
        	
        	if(vm.instalacoesSelecionada.length < 1 && vm.instalacao == "usina"){
        		$scope.listaErro.push({"erroCod":"RS_MENS_002","critica":"Instalação", "mensagem":"Informações obrigatórias"});
        		flagErro = true;
        	}
        	
        	if($scope.mesFinal != "" && $scope.mesInicial != "" && new Date($scope.mesInicial).getTime() > new Date($scope.mesFinal).getTime()){
        		$scope.listaErro.push({"erroCod":"RS_MENS_004","critica":"", "mensagem":"Mês inicial maior que mês final"});
        		flagErro = true;
        	} 
        	if(new Date().getTime() < new Date($scope.mesInicial).getTime()){
        		$scope.listaErro.push({"erroCod":"RS_MENS_026", "critica":"Mês inicial", "mensagem":"As datas usadas nos filtros devem ser anteriores à data corrente"});
        		flagErro = true;
        	}
        	if( new Date().getTime() < new Date($scope.mesFinal).getTime()){
        		$scope.listaErro.push({"erroCod":"RS_MENS_026", "critica":"Mês final", "mensagem":"As datas usadas nos filtros devem ser anteriores à data corrente"});
        		flagErro = true;
        	}
        	if(new Date("2001-01").getTime() > new Date($scope.mesInicial).getTime()){
            	$scope.listaErro.push({"erroCod":"RS_MENS_023", "critica":"Mês inicial", "mensagem":"O mês do filtro não pode ser anterior a 01/2001"});
            	flagErro = true;
        	}
        	if(new Date("2001-01").getTime() > new Date($scope.mesFinal).getTime()){
            	$scope.listaErro.push({"erroCod":"RS_MENS_023", "critica":"Mês final", "mensagem":"O mês do filtro não pode ser anterior a 01/2001"});
            	flagErro = true;
        	}
        	if($("#mesFinal").val() == ""){
            	$scope.listaErro.push({"erroCod":"RS_MENS_002", "critica":"Mês final", "mensagem":"Informações obrigatórias"});
            	flagErro = true;
        	}
        	if($("#mesInicial").val() == ""){
            	$scope.listaErro.push({"erroCod":"RS_MENS_002", "critica":"Mês inicial", "mensagem":"Informações obrigatórias"});
            	flagErro = true;
        	}
        	
        	if($scope.tipoRelatorio==null){
            	$scope.listaErro.push({"erroCod":"RS_MENS_002", "critica":"Tipo de Relatório", "mensagem":"Informações obrigatórias"});
            	flagErro = true;
        	}
        	
        	if($scope.tipoArquivo==null){
            	$scope.listaErro.push({"erroCod":"RS_MENS_002", "critica":"Tipo de Arquivo", "mensagem":"Informações obrigatórias"});
            	flagErro = true;
        	}
        	
      	  var diaHoje = new Date();
      	  diaHoje.setDate(1);
    	  diaHoje.setHours(0);
    	  diaHoje.setMinutes(0);
    	  diaHoje.setSeconds(0);
    	  diaHoje.setMilliseconds(0);
    	  
    	  	var instalacoesReq = [];
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
			
			if(!flagErro){
			 	var filtersParam = {
		 			tipoArquivo : $scope.tipoArquivo,
		 			tipoRelatorio : $scope.tipoRelatorio,
        			dtInicio: new Date($scope.mesInicial).toISOString(),
        			dtFim: new Date($scope.mesFinal).toISOString()
	        			
	        	};
			 	var filtersBody = instalacoesReq;
			 	
			 	loadAll(filtersParam, filtersBody);	 	
			}
		
    	}
    }
})();
