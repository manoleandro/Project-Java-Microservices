(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('NotificacoesController', NotificacoesController);

    NotificacoesController.$inject = ['$scope', '$state', 'Auth', 'Principal','NotificacaoService'];

    function NotificacoesController ($scope, $state, Auth, Principal, NotificacaoService) {
    	var vm = this;
    	vm.settingsAccount;
    	
    	var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login,
                authorities: account.authorities
            };
        };
    	
    	Principal.identity().then(function(account) {
            vm.settingsAccount = copyAccount(account);
            vm.loadAll();
        });
    	
    	
    	$scope.notificacoes = [];	
    	
    	vm.loadAll = function() {
    		
        	var params = {
                    roles:vm.settingsAccount.authorities,
                    usuario:vm.settingsAccount.login
            };
        	
        	
        	NotificacaoService.getNotificacoes(params, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
            	$scope.notificacoes = [];
                vm.queryCount = vm.totalItems;
                vm.disp = data.instalacoes;
                vm.tiposSelecionados = [];
                data.filter(function(dt){
                	$scope.notificacoes.push({
                		badgeClass: dt.tipo,
                	    badgeIconClass: dt.status,
                	    title: dt.comando,
                	    content:new Date(dt.data),
                	    id:dt.id,
                	    protocolo: dt.protocolo,
                	    roles: dt.roles,
                	    usuario : dt.usuario,
                	    protocoloID : dt.protocoloID,
                	    idBusca : dt.idBusca
                	});
                });
                console.log($scope.notificacoes);
            }
            function onError(error) {
            	console.log("Error na chamada");
            }
            
            var paramsLidas = {usuario:vm.settingsAccount.login};
            
            NotificacaoService.getNotificacoesLidasByUser(paramsLidas, onSuccessLidas, onError);
            $scope.notificacoesLidas = [];
            function onSuccessLidas(data, headers) {            	
                vm.queryCount = vm.totalItems;
                vm.disp = data.instalacoes;
                vm.tiposSelecionados = [];
                data.filter(function(dt){
                	$scope.notificacoesLidas.push({
                		badgeClass: dt.tipo,
                		badgeIconClass: 'glyphicon-check',
                	    title: dt.comando,
                	    content:new Date(dt.data),
                	    id:dt.id,
                	    protocolo: dt.protocolo,
                	    roles: dt.roles,
                	    usuario : dt.usuario,
                	    protocoloID : dt.protocoloID,
                	    idBusca : dt.idBusca
                	});
                });
            }
        }
    	
    	  vm.marcarLida = function(){
          	var lidas = [];
          	$scope.notificacoes.filter(function(data){
          		if(data.selected){
          			var ler = {};
          			ler.tipo = data.badgeClass;
          			ler.status = data.badgeIconClass;
          			ler.comando = data.title;
          			ler.data = data.content;
          			ler.id = data.id;
          			ler.protocolo = data.protocolo;
          			ler.roles = data.roles;
          			ler.usuario = vm.settingsAccount.login;
          			ler.lida = "lida";
          			ler.idLida = data.id+vm.settingsAccount.login;
          			ler.protocoloID = data.protocoloID;
          			ler.idBusca = data.idBusca;
          			console.log(ler);
          			lidas.push(ler);
          		}
          		
          	});
          	
          	NotificacaoService.lerNotificacao(lidas, onSuccessLer, onError);

          	function onSuccessLer(data, headers) {            	
              vm.loadAll();
            }
          	
            function onError(error) {
            	console.log("Error ao marcar as notificações");
            }
          	
          	
          }
    	  
    	  vm.marcarTodasLida = function(){
        		var lidas = [];
            	$scope.notificacoes.filter(function(data){
            		
	      			var ler = {};
	      			ler.tipo = data.badgeClass;
	      			ler.status = data.badgeIconClass;
	      			ler.comando = data.title;
	      			ler.data = data.content;
	      			ler.id = data.id;
	      			ler.protocolo = data.protocolo;
	      			ler.roles = data.roles;
	      			ler.usuario = vm.settingsAccount.login;
	      			ler.lida = "lida";
	      			ler.idLida = data.id+vm.settingsAccount.login;
	      			ler.protocoloID = data.protocoloID;
	      			ler.idBusca = data.idBusca;
	      			console.log(ler);
	      			lidas.push(ler);
            	});
            	NotificacaoService.lerNotificacao(lidas, onSuccessLer, onError);
              	
              	function onSuccessLer(data, headers) {            	
                  vm.loadAll();
                }
              	
                function onError(error) {
                	console.log("Error ao marcar as notificações");
                }
            		
        	}
    	
    }
})();