package br.org.ons.exemplo.service;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import br.org.ons.exemplo.OnsExemploReadApp;
import br.org.ons.exemplo.common.Apuracao;
import br.org.ons.exemplo.common.Evento;
import br.org.ons.exemplo.common.EventosApuradosEvent;
import br.org.ons.exemplo.common.Parametro;
import br.org.ons.exemplo.common.ParametrosCalculadosEvent;
import br.org.ons.exemplo.common.Taxa;
import br.org.ons.exemplo.common.TaxasCalculadasEvent;
import br.org.ons.exemplo.common.Usina;
import br.org.ons.exemplo.common.UsinaAtualizadaEvent;
import br.org.ons.exemplo.common.UsinaCriadaEvent;
import br.org.ons.exemplo.common.UsinaExcluidaEvent;
import br.org.ons.exemplo.domain.CadastroUsina;
import br.org.ons.exemplo.repository.CadastroUsinaRepository;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;

/**
 * Teste para CadastroUsinaEventHandler
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnsExemploReadApp.class)
@WebAppConfiguration
@IntegrationTest
public class CadastroUsinaEventHandlerTest {

	private static final String DEFAULT_ID = "AAAAA";
	private static final String DEFAULT_NOME_CURTO = "AAAAA";
	private static final String DEFAULT_TIPO_USINA = "AAAAA";
	private static final Double DEFAULT_POTENCIA_CALCULO = 1.0;
	private static final String DEFAULT_APURACOES = "[{\"dataInicio\":\"1970-01-01T00:00Z[GMT]\",\"dataFim\":\"1970-01-31T00:00Z[GMT]\",\"status\":\"EVENTOS_APURADOS\"}]";
	
	private static final Long DEFAULT_MAJOR_VERSION = 1L;
	private static final Long DEFAULT_MINOR_VERSION = 1L;
	private static final String DEFAULT_VERSION = "1.1";
	
	private static final String UPDATED_NOME_CURTO = "BBBBB";
	private static final String UPDATED_TIPO_USINA = "BBBBB";
	private static final Double UPDATED_POTENCIA_CALCULO = 2.0;
	
	private static final Long UPDATED_MAJOR_VERSION = 2L;
	private static final Long UPDATED_MINOR_VERSION = 2L;
	private static final Object UPDATED_VERSION = "2.2";
	
	private static final ZonedDateTime DATA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.of("GMT"));
	private static final ZonedDateTime DATA_FIM = DATA_INICIO.plusMonths(1).minusDays(1);
	private static final String EVENTO_ID_1 = "aaaaa";
	private static final String EVENTO_ID_2 = "bbbbb";
	private static final String EVENTO_ID_3 = "ccccc";

	/**
	 * Repositório real com base de dados de teste em memória
	 */
	@Inject
	private CadastroUsinaRepository cadastroUsinaRepository;

	/**
	 * Serializador Jackson
	 */
	@Inject
	private ObjectMapper objectMapper;

	/**
	 * Instância testada
	 */
	private CadastroUsinaEventHandler eventHandler;
	
	@PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        eventHandler = new CadastroUsinaEventHandler();
        ReflectionTestUtils.setField(eventHandler, "cadastroUsinaRepository", cadastroUsinaRepository);
        ReflectionTestUtils.setField(eventHandler, "objectMapper", objectMapper);
    }

	@Before
	public void resetTest() {
		// Limpa a base de dados
		cadastroUsinaRepository.deleteAll();
	}
	
	/**
	 * Testa o tratamento do evento UsinaCriadaEvent
	 * @throws Exception
	 */
	@Test
	public void handleUsinaCriadaEvent() throws Exception {
        UsinaCriadaEvent event = new UsinaCriadaEvent();
		event.setUsina(defaultUsina());
		
		EventMessage<UsinaCriadaEvent> message = new EventMessage<>();
		message.setEvent(event);
		message.setMetadata(defaultMetadata());
		
		eventHandler.handleUsinaCriadaEvent(message);

        CadastroUsina cadastroUsina = cadastroUsinaRepository.findOne(DEFAULT_ID);
        assertThat(cadastroUsina.getNomeCurto()).isEqualTo(DEFAULT_NOME_CURTO);
        assertThat(cadastroUsina.getTipoUsina()).isEqualTo(DEFAULT_TIPO_USINA);
        assertThat(cadastroUsina.getPotenciaCalculo()).isEqualTo(DEFAULT_POTENCIA_CALCULO);
        assertThat(cadastroUsina.getVersion()).isEqualTo(DEFAULT_VERSION);
	}

	/**
	 * Testa o tratamento do evento UsinaCriadaEvent com erro
	 * @throws Exception
	 */
	@Test
	public void handleUsinaCriadaEventFail() throws Exception {
		// Envia o evento com uma usina já existente na base
		cadastroUsinaRepository.save(defaultCadastroUsina());
		
		UsinaCriadaEvent event = new UsinaCriadaEvent();
		event.setUsina(defaultUsina());
		
		EventMessage<UsinaCriadaEvent> message = new EventMessage<>();
		message.setEvent(event);
		message.setMetadata(defaultMetadata());
		
		eventHandler.handleUsinaCriadaEvent(message);
	}

	/**
	 * Testa o tratamento do evento UsinaAtualizadaEvent
	 * @throws Exception
	 */
	@Test
	public void handleUsinaAtualizadaEvent() throws Exception {
		cadastroUsinaRepository.save(defaultCadastroUsina());
		
		UsinaAtualizadaEvent event = new UsinaAtualizadaEvent();
		event.setUsina(updatedUsina());
		
		EventMessage<UsinaAtualizadaEvent> message = new EventMessage<>();
		message.setEvent(event);
		message.setMetadata(updatedMetadata());
		
		eventHandler.handleUsinaAtualizadaEvent(message);
		
		CadastroUsina cadastroUsina = cadastroUsinaRepository.findOne(DEFAULT_ID);
		assertThat(cadastroUsina.getNomeCurto()).isEqualTo(UPDATED_NOME_CURTO);
		assertThat(cadastroUsina.getTipoUsina()).isEqualTo(UPDATED_TIPO_USINA);
		assertThat(cadastroUsina.getPotenciaCalculo()).isEqualTo(UPDATED_POTENCIA_CALCULO);
		assertThat(cadastroUsina.getVersion()).isEqualTo(UPDATED_VERSION);
	}

	/**
	 * Testa o tratamento do evento UsinaAtualizadaEvent com erro
	 * @throws Exception
	 */
	@Test
	public void handleUsinaAtualizadaEventFail() throws Exception {
		// Envia o evento sem haver uma usina existente na base
		UsinaAtualizadaEvent event = new UsinaAtualizadaEvent();
		event.setUsina(updatedUsina());
		
		EventMessage<UsinaAtualizadaEvent> message = new EventMessage<>();
		message.setEvent(event);
		message.setMetadata(updatedMetadata());
		
		eventHandler.handleUsinaAtualizadaEvent(message);
	}

	/**
	 * Testa o tratamento do evento UsinaExcluidaEvent
	 * @throws Exception
	 */
	@Test
	public void handleUsinaExcluidaEvent() throws Exception {
		cadastroUsinaRepository.save(defaultCadastroUsina());
		
		UsinaExcluidaEvent event = new UsinaExcluidaEvent();
		
		EventMessage<UsinaExcluidaEvent> message = new EventMessage<>();
		message.setEvent(event);
		message.setMetadata(defaultMetadata());
		
		eventHandler.handleUsinaExcluidaEvent(message);
		
		CadastroUsina cadastroUsina = cadastroUsinaRepository.findOne(DEFAULT_ID);
		assertThat(cadastroUsina).isNull();
	}

	/**
	 * Testa o tratamento do evento UsinaExcluidaEvent com erro
	 * @throws Exception
	 */
	@Test
	public void handleUsinaExcluidaEventFail() throws Exception {
		// Envia o evento sem haver uma usina existente na base
		UsinaExcluidaEvent event = new UsinaExcluidaEvent();
		
		EventMessage<UsinaExcluidaEvent> message = new EventMessage<>();
		message.setEvent(event);
		message.setMetadata(defaultMetadata());
		
		eventHandler.handleUsinaExcluidaEvent(message);
	}

	/**
	 * Testa o tratamento do evento EventosApuradosEvent
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void handleEventosApuradosEvent() throws Exception {
		// Testando criação da apuração
		CadastroUsina cadastro = defaultCadastroUsina();
		cadastro.setApuracoes("[]");
		cadastroUsinaRepository.save(cadastro);
		
		EventosApuradosEvent event = new EventosApuradosEvent();
		event.setDataInicio(DATA_INICIO);
		event.setDataFim(DATA_FIM);
		event.setEventos(eventos());
		
		EventMessage<EventosApuradosEvent> message = new EventMessage<>();
		message.setEvent(event);
		message.setMetadata(defaultMetadata());
		
		eventHandler.handleEventosApuradosEvent(message);
		
		CadastroUsina cadastroUsina = cadastroUsinaRepository.findOne(DEFAULT_ID);
		String apuracoes = cadastroUsina.getApuracoes();
		assertThat(((List<String>) JsonPath.read(apuracoes, "$.[*].status"))).contains(Apuracao.Status.EVENTOS_APURADOS.name());

		// Testando atualização de apuração existente
		eventHandler.handleEventosApuradosEvent(message);
		
		// Testando com cadastroUsina não existente
		cadastroUsinaRepository.deleteAll();
		eventHandler.handleEventosApuradosEvent(message);
	}

	/**
	 * Testa o tratamento do evento ParametrosCalculadosEvent
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void handleParametrosCalculadosEvent() throws Exception {
		cadastroUsinaRepository.save(defaultCadastroUsina());
		
		ParametrosCalculadosEvent event = new ParametrosCalculadosEvent();
		event.setDataInicio(DATA_INICIO);
		event.setDataFim(DATA_FIM);
		event.setParametros(parametros());
		
		EventMessage<ParametrosCalculadosEvent> message = new EventMessage<>();
		message.setEvent(event);
		message.setMetadata(defaultMetadata());
		
		eventHandler.handleParametrosCalculadosEvent(message);
		
		CadastroUsina cadastroUsina = cadastroUsinaRepository.findOne(DEFAULT_ID);
		String apuracoes = cadastroUsina.getApuracoes();
		assertThat(((List<String>) JsonPath.read(apuracoes, "$.[*].status"))).contains(Apuracao.Status.PARAMETROS_CALCULADOS.name());

		// Testando com cadastroUsina não existente
		cadastroUsinaRepository.deleteAll();
		eventHandler.handleParametrosCalculadosEvent(message);
	}

	/**
	 * Testa o tratamento do evento TaxasCalculadasEvent
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void handleTaxasCalculadasEvent() throws Exception {
		cadastroUsinaRepository.save(defaultCadastroUsina());
		
		TaxasCalculadasEvent event = new TaxasCalculadasEvent();
		event.setDataInicio(DATA_INICIO);
		event.setDataFim(DATA_FIM);
		event.setTaxas(taxas());
		
		EventMessage<TaxasCalculadasEvent> message = new EventMessage<>();
		message.setEvent(event);
		message.setMetadata(defaultMetadata());
		
		eventHandler.handleTaxasCalculadasEvent(message);
		
		CadastroUsina cadastroUsina = cadastroUsinaRepository.findOne(DEFAULT_ID);
		String apuracoes = cadastroUsina.getApuracoes();
		assertThat(((List<String>) JsonPath.read(apuracoes, "$.[*].status"))).contains(Apuracao.Status.TAXAS_CALCULADAS.name());
		assertThat(((List<String>) JsonPath.read(apuracoes, "$.[*].taxas[*].codigo"))).contains("TEIP", "TEIP oper");
		assertThat(((List<String>) JsonPath.read(apuracoes, "$.[*].taxas[*].parametros[*].codigo"))).contains("HP", "HDP", "HEDP");
		assertThat(((List<String>) JsonPath.read(apuracoes, "$.[*].taxas[*].parametros[*].eventos[*].estadoOperativo"))).contains("LIG", "DPR");
		assertThat(((List<String>) JsonPath.read(apuracoes, "$.[*].taxas[*].parametros[*].eventos[*].condicaoOperativa"))).contains("NOR", "-");
		assertThat(((List<String>) JsonPath.read(apuracoes, "$.[*].taxas[*].parametros[*].eventos[*].classificacaoOrigem"))).contains("-", "GTR");
		assertThat(((List<Double>) JsonPath.read(apuracoes, "$.[*].taxas[*].parametros[*].eventos[*].potenciaDisponivel"))).contains(0.0, 100.0);

		// Testando com cadastroUsina não existente
		cadastroUsinaRepository.deleteAll();
		eventHandler.handleTaxasCalculadasEvent(message);
	}

	/**
	 * @return CadastroUsina com valores default
	 */
	private CadastroUsina defaultCadastroUsina() {
		CadastroUsina cadastroUsina = new CadastroUsina();
		cadastroUsina.setId(DEFAULT_ID);
		cadastroUsina.setNomeCurto(DEFAULT_NOME_CURTO);
		cadastroUsina.setTipoUsina(DEFAULT_TIPO_USINA);
		cadastroUsina.setPotenciaCalculo(DEFAULT_POTENCIA_CALCULO);
		cadastroUsina.setApuracoes(DEFAULT_APURACOES);
		return cadastroUsina;
	}

	/**
	 * @return Usina com os valores default
	 */
	private Usina defaultUsina() {
		Usina usina = new Usina();
        usina.setId(DEFAULT_ID);
        usina.setNomeCurto(DEFAULT_NOME_CURTO);
        usina.setTipoUsina(DEFAULT_TIPO_USINA);
        usina.setPotenciaCalculo(DEFAULT_POTENCIA_CALCULO);
		return usina;
	}
	
	/**
	 * @return Usina com os valores alterados
	 */
	private Usina updatedUsina() {
		Usina usina = new Usina();
		usina.setId(DEFAULT_ID);
		usina.setNomeCurto(UPDATED_NOME_CURTO);
		usina.setTipoUsina(UPDATED_TIPO_USINA);
		usina.setPotenciaCalculo(UPDATED_POTENCIA_CALCULO);
		return usina;
	}

	/**
	 * @return Metadata com valores default
	 */
	private EventMetadata defaultMetadata() {
		EventMetadata metadata = new EventMetadata();
		metadata.setAggregateId(DEFAULT_ID);
		metadata.setMajorVersion(DEFAULT_MAJOR_VERSION);
		metadata.setMinorVersion(DEFAULT_MINOR_VERSION);
		return metadata;
	}

	/**
	 * @return Metadata com valores alterados
	 */
	private EventMetadata updatedMetadata() {
		EventMetadata metadata = new EventMetadata();
		metadata.setAggregateId(DEFAULT_ID);
		metadata.setMajorVersion(UPDATED_MAJOR_VERSION);
		metadata.setMinorVersion(UPDATED_MINOR_VERSION);
		return metadata;
	}

	/**
	 * @return Eventos com valores default
	 */
	private List<Evento> eventos() {
		List<Evento> eventos = new ArrayList<>();
		eventos.add(new Evento(EVENTO_ID_1, DATA_INICIO, "LIG", "NOR", "-", 100.0));
		eventos.add(new Evento(EVENTO_ID_2, DATA_INICIO.plusDays(15).plusHours(10), "DPR", "-", "GTR", 0.0));
		eventos.add(new Evento(EVENTO_ID_3, DATA_INICIO.plusDays(15).plusHours(12), "LIG", "NOR", "-", 100.0));
		return eventos;
	}

	/**
	 * @return Parâmetros com valores default
	 */
	private List<Parametro> parametros() {
		List<Parametro> parametros = new ArrayList<>();
		Parametro hp = new Parametro();
		hp.setCodigo("HP");
		hp.setValor(720.0);
		hp.setEventos(eventos());
		parametros.add(hp);
		Parametro hdp = new Parametro();
		hdp.setCodigo("HDP");
		hdp.setValor(2.5);
		hdp.setEventos(eventos());
		parametros.add(hdp);
		Parametro hedp = new Parametro();
		hedp.setCodigo("HEDP");
		hedp.setValor(2.5);
		hedp.setEventos(eventos());
		parametros.add(hedp);
		return parametros;
	}

	/**
	 * @return Taxas com valores default
	 */
	private List<Taxa> taxas() {
		List<Taxa> taxas = new ArrayList<>();
		Taxa teip = new Taxa();
		teip.setCodigo("TEIP");
		teip.setValor(0.12345678);
		teip.setParametros(parametros());
		taxas.add(teip);
		Taxa teipOper = new Taxa();
		teipOper.setCodigo("TEIP oper");
		teipOper.setValor(0.87654321);
		teipOper.setParametros(parametros());
		taxas.add(teipOper);
		return taxas;
	}

}
