    angular.module('onsPortalApp')
    .directive("uiInstalacoes",function(){
    	return{
    		templateUrl:"app/components/instalacoes/instalacoes.html",
    		restrict:"E",
    		scope:{    			
    			instalacao:"=",
    			instalacoesSelectedGrid:'=instalacoesSelecionadas',
    		},
    		 controller: ['$scope', '$state','Usinas','Interligacoes', '$http', '$filter', '$timeout', '$q', '$interval', 'AlertService', 'i18nService', 
    			 function InstalacoesControlerDirective($scope, $state, Usinas, Interligacoes,  $http, $filter, $timeout, $q, $interval, AlertService, i18nService){
			 		loadInterligacoesInternacionais();
    		    	loadUsinas();
    		    	loadInterligacoesInternacionais();
    		    	loadUsinas();

    		        $scope.taxasUsina = [];
    		        $scope.taxasInterligacao = [];
    		        
    		        $scope.instalacao = 'usina';
    				$scope.instalacoesSelected = [];
    				
    				$scope.langs = i18nService.getAllLangs();
    		        $scope.lang = 'pt-br';
    				
    				$scope.interligacaoDisabled = false;
    				$scope.usinaDisabled = false;
    				
    		        $scope.gridOptionsUsina = {
		                enableRowSelection: true,
		                enableFullRowSelection: true,
		                enableSelectAll: true,
		                multiSelect: true,
		                enableSelectionBatchEvent: false,
		                selectionRowHeaderWidth: 35,
		                rowHeight: 35,
		                enableFiltering: true,
		                enableColumnResize: true,
		                enableHorizontalScrollbar: 0, 
		                enableVerticalScrollbar: 2,
		                showGridFooter:true,
		                onRegisterApi: function(gridApi) {
		                    $scope.gridApiUsinas = gridApi;
		                    $interval(function() {
		                        $scope.gridApiUsinas.core.handleWindowResize();
		                    }, 10, 500);
		                    $scope.gridApiUsinas.selection.on.rowSelectionChanged($scope, function(row) {
		                          if (row.isSelected) {
		                        	  $scope.instalacoesSelected.push(row.entity.nome);
		                        	  $scope.instalacoesSelectedGrid.push(row.entity);
		                          } else {
		                            var index = $scope.instalacoesSelected.lastIndexOf(row.entity.nome);
		                            if (index >= 0 ){
		                            	$scope.instalacoesSelected.splice(index,1);
		                            }
		                            index = $scope.instalacoesSelectedGrid.lastIndexOf(row.entity);
		                            if (index >= 0 ){
		                            	$scope.instalacoesSelectedGrid.splice(index,1);
		                            }
		                          }
		                      });
		                },
		                columnDefs: [
    		                { field: 'nome' },
    		                { field: 'tipo_id', displayName: 'Tipo'},
    		                { field: 'agente'}
		                ]
		            };
    		        
    		        $scope.gridOptionsInterligacao = {
    		                enableRowSelection: true,
    		                enableFullRowSelection: true,
    		                enableSelectAll: true,
    		                multiSelect: true,
    		                enableSelectionBatchEvent: false,
    		                selectionRowHeaderWidth: 35,
    		                rowHeight: 35,
    		                enableFiltering: true,
    		                enableColumnResize: true,
    		                enableHorizontalScrollbar: 0, 
    		                enableVerticalScrollbar: 2,
    		                showGridFooter:true,
    		                onRegisterApi: function(gridApi) {
    		                    $scope.gridApiInterligacao = gridApi;
    		                    $interval(function() {
    		                        $scope.gridApiInterligacao.core.handleWindowResize();
    		                    }, 10, 500);
    		                    $scope.gridApiInterligacao.selection.on.rowSelectionChanged($scope, function(row) {
    		                    	if (row.isSelected) {
   		                        	  	$scope.instalacoesSelected.push(row.entity.nome);
   		                        	  	$scope.instalacoesSelectedGrid.push(row.entity);
   		                          	} else {
	   		                          	var index = $scope.instalacoesSelected.lastIndexOf(row.entity.nome);
			                            if (index >= 0 ){
			                            	$scope.instalacoesSelected.splice(index,1);
			                            }
			                            index = $scope.instalacoesSelectedGrid.lastIndexOf(row.entity);
			                            if (index >= 0 ){
			                            	$scope.instalacoesSelectedGrid.splice(index,1);
			                            }
	   		                        }
    		                      });
    		                },
    		                columnDefs: [
        		                { field: 'nome' },
        		                { field: 'nomeagente', displayName: 'Agente'}
    		                ]
    		            };
    		        
    		            

//		            $scope.checkSelections = function() {
//		                return $scope.gridApi.selection.getSelectedCount() > 0;
//		            };
//
//		            $scope.unselect = function(row) {
//		                $scope.gridApi.selection.toggleRowSelection(row);
//		            }
    		        
//    		        $scope.$watch('gridApiUsinas', function() {
//    		        	alert('gridApiUsinas: ' +  $scope.gridApiUsinas.rows.length);
//    		        }, true);
//    		        
//    		        $scope.$watch('gridApiInterligacao', function() {
//    		        	alert('gridApiInterligacao: ' +  $scope.gridApiInterligacao.rows.length);
//    		        }, true);
    		        
    		        $scope.$watch('instalacoesSelected', function() {
    		        	//If para manter iguais as listas de seleção da tab e do chip
    		            if ($scope.instalacoesSelectedGrid != undefined && $scope.instalacoesSelected.length != $scope.instalacoesSelectedGrid.length){
    		            	for (var i =0; i < $scope.instalacoesSelectedGrid.length; i++ ){
    		            		var entity = $scope.instalacoesSelectedGrid[i];
    		            		var index = $scope.instalacoesSelected.lastIndexOf(entity.nome);
    		            		if (index<0){
    		            			
    		            			if ($scope.instalacao == 'usina'){
    		            				var rowUsina = $scope.gridApiUsinas.grid.getRow(entity);
    		            				if (rowUsina !== null && rowUsina.isSelected) {
    		            					$scope.gridApiUsinas.selection.toggleRowSelection(rowUsina,null);
    		            				}
    		            				
    		            				$scope.gridApiUsinas.selection.unSelectRow(entity);    		            		
    		            				$scope.gridApiUsinas.grid.modifyRows($scope.gridOptionsUsina.data);
    		            			} else if ($scope.instalacao == 'interligacao'){
    		            				var rowInterligacao = $scope.gridApiInterligacao.grid.getRow(entity);
    		            				if (rowInterligacao !== null && rowInterligacao.isSelected) {
    		            					$scope.gridApiInterligacao.selection.toggleRowSelection(rowInterligacao,null);
    		            				}
    		            				
    		            				$scope.gridApiInterligacao.selection.unSelectRow(entity);    		            		
    		            				$scope.gridApiInterligacao.grid.modifyRows($scope.gridOptionsInterligacao.data);
    		            			}
    		            			
    		            		}
    		            	}
    		            }
    		            
    		            if ($scope.instalacoesSelected.length > 0){
    		            	if ($scope.instalacao == 'usina'){
    		            		$scope.interligacaoDisabled = true;
    		            		$scope.usinaDisabled = false;
    		            	}else{
    		            		$scope.interligacaoDisabled = false;
    		            		$scope.usinaDisabled = true;
    		            	}
    		            	
    		            }
    		            else{
    		            	$scope.interligacaoDisabled = false;
    		            	$scope.usinaDisabled = false;
    		            }
    		        }, true);
    		 
    		        
    		        //$scope.instalacao é o nome da aba selecionada
    		        $scope.alterarInstalacao = function(instalacao){
		        		$scope.instalacao = instalacao;
		            	$scope.instalacoesSelected = [];
		            	$scope.instalacoesSelectedGrid = [];
		            	$scope.gridApiInterligacao.selection.clearSelectedRows();
		            	$scope.gridApiUsinas.selection.clearSelectedRows();
    		        }
    		        
//    		        //$scope.instalacao é o nome da aba selecionada
//    		        $scope.alterarInstalacao = function(instalacao){
//    		        	if($scope.instalacao != instalacao){
//    		        		$scope.instalacao = instalacao;
//    		            	$scope.instalacoesSelected = [];
//    		            	$scope.selecionarTodasUsinas = false;
//    		            	$scope.selecionarTodasInterligacoes = false;
//    		        	}
//    		        }
    		        
//    		        $scope.selecionarTodas = function(tipo){
//    		        	$scope.instalacoesSelected = [];
//    		        	$scope.filtered = "";
//    		        	if(tipo == "usinas"){
//    		        		if($scope.buscaPorUsina != undefined && $scope.buscaPorUsina != ""){
//    		        			$scope.filtered = $filter('filter')($scope.usinas,{nome:$scope.buscaPorUsina});
//    		        		}
//    		        		if($scope.buscaPorTipoUsina != "" && $scope.buscaPorTipoUsina != undefined){
//    		        			$scope.filtered = $filter('filter')($scope.usinas,{tipo_id:$scope.buscaPorTipoUsina});
//    		        		}
//    		        		if($scope.buscaPorAgenteU != "" && $scope.buscaPorAgenteU != undefined){
//    		        			$scope.filtered = $filter('filter')($scope.usinas,{agente:$scope.buscaPorAgenteU});
//    		        		}
//    		        		
//    		        		for(var i = 0; i < $scope.usinas.length ; i++){
//    		        			$scope.usinas[i].selected = $scope.selecionarTodasUsinas;
//    		        			if( $scope.usinas[i].selected && ($scope.filtered.includes($scope.usinas[i]) || $scope.filtered == "")  ){
//    		        				$scope.instalacoesSelected.push($scope.usinas[i].nome);
//    		        			}else{
//    		        			}
//    		        		}
//    		        	} else {
//    		        		if($scope.buscaPorNomeInterligacao != undefined && $scope.buscaPorNomeInterligacao != ""){
//    		        			$scope.filtered = $filter('filter')($scope.interligacoes,{nome:$scope.buscaPorNomeInterligacao});
//    		        		}
//    		        		if($scope.buscaPorAgenteInterligacao != "" && $scope.buscaPorAgenteInterligacao != undefined){
//    		        			$scope.filtered = $filter('filter')($scope.interligacoes,{nomeagente:$scope.buscaPorAgenteInterligacao});
//    		        		}
//    		        		
//    		        		for(var i = 0; i < $scope.interligacoes.length ; i++){
//    		        			$scope.interligacoes[i].selected = !$scope.selecionarTodasInterligacoes;
//    		        			if($scope.interligacoes[i].selected && $scope.filtered.includes($scope.interligacoes[i])){
//    		        				$scope.instalacoesSelected.push($scope.interligacoes[i].nome);
//    		        			}else{
//    		        				
//    		        			}
//    		        		}
//    		        	}
//    		        }
    		        
    		        function loadUsinas() {
    		        	Usinas.query({
    		                size: $scope.itemsPerPage,
    		                sort: sort()
    		            }, onSuccess, onError);
    		            function sort() {
    		                var result = [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc')];
    		                if ($scope.predicate !== 'id') {
    		                    result.push('id');
    		                }
    		                return result;
    		            }
    		            function onSuccess(data, headers) {
    		                //$scope.links = ParseLinks.parse(headers('link'));
    		                $scope.totalItems = headers('X-Total-Count');
    		                $scope.queryCount = $scope.totalItems;
    		                $scope.usinas = data;
    		                $scope.gridOptionsUsina.data = data;
    		            }
    		            function onError(error) {
    		                AlertService.error(error.data.message);
    		            }
    		        }
    		        
    		        function loadInterligacoesInternacionais() {
    		            
    		        	Interligacoes.query({
    		                size: $scope.itemsPerPage,
    		                sort: sort()
    		            }, onSuccess, onError);
    		            function sort() {
    		                var result = [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc')];
    		                if ($scope.predicate !== 'id') {
    		                    result.push('id');
    		                }
    		                return result;
    		            }
    		            function onSuccess(data, headers) {
//    		                $scope.links = ParseLinks.parse(headers('link'));
    		                $scope.totalItems = headers('X-Total-Count');
    		                $scope.queryCount = $scope.totalItems;
    		                $scope.interligacoes = data;
    		                $scope.gridOptionsInterligacao.data = data;
    
    		            }
    		            function onError(error) {
//    		                AlertService.error(error.data.message);
    		            }
    		        } 
    		        
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
    		          
    		          //config dos chips
    		          $scope.readonly = false;
    		          $scope.instalacoesSelected = [];
    		          $scope.instalacoesSelectedGrid = [];
    		        
    		          $scope.instalacao = "usina";
//    		          $scope.instalacao = $scope.instalacoesSelected;
    		 }]
    	}
    });
