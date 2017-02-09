(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('DispComentariosController', DispComentariosController);

    DispComentariosController.$inject = ['$scope', '$rootScope', '$stateParams', '$timeout', '$uibModalInstance', 'entity', '$filter', '$interval'];

    function DispComentariosController($scope, $rootScope, $stateParams, $timeout, $uibModalInstance, entity, $filter, $interval) {
        var vm = this;

        vm.clear = clear;
        vm.entity = entity;
        
        vm.comentariosAviso = vm.entity.comentariosAviso;
        vm.comentariosErro = vm.entity.comentariosErro;
        vm.comentarios = vm.entity.comentarios;
        
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        
    }
})();
