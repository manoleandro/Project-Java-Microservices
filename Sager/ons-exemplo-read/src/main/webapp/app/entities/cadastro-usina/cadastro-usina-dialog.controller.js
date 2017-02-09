(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('CadastroUsinaDialogController', CadastroUsinaDialogController);

    CadastroUsinaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CadastroUsina'];

    function CadastroUsinaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CadastroUsina) {
        var vm = this;

        vm.cadastroUsina = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cadastroUsina.id !== null) {
                CadastroUsina.update(vm.cadastroUsina, onSaveSuccess, onSaveError);
            } else {
                CadastroUsina.save(vm.cadastroUsina, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('onsPortalApp:cadastroUsinaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
