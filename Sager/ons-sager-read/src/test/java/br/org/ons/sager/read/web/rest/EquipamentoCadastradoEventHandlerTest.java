package br.org.ons.sager.read.web.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.org.ons.geracao.cadastro.PotenciaCalculo;
import br.org.ons.geracao.cadastro.TipoInstalacao;
import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.OrigemComentario;
import br.org.ons.geracao.evento.StatusEvento;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.evento.franquia.Franquia;
import br.org.ons.geracao.evento.ComentarioSituacao.StatusObjeto;
import br.org.ons.geracao.evento.ComentarioSituacao.TipoObjeto;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.geracao.modelagem.PeriodoApuracao;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.sager.instalacao.event.EquipamentoCadastradoEvent;
import br.org.ons.sager.instalacao.event.EventosApuradosEvent;
import br.org.ons.sager.read.OnsSagerReadApp;
import br.org.ons.sager.read.domain.Equipamento;
import br.org.ons.sager.read.repository.EquipamentoRepository;
import br.org.ons.sager.read.service.EquipamentoEventHandler;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class EquipamentoCadastradoEventHandlerTest {

	@Inject
	private EquipamentoRepository equipamentoCadastradoRepository;

	@Inject
	private EquipamentoEventHandler equipamentoCadastradoEventHandler;
	
	/*
	 * CT010004 - Adicionar um equipamento.
	 * Resultado esperado: adicionar o equipamento no repositorio.
	 */
	
	@Test
	public void CT010004() throws Exception {
		
		equipamentoCadastradoRepository.deleteAll();
		
		@SuppressWarnings("deprecation")
		Date data = new Date("2016/11/28 03:00:00");

		EventoMudancaEstadoOperativo evento = new EventoMudancaEstadoOperativo();
			evento.setId("ID01");
			evento.setVersion("version01");
			evento.setIdInstalacao("idInstalacao01");
			evento.setIdEquipamento("idEquipamento01");
			evento.setDataVerificada(data);
			ComentarioSituacao comentario1 = new ComentarioSituacao();
				comentario1.setDataInsercao(data);
				comentario1.setDescricao("Descricao");
				comentario1.setOrigem(OrigemComentario.ONS);
				comentario1.setTipo(TipoComentario.COMENTARIO);
				comentario1.setDataFim(data);
				comentario1.setNomeObjeto("nome01");
				comentario1.setStatusObjeto(StatusObjeto.FORA_OPERACAO_COMERCIAL);
				comentario1.setTipoObjeto(TipoObjeto.USINA);
			List<Comentario> comentarios = new ArrayList<>();	
				evento.setComentarios(comentarios);
				evento.setJustificativaRestricaoDesligamento("justificativaDesliagamento");
				evento.setStatus(StatusEvento.RETIFICADO_REMOVIDO);
				evento.setDataUltimaConsolidacao(data);
				evento.setValorPotenciaDisponivel(12.30);
				evento.setEstadoOperativo("estadoOperativo");
				evento.setCondicaoOperativa("CONDICAO");
				
			List<EventoMudancaEstadoOperativo> eventos = new ArrayList<>();
				eventos.add(evento);
				
			PeriodoApuracao apuracao = new PeriodoApuracao();
			apuracao.setDataFim(data);
			apuracao.setDataInicio(data);
			apuracao.setEventos(eventos);
			
		Periodo periodoValidade = new Periodo();
			periodoValidade.setDataFim(data);
			periodoValidade.setDataInicio(data);
			
		Franquia franquia = new Franquia();
			franquia.setCodigo("cod01");
			franquia.setIdEquipamento("idEquipe01");
			franquia.setPeriodoValidade(periodoValidade);
			franquia.setPeriodoVigencia(periodoValidade);
			franquia.setQtMinutosServicoParaUso(10);
			franquia.setValorDisponivel(12);
			franquia.setValorLimite(13);
			franquia.setVersao("01");
			
		List<Franquia> franquias = new ArrayList<>();
			franquias.add(franquia);
			
		PotenciaCalculo pot = new PotenciaCalculo();
			pot.setIdEquipamento("idEquipe01");
			pot.setValor(30.2);
			pot.setVigencia(periodoValidade);
		
		List<PotenciaCalculo> potenciasCalculo = new ArrayList<>();
			potenciasCalculo.add(pot);
			
		EventosApuradosEvent eventosApurados = new EventosApuradosEvent();
			eventosApurados.setApuracao(apuracao);
		
		List<Periodo> suspensoes = new ArrayList<>();
			suspensoes.add(periodoValidade);
			
		UnidadeGeradora equipamento = new UnidadeGeradora();
			equipamento.setDataDesativacao(data);
			equipamento.setDataEventoEOC(data);
			equipamento.setDataImplantacao(data);
			equipamento.setDataRenovacaoProrrogacaoConcessao(data);
			equipamento.setFranquias(franquias);
			equipamento.setId("id01");
			equipamento.setIdInstalacao("idInst01");
			equipamento.setNome("nome01");
			equipamento.setPotenciasCalculo(potenciasCalculo);
			equipamento.setQuantidadeHorasServico(12);
			equipamento.setSuspensoes(suspensoes);
			equipamento.setTipoInstalacao(TipoInstalacao.USINA);
			equipamento.setVersao("01");
			
			
		EquipamentoCadastradoEvent equip = new EquipamentoCadastradoEvent();
			equip.setEquipamento(equipamento);
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("Usina01");
		
		EventMessage<EquipamentoCadastradoEvent> eventMessage = new EventMessage<>();
			eventMessage.setEvent(equip);
			eventMessage.setMetadata(metadata);
		
		equipamentoCadastradoEventHandler.handleEquipamentoCadastradoEvent(eventMessage);

		assertThat(equipamentoCadastradoRepository.count() == 1 ).isTrue();

	}
	
	/*
	 * CT010005 - Adicionar um equipamento sem comentario.
	 * Resultado esperado: adicionar o equipamento no repositorio.
	 */
	
	@Test
	public void CT010005() throws Exception {
		
		equipamentoCadastradoRepository.deleteAll();
		
		@SuppressWarnings("deprecation")
		Date data = new Date("2016/11/28 03:00:00");

			EventoMudancaEstadoOperativo evento = new EventoMudancaEstadoOperativo();
				evento.setId("ID01");
				evento.setVersion("version01");
				evento.setIdInstalacao("idInstalacao01");
				evento.setIdEquipamento("idEquipamento01");
				evento.setDataVerificada(data);
				
			List<EventoMudancaEstadoOperativo> eventos = new ArrayList<>();
				eventos.add(evento);
				
			PeriodoApuracao apuracao = new PeriodoApuracao();
			apuracao.setDataFim(data);
			apuracao.setDataInicio(data);
			apuracao.setEventos(eventos);
			
		Periodo periodoValidade = new Periodo();
			periodoValidade.setDataFim(data);
			periodoValidade.setDataInicio(data);
			
		Franquia franquia = new Franquia();
			franquia.setCodigo("cod01");
			franquia.setIdEquipamento("idEquipe01");
			franquia.setPeriodoValidade(periodoValidade);
			franquia.setPeriodoVigencia(periodoValidade);
			franquia.setQtMinutosServicoParaUso(10);
			franquia.setValorDisponivel(12);
			franquia.setValorLimite(13);
			franquia.setVersao("01");
			
		List<Franquia> franquias = new ArrayList<>();
			franquias.add(franquia);
			
		PotenciaCalculo pot = new PotenciaCalculo();
			pot.setIdEquipamento("idEquipe01");
			pot.setValor(30.2);
			pot.setVigencia(periodoValidade);
		
		List<PotenciaCalculo> potenciasCalculo = new ArrayList<>();
			potenciasCalculo.add(pot);
			
		EventosApuradosEvent eventosApurados = new EventosApuradosEvent();
			eventosApurados.setApuracao(apuracao);
		
		List<Periodo> suspensoes = new ArrayList<>();
			suspensoes.add(periodoValidade);
			
		UnidadeGeradora equipamento = new UnidadeGeradora();
			equipamento.setDataDesativacao(data);
			equipamento.setDataEventoEOC(data);
			equipamento.setDataImplantacao(data);
			equipamento.setDataRenovacaoProrrogacaoConcessao(data);
			equipamento.setFranquias(franquias);
			equipamento.setId("id01");
			equipamento.setIdInstalacao("idInst01");
			equipamento.setNome("nome01");
			equipamento.setPotenciasCalculo(potenciasCalculo);
			equipamento.setQuantidadeHorasServico(12);
			equipamento.setSuspensoes(suspensoes);
			equipamento.setTipoInstalacao(TipoInstalacao.USINA);
			equipamento.setVersao("01");
			
			
		EquipamentoCadastradoEvent equip = new EquipamentoCadastradoEvent();
			equip.setEquipamento(equipamento);
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("Usina01");
		
		EventMessage<EquipamentoCadastradoEvent> eventMessage = new EventMessage<>();
			eventMessage.setEvent(equip);
			eventMessage.setMetadata(metadata);
		
		equipamentoCadastradoEventHandler.handleEquipamentoCadastradoEvent(eventMessage);

		assertThat(equipamentoCadastradoRepository.count() == 1 ).isTrue();

	}
	
	/*
	 * CT010006 - Adicionar um equipamento sem os valores basicos.
	 * Resultado esperado: gerar excessão de erro.
	 */
	
	@Test(expected=OnsRuntimeException.class)
	public void CT010006() throws Exception {
		
		equipamentoCadastradoRepository.deleteAll();
			
		EquipamentoCadastradoEvent equip = new EquipamentoCadastradoEvent();
			
		EventMetadata metadata= new EventMetadata();
		
		EventMessage<EquipamentoCadastradoEvent> eventMessage = new EventMessage<>();
			eventMessage.setEvent(equip);
			eventMessage.setMetadata(metadata);
		
		equipamentoCadastradoEventHandler.handleEquipamentoCadastradoEvent(eventMessage);

	}
	
	/*
	 * CT010007 - Procurar equipamento.
	 * Resultado esperado: 
	 */
	
	@Test
	public void ct010007() throws Exception {
		@SuppressWarnings("deprecation")
		Date data = new Date("2016/11/28 03:00:00");

		UnidadeGeradora equipamento = new UnidadeGeradora();
			
		EquipamentoCadastradoEvent equip = new EquipamentoCadastradoEvent();
			equip.setEquipamento(equipamento);
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("Usina01");
		
		EventMessage<EquipamentoCadastradoEvent> eventMessage = new EventMessage<>();
			eventMessage.setMetadata(metadata);

		String[] ids = new String[]{"a","b","c"};
		
		List<Equipamento> result = equipamentoCadastradoEventHandler.findById(ids, null);
		
		System.out.println(result);
		assertThat(result == null ).isTrue();
	}
	
	/*
	 * CT010008 - Procurar equipamento passando valores null.
	 * Resultado esperado: 
	 */
	
	@Test
	public void ct010008() throws Exception {
		@SuppressWarnings("deprecation")
		Date data = new Date("2016/11/28 03:00:00");

		UnidadeGeradora equipamento = new UnidadeGeradora();
			
		EquipamentoCadastradoEvent equip = new EquipamentoCadastradoEvent();
			equip.setEquipamento(equipamento);
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("Usina01");
		
		EventMessage<EquipamentoCadastradoEvent> eventMessage = new EventMessage<>();
			eventMessage.setMetadata(metadata);
		
		List<Equipamento> result = equipamentoCadastradoEventHandler.findById(null, null);
		
		System.out.println(result);
		assertThat(result == null ).isTrue();
	}
	
}
