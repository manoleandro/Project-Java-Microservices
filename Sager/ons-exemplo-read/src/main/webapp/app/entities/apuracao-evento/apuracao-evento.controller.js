(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ApuracaoEventoController', ApuracaoEventoController);

    ApuracaoEventoController.$inject = ['$scope', '$state', 'CadastroEvento', 'CadastroUsina'];

    function ApuracaoEventoController ($scope, $state, CadastroEvento, CadastroUsina) {
        var vm = this;
        
        vm.cadastroUsinas = [];

        loadAllUsinas();
        
        vm.cadastroEventos = null;
        
        vm.loadEventosByUsinaId = loadEventosByUsinaId;

        function loadAllUsinas() {
            CadastroUsina.query(function(result) {
                vm.cadastroUsinas = result;
            });
            vm.selectedUsina = null;
        }
       
        function loadEventosByUsinaId() {
            CadastroEvento.queryByUsinaId({usinaId : vm.selectedUsina.id}, function(result) {
                vm.cadastroEventos = result;
            });
        }
    }
})();
