(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('CadastroEventoDeleteController',CadastroEventoDeleteController);

    CadastroEventoDeleteController.$inject = ['$uibModalInstance', 'entity'];

    function CadastroEventoDeleteController($uibModalInstance, entity) {
        var vm = this;

        vm.cadastroEvento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (eventoId) {
    		vm.cadastroEvento.dirty = false;
    		vm.cadastroEvento.deleted = true;
            $uibModalInstance.close(eventoId);
        }
    }
})();
