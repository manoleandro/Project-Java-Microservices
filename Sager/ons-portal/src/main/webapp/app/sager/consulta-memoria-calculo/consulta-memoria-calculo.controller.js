(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(['$mdIconProvider', function($mdIconProvider) {
            $mdIconProvider.icon('md-close', 'img/icons/ic_close_24px.svg', 24);
        }])
        .controller('ConsultaMemoriaCalculoController', ConsultaMemoriaCalculoController);

    ConsultaMemoriaCalculoController.$inject = ['$scope', '$state', '$http', '$filter', '$stateParams', '$mdDialog', '$interval', 'MemoriaCalculo', 'EventoService'];

    function ConsultaMemoriaCalculoController ($scope, $state, $http, $filter, $stateParams, $mdDialog, $interval, MemoriaCalculo,EventoService) {
    	var vm = this;
    	console.log("$stateParams");
    	console.log($stateParams);
    	vm.taxasSelecionadasBack = $stateParams.taxasSelecionadas;
	 	vm.usinasBack = $stateParams.usinasGo;
	 	vm.periodoBack = $stateParams.periodo;
    	$scope.instalacao = $stateParams.instalacao;
    	$scope.idInstalacao = $stateParams.idInstalacao;
    	$scope.versaoTaxa = $stateParams.versaoTaxa;
    	$scope.versaoCenario = $stateParams.versaoCenario;
		$scope.taxaSelecionada = $stateParams.taxa;
		$scope.dataRef =  new Date($stateParams.dataRef.substring(0,8)+"01 00:00:00");
		$scope.mesInicial = new Date(($scope.dataRef.getFullYear()-5)+"-"+($scope.dataRef.getMonth()+1)+'-01 00:00:00');
		$scope.regulamentacao = $stateParams.regulamentacao;
		$scope.tipoTaxa = $stateParams.tipoTaxa;
    	function onSuccess(data){
    		// criando o campo de data com ano/mes/dia e adicionando
			// ao $scope
    		$scope.unidadeOperando = 0;
    		$scope.unidadesGeradoras = data;
    		var flagPri = 0;
    		$scope.unidadesGeradoras.filter(function(uni){
    			if(uni.ident == 1 && $scope.regulamentacao == 1){
    				flagPri++;
    			}
    			if(flagPri <=1 && uni.ident == 2 && $scope.regulamentacao == 1){
    				$scope.unidadeOperando++;
    			}
    			if(uni.ident == 1 && $scope.regulamentacao == 2){
    				$scope.unidadeOperando++;
    			}
    		});
    		$scope.mostrarEquipamentos = [];
    		
        }
    	
    	function onError(){//
    	}
    	
    	if($stateParams.dataRef){
    		
    		var param = {
    				instalacao: $scope.idInstalacao,
    				versaoTaxa: $scope.versaoTaxa,
    				versaoCenario: $scope.versaoCenario,
    				dataApuracao : $scope.dataRef,
    				taxaMemoriaCalculo : $scope.taxaSelecionada,
    		}
    		
    		MemoriaCalculo.getByInstalacaoDataTaxa(param,'', onSuccess, onError);
    		
        	
    		
    		

    		
        	vm.todos = false;
        	vm.linha = [];
        	
        	
        	vm.isLinha = function(Linha){
            	return vm.linha.indexOf(Linha) != -1;
            }
        	
        	$scope.mostrarLinha = [];
        	
            vm.addLinha = function(Linha){
            	if(vm.linha.indexOf(Linha) != -1){
            		vm.linha = jQuery.grep(vm.linha, function(value) {
            			  return value != Linha;
            			});
            	}else{
            		vm.linha.push(Linha);
            	}
            	
            }
        	    		
    	}
    	 
    	/* export to excel */
		$scope.exportToExcel=function(tableId){
			
        	var data = Array();
        	var b = 0;
        	$("[id='"+tableId+"'] #head th").each(function(i){
        		data[i] = Array();
        		data[0][b] = $(this).text();
        		b = b+1;
        	})
        	data[0][b-1] = "";
        	if($scope.regulamentacao == 1){
        		$.each($scope.unidadesGeradoras, function(i, v) {
        			console.log(v);
        			if(v.ident == 1){
        				data[i+1] = Array();
                		data[i+1][1] = $filter('date')(v.data, "MM/yyyy" );
                		data[i+1][2] = "";
                		data[i+1][3] = v.tipoTaxa;
                		data[i+1][4] = v.valor;
                		data[i+1][5] = "";
        			}else if(v.ident == 2){
        				data[i+1] = Array();
                		data[i+1][1] = "";
                		data[i+1][2] = v.equipamento.nome;
                		data[i+1][3] = "Potencia";
                		data[i+1][4] = v.equipamento.valorPotencia;
                		data[i+1][5] = v.equipamento.participacao;
        			}else if(v.ident == 3){
        				data[i+1] = Array();
                		data[i+1][1] = "";
                		data[i+1][2] = "";
                		data[i+1][3] = v.param.nomeParametro;
                		data[i+1][4] = v.param.valorParametro;
                		data[i+1][5] = "";
        			}
            		
            	});
        	}else if($scope.regulamentacao == 2){
        		$.each($scope.unidadesGeradoras, function(i, v) {
	        		if(v.ident == 1){
	    				data[i+1] = Array();
	            		data[i+1][1] = v.equipamento.nome;
	            		data[i+1][2] = "";
	            		data[i+1][3] = "";
	            		data[i+1][4] = "";
	            		data[i+1][5] = v.equipamento.participaca;
	    			}else if(v.ident == 2){
	    				data[i+1] = Array();
	            		data[i+1][1] = "";
	            		data[i+1][2] = $filter('date')(v.data, "MM/yyyy" );
	            		data[i+1][3] = "Potencia";
	            		data[i+1][4] = v.equipamento.valorPotencia;
	            		data[i+1][5] = "";
	    			}else if(v.ident == 3){
	    				data[i+1] = Array();
	            		data[i+1][1] = "";
	            		data[i+1][2] = "";
	            		data[i+1][3] = v.param.nomeParametro;
	            		data[i+1][4] = v.param.valorParametro;
	            		data[i+1][5] = "";
	    			}
        		});
        	}
        		
            var flagErro = false;
        	
            if(data[1].length == 0){
          	  $scope.listaErro.push({"erroCod":"RS_MENS_015","critica":"", "mensagem":"Não existem dados para os filtros selecionados."});
          	  flagErro = true;
            }        	
        	if(!flagErro){
	        	var ranges = '';
	        	var ws_name = tableId;     	
	        	var wb = new Workbook(), ws = sheet_from_array_of_arrays(data);
	        	ws['!merges'] = ranges;
	        	wb.SheetNames.push(ws_name);
	        	wb.Sheets[ws_name] = ws;
	        	var wbout = XLSX.write(wb, {bookType:'xlsx', bookSST:false, type: 'binary'});
	        	saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), "Memória_de_calculo_"+tableId+".xlsx");
        	}
		}
    	
		
		
		
    	//-------Configuração para o Dialog angular Material-------
        
  	    $scope.data = "teste";
		$scope.gerarGraficoPorTaxa = function(){
			$scope.labels = [];
			$scope.series = ['Series A', 'Series B'];
			$scope.data = [[],[],[],[]];
			var event;
						
			$scope.unidadesGeradoras.filter(function(unidade){
				if(unidade.ident == 1){
					$scope.labels.push($filter('date')(unidade.data, "MM/yyyy" ));
					$scope.data[0].push(unidade.valor);
				}
			});
					
		  	$scope.showAdvanced(event);
		}
		
		vm.mostrarEventos = function(idsEv){
			console.log("ids eventos");
			console.log(idsEv);
			
			$scope.gridOptions = {
	        		enableFiltering: true,
	        		enableGridMenu: true,
	        	    enableSelectAll: true,
	        	    exporterCsvFilename: 'Eventos.csv',
	        	    exporterPdfDefaultStyle: {fontSize: 9},
	        	    exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
	        	    exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
	        	    exporterPdfHeader: { text: "My Header", style: 'headerStyle' },
	        	    exporterPdfFooter: function ( currentPage, pageCount ) {
	        	      return { text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
	        	    },
	        	    exporterPdfCustomFormatter: function ( docDefinition ) {
	        	      docDefinition.styles.headerStyle = { fontSize: 22, bold: true };
	        	      docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
	        	      return docDefinition;
	        	    },
	        	    exporterPdfOrientation: 'portrait',
	        	    exporterPdfPageSize: 'LETTER',
	        	    exporterPdfMaxGridWidth: 500,
	        	    exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
	        	    onRegisterApi: function(gridApi){
	        	      $scope.gridApi = gridApi;
	        	    },
	                showGridFooter:true,
	                onRegisterApi: function(gridApi) {
	                    $scope.gridApiUsinas = gridApi;
	                    $interval(function() {
	                        $scope.gridApiUsinas.core.handleWindowResize();
	                    }, 10, 500);
	                },
	                columnDefs: [
		                { field: 'nomeInstalacao', displayName: 'Instalação' },
		                { field: 'codigoONSEquipamento', displayName: 'Unidade Geradora'},
		                { field: 'dataVerificada', displayName: 'Data/Hora', cellFilter: 'date: "yyyy/MM/dd HH:mm"'},
		                { field: 'estadoOperativo', displayName: 'Estado Operativo'},
		                { field: 'condicaoOperativa', displayName: 'Condição Operativa'},
		                { field: 'origem', displayName: 'Origem'},
		                { field: 'valorPotenciaDisponivel', displayName: 'Disponibilidade', cellClass: 'grid-align', cellFilter: 'number: 3'}
	                ]
	            };
			
//			var instalacoesReq = [];
//			instalacoesReq.push($scope.instalacao);
			
			EventoService.getById({
        		ids:idsEv
            }, $stateParams.usinasGo, onSuccessEv, onErrorEv);
            
            function onSuccessEv(data, headers) {
                $scope.totalItems = headers('X-Total-Count');
                $scope.queryCount = $scope.totalItems;
//                $scope.gridOptions.data = data;
                console.log("result");
                console.log(data);
                $scope.gridOptions.data = data;
            }
            function onErrorEv(error) {
            }
			var event;
			$scope.showEventos(event);
			
			
		}
		
		$scope.showEventos = function(ev) {
	  	    $mdDialog.show({
	  	      controller: function () { 
	  	    	$scope.hide = function() {
	  	  	      $mdDialog.hide();
	  	  	    };
	  	  	    
	  	  	    this.cancel = function() {
	  	  	      $mdDialog.cancel();
	  	  	    };
	  	  	    
	  	  	    this.parent = $scope; 
	  	  	    
	  	  	
	  	    },
	  	      controllerAs: 'ctrl',
	  	      templateUrl: 'app/sager/consulta-memoria-calculo/eventosFromMemoriaCalculo-Dialog.html',
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
		
		$scope.gerarGraficoPorParametro =  function(){
			
			$scope.labels = [];
			$scope.series = [];
			
			var event;
			$scope.parametros = [];
			$scope.datasParam = [];
			$scope.equipamentos = [];
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
			
		  	$scope.showGraficoParametro(event);
		}
		
		//Por taxa
		$scope.showAdvanced = function(ev) {
	  	    $mdDialog.show({
	  	      controller: function () { 
	  	    	$scope.hide = function() {
	  	  	      $mdDialog.hide();
	  	  	    };
	  	  	    
	  	  	    this.cancel = function() {
	  	  	      $mdDialog.cancel();
	  	  	    };
	  	  	    
	  	  	    this.parent = $scope; 
	  	  	    
	  	  	    
	  	    },
	  	      controllerAs: 'ctrl',
	  	      templateUrl: 'app/sager/consulta-memoria-calculo/graficoMemoriaCalculo.html',
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
	  	
	  	  vm.lerParam = function(ctr, flagPrimeiraUnidade){
	  		for(var cont = 0; cont < ctr.parent.unidadesGeradoras.length ; cont ++){
				
				if(ctr.parent.unidadesGeradoras[cont].ident == 3){
					flagPrimeiraUnidade = false;
					if(!ctr.parent.series.includes(ctr.parent.unidadesGeradoras[cont].param.nomeParametro)){
						ctr.parent.series.push(ctr.parent.unidadesGeradoras[cont].param.nomeParametro);						
					}
					
				}
			}
	  	  }
	  	  
	  	  //Por parametro
	  	$scope.showGraficoParametro = function(ev) {
	  	    $mdDialog.show({
	  	      controller: function () { 
	  	    	 var ctr = this;
	  	    	 var flagPrimeiraUnidade = true;
	  	    	 
	  	    	 
	  	    	$scope.hide = function() {
	  	  	      $mdDialog.hide();
	  	  	    };
	  	  	    ctr.cancel = function() {
	  	  	      $mdDialog.cancel();
	  	  	    };	  	  	    
	  	  	    ctr.parent = $scope; 
	  	  	    
	  	  	    ctr.parent.equipamentos;
	  	  	    
	  	  	    if($scope.regulamentacao == 2){
	  	  	    	
	  	  	    	//loop resgatar todos os equipamentos
	  	  	    	ctr.parent.equipamentos = [];
		  	  	    ctr.parent.unidadesGeradoras.filter(function(unidade){
						if(unidade.ident == 1){
							ctr.parent.equipamentos.push(unidade.equipamento.nome);
						}
					});
		  	  	    ctr.parent.equipamentoSelecionado = ctr.parent.equipamentos[0];

		  	  	    //loop para resgatar os $scope.parametros
		  	  	    vm.lerParam(ctr, flagPrimeiraUnidade);
			  	  	
			  	  	$scope.data = [];
		  	  		ctr.parent.series.filter(function(){
		  	  			$scope.data.push([]);
		  	  		});
			  	  	ctr.parent.unidadesGeradoras.filter(function(unidade){
						if(unidade.ident == 2 && ctr.parent.equipamentoSelecionado == unidade.equipamento.nome){
							ctr.parent.labels.push( $filter('date')(unidade.data, "MM/yyyy" ));
						}
						if(unidade.ident == 3 && ctr.parent.equipamentoSelecionado == unidade.equipamentoNome){
							ctr.parent.data[ctr.parent.series.indexOf(unidade.param.nomeParametro)].push(unidade.param.valorParametro);
						}
					});
			  	  	
			  	  	ctr.changeUnidadeGeradora = function(){
			  	  		ctr.parent.labels = [];
				  	  	$scope.data = [];
			  	  		ctr.parent.series.filter(function(){
			  	  			$scope.data.push([]);
			  	  		});
				  	  	ctr.parent.unidadesGeradoras.filter(function(unidade){
							if(unidade.ident == 2 && ctr.parent.equipamentoSelecionado == unidade.equipamento.nome){
								ctr.parent.labels.push( $filter('date')(unidade.data, "MM/yyyy" ));
							}
							if(unidade.ident == 3 && ctr.parent.equipamentoSelecionado == unidade.equipamentoNome){
								ctr.parent.data[ctr.parent.series.indexOf(unidade.param.nomeParametro)].push(unidade.param.valorParametro);
							}
						});
			  	  	}
	  	  	    	
	  	  	    	
	  	  	    } else if($scope.regulamentacao == 1){
	  	  	    	//loop resgatar todos os equipamentos
		  	  	    ctr.parent.unidadesGeradoras.filter(function(unidade){
						if(unidade.ident == 2 && !ctr.parent.equipamentos.includes(unidade.equipamento.nome)){
							ctr.parent.equipamentos.push(unidade.equipamento.nome);
						}
					});
		  	  	    ctr.parent.equipamentoSelecionado = ctr.parent.equipamentos[0];

		  	  	    //loop para resgatar os $scope.parametros
			  	  	for(var contador = 0; contador < ctr.parent.unidadesGeradoras.length ; contador ++){
						if(ctr.parent.unidadesGeradoras[contador].ident == 2 && !flagPrimeiraUnidade){
							break;
						}
						if(ctr.parent.unidadesGeradoras[contador].ident == 3){
							flagPrimeiraUnidade = false;
							ctr.parent.series.push(ctr.parent.unidadesGeradoras[contador].param.nomeParametro);
						}
					}
			  	  	
			  	  	$scope.data = [];
		  	  		ctr.parent.series.filter(function(){
		  	  			$scope.data.push([]);
		  	  		});
			  	  	ctr.parent.unidadesGeradoras.filter(function(unidade){
						if(unidade.ident == 1){
							ctr.parent.labels.push($filter('date')(unidade.data, "MM/yyyy" ));
						}
						if(unidade.ident == 3 && ctr.parent.equipamentoSelecionado == unidade.equipamentoNome && ctr.parent.series.indexOf(unidade.param.nomeParametro) != -1){
							ctr.parent.data[ctr.parent.series.indexOf(unidade.param.nomeParametro)].push(unidade.param.valorParametro);
							
						}
					});
			  	  
			  	  	
			  	  	ctr.changeUnidadeGeradora = function(){
			  	  		ctr.parent.labels = [];
			  	  		$scope.data = [];
			  	  		ctr.parent.series.filter(function(){
			  	  			$scope.data.push([]);
			  	  		});	
			  	  		
				  	  	ctr.parent.unidadesGeradoras.filter(function(unidade){
							if(unidade.ident == 1){
								ctr.parent.labels.push($filter('date')(unidade.data, "MM/yyyy"));
							}
							if(unidade.ident == 3 && ctr.parent.equipamentoSelecionado == unidade.equipamentoNome){
								ctr.parent.data[ctr.parent.series.indexOf(unidade.param.nomeParametro)].push(unidade.param.valorParametro);
							}
						});
			  	  	}
	  	  	    }
	  	  	    
	  	  	    
		  	  	
	  	  	    
	  	    },
	  	      controllerAs: 'ctrl',
	  	      templateUrl: 'app/sager/consulta-memoria-calculo/graficoMemoriaCalculoParametro.html',
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

	  	  
	    	$scope.backConsultarTaxas = function(){
	    		
	    		var paramsConsultarTaxa;
	    		if($stateParams.usinasGo){
	    			paramsConsultarTaxa = {
		    				taxasSelecionadas : vm.taxasSelecionadasBack,
		    				usinasBack : vm.usinasBack, 
		    				periodoBack : vm.periodoBack
		    		};
	    			
	    		}else if($stateParams.idBusca){
	    			paramsConsultarTaxa = {
	    					idBusca:$stateParams.idBusca
	    			};
	    		}
	    		$state.go("consulta-taxa", paramsConsultarTaxa);
	    		
	    	}
	    	
	  	  
    }
    
    
})();