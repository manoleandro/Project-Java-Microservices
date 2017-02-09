//(function() {
//    'use strict';
//
//    angular
//        .module('onsPortalApp')
//        .controller('ConsultaTaxaController', ConsultaTaxaController);
//
//    ConsultaTaxaController.$inject = ['$scope', '$state', 'ConsultaTeste'];
//
//    function ConsultaTaxaController ($scope, $state, ConsultaTeste) {
//        var vm = this;
//        
//        vm.instalacoes = [];
//
//        loadAllInstalacoes();
//
//        function loadAllInstalacoes() {
//        	ConsultaTeste.queryInstalacoes(function(result) {
//                vm.instalacoes = result;
//            });
//        }
//    }
//})();
