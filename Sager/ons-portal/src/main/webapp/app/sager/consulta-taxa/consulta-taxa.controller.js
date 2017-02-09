(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(['$mdIconProvider', function($mdIconProvider) {
            $mdIconProvider.icon('md-close', 'img/icons/ic_close_24px.svg', 24);
        }])
        .controller('ConsultaTaxaController', ConsultaTaxaController);

    ConsultaTaxaController.$inject = ['$scope', '$state', 'ConsultaTaxa', '$filter', '$mdDialog', 'InstalacaoTaxasByInstalacaoPeriodo', '$stateParams'];

    function ConsultaTaxaController ($scope, $state, ConsultaTaxa, $filter, $mdDialog, InstalacaoTaxasByInstalacaoPeriodo, $stateParams) {
    	var vm = this;
    	$scope.listaErro = [];
    	vm.par = false;
    	
    	vm.instalacao = "usina";
    	
    	// pegando o value do input month
    	if($("#mesInicial").val() != ""){
    		$scope.mesInicial = $("#mesInicial").val();
    	}
    	if($("#mesFinal").val() != ""){
    		$scope.mesFinal = $("#mesFinal").val();
    	}
    	
    	$scope.mesInicial = $("#mesInicial").val();
    	$scope.mesFinal = $("#mesFinal").val();
        
        $scope.usinas = [];
        $scope.taxasUsinas = [];
        $scope.taxasInterligacao = [];
        vm.instalacao = "usina";
        
        vm.dtTeste = new Date();
        $scope.mesInicial = new Date();
        $scope.mesFinal = new Date();
        vm.dtInicio = new Date();
        vm.dtInicio.setHours(0,0,0,0); 	
        vm.dtFim = new Date();
        $scope.mesInicial.setHours(0,0,0,0);
        $scope.mesInicial.setDate(1);
        
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
            minDate: new Date('01/01/2001'),
        	maxDate: vm.dtFim
        };
        vm.datepickerOptionsFinal= {
            mode: 'month',
            minMode: 'month',
            maxMode: 'year',
            maxDate: new Date(),
            minDate: vm.dtInicio
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
        
        /* export to excel */
        $scope.exportToExcel=function(){
        	var wb = new Workbook();
        	
        	for (var i = 0; i < vm.instalacoesRetornadas.length; i++) {
        		var data = Array();
        		var b = 0;
        		var tableId = vm.instalacoesRetornadas[i].nome;
        		var tabela = $("[id='"+tableId+"'] th[id='head']");
        		
        		for(var c = 0; c < tabela.length;  c = c+1){
        			data[c] = Array();
            		data[0][b] = $(tabela[c]).text();
            		b = b+1;
        		}
        		
        		
            	$("[id='"+tableId+"'] tbody tr").each(function(i){
            		data[i+1] = Array();
            	    $(this).children('td').each(function(ii){
            	    	if($(this).text().trim() == "-"){
            	    		data[i+1][ii] = $("[id='"+tableId+"'] tbody tr td input[id='tooltip']").val();
            	    	} else {
            	    		var cellValue = $(this).text();
            	    		if(cellValue.match("V")){
            	    			data[i+1][ii] = cellValue;
            	    		}else{
            	    			if(cellValue.trim() != ""){
            	    				cellValue = cellValue.replace(/[^0-9]/g,'');
            	    				data[i+1][ii] = cellValue.substr(0,1) + ',' + cellValue.substr(1,cellValue.lenght);
            	    			} else {
            	    				data[i+1][ii] = cellValue;
            	    			}
            	    		}
            	    	}
            	    }); 
            	}) 
            	
	            var flagErro = false;
	
	            if(data[1].length == 0){
	          	  $scope.listaErro.push({"erroCod":"RS_MENS_015","critica":"", "mensagem":"Não existem dados para os filtros selecionados."});
	          	  flagErro = true;
	            }
            
            	if(!flagErro){
		        	var ranges = '';
		        	var ws_name = vm.instalacoesRetornadas[i].nome;     	
		        	var ws = sheet_from_array_of_arrays(data);
		        	
		        	ws['!merges'] = ranges;
		        	wb.SheetNames.push(ws_name);
		        	wb.Sheets[ws_name] = ws;
            	}
            }
            var wbout = XLSX.write(wb, {bookType:'xlsx', bookSST:false, type: 'binary'});
            saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), "TaxasCalculadas.xlsx");
        }/*- end excel-*/
		
		
        // --------------configuração para selecionar os tipos de taxas
        $scope.tipoTaxa = ['TEIP mensal','TEIP_fc mensal', 'TEIFa mensal', 'TEIFa_fc mensal', 'TEIP_oper mensal', 'TEIF_oper mensal', 'TEIP acumulada', 'TEIP_fc acumulada', 'TEIP_oper acumulada', 'TEIFa acumulada', 'TEIFa_fc acumulada', 'TEIF_oper acumulada', 'Indice de indisponibilidade'];
        $scope.taxasSelecionadas = [];
        
        $scope.toggle = function (item, list) {
          var idx = list.indexOf(item);
          if (idx > -1) {
            list.splice(idx, 1);
          }
          else {
            list.push(item);
          }
        };

        $scope.exists = function (item, list) {
          return list.indexOf(item) > -1;
        };
        
        // config dos chips
        vm.readonly = false;
        $scope.instalacoesSelected = [];
        
        //config FAB Speed Dial
        vm.versaoTaxaMostrar = [];
        
        vm.isVersaoTaxaMostrar = function(taxaMostrar){
        	return vm.versaoTaxaMostrar.indexOf(taxaMostrar) != -1;
        }
       
        vm.addVersao = function(taxaMostrar){
        	if(vm.versaoTaxaMostrar.indexOf(taxaMostrar) != -1){
        		vm.versaoTaxaMostrar = jQuery.grep(vm.versaoTaxaMostrar, function(value) {
        			  return value != taxaMostrar;
        			});
        	}else{
        		vm.versaoTaxaMostrar.push(taxaMostrar);
        	}
        }
        
        //-------Configuração para o Dialog angular Material-------
  	    $scope.data = "teste";
		$scope.gerarGrafico = function(usinasGraf, usinasTaxas, instalacaoParam){
			$scope.labels = [];
			$scope.series = [];
			$scope.data = [[],[],[],[],[],[],[],[],[],[],[],[],[],[],[]];
			$scope.options = {
		      scales: {
		        xAxes: [{
		          stacked: true,
		        }],
		        yAxes: [{
		          stacked: true
		        }]
		      }
		    };
			var event
			var usinaTx = [];
			var TaxasGraf;
			usinasTaxas.filter(function(usTx){
				if(usTx.id == usinasGraf){
					usinaTx.push(usTx);
				}
			});
			TaxasGraf = usinaTx;

			TaxasGraf = $filter('filter')(TaxasGraf, {ident : 1});
			var i =0;
			
			TaxasGraf.filter(function(taxa){
				$scope.labels.push($filter('date')(taxa.data, "MM/yyyy"));
//				$scope.labels.push(taxa.data);
				i =0;
				$scope.taxasSelecionadas.filter(function(taxaSelec){
					taxa.valores.filter(function(tx){
						if(tx.nome == taxaSelec){
							$scope.series.push(taxaSelec);
							$scope.data[i].push(tx.valor);
							i++;
						}
						
					});
				});
				
			});
			
		  	$scope.showAdvanced(event, instalacaoParam);
		}
		$scope.showAdvanced = function(ev, instalacaoParam) {
	  	    $mdDialog.show({
	  	      controller: function () { 
	  	    	$scope.hide = function() {
	  	  	      $mdDialog.hide();
	  	  	    };

	  	  	    this.cancel = function() {
	  	  	      $mdDialog.cancel();
	  	  	    };

	  	  	    
	  	    	  this.parent = $scope;
	  	    	  this.parent.instalacaoParam = instalacaoParam;
	  	    },
	  	      controllerAs: 'ctrl',
	  	      templateUrl: 'app/sager/consulta-taxa/graficoTaxas.html',
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
	  	  
	  	//------Configuração para o Angular Chart (Gráfico)
	  	vm.consultarMemoriaCalculo = function(request){
	  		request.taxasSelecionadas = $scope.taxasSelecionadas;
	  		if($stateParams.usinasBack){
	  			request.usinasGo = $stateParams.usinasBack;
	  			request.periodo = $stateParams.periodoBack;
	  		}else{
	  			request.usinasGo = vm.usinasSelecionadas;
	  			request.periodo = {inicio:$scope.mesInicial, final:$scope.mesFinal};
	  		}
	  		
	  		
	  		$state.go("consulta-memoria-calculo",request);
	  	};
	  	
	  	vm.reproduzirCalculo = function(request){
	  		console.log("request");
	  		console.log(request);
	  		request.taxasSelecionadas = $scope.taxasSelecionadas;
	  		if($stateParams.usinasBack){
	  			request.usinasGo = $stateParams.usinasBack;
	  			request.periodo = $stateParams.periodoBack;
	  		}else{
	  			request.usinasGo = vm.usinasSelecionadas;
	  			request.periodo = {inicio:$scope.mesInicial, final:$scope.mesFinal};
	  		}
	  		
	  		
	  		$state.go("reproducao-calculo",request);
	  	};
	  	
	  	//Metodos para consultar taxa
	  	vm.consultarByPeriodo = function(param, nomeUsinas){
	  		
        	InstalacaoTaxasByInstalacaoPeriodo.getByInstalacaoPeriodo(param, nomeUsinas, onSuccess);
    		
        	function onSuccess(data){
                $scope.mesFinal.setHours(0,0,0,0);
                $scope.mesInicial.setDate(1);
                $scope.mesFinal.setDate(1);
        		$scope.taxasUsinas = data;
        		vm.instalacoesRetornadas = [];
        		var idsFound = [];
    			vm.instalacoesRetornadas = [];
        		$scope.taxasUsinas = data.filter(function(taxa){
        			if(!idsFound.includes(taxa.id)){
        				idsFound.push(taxa.id);
        				vm.instalacoesRetornadas.push({id:taxa.id,nome:taxa.nomeInstalacao});
        			}
        				if(taxa.mes >= 10  ){
                			taxa.data = taxa.ano+"-"+taxa.mes+"-01";
                			return taxa;
                		}else{
                			taxa.data = taxa.ano+"-0"+taxa.mes+"-01";
                			return taxa;
                		}
        		});
        		$scope.mostrarTaxas = true;
            }
        	
        }
        
	  	
        vm.consultarTaxa = function(){
       	 
        	$scope.listaErro =[];
        	var flagErro = false;
        	function validarDataFim(){
        		if(vm.usinasSelecionadas.length < 1){
            		$scope.listaErro.push({"erroCod":"RS_MENS_002","critica":"Instalação", "mensagem":"Informações obrigatórias"});
            		flagErro = true;
            	}
        	}
        	validarDataFim();
        	if(!flagErro){

	        		var nomeUsinas = [];
	        		
	        		for(var i = 0 ; i < vm.usinasSelecionadas.length; i++){
	        			nomeUsinas.push(vm.usinasSelecionadas[i].id);
	        		}
	                $scope.mesInicial.setHours(0,0,0,0);
	                $scope.mesInicial.setDate(1);
	        		
	        		var param = {
	        				dataInicio : $scope.mesInicial,
	        				dataFim : $scope.mesFinal,
	        		}

	        		vm.consultarByPeriodo(param, nomeUsinas);
	        	
        	}
        }
	  	
        //retorno do consulta memória de calculo
        if($stateParams.usinasBack){
        	$scope.taxasSelecionadas = $stateParams.taxasSelecionadas;
	  		vm.par = true;
	  		var nomeUsinas = [];
    		for(var i = 0 ; i < $stateParams.usinasBack.length; i++){
    			nomeUsinas.push($stateParams.usinasBack[i].id);
    		}
	  		var param = {
    				dataInicio : $stateParams.periodoBack.inicio,
    				dataFim : $stateParams.periodoBack.final,
    		}
    		vm.consultarByPeriodo(param, nomeUsinas);
	  	}
    }
})();
