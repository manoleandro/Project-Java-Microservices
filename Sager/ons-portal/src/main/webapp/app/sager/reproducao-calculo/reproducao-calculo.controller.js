(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(['$mdIconProvider', function($mdIconProvider) {
            $mdIconProvider.icon('md-close', 'img/icons/ic_close_24px.svg', 24);
        }])
        .controller('ReproducaoCalculoController', ReproducaoCalculoController);

    ReproducaoCalculoController.$inject = ['$scope', '$state', '$http', '$filter', '$stateParams', '$mdDialog', '$interval', 'MemoriaCalculo', 'EventoService', 'ReproducaoCalculoService', 'ObjectDiff'];

    function ReproducaoCalculoController ($scope, $state, $http, $filter, $stateParams, $mdDialog, $interval, MemoriaCalculo, EventoService, ReproducaoCalculoService, ObjectDiff) {
    	var vm = this;
    	console.log("$stateParams");
    	console.log($stateParams);

    	console.log($stateParams.instalacaoEquip);
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
    		console.log("Data");
    		console.log(data);
    		$scope.unidadeOperando = 0;
    		vm.memoria1 = data.memoriaCalculoV1;
    		vm.memoria2 = data.memoriaCalculoV2;
    		
    		vm.diffAll = ObjectDiff.diff(data.memoriaCalculoV1, data.memoriaCalculoV2);
    		console.log("diferença");
    		console.log(vm.diffAll);
    		console.log("diffAll");
    		console.log(vm.diffAll);
    		
    		$scope.unidadesGeradoras = data;
    		var flagPri = 0;
    		var diffAllArray = [];	  
    		if($stateParams.regulamentacao == "1"){
    			$.each(vm.diffAll.value, function(i,n) {
        			if(n.changed == "equal"){
        				vm.memoria1[i].resultado="Igual";
        				
        			}else{
        				vm.memoria1[i].resultado="Diferente";
        			}
        			if(vm.memoria1[i].ident == 1){
        				vm.memoria1[i].parametros = "";
        				vm.memoria1[i].valor1 = vm.memoria1[i].valor;
        				vm.memoria1[i].valor2 = vm.memoria2[i].valor;
        				vm.memoria1[i].data1 = vm.memoria1[i].data;
        				vm.memoria1[i].idsEventos = vm.memoria1[i].taxaEventosId;
        				vm.memoria1[i].idsEventos2 = vm.memoria2[i].taxaEventosId;
        			}else if(vm.memoria1[i].ident == 2){
        				vm.memoria1[i].parametros = "Potência";
        				vm.memoria1[i].valor1 = vm.memoria1[i].equipamento.valorPotencia;
        				vm.memoria1[i].valor2 = vm.memoria2[i].equipamento.valorPotencia;
        				vm.memoria1[i].participacao = vm.memoria1[i].equipamento.participacao;
        				vm.memoria1[i].idsEventos = vm.memoria1[i].equipamento.equipamentosEventosId;
        				vm.memoria1[i].idsEventos2 = vm.memoria2[i].equipamento.equipamentosEventosId;
        			}else if(vm.memoria1[i].ident == 3){
        				vm.memoria1[i].parametros = vm.memoria2[i].param.nomeParametro;
        				vm.memoria1[i].valor1 = vm.memoria1[i].param.valorParametro;
        				vm.memoria1[i].valor2 = vm.memoria2[i].param.valorParametro; 
        				vm.memoria1[i].idsEventos = vm.memoria1[i].param.paramEventosId;
        				vm.memoria1[i].idsEventos2 = vm.memoria2[i].param.paramEventosId;
        			}
        			vm.memoria1[i].$$treeLevel = vm.memoria1[i].ident - 1;
        		});
    		}else{
    			$.each(vm.diffAll.value, function(i,n) {
        			if(n.changed == "equal"){
        				vm.memoria1[i].resultado="Igual";
        				
        			}else{
        				vm.memoria1[i].resultado="Diferente";
        			}
        			if(vm.memoria1[i].ident == 1){
        				vm.memoria1[i].parametros = "";
        				vm.memoria1[i].valor1 = vm.memoria1[i].valor;
        				vm.memoria1[i].valor2 = vm.memoria2[i].valor;
        				vm.memoria1[i].data1 = vm.memoria1[i].data;
        				vm.memoria1[i].idsEventos = vm.memoria1[i].taxaEventosId;
        				vm.memoria1[i].idsEventos2 = vm.memoria2[i].taxaEventosId;
        			}else if(vm.memoria1[i].ident == 2){
        				vm.memoria1[i].parametros = "Potência";
        				vm.memoria1[i].valor1 = vm.memoria1[i].equipamento.valorPotencia;
        				vm.memoria1[i].valor2 = vm.memoria2[i].equipamento.valorPotencia;
        				vm.memoria1[i].participacao = vm.memoria1[i].equipamento.participacao;
        				vm.memoria1[i].idsEventos = vm.memoria1[i].equipamento.equipamentosEventosId;
        				vm.memoria1[i].idsEventos2 = vm.memoria2[i].equipamento.equipamentosEventosId;
        			}else if(vm.memoria1[i].ident == 3){
        				vm.memoria1[i].parametros = vm.memoria2[i].param.nomeParametro;
        				vm.memoria1[i].valor1 = vm.memoria1[i].param.valorParametro;
        				vm.memoria1[i].valor2 = vm.memoria2[i].param.valorParametro; 
        				vm.memoria1[i].idsEventos = vm.memoria1[i].param.paramEventosId;
        				vm.memoria1[i].idsEventos2 = vm.memoria2[i].param.paramEventosId;
        			}
        			vm.memoria1[i].$$treeLevel = vm.memoria1[i].ident - 1;
        		});
    		}
    		

    		
    		console.log(vm.memoria1);
    		var testem = [{teste:"dfsd",ui:5},{teste:"dgdf",ui:20}]
    		console.log(diffAllArray);
    		console.log("vm.diffAll.value");
    		$scope.gridOptionsMemoria.data = vm.memoria1;
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
    		
    		ReproducaoCalculoService.getByInstalacaoDataTaxa(param,'', onSuccess, onError);
    		
        	    		
    	}
    	 
    	/* export to excel */
    	
		
		
		
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
		
		
		var resultado = '<div ng-if="row.entity.resultado == \'Diferente\'"> <button ui-sref-active="active"'+ 
			'has-any-authority="ROLE_CNOS,ROLE_COSR_NE,ROLE_ADMIN"'+
			'ng-click="grid.appScope.mostrarEventos(row.entity.idsEventos, row.entity.idsEventos2)" class="btn-dt btn btn-success">'+
			'<span class="fa fa-eye"></span>'+
			'</button>'+
			'<md-tooltip md-direction="up">Visualizar Eventos</md-tooltip> </div>';

		var template = '<div ng-class="{ \'redRow\':row.entity.resultado==\'Diferente\' }"><div ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name" class="ui-grid-cell" ng-class="{ \'ui-grid-row-header-cell\': col.isRowHeader }" ui-grid-cell></div> </div>';
		if($stateParams.regulamentacao == "1"){
			$scope.gridOptionsMemoria = {
				    enableSorting: true,
				    rowTemplate: template,
				    enableFiltering: true,
				    showTreeExpandNoChildren: true,
				    columnDefs: [
				      { name: 'data1',   displayName: 'Mês', cellFilter: 'date: "yyyy/MM"'},
				      { name: 'equipamento.id',  displayName: 'Unidade Geradora'},
				      { name: 'parametros', displayName: 'Parâmetro'},
				      { name: 'valor1',  displayName: 'Valor'},
				      { name: 'valor2',  displayName: 'Valor (Reprodutibilidade)'},
				      { name: 'resultado',  displayName: 'Resultado', headerCellClass:'mostrarDiferentes'},
				      { name: 'eventos',  displayName: 'Eventos', cellTemplate: resultado,
			            	cellTooltip: true }
				    ],
				    onRegisterApi: function( gridApi ) {
				      $scope.gridApi = gridApi;
				    }
				  };
		}else{
			$scope.gridOptionsMemoria = {
				    enableSorting: true,
				    rowTemplate: template,
				    enableFiltering: true,
				    showTreeExpandNoChildren: true,
				    columnDefs: [
				      { name: 'equipamento.id',  displayName: 'Unidade Geradora'},
				      { name: 'data1',   displayName: 'Mês', cellFilter: 'date: "yyyy/MM"'},
				      { name: 'parametros', displayName: 'Parâmetro'},
				      { name: 'valor1',  displayName: 'Valor'},
				      { name: 'valor2',  displayName: 'Valor (Reprodutibilidade)'},
				      { name: 'resultado',  displayName: 'Resultado', headerCellClass:'mostrarDiferentes'},
				      { name: 'eventos',  displayName: 'Eventos', cellTemplate: resultado,
			            	cellTooltip: true }
				    ],
				    onRegisterApi: function( gridApi ) {
				      $scope.gridApi = gridApi;
				    }
				  };
		} 
		
		vm.mostrarDiferentes = function(mostrarTodosBollean){
			
			if(mostrarTodosBollean){
				$scope.gridApi.grid.columns[6].filters[0].term = "Diferente";
			}else{
				$scope.gridApi.grid.columns[6].filters[0].term = "";
			}
		}
		 
		 $scope.expandAll = function(){
		    $scope.gridApi.treeBase.expandAllRows();
		  };
		 
		  $scope.toggleRow = function( rowNum ){
		    $scope.gridApi.treeBase.toggleRowTreeState($scope.gridApi.grid.renderContainers.body.visibleRowCache[rowNum]);
		  };
		 
		  $scope.toggleExpandNoChildren = function(){
		    $scope.gridOptionsMemoria.showTreeExpandNoChildren = !$scope.gridOptionsMemoria.showTreeExpandNoChildren;
		    $scope.gridApi.grid.refresh();
		  };
		
		$scope.mostrarEventos = function(idsEv, idsEv2){
			console.log("ids eventos");
			console.log(idsEv, idsEv2);
			
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
                vm.eventos1 = data;
                EventoService.getById({
            		ids:idsEv2
                }, $stateParams.usinasGo, onSuccessEv2, onErrorEv);
            }
            function onSuccessEv2(data, headers) {
                $scope.totalItems = headers('X-Total-Count');
                $scope.queryCount = $scope.totalItems;
//              $scope.gridOptions.data = data;
                console.log("result");
                console.log(data);
                vm.eventos2 = data;
                vm.diffEventos = ObjectDiff.diff(vm.eventos1, vm.eventos2);
                $scope.gridOptions.data =vm.diffEventos;
                
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
	    	vm.usinaSelected = $stateParams.usinasGo.filter(function(usina){
	    		if(usina.id = $stateParams.instalacaoEquip.id){
	    			return usina
	    		}
	    	});
    }
    
    
})();