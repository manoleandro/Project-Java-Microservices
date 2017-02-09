(function() {
    'use strict';

    angular.module('onsPortalApp').controller('DispController', DispController);

    DispController.$inject = ['$scope', '$window',  '$state','Usinas','Interligacoes', 'DispService', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', 'DispExcelService'];

    function DispController ($scope, $window, $state, Usinas, Interligacoes, DispService, ParseLinks, AlertService, pagingParams, paginationConstants, DispExcelService) {
        var vm = this;
        
        vm.instalacoesSelecionada = [];
	        
        vm.dtInicio = new Date();
        vm.dtInicio.setHours(0,0,0,0); 	
        vm.dtFim = new Date();
        vm.dtFim.setHours(23,59,59,999);
        vm.tipoDisp = [ {name:"Operacional",value:"O"},
            {name:"Comercial",value:"C"},
            {name:"Eletromecânica",value:"E"}];
        vm.tipoDispSelecionadas = [];
        $scope.listaErro = [];    
        vm.showDisp = false;
        
        vm.instalacoesSelecionada = [];
        
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.equipamento= '';
        		 	
	 	vm.tiposSelecionados = [];
	 	
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.creationDate = false;
        
        vm.datepickerOptionsInicial= {
        	showWeeks: false,
        	minDate: new Date("01/01/2001"),
            maxDate: vm.dtFim
        };
        vm.datepickerOptionsFinal= {
        	showWeeks: false,
        	minDate: vm.dtInicio,
            maxDate: new Date()
        };
        
        vm.filtersParam = null;
        vm.filtersBody = null;
        
        vm.dispSelecionada = null;
        
        
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        $scope.changeDate = function () {
        	
            // min max datesc
        	if(vm.dtInicio){
        		vm.datepickerOptionsFinal.minDate = vm.dtInicio;
        		vm.datepickerOptionsInicial.maxDate = vm.dtFim;
        	}else{
        		vm.datepickerOptionsFinal.minDate = vm.dtFim;
        	}
            if(vm.dtFim){
                vm.dtFim.setHours(23,59,59,999);
            }
        }

        /* show th de acordo com tipo selecionado*/
        vm.showTh = function(value) {
        	if (value == 'Data') {
        		return true;
        	}
        	if (value == 'O'){
        		for (var i = 0; i < vm.tipoDispSelecionadas.length; i ++) {
        			if (vm.tipoDispSelecionadas[i].value == 'O') {
        				return true;
        			}
        		}
        	}
        	if (value == 'C') {
        		for (var j = 0; j < vm.tipoDispSelecionadas.length; j ++) {
        			if (vm.tipoDispSelecionadas[j].value == 'C') {
        				return true;
        			}
        		}
        	}
        	if (value == 'E') {
        		for (var k = 0; k < vm.tipoDispSelecionadas.length; k ++) {
        			if (vm.tipoDispSelecionadas[k].value == 'E') {
        				return true;
        			}
        		}
        	}
        	return false;
        };
        /* show td de acordo com tipo selecionado*/
        vm.showTd = function(value){
        	
        	if (value.indexOf('_O')>0) {
        		for (var i = 0; i < vm.tipoDispSelecionadas.length; i ++) {
        			if (vm.tipoDispSelecionadas[i].value == 'O') {
        				return true;
        			}
        		}
        	}
        	if (value.indexOf('_C')>0) {
        		for (var i = 0; i < vm.tipoDispSelecionadas.length; i ++) {
        			if (vm.tipoDispSelecionadas[i].value == 'C') {
        				return true;
        			}
        		}
        	}
        	if (value.indexOf('_E')>0) {
        		for (var i = 0; i < vm.tipoDispSelecionadas.length; i ++) {
        			if (vm.tipoDispSelecionadas[i].value == 'E') {
        				return true;
        			}
        		}
        	}
        	return false;
        }

    	function loadAll (filtersParam, filtersBody) {
        	var params = {
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
            };
        	
        	if(filtersParam!=null) {
        		jQuery.extend(params,filtersParam);
        	}
        	
        	function sort() {
        		var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
        	
        	vm.filtersParam = params;
        	vm.filtersBody = filtersBody;
        	
        	DispService.checkDispInstalacoes(params, filtersBody, onSuccess, onError);
            
            function onSuccess(data, headers) {            	
                vm.queryCount = vm.totalItems;
                vm.disp = data.instalacoes;
                vm.disp.hasExcelGeral = data.hasExcelGeral;
                if (vm.disp.hasExcelGeral){
                	vm.getExcel(params, new Object(vm.disp));
                }else if (data.instalacoes[0].hasDisponibilidades){                
                //	OBS - jcardoso - Se a primeira  instalação selecionada tiver disponibilidade chamar serviço para retornar os valores.
                	vm.changeTab(data.instalacoes[0].nome);
                }
                vm.tiposSelecionados = [];
                vm.showDisp = true;
            }
            function onError(error) {
                AlertService.error(error.data.message);
                vm.showDisp = false;
                vm.tiposSelecionados = [];
            }
        	
        }
    	
    	vm.getExcel = function(params, instalacao){
    		var instalacoes = [];
    		if ( Array.isArray(instalacao) ){
    			instalacoes = instalacao;
    			for(var i=0; i< instalacoes.length; i++){
    				delete instalacoes[i]['comentarios'];
        			delete instalacoes[i]['comentariosAviso'];
        			delete instalacoes[i]['comentariosErro'];
    			}
    			
    		}else {
    			delete instalacao['comentarios'];
    			delete instalacao['comentariosAviso'];
    			delete instalacao['comentariosErro'];
    			instalacoes.push(instalacao);
    		}
    		
    		
    		DispExcelService.getExcelGeral(params, instalacoes, onGenerateExcelSuccess, onGenerateExcelError)	        
	        
	        function onGenerateExcelSuccess(data, headers){
	        	console.log("onGenerateExcelSuccess");
	        }
	        function onGenerateExcelError(error){
	        	console.log("onGenerateExcelError");
	        }
    	}
    	
        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
        
        
        $scope.toggle = function(item, list) {
			var idx = list.indexOf(item);
			if (idx > -1) {
				list.splice(idx, 1);
			} else {
				list.push(item);
			}
		};
		
		$scope.exists = function(item, list) {
			return list.indexOf(item) > -1;
		};
        
        $scope.isIndeterminate = function() {
			return (vm.tipoDispSelecionadas.length !== 0 && vm.tipoDispSelecionadas.length !== vm.tipoDisp.length);
		};

		$scope.isChecked = function() {
			return vm.tipoDispSelecionadas.length === vm.tipoDisp.length;
		};

		$scope.toggleAll = function() {
			if (vm.tipoDispSelecionadas.length === vm.tipoDisp.length) {
				vm.tipoDispSelecionadas = [];
			} else if (vm.tipoDispSelecionadas.length === 0
					|| vm.tipoDispSelecionadas.length > 0) {
				vm.tipoDispSelecionadas = vm.tipoDisp.slice(0);
			}
		};
		$scope.selecionado = function(value) {
			var ret = false;
			for (var i = 0; i < vm.tipoDispSelecionadas.length; i++) {
				if(vm.tipoDispSelecionadas[i].value === value) {
					ret = true;
				}
			}
			return ret;
		}		

		vm.consultarDisp = function(){
			vm.filtersParam = null;
	        vm.filtersBody = null;
			
			vm.showDisp = false;
			var flagErro = false;
	       
	        //----------------Validação de filtro--------------------------------
			vm.dataAtual = new Date();
			vm.dataMinima = new Date(2001, 1, 1);
			
		 	if(vm.dtInicio != null && vm.dtFim != null && new Date(vm.dtInicio).getTime() > new Date(vm.dtFim).getTime()){		 		
		 		$scope.listaErro.push({"erroCod":"RS_MENS_013","critica":"", "mensagem":"Data inicial maior que data final"});
		        flagErro = true;
		    }	
		 	
		 	if(vm.dtInicio != null && vm.dtFim != null && new Date(vm.dtInicio).getTime() > new Date(vm.dataAtual).getTime()){		 		
		 		$scope.listaErro.push({"erroCod":"RS_MENS_026","critica":"Data inicial", "mensagem":"Data Inicial maior que a Data Atual."});
		        flagErro = true;
		    }
		 	
		 	var tomorrow = new Date(vm.dataAtual);
		 	tomorrow.setDate(vm.dataAtual.getDate() + 1);
		 	if(vm.dtInicio != null && vm.dtFim != null && new Date(vm.dtFim).getTime() >  new Date(tomorrow).getTime()){		 		
		 		$scope.listaErro.push({"erroCod":"RS_MENS_026","critica":"Data final", "mensagem":"Data Final maior que a Data Atual."});
		        flagErro = true;
		    }
		 	if(vm.dtInicio != null && vm.dtFim != null && new Date(vm.dtInicio).getTime() < new Date(vm.dataMinima).getTime()){		 		
		 		$scope.listaErro.push({"erroCod":"RS_MENS_022","critica":"Data inicial", "mensagem":"As datas dos filtros não podem ser anteriores a 01/01/2001: Data inicial"});
		        flagErro = true;
		    }
		 	if(vm.dtInicio != null && vm.dtFim != null && new Date(vm.dtFim).getTime() < new Date(vm.dataMinima).getTime()){
		 		$scope.listaErro.push({"erroCod":"RS_MENS_022","critica":"Data final", "mensagem":"As datas dos filtros não podem ser anteriores a 01/01/2001: Data final"});
		        flagErro = true;
		    }
		 	if(vm.dtInicio == null){		 		
		 		$scope.listaErro.push({"erroCod":"RS_MENS_002","critica":"Data inicial", "mensagem":"Informações obrigatórias não informadas: Data inicial"});
		        flagErro = true;
		    }
		 	if(vm.dtFim == null){		 		
		 		$scope.listaErro.push({"erroCod":"RS_MENS_002","critica":"Data final", "mensagem":"Informações obrigatórias não informadas: Data final"});
		        flagErro = true;
		    }
		 	if(vm.tipoDispSelecionadas.length == 0){		 		
		 		$scope.listaErro.push({"erroCod":"RS_MENS_002","critica":"Tipo de Disponibilidade", "mensagem":"Informações obrigatórias não informadas: Tipo de Disponibilidade"});
		        flagErro = true;
		    }
		 	
		        //------------------------------------------------
		 	var instalacoesReq = [];
		 	var instalacao;
		 	for (var i = 0; i < vm.instalacoesSelecionada.length; i++) {
		 		var instalacaoSelecionada = vm.instalacoesSelecionada[i];
		 		instalacao = new Object();
	 			instalacao.nome = instalacaoSelecionada.nome;
	 			instalacao.id = instalacaoSelecionada.id;
	 			instalacao.equipamentos = instalacaoSelecionada.equipamentos;
	 			instalacao.minorVersion = instalacaoSelecionada.minorVersion
	 			instalacoesReq.push(instalacao);	
		 	}
		 	
		 	vm.tiposSelecionados = [];
		 	for (var i = 0; i < vm.tipoDispSelecionadas.length; i++) {
		 		vm.tiposSelecionados.push(vm.tipoDispSelecionadas[i].value);
		 	}
		 	
		 	if(instalacoesReq.length == 0){		 		
		 		$scope.listaErro.push({"erroCod":"RS_MENS_002","critica":"Instalação", "mensagem":"Informações obrigatórias não informadas: Instalação"});
		        flagErro = true;
		    }
		 	var filtersParam = {
	 			tipos : vm.tiposSelecionados,
		 		dtInicio: vm.dtInicio.toISOString(),
    			dtFim: vm.dtFim.toISOString()
        	};
		 	var filtersBody = instalacoesReq;
		 	
		 	if (!flagErro)
		 	 	loadAll(filtersParam, filtersBody);	 	
		 	else{
		 		$window.scrollTo(0, angular.element('mensagemErro').offsetTop);
		 	}
    	}
		
		
		vm.abrirComentarios = function(comentariosAviso, comentariosErro,comentarios) {
		    $mdDialog.show({
		      controller: DialogController,
		      templateUrl: 'dialog1.tmpl.html',
		      parent: angular.element(document.body),
		      targetEvent: ev,
		      clickOutsideToClose:true,
		      fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
		    })
		    .then(function(answer) {
		      $scope.status = 'You said the information was "' + answer + '".';
		    }, function() {
		      $scope.status = 'You cancelled the dialog.';
		    });
		};
		
		vm.changeTab = function(usinaName){
		 	var instalacao;
		 	
		 	if (!vm.disp.hasExcelGeral){
		 		var hasDisponibilidade = false;
		 		for (var i = 0; i < vm.disp.length; i++) {		 		
		 			var instalacaoSelecionada = Object.create( vm.disp[i] );
		 			
		 			if ( instalacaoSelecionada.nome == usinaName){
		 				instalacao = new Object();
		 				instalacao.nome = instalacaoSelecionada.nome;
		 				instalacao.id = instalacaoSelecionada.id;
		 				instalacao.equipamentos = instalacaoSelecionada.equipamentos;
		 				instalacao.minorVersion = instalacaoSelecionada.minorVersion;
		 				
		 				hasDisponibilidade = instalacaoSelecionada.hasDisponibilidades;
		 				
		 				vm.dispSelecionada = new Object(instalacao);
		 				vm.dispSelecionada.hasDisponibilidades = instalacaoSelecionada.hasDisponibilidades;
		 				vm.dispSelecionada.hasComentarios = instalacaoSelecionada.hasComentarios;
		 				vm.dispSelecionada.comentariosAviso = instalacaoSelecionada.comentariosAviso;
		 				vm.dispSelecionada.comentariosErro = instalacaoSelecionada.comentariosErro;
		 				vm.dispSelecionada.comentarios = instalacaoSelecionada.comentarios;
		 			}
		 		}
		 		if (instalacao != null && hasDisponibilidade)
		 		{
		 			vm.getDisps(vm.filtersParam, instalacao);
		 		}
		 	}
		};
		
		vm.getDisps = function(filtersParam, filterBody){
			function onSuccess(data, headers) {            	
		 		vm.queryCount = vm.totalItems;
		 		vm.dispSelecionada.hasExcel = data.hasExcel;
		 		if (vm.dispSelecionada.hasExcel){
		 			vm.getExcel(filtersParam, filterBody);
		 		} else{
		 			vm.dispSelecionada.disponibilidades = data.disponibilidades;
		 			vm.dispSelecionada.colunas = data.colunas;
		 			vm.tiposSelecionados = [];
		 		}
		 		
		 	}
		 	
		 	function onError(error) {
		 		AlertService.error(error.data.message);
		 		vm.showDisp = false;
		 		vm.tiposSelecionados = [];
		 	}
			
		 	DispService.getDisps(filtersParam, filterBody, onSuccess, onError);
		}
		
		/* export to excel */
		$scope.exportToExcel=function(){
	        	var wb = new Workbook();
	        	
	        	for (var i = 0; i < vm.disp.length; i++) {
	        		if( vm.disp[i].hasDisponibilidades){
	        			var data = Array();
	        			var b = 0;
	        			var tableId = vm.disp[i].id;
	        			$("#"+tableId+" th[id='head']").each(function(i, v){
	        				data[i] = Array();
	        				b = b+1;
	        				data[0][b] = $(this).text();
	        				for(var m = 0; m < vm.tipoDispSelecionadas.length-1; m++){
	        					b = b +1;
	        					data[0][b] = "";
	        					//TODO MESCLAR O CARINHA
	        				}
	        			})
	        			$("#"+tableId+" th[id='head2'][aria-hidden=false]").each(function(i, v){//
	        				data[i+1] = Array();
	        				data[1][i] = $(this).text();
	        			})
	        			$("#"+tableId+" tr[id='body']").each(function(i, v){
	        				data[i+2] = Array();
	        				$(this).children('td[aria-hidden=false]').each(function(ii, vv){
	        					data[i+2][ii] = $(this).text();
	        				}); 
	        			})    	
	        			var flagErro = false;
	        			
	        			if(data[1].length == 0){
	        				$scope.listaErro.push({"erroCod":"RS_MENS_015","critica":"", "mensagem":"Não existem dados para os filtros selecionados."});
	        				flagErro = true;
	        			}
	        			
	        			if(!flagErro){
	        				var ranges = '';
	        				var ws_name = vm.disp[i].nome;     	
	        				var ws = sheet_from_array_of_arrays(data);
	        				
	        				ws['!merges'] = ranges;
	        				wb.SheetNames.push(ws_name);
	        				wb.Sheets[ws_name] = ws;
	        			}
	        		}
	            }
	            var wbout = XLSX.write(wb, {bookType:'xlsx', bookSST:false, type: 'binary'});
	            saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), "Disponibilidade.xlsx");
		}/*- end excel-*/
		
    }/* final */
		       
})();
