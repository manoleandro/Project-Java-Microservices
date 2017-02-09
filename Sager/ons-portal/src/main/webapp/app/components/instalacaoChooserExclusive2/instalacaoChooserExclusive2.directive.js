'use strict';

angular.module('sagerApp.instalacaoChooserExclusive2', ['ui.grid', 'ui.grid.treeView', 'ui.grid.selection', 'ui.grid.exporter'])
.controller('ExclusiveChooserController2', ['$scope', '$http', 'uiGridConstants', '$interval','AlertService', 'UsinaService','InterligacaoService',
    function($scope, $http, uiGridConstants, $interval, AlertService, UsinaService,InterligacaoService) {
		

        UsinaService.query(onSuccess, onError);
        InterligacaoService.query(onSuccess2, onError);
        
        function onSuccess(data, headers) {
        	$scope.gridOptions.data = data;
		}
        function onSuccess2(data, headers) {
        	$scope.gridOptions2.data = data;
		}
		function onError(error) {
			AlertService.error(error.data.message);
		}

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
                      if (row.isSelected) {
                         $scope.selectedItems.push(row.entity.id);
                      } else {
                        var index = $scope.selectedItems.lastIndexOf(row.entity.id);
                         $scope.selectedItems.splice(index,1);
                      }
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
                // $scope.gridApi2.selection.on.rowSelectionChanged($scope, function(row) {
                //     $scope.gridOptions2.data.splice($scope.gridOptions2.data.lastIndexOf(row.entity), 1);
                //     $scope.gridOptions.data.push(row.entity);
                    
                // });
            },
            columnDefs: [
                { field: 'nome', cellTooltip: true },
                //{ field: 'tipo', width: '100' },
                { field: 'agente', width: '300', cellTooltip: true }
            ]
        };
        //$scope.getUsinas();
        //$scope.getInterligacoes();


        $scope.checkSelections = function() {
            return $scope.gridApi.selection.getSelectedCount() > 0;
        };

        $scope.unselect = function(row) {
            $scope.gridApi.selection.toggleRowSelection(row);
        }
        
        

    }])
.directive('instalacaoChooserExclusive2', function () {
    return {
        templateUrl: 'app/components/instalacaoChooserExclusive2/instalacaoChooserExclusive2.html',
        restrict: 'EA',
        link: function ($scope, element, attrs) {
            console.log('link');
        },
        scope: {
        	selectedItems: '='
        }
    };
});
