package br.org.ons.sager.read.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jayway.jsonpath.JsonPath;

import br.org.ons.geracao.cadastro.Agente;
import br.org.ons.geracao.cadastro.EquipamentoInterligacaoInternacional;
import br.org.ons.geracao.cadastro.Instalacao;
import br.org.ons.geracao.cadastro.InterligacaoInternacional;
import br.org.ons.geracao.cadastro.TipoInstalacao;
import br.org.ons.geracao.cadastro.TipoUsina;
import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.geracao.cadastro.Usina;
import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.OrigemComentario;
import br.org.ons.geracao.evento.StatusEvento;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.evento.TipoDisponibilidade;
import br.org.ons.geracao.evento.franquia.Franquia;
import br.org.ons.geracao.evento.taxa.Taxa;
import br.org.ons.geracao.evento.taxa.TipoTaxa;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.geracao.evento.ComentarioSituacao.StatusObjeto;
import br.org.ons.geracao.evento.ComentarioSituacao.TipoObjeto;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.sager.instalacao.event.DisponibilidadesCalculadasEvent;
import br.org.ons.sager.instalacao.event.EquipamentoCadastradoEvent;
import br.org.ons.sager.instalacao.event.InstalacaoCadastradaEvent;
import br.org.ons.sager.read.OnsSagerReadApp;
import br.org.ons.sager.read.domain.Equipamento;
import br.org.ons.sager.read.domain.Evento;
import br.org.ons.sager.read.domain.Interligacoes;
import br.org.ons.sager.read.domain.QInterligacoes;
import br.org.ons.sager.read.domain.QUsinas;
import br.org.ons.sager.read.domain.Usinas;
import br.org.ons.sager.read.repository.EventoRepository;
import br.org.ons.sager.read.repository.InterligacoesRepository;
import br.org.ons.sager.read.repository.UsinasRepository;
import br.org.ons.sager.read.service.EventoService;
import br.org.ons.sager.read.service.InstalacaoEventHandler;

/**
 * EventHandler que recebe eventos publicados no barramento
 * e atualiza o repositório de dados de leitura
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class InstalacaoEventInsHandlerTest {
    @Inject
    private UsinasRepository usinasRepository;

    
	@Inject
	private InterligacoesRepository interligacoesRepository;
	
	private InstalacaoEventHandler eventHandler;

	@PostConstruct
	public void setup() {
		eventHandler = new InstalacaoEventHandler(usinasRepository,interligacoesRepository);

	}
	
	@Before
	public void initTest() {
		usinasRepository.deleteAll();
		interligacoesRepository.deleteAll();
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("cnos", ""));
		Date data = new Date("2016/11/28 03:00:00");
		BigDecimal bg = new BigDecimal(12);


		Periodo periodoValidade = new Periodo();
			periodoValidade.setDataFim(data);
			periodoValidade.setDataInicio(data);
		
		Franquia franquia = new Franquia();
			franquia.setCodigo("cod01");
			franquia.setIdEquipamento("idEquip01");
			franquia.setPeriodoValidade(periodoValidade);
			franquia.setQtMinutosServicoParaUso(12);
			franquia.setValorDisponivel(10);
			franquia.setValorLimite(13);
			franquia.setVersao("01");
		
		List<Franquia> franquias = new ArrayList<>();
			franquias.add(franquia);
		
		Equipamento equipamento = new Equipamento();
			equipamento.setId("id01");
			equipamento.setNome("nom01");
			equipamento.setParticipacao(bg);
			equipamento.setValorPotencia(bg);
			
		Usinas usina1 = new Usinas();
			usina1.setId("idUsina01");
			usina1.setNome("NomeUsina01");
			usina1.setTipo_id("tipoUsina");
			
			
		List<Equipamento> equipamentos = new ArrayList<>();
			equipamentos.add(equipamento);
		
		Interligacoes inters = new Interligacoes();
			inters.setEquipamentos(equipamentos);
			inters.setId("idInter01");
			inters.setIdagente("idAgente01");

		usinasRepository.save(usina1);
		interligacoesRepository.save(inters);
	}
	
	@Test
	public void cadastrarEquipamentoTipoUnidadeGeradora() throws Exception {
		
		Date data = new Date("2016/11/28 03:00:00");

		Periodo periodoValidade = new Periodo();
			periodoValidade.setDataFim(data);
			periodoValidade.setDataInicio(data);
			
		Franquia franquia = new Franquia();
			franquia.setCodigo("cod01");
			franquia.setIdEquipamento("idEquip01");
			franquia.setPeriodoValidade(periodoValidade);
			franquia.setQtMinutosServicoParaUso(12);
			franquia.setValorDisponivel(10);
			franquia.setValorLimite(13);
			franquia.setVersao("01");
			
		List<Franquia> franquias = new ArrayList<>();
			franquias.add(franquia);
			
		UnidadeGeradora equipamento = new UnidadeGeradora();
			equipamento.setDataDesativacao(data);
			equipamento.setDataEventoEOC(data);
			equipamento.setDataImplantacao(data);
			equipamento.setDataRenovacaoProrrogacaoConcessao(data);
			equipamento.setId("id02");
			equipamento.setIdInstalacao("idInstalacao01");
			equipamento.setNome("nome01");
			equipamento.setTipoInstalacao(TipoInstalacao.USINA);
			equipamento.setVersao("01");
			equipamento.setFranquias(franquias);

			
		EquipamentoCadastradoEvent event = new EquipamentoCadastradoEvent();	
			event.setEquipamento(equipamento);
			
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("idUsina01");
			
		EventMessage<EquipamentoCadastradoEvent> eventMessage = new EventMessage<>();	
			eventMessage.setEvent(event);
			eventMessage.setMetadata(metadata);
			
			eventHandler.handleEquipamentoCadastradoEvent(eventMessage);
			eventHandler.handleEquipamentoCadastradoEvent(eventMessage);
	}
	
	@Test
	public void cadastrarEquipamentoSemUnidadeGeradoraNoRepository() throws Exception {
		usinasRepository.deleteAll();
		
		Date data = new Date("2016/11/28 03:00:00");

		Periodo periodoValidade = new Periodo();
			periodoValidade.setDataFim(data);
			periodoValidade.setDataInicio(data);
			
		Franquia franquia = new Franquia();
			franquia.setCodigo("cod01");
			franquia.setIdEquipamento("idEquip01");
			franquia.setPeriodoValidade(periodoValidade);
			franquia.setQtMinutosServicoParaUso(12);
			franquia.setValorDisponivel(10);
			franquia.setValorLimite(13);
			franquia.setVersao("01");
			
		List<Franquia> franquias = new ArrayList<>();
			franquias.add(franquia);
			
		UnidadeGeradora equipamento = new UnidadeGeradora();
			equipamento.setDataDesativacao(data);
			equipamento.setDataEventoEOC(data);
			equipamento.setDataImplantacao(data);
			equipamento.setDataRenovacaoProrrogacaoConcessao(data);
			equipamento.setId("id02");
			equipamento.setIdInstalacao("idInstalacao01");
			equipamento.setNome("nome01");
			equipamento.setTipoInstalacao(TipoInstalacao.USINA);
			equipamento.setVersao("01");
			equipamento.setFranquias(franquias);

			
		EquipamentoCadastradoEvent event = new EquipamentoCadastradoEvent();	
			event.setEquipamento(equipamento);
			
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("idUsina01");
			
		EventMessage<EquipamentoCadastradoEvent> eventMessage = new EventMessage<>();	
			eventMessage.setEvent(event);
			eventMessage.setMetadata(metadata);
			
			eventHandler.handleEquipamentoCadastradoEvent(eventMessage);

	}
	
	@Test
	public void cadastrarEquipamentoTipoEquipamentoInterligacaoInternacional() throws Exception {
		
		Date data = new Date("2016/11/28 03:00:00");

		Periodo periodoValidade = new Periodo();
			periodoValidade.setDataFim(data);
			periodoValidade.setDataInicio(data);
			
		Franquia franquia = new Franquia();
			franquia.setCodigo("cod01");
			franquia.setIdEquipamento("idEquip01");
			franquia.setPeriodoValidade(periodoValidade);
			franquia.setQtMinutosServicoParaUso(12);
			franquia.setValorDisponivel(10);
			franquia.setValorLimite(13);
			franquia.setVersao("01");
			
		List<Franquia> franquias = new ArrayList<>();
			franquias.add(franquia);
			
		EquipamentoInterligacaoInternacional equipamento = new EquipamentoInterligacaoInternacional();
			equipamento.setDataDesativacao(data);
			equipamento.setDataEventoEOC(data);
			equipamento.setDataImplantacao(data);
			equipamento.setDataRenovacaoProrrogacaoConcessao(data);
			equipamento.setId("id02");
			equipamento.setIdInstalacao("idInstalacao01");
			equipamento.setNome("nome01");
			equipamento.setTipoInstalacao(TipoInstalacao.INTERLIGACAO_INTERNACIONAL);
			equipamento.setVersao("01");
			equipamento.setFranquias(franquias);

			
		EquipamentoCadastradoEvent event = new EquipamentoCadastradoEvent();	
			event.setEquipamento(equipamento);
			
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("idInter01");
			
		EventMessage<EquipamentoCadastradoEvent> eventMessage = new EventMessage<>();	
			eventMessage.setEvent(event);
			eventMessage.setMetadata(metadata);
			eventHandler.handleEquipamentoCadastradoEvent(eventMessage);
			eventHandler.handleEquipamentoCadastradoEvent(eventMessage);
	}
	
	@Test
	public void cadastrarEquipamentoTipoEquipamentoInterligacaoInternacionalSemRepositorio() throws Exception {
		interligacoesRepository.deleteAll();
		
		Date data = new Date("2016/11/28 03:00:00");

		Periodo periodoValidade = new Periodo();
			periodoValidade.setDataFim(data);
			periodoValidade.setDataInicio(data);
			
		Franquia franquia = new Franquia();
			franquia.setCodigo("cod01");
			franquia.setIdEquipamento("idEquip01");
			franquia.setPeriodoValidade(periodoValidade);
			franquia.setQtMinutosServicoParaUso(12);
			franquia.setValorDisponivel(10);
			franquia.setValorLimite(13);
			franquia.setVersao("01");
			
		List<Franquia> franquias = new ArrayList<>();
			franquias.add(franquia);
			
		EquipamentoInterligacaoInternacional equipamento = new EquipamentoInterligacaoInternacional();
			equipamento.setDataDesativacao(data);
			equipamento.setDataEventoEOC(data);
			equipamento.setDataImplantacao(data);
			equipamento.setDataRenovacaoProrrogacaoConcessao(data);
			equipamento.setId("id02");
			equipamento.setIdInstalacao("idInstalacao01");
			equipamento.setNome("nome01");
			equipamento.setTipoInstalacao(TipoInstalacao.INTERLIGACAO_INTERNACIONAL);
			equipamento.setVersao("01");
			equipamento.setFranquias(franquias);

			
		EquipamentoCadastradoEvent event = new EquipamentoCadastradoEvent();	
			event.setEquipamento(equipamento);
			
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("idInter01");
			
		EventMessage<EquipamentoCadastradoEvent> eventMessage = new EventMessage<>();	
			eventMessage.setEvent(event);
			eventMessage.setMetadata(metadata);
			eventHandler.handleEquipamentoCadastradoEvent(eventMessage);
			eventHandler.handleEquipamentoCadastradoEvent(eventMessage);
	}
	
	@Test
	public void cadastrarEventoTipoUsina() throws Exception {
		BigDecimal bg = new BigDecimal(12);
		Date data = new Date("2016/11/28 03:00:00");
			
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
			comentarios.add(comentario1);
			
		Taxa taxa = new Taxa();	
			taxa.setCodigo("cod01");
			taxa.setId("id01");
			taxa.setTipo(TipoTaxa.ACUMULADA);
			taxa.setValor(bg);
			taxa.setDenominador(bg);
			taxa.setComentarios(comentarios);
			
		List<Taxa> taxasAjustadas = new ArrayList<>();
			taxasAjustadas.add(taxa);
			
		UnidadeGeradora unidade = new UnidadeGeradora();
			unidade.setId("idUni01");
			unidade.setNome("nomeUni01");

		List<UnidadeGeradora> unidadesGeradoras = new ArrayList<>();	
			unidadesGeradoras.add(unidade);
			
		Agente agenteProprietario = new Agente();
			agenteProprietario.setId("id01");
			agenteProprietario.setNomeCurto("nomeCurtoAgente01");
			agenteProprietario.setSigla("SIGLA");
		
		Usina instalacao = new Usina();
			instalacao.setId("id01");
			instalacao.setDataOutorgaImplantacao(data);
			instalacao.setNomeCurto("nomeCurto01");
			instalacao.setTaxasAjustadas(taxasAjustadas);
			instalacao.setUnidadesGeradoras(unidadesGeradoras);
			instalacao.setTipo(TipoUsina.UHE);
			instalacao.setAgenteProprietario(agenteProprietario);
			
		InstalacaoCadastradaEvent event = new InstalacaoCadastradaEvent();	
			event.setInstalacao(instalacao);
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("idInter01");
			Long i = 12L;
			metadata.setMinorVersion(i);
	
			
		EventMessage<InstalacaoCadastradaEvent> eventMessage = new EventMessage<>();	
			InstalacaoCadastradaEvent e;
			eventMessage.setEvent(event);

		eventHandler.handleInstalacaoCadastradaEvent(eventMessage);
		eventHandler.handleInstalacaoCadastradaEvent(eventMessage);
	}
	
	@Test
	public void cadastrarEventoTipoInterligacaoInternacional() throws Exception {
		BigDecimal bg = new BigDecimal(12);
		Date data = new Date("2016/11/28 03:00:00");
			
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
			comentarios.add(comentario1);
			
		Taxa taxa = new Taxa();	
			taxa.setCodigo("cod01");
			taxa.setId("id01");
			taxa.setTipo(TipoTaxa.ACUMULADA);
			taxa.setValor(bg);
			taxa.setDenominador(bg);
			taxa.setComentarios(comentarios);
			
		List<Taxa> taxasAjustadas = new ArrayList<>();
			taxasAjustadas.add(taxa);
			
		UnidadeGeradora unidade = new UnidadeGeradora();
			unidade.setId("idUni01");
			unidade.setNome("nomeUni01");

		List<UnidadeGeradora> unidadesGeradoras = new ArrayList<>();	
			unidadesGeradoras.add(unidade);
			
		Agente agenteProprietario = new Agente();
			agenteProprietario.setId("id01");
			agenteProprietario.setNomeCurto("nomeCurtoAgente01");
			agenteProprietario.setSigla("SIGLA");
			
		EquipamentoInterligacaoInternacional equipamento = new EquipamentoInterligacaoInternacional();
			equipamento.setId("idEquip01");
			equipamento.setNome("nomeEquip01");
			
		InterligacaoInternacional instalacao = new InterligacaoInternacional();
			instalacao.setId("id01");
			instalacao.setDataOutorgaImplantacao(data);
			instalacao.setNomeCurto("nomeCurto01");
			instalacao.setTaxasAjustadas(taxasAjustadas);
			instalacao.setAgenteResponsavel(agenteProprietario);
			instalacao.setEquipamento(equipamento);
			
		InstalacaoCadastradaEvent event = new InstalacaoCadastradaEvent();	
			event.setInstalacao(instalacao);
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("idInter01");
			Long i = 12L;
			metadata.setMinorVersion(i);
	
			
		EventMessage<InstalacaoCadastradaEvent> eventMessage = new EventMessage<>();	
			InstalacaoCadastradaEvent e;
			eventMessage.setEvent(event);

		eventHandler.handleInstalacaoCadastradaEvent(eventMessage);
		eventHandler.handleInstalacaoCadastradaEvent(eventMessage);
	}
}
