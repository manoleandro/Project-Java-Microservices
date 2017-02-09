'use strict';

//angular.module('sagerApp.platformScheduleList')
angular.module('sagerApp.platformScheduleList', ['ui.grid', 'ui.grid.treeView', 'ui.grid.selection', 'ui.grid.exporter'])
.controller('PlatformScheduleList', ['$scope', '$http', 'uiGridConstants', '$interval','AlertService', 'ScheduleServiceByInstalacao',
    function($scope, $http, uiGridConstants, $interval, AlertService, ScheduleServiceByInstalacao) {
		
	ScheduleServiceByInstalacao.getAgendamentos($scope.instalacoes, onSuccess, onError);
        
        function onSuccess(data, headers) {
        	$scope.gridOptions.data = data;
		}

        function onError(error) {
			AlertService.error(error.data.message);
		}

        $scope.gridOptions = {
            enableRowSelection: false,
            enableSelectAll: false,
            selectionRowHeaderWidth: 35,
            rowHeight: 35,
            showGridFooter: true,
            enableFiltering: false,
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
            { field: 'protocoloStr', displayName: 'Protocolo', cellTooltip: true },
            { field: 'nomeInstalacao', displayName: 'Instalação' },
            { field: 'dataAgendamento', cellFilter: 'date: "dd/MM/yyyy HH:mm"', cellTooltip: true },
            { field: 'solicitante', cellTooltip: true },
            { field: 'situacao', cellTooltip: true },
            { field: 'resultado', cellTooltip: true }
            ]
        };
        

        $scope.checkSelections = function() {
            return $scope.gridApi.selection.getSelectedCount() > 0;
        };

        $scope.unselect = function(row) {
            $scope.gridApi.selection.toggleRowSelection(row);
        }
        
        

    }])
.directive('platformScheduleList', function () {
    return {
        templateUrl: 'app/components/platform-schedule-list/platform-schedule-list.html',
        restrict: 'EA',
        link: function ($scope, element, attrs) {
            console.log('link');
        },
        scope: {
        	selectedItems: '=',
        	instalacoes: '='
        	
        }
    };
});
