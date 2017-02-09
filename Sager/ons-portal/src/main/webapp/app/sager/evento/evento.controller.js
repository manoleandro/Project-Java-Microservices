(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('EventoController', EventoController);

    EventoController.$inject = ['$scope', '$state', '$interval', 'EventoService','$http', '$stateParams','Usinas', 'AlertService', 'paginationConstants'];

    function EventoController ($scope, $state, $interval, EventoService,$http, $stateParams,Usinas, AlertService, paginationConstants) {
        var vm = this;
        $scope.eventos = [];
        $scope.parametros = $stateParams;
        vm.showEventos = false;
        
        vm.loadPage = loadPage;

        if($scope.parametros.idEvento != undefined){
        	console.log("entrou com id");
        	var id = angular.copy($scope.parametros.idEvento);
        	console.log(id);
        	loadEventos(id);
        }
        function loadEventos(ev) {
        	console.log("dentro do paramentro");
        	console.log(ev);
        	
        	
        	var instalacoesReq = [];
            var instalacao;
            
            for (var i = 0; i < vm.instalacoesSelecionada.length; i++) {
	  	 		var instalacaoSelecionada = vm.instalacoesSelecionada[i];
	  	 		instalacao = {};
	  			instalacao.nome = instalacaoSelecionada.nome;
	  			instalacao.id = instalacaoSelecionada.id;
	  			instalacao.equipamentos = instalacaoSelecionada.equipamentos;
	  			instalacao.minorVersion = instalacaoSelecionada.minorVersion
	  			instalacoesReq.push(instalacao);	
            }
        	
        	EventoService.getById({
        		ids:ev
            }, instalacoesReq, onSuccess, onError);
            
            function onSuccess(data, headers) {
                $scope.totalItems = headers('X-Total-Count');
                $scope.queryCount = $scope.totalItems;
                $scope.eventos = data;
                $scope.gridOptions.data = data;
                console.log($scope.eventos);
                vm.showEventos = true;
                $scope.eventos = $scope.eventos.filter(function(event){
                	event.instalacao = $scope.parametros.instalacao;
                	return event;
                });
            }
            function onError(error) {
            }
        }
        
        vm.consultarEvento = function(){
        	var instalacoesReq = [];
            var instalacao;
            
            for (var i = 0; i < vm.instalacoesSelecionada.length; i++) {
	  	 		var instalacaoSelecionada = vm.instalacoesSelecionada[i];
	  	 		instalacao = {};
	  			instalacao.nome = instalacaoSelecionada.nome;
	  			instalacao.id = instalacaoSelecionada.id;
	  			instalacao.equipamentos = instalacaoSelecionada.equipamentos;
	  			instalacao.minorVersion = instalacaoSelecionada.minorVersion
	  			instalacoesReq.push(instalacao);	
            }
        	
        	var params = new Object();
        	params.dtInicio = vm.dtInicio;
        	params.dtFim = vm.dtFim;
        	params.size = vm.itemsPerPage,
        	params.sort = sort();
        	
        	function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
        	
        	var filtersBody = instalacoesReq;
        	
        	EventoService.getByInstalacoes(params, filtersBody, onSuccess, onError);
          
        	function onSuccess(data, headers) {
		        $scope.totalItems = headers('X-Total-Count');
		        $scope.queryCount = $scope.totalItems;
		        $scope.eventos = data;
		        $scope.gridOptions.data = data;
		        vm.showEventos = true;
        	}
        	function onError(error) {
        	}
        }     
        
        vm.dtInicio = new Date();
        vm.dtInicio.setHours(0,0,0,0); 	
        vm.dtFim = new Date();
        vm.dtFim.setHours(23,59,59,999);

        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.creationDate = false;
        
        vm.datepickerOptionsInicial= {
        	showWeeks: false,
        	minDate: new Date("01/01/2001"),
//            maxDate: new Date(),
            maxDate: vm.dtFim
        	
        };
        vm.datepickerOptionsFinal= {
        	showWeeks: false,
        	minDate: vm.dtInicio,
            maxDate: new Date()
        };
        
        $scope.changeDate = function () {
            // min max datesc
        	if(vm.dtInicio){
        		vm.datepickerOptionsFinal.minDate = vm.dtInicio;
        		vm.datepickerOptionsInicial.maxDate = vm.dtFim;
        	}else{
        		vm.datepickerOptionsFinal.minDate = new Date();
        	}
            if(vm.dtFim){
                vm.dtFim.setHours(23,59,59,999);
            }else{
                vm.dtFim.setHours(23,59,59,999);
            }
        }
        
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
        	console.log(vm.currentSearch);
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
        
        $scope.gridOptions = {
        		enableFiltering: true,
        		enableGridMenu: true,
        	    enableSelectAll: true,
        	    exporterCsvFilename: 'Eventos.csv',
        	    exporterPdfDefaultStyle: {fontSize: 9},
        	    exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
        	    exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
        	    exporterPdfHeader: { text: "Eventos", style: 'headerStyle' },
        	    exporterPdfFooter: function ( currentPage, pageCount ) {
        	      return { text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
        	    },
        	    exporterPdfCustomFormatter: function ( docDefinition ) {
        	      docDefinition.styles.headerStyle = { fontSize: 22, bold: true };
        	      docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
        	      return docDefinition;
        	    },
        	    exporterPdfOrientation: 'portrait',
        	    exporterPdfPageSize: 'LETTER',
        	    exporterPdfMaxGridWidth: 500,
        	    exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
        	    onRegisterApi: function(gridApi){
        	      $scope.gridApi = gridApi;
        	    },
                showGridFooter:true,
                onRegisterApi: function(gridApi) {
                    $scope.gridApiUsinas = gridApi;
                    $interval(function() {
                        $scope.gridApiUsinas.core.handleWindowResize();
                    }, 10, 500);
                },
                columnDefs: [
	                { field: 'nomeInstalacao', displayName: 'Instalação' },
	                { field: 'codigoONSEquipamento', displayName: 'Unidade Geradora'},
	                { field: 'dataVerificada', displayName: 'Data/Hora', cellFilter: 'date: "yyyy/MM/dd HH:mm"'},
	                { field: 'estadoOperativo', displayName: 'Estado Operativo'},
	                { field: 'condicaoOperativa', displayName: 'Condição Operativa'},
	                { field: 'origem', displayName: 'Origem'},
	                { field: 'valorPotenciaDisponivel', displayName: 'Disponibilidade', cellClass: 'grid-align', cellFilter: 'number: 3'}
                ]
        };
    }
})();
