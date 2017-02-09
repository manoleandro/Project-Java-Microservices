(function() {
	'use strict';

	angular.module('onsPortalApp').factory('mockHandler', mockHandler);

	mockHandler.$inject = [ '$httpBackend', '$http' ];

	function mockHandler($httpBackend, $http) {
		return {
			initialize : initialize
		};

		function initialize() {
			
			var getByInstalacaoDataTaxa;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/reproduzirCalculo.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  getByInstalacaoDataTaxa = response;
				  }
				});
			
			var usinas;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/usinas.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  usinas = response;
				  }
				});
			
			var interligacaoInternacional;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/interligacaoInternacional.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  interligacaoInternacional = response;
				  }
				});
			
			var instalacaoTaxasByInstalacaoPeriodo;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/instalacaoTaxasByInstalacaoPeriodo.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  instalacaoTaxasByInstalacaoPeriodo = response;
				  }
				});
			
			var agendamentosCalculoByInstalacao;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/agendamentos-calculo-by-instalacao.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  agendamentosCalculoByInstalacao = response;
				  }
				});
			
			var agendamentosCalculo;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/agendamentos-calculo.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  agendamentosCalculo = response;
				  }
				});
			
			var notificacaoByRoles;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/notificacaoByRoles.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  notificacaoByRoles = response;
				  }
				});
			
			var getNotificacoesLidasByUser;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/getNotificacoesLidasByUser.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  getNotificacoesLidasByUser = response;
				  }
				});
			
			//OBS - jcardoso - retorna []
			var lerNotificacao = {};
			
			var memoriacaAcumuladaRegraNova = function(method, url, data, headers, params) {
				var result;
				if (params.taxaMemoriaCalculo.indexOf('acumulada') > -1 && new Date(params.dataApuracao) > new Date(2014,9,1) ){
					$.ajax({
					  url: 'app/blocks/handlers/mock_files/memoriaAcumuladaRegraNova.json',
					  async: false,
					  dataType: 'json',
					  success: function (response) {
						  result = response;
					  }
					});
				} else if (params.taxaMemoriaCalculo.indexOf('acumulada') > -1  ){
					$.ajax({
						  url: 'app/blocks/handlers/mock_files/memoriaAcumuladaRegraAntiga.json',
						  async: false,
						  dataType: 'json',
						  success: function (response) {
							  result = response;
						  }
						});
				} else {
					$.ajax({
						  url: 'app/blocks/handlers/mock_files/memoriaMensal.json',
						  async: false,
						  dataType: 'json',
						  success: function (response) {
							  result = response;
						  }
						});
				}
				
				return [200, result, {}, ""] ;
			};
			
			var eventos;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/eventos.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  eventos = response;
				  }
				});
			
			var eventos2407;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/eventos2407.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  eventos2407 = response;
				  }
				});
			
			var agendamentosCalculoByDate;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/agendamentos-calculo-by-date.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  agendamentosCalculoByDate = response;
				  }
				});
			
			var agendamentosRetificacoesByDate;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/agendamentos-retificacoes-by-date.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  agendamentosRetificacoesByDate = response;
				  }
				});
			
			var taxaReferenciaByTipoInstalacao = function(method, url, data, headers, params) {
				var result;
				if (params.tipoInstalacao.indexOf('USINA') > -1 ){
					$.ajax({
					  url: 'app/blocks/handlers/mock_files/taxaReferenciaByTipoInstalacao_Usina.json',
					  async: false,
					  dataType: 'json',
					  success: function (response) {
						  result = response;
					  }
					});
				}
				return [200, result, {}, ""] ;
			}
			
			var parametrizacaoRetificacoes;
			$.ajax({
			  url: 'app/blocks/handlers/mock_files/parametrizacao-retificacoes.json',
			  async: false,
			  dataType: 'json',
			  success: function (response) {
				  parametrizacaoRetificacoes = response;
			  }
			});
			
			var parametrizacaoRetificacoesPOST;
			$.ajax({
			  url: 'app/blocks/handlers/mock_files/parametrizacao-retificacoes_POST.json',
			  async: false,
			  dataType: 'json',
			  success: function (response) {
				  parametrizacaoRetificacoesPOST = response;
			  }
			});
			
			var dispPUT;
			$.ajax({
				  url: 'app/blocks/handlers/mock_files/disp_PUT.json',
				  async: false,
				  dataType: 'json',
				  success: function (response) {
					  dispPUT = response;
				  }
				});
				
			var dispPOST = function(method, url, data, headers, params) {
				var result;
				if (data.indexOf('\"id\":\"ALUXG\"') > -1 ){
					$.ajax({
					  url: 'app/blocks/handlers/mock_files/disp_POST.json',
					  async: false,
					  dataType: 'json',
					  success: function (response) {
						  result = response;
					  }
					});
				} else if (data.indexOf('\"id\":\"RJUTME\"') > -1 ){
					$.ajax({
						  url: 'app/blocks/handlers/mock_files/disp_POST2.json',
						  async: false,
						  dataType: 'json',
						  success: function (response) {
							  result = response;
						  }
						});
					}
				
				
				return [200, result, {}, ""] ;
			};
			
			var excelDisp = function(method, url, data, headers, params) {
				var result;
				$.ajax({
					  url: 'app/blocks/handlers/mock_files/CalculoDisponibilidade.xlsx',
					  async: false,
					  dataType: 'vnd.openxmlformats-officedocument.spreadsheetml.sheet',
					  success: function (response) {
						  result = response;
					  }
				});
//				var blob = new Blob(result, {
//		            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
//		        });
				
				saveAs(result, "CalculoDisponibilidade.xlsx");
				
				return [200, result, {}, ""] ;
			};
			
	        
			$httpBackend.whenPOST(/\/reproduzirTaxa.*/).respond(getByInstalacaoDataTaxa);
			$httpBackend.whenGET(/\/interligacoes.*/).respond(interligacaoInternacional);
			$httpBackend.whenGET(/\/usinas.*/).respond(usinas);
			//2405 -- Manter Agendamentos - OBS cuidado com a ordem dessa URL, pode ser influenciada pela URL do 2401 - agendamentos-calculo
			$httpBackend.whenPOST(/\/agendamentos-calculo-by-date.*/).respond(agendamentosCalculoByDate);
			$httpBackend.whenPOST(/\/agendamentos-retificacoes-by-date.*/).respond(agendamentosRetificacoesByDate);
			//2401 -- Solicitar Cálculo de Taxas
			$httpBackend.whenPOST(/\/agendamentos-calculo-by-instalacao.*/).respond(agendamentosCalculoByInstalacao);
			$httpBackend.whenPOST(/\/agendamentos-calculo.*/).respond(agendamentosCalculo);
			//2402 - Consultar Taxas
			$httpBackend.whenPOST(/\/instalacaoTaxasByInstalacaoPeriodo.*/).respond(instalacaoTaxasByInstalacaoPeriodo);
			$httpBackend.whenPOST(/\/memoriacalculo.*/).respond(memoriacaAcumuladaRegraNova);
//			$httpBackend.whenPUT(/\/eventos.*/).respond(eventos);
			//2406 -- Exibir Notificações
			$httpBackend.whenGET(/\/notificacaoByRoles.*/).respond(notificacaoByRoles);
			$httpBackend.whenGET(/\/getNotificacoesLidasByUser.*/).respond(getNotificacoesLidasByUser);
			$httpBackend.whenPOST(/\/lerNotificacao.*/).respond(lerNotificacao);
			//2407 -- Consultar Eventos
			$httpBackend.whenPOST(/\/eventos.*/).respond(eventos2407);
			//Taxa Ref
			$httpBackend.whenGET(/\/taxaReferenciaByTipoInstalacao.*/).respond(taxaReferenciaByTipoInstalacao);
			//0607 -- Manter Parametrização
			$httpBackend.whenGET(/\/parametrizacao-retificacoes.*/).respond(parametrizacaoRetificacoes);
			$httpBackend.whenPOST(/\/parametrizacao-retificacoes.*/).respond(parametrizacaoRetificacoesPOST);
			//2403 -- Consultar Disponibilidades
			$httpBackend.whenPUT(/\/disp.*/).respond(dispPUT);
			$httpBackend.whenPOST(/\/disp.*/).respond(dispPOST);
			$httpBackend.whenPOST(/\/excel-disp.*/).respond(excelDisp);
			
			
			$httpBackend.whenPOST(/.*/).passThrough();
			$httpBackend.whenGET(/.*/).passThrough();
			$httpBackend.whenPUT(/.*/).passThrough();
			$httpBackend.whenDELETE(/.*/).passThrough();
//
//		}
		}
	}
})();
