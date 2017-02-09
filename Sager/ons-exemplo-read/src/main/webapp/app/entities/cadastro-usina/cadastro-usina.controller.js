(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('CadastroUsinaController', CadastroUsinaController);

    CadastroUsinaController.$inject = ['$scope', '$state', 'CadastroUsina'];

    function CadastroUsinaController ($scope, $state, CadastroUsina) {
        var vm = this;
        
        vm.cadastroUsinas = [];

        loadAll();

        function loadAll() {
            CadastroUsina.query(function(result) {
                vm.cadastroUsinas = result;
            });
        }
    }
})();
