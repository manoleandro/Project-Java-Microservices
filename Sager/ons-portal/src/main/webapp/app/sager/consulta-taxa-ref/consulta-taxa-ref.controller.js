(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ConsultaTaxaRefController', ConsultaTaxaRefController);

    ConsultaTaxaRefController.$inject = ['$scope', '$http', '$state', 'TaxaRef','AlertService',
    	'$timeout', 'uiGridExporterService', 'uiGridExporterConstants','$interval'];

    function ConsultaTaxaRefController ($scope, $http, $state, TaxaRef, AlertService, 
    		$timeout, uiGridExporterService, uiGridExporterConstants, $interval) {
        var vm = this;  
        vm.transition = transition;
        $scope.listaErro = [];
        
        loadAll();
        loadTaxaRefInternacional();

        $scope.gridOptionsUsina = {
        		enableFiltering: true,
        	    enableSelectAll: true,
        	    exporterCsvFilename: 'Taxas_de_Referencia.csv',
        	    exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
                showGridFooter:true,
                onRegisterApi: function(gridApi) {
                    $scope.gridApiUsinas = gridApi;
                    $interval(function() {
                        $scope.gridApiUsinas.core.handleWindowResize();
                    }, 10, 500);
                },
                columnDefs: [
	                { field: 'usi', displayName: 'Usina'},
	                { field: 'dtRef', displayName: 'Mês de Referência', cellFilter: 'date: "yyyy/MM"'},
	                { field: 'teif', displayName: 'Teif', cellFilter: 'number:8', filterCellFiltered:true, cellClass: 'grid-align'},
	                { field: 'ip', displayName: 'Ip', cellFilter: 'number:8', filterCellFiltered:true, cellClass: 'grid-align'}
                ]
            };
        
        $scope.gridOptionsInterligacao = {
        	    enableSelectAll: true,
        	    exporterCsvFilename: 'Taxas_de_Referencia.csv',
        	    exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
                showGridFooter:true,
                onRegisterApi: function(gridApi) {
                    $scope.gridApInterligacao = gridApi;
                    $interval(function() {
                        $scope.gridApInterligacao.core.handleWindowResize();
                    }, 10, 500);
                },
                columnDefs: [
	                { field: 'usi', displayName: 'Interligação Internacional'},
	                { field: 'dtRef', displayName: 'Mês de Referência', cellFilter: 'date: "yyyy/MM"'},
	                { field: 'teif', displayName: 'Teif', cellFilter: 'number:8', filterCellFiltered:true, cellClass: 'grid-align'},
	                { field: 'ip', displayName: 'Ip', cellFilter: 'number:8', filterCellFiltered:true, cellClass: 'grid-align'}
                ]
            };
        
       vm.exportToExcel = function() {
    	   
		   var grid = $scope.gridApiUsinas.grid;
		   var rowTypes = uiGridExporterConstants.VISIBLE;
		   var colTypes = uiGridExporterConstants.VISIBLE;
		   uiGridExporterService.csvExport(grid, rowTypes, colTypes);
        }
    
        
        
        
        function loadAll () {
        	
        	var param = {
        					'tipoInstalacao':'USINA'
        				};
        	
            TaxaRef.query(param,{sort: sort()}, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data) {
            	$scope.gridOptionsUsina.data = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
        function loadTaxaRefInternacional () {
            
        	var param = {
        					'tipoInstalacao':'INTERLIGACAO_INTERNACIONAL'
        				};
        	
        	TaxaRef.query(param,{sort: sort()}, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data) {
            	$scope.gridOptionsInterligacao.data = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }        


        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
