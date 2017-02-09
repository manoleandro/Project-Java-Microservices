(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('CadastroUsinaDetailController', CadastroUsinaDetailController);

    CadastroUsinaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CadastroUsina'];

    function CadastroUsinaDetailController($scope, $rootScope, $stateParams, previousState, entity, CadastroUsina) {
        var vm = this;

        vm.cadastroUsina = entity;
        vm.previousState = previousState.name;

        vm.apuracoes = CadastroUsina.getApuracoes({id : $stateParams.id});
        
        var unsubscribe = $rootScope.$on('onsPortalApp:cadastroUsinaUpdate', function(event, result) {
            vm.cadastroUsina = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
