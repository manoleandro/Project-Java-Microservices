(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('CadastroEventoController', CadastroEventoController);

    CadastroEventoController.$inject = ['$scope', '$state', '$uibModal', 'CadastroEvento', 'CadastroUsina'];

    function CadastroEventoController ($scope, $state, $uibModal, CadastroEvento, CadastroUsina) {
        var vm = this;
        
        vm.cadastroUsinas = [];

        loadAllUsinas();
        
        vm.cadastroEventos = null;
        
        vm.loadEventosByUsinaId = loadEventosByUsinaId;
        
        vm.sortEventos = sortEventos;

        vm.newEvento = newEvento;
        
        vm.editEvento = editEvento;
        
        vm.deleteEvento = deleteEvento;
        
        vm.disableRestore = disableRestore;
        
        vm.restoreEvento = restoreEvento;
        
        vm.retificar = retificar;

        function loadAllUsinas() {
            CadastroUsina.query(function(result) {
                vm.cadastroUsinas = result;
            });
            vm.selectedUsina = null;
        }
       
        function loadEventosByUsinaId() {
            CadastroEvento.queryByUsinaId({usinaId : vm.selectedUsina.id}, function(result) {
                vm.cadastroEventos = result;
                vm.cadastroEventosBackup = {};
                for (var i = 0; i < vm.cadastroEventos.length; i++) {
                	vm.cadastroEventosBackup[vm.cadastroEventos[i].id] = angular.copy(vm.cadastroEventos[i]);
                }
            });
        }

        function sortEventos() {
        	vm.cadastroEventos.sort(function(a, b){return new Date(a.dataVerificada) - new Date(b.dataVerificada);});
        }
        
        function newEvento() {
        	$uibModal.open({
                templateUrl: 'onsexemploread/app/entities/cadastro-evento/cadastro-evento-dialog.html',
                controller: 'CadastroEventoDialogController',
                controllerAs: 'vm',
                size: 'md',
            	resolve: {
            		entity: function () {
                        return {
                        	id: null,
                            dataVerificada: null,
                            estadoOperativo: null,
                            condicaoOperativa: null,
                            classificacaoOrigem: null,
                            potenciaDisponivel: null,
                            aggregateId: null,
                            aggregateVersion: null,
                            timelineId: null,
                            timelineDate: null,
                            correlationId: null,
                            deleted: false,
                            dirty: false
                        };
                    },
                    minDate: function() {
                    	return vm.cadastroEventos[0].dataVerificada;
                    }
                  }
            }).result.then(function(cadastroEvento) {
            	vm.cadastroEventos.push(cadastroEvento);
            	vm.sortEventos();
            }, function() {
            	
            });
        }
        
        function editEvento(index) {
        	$uibModal.open({
                templateUrl: 'onsexemploread/app/entities/cadastro-evento/cadastro-evento-dialog.html',
                controller: 'CadastroEventoDialogController',
                controllerAs: 'vm',
                size: 'md',
            	resolve: {
                    entity: function () {
                    	return vm.cadastroEventos[index];
                    },
                    minDate: function() {
                    	return vm.cadastroEventos[0].dataVerificada;
                    }
                  }
            }).result.then(function() {
            	vm.sortEventos();
            }, function() {
            	vm.cadastroEventos[index] = angular.copy(vm.cadastroEventosBackup[vm.cadastroEventos[index].id]);
            });
        }
        
        function deleteEvento(index) {
        	$uibModal.open({
        		templateUrl: 'onsexemploread/app/entities/cadastro-evento/cadastro-evento-delete-dialog.html',
        		controller: 'CadastroEventoDeleteController',
        		controllerAs: 'vm',
        		size: 'md',
        		resolve: {
        			entity: function () {
        				return vm.cadastroEventos[index];
        			}
        		}
        	}).result.then(function(eventoId) {
        		if (eventoId == null) {
        			vm.cadastroEventos.splice(index, 1);
        		}
            	vm.sortEventos();
        	}, function() {
        		
        	});
        }
        
        function disableRestore(cadastroEvento) {
        	if (cadastroEvento.id === null) {
        		return true;
        	}
        	if (cadastroEvento.dirty === null) {
        		cadastroEvento.dirty = false;
        	}
        	if (cadastroEvento.deleted === null) {
        		cadastroEvento.deleted = false;
        	}
        	return !cadastroEvento.dirty && !cadastroEvento.deleted;
        }

        function restoreEvento(index) {
        	vm.cadastroEventos[index] = angular.copy(vm.cadastroEventosBackup[vm.cadastroEventos[index].id]);
        	vm.disableRestore(vm.cadastroEventos[index]);
        	vm.sortEventos();
        }
        
        function retificar() {
        	vm.isSaving = true;
        	CadastroEvento.saveRetificacao({usinaId : vm.selectedUsina.id}, vm.cadastroEventos, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('onsPortalApp:apuracaoEventoApuracao', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
