(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('GerarRelatorioDetailController', GerarRelatorioDetailController);

    GerarRelatorioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GerarRelatorio'];

    function GerarRelatorioDetailController($scope, $rootScope, $stateParams, previousState, entity, GerarRelatorio) {
        var vm = this;

        vm.calculoTaxa = entity;
        vm.previousState = previousState.name;

        vm.apuracoes = GerarRelatorio.getApuracoes({id : $stateParams.id});
        
        var unsubscribe = $rootScope.$on('onsPortalApp:calculoTaxaUpdate', function(event, result) {
            vm.calculoTaxa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
