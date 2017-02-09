(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', 'LoginService', 'Principal', 'ProfileService', 'NotificacaoService'];

    function NavbarController ($scope, LoginService, Principal, ProfileService, NotificacaoService) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });
        
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

        function getAccount() {
            Principal.identity().then(function(account) {
            	if (account) 
            		vm.settingsAccount = copyAccount(account);
            		vm.loadAllNotificacao();
            });
        }
        
        getAccount();

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;

        function login() {
        	LoginService.login(); 
        }

        function logout() {
        	LoginService.logout();
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
        
        $scope.notificacoes = [];
        
        vm.loadAllNotificacao = function() {
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
                vm.queryCount = vm.totalItems;
                vm.disp = data.instalacoes;
                vm.tiposSelecionados = [];
                data.filter(function(dt){
                	$scope.notificacoes.push({
                		badgeClass: dt.tipo,
                	    badgeIconClass: dt.status,
                	    title: dt.comando,
                	    content:new Date(dt.data).toLocaleString(),
                	    id:dt.id
                	});
                });
            }
            function onError(error) {
            	console.log("Error na chamada");
            }
        }
    }
})();
