'use strict';

angular.module('sagerApp.instalacaoChooserExclusive', ['ui.grid', 'ui.grid.treeView', 'ui.grid.selection', 'ui.grid.exporter'])
.controller('ExclusiveChooserController', ['$scope', '$http', 'uiGridConstants', '$interval',
    function($scope, $http, uiGridConstants, $interval) {
		
        $scope.getUsinas = function() {
            $http.get('app/components/instalacaoChooserExclusive/usinas.json')
                .success(function(data) {
                    $scope.gridOptions.data = data;
                    $scope.hideGrid = false;
                });
        };
        $scope.getInterligacoes = function() {
            $http.get('app/components/instalacaoChooserExclusive/interligacoes.json')
                .success(function(data) {
                    $scope.gridOptions.data = data;
                    $scope.hideGrid = false;
                });
        };
        $scope.hideGrid = true;
        $scope.tpInstalacao = 'usi';
        $scope.gridOptions = {
            enableRowSelection: true,
            enableSelectAll: true,
            selectionRowHeaderWidth: 35,
            rowHeight: 35,
            showGridFooter: true,
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                $interval(function() {
                    $scope.gridApi.core.handleWindowResize();
                }, 10, 500);
                $scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {
                    $scope.gridOptions.data.splice($scope.gridOptions.data.lastIndexOf(row.entity), 1);
                    $scope.gridOptions2.data.push(row.entity);
                    $scope.selectedItems.push(row.entity);
                });
            },
            columnDefs: [
            { field: 'nome', width: '40%', cellTooltip: true },
            { field: 'tipo', width: '30%' },
            { field: 'agente', width: '30%', cellTooltip: true }
            ]
        };
        $scope.gridOptions2 = {
            enableRowSelection: false,
            enableSelectAll: false,
            selectionRowHeaderWidth: 35,
            rowHeight: 35,
            showGridFooter: true,
            enableFiltering: false,
            onRegisterApi: function(gridApi) {
                $scope.gridApi2 = gridApi;
                $interval(function() {
                    $scope.gridApi2.core.handleWindowResize();
                }, 10, 500);
                $scope.gridApi2.selection.on.rowSelectionChanged($scope, function(row) {
                    $scope.gridOptions2.data.splice($scope.gridOptions2.data.lastIndexOf(row.entity), 1);
                    $scope.gridOptions.data.push(row.entity);
                    
                });
            },
            columnDefs: [
                { field: 'nome', cellTooltip: true }
                //{ field: 'tipo', width: '100' },
                //{ field: 'agente', width: '300', cellTooltip: true }
            ]
        };
        $scope.getUsinas();


        $scope.checkSelections = function() {
            return $scope.gridApi.selection.getSelectedCount() > 0;
        };
        
        

    }])
.directive('instalacaoChooserExclusive', function () {
    return {
        templateUrl: 'app/components/instalacaoChooserExclusive/instalacaoChooserExclusive.html',
        restrict: 'EA',
        link: function ($scope, element, attrs) {
            console.log('link');
        },
        scope: {
        	selectedItems: '='
        }
    };
});
