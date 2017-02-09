(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('CadastroUsinaDeleteController',CadastroUsinaDeleteController);

    CadastroUsinaDeleteController.$inject = ['$uibModalInstance', 'entity', 'CadastroUsina'];

    function CadastroUsinaDeleteController($uibModalInstance, entity, CadastroUsina) {
        var vm = this;

        vm.cadastroUsina = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CadastroUsina.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
