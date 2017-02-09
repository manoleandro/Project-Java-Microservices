package br.org.ons.exemplo.service;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.ons.exemplo.OnsExemploReadApp;
import br.org.ons.exemplo.common.Evento;
import br.org.ons.exemplo.common.EventosApuradosEvent;
import br.org.ons.exemplo.domain.CadastroEvento;
import br.org.ons.exemplo.repository.CadastroEventoRepository;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;

/**
 * Teste para CadastroEventoEventHandler
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnsExemploReadApp.class)
@WebAppConfiguration
@IntegrationTest
public class CadastroEventoEventHandlerTest {

	private static final String DEFAULT_ID = "AAAAA";
	
	private static final Long DEFAULT_MAJOR_VERSION = 1L;
	private static final Long DEFAULT_MINOR_VERSION = 1L;
	
	private static final ZonedDateTime DATA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.of("GMT"));
	private static final ZonedDateTime DATA_FIM = DATA_INICIO.plusMonths(1).minusDays(1);

	private static final String EVENTO_ID_1 = "aaaaa";
	private static final String EVENTO_ID_2 = "bbbbb";
	private static final String EVENTO_ID_3 = "ccccc";

	/**
	 * Repositório real com base de dados de teste em memória
	 */
	@Inject
	private CadastroEventoRepository cadastroEventoRepository;

	/**
	 * Instância testada
	 */
	private CadastroEventoEventHandler eventHandler;
	
	@PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        eventHandler = new CadastroEventoEventHandler();
        ReflectionTestUtils.setField(eventHandler, "cadastroEventoRepository", cadastroEventoRepository);
    }

	@Before
	public void resetTest() {
		// Limpa a base de dados
		cadastroEventoRepository.deleteAll();
	}
	
	/**
	 * Testa o tratamento do evento
	 * @throws Exception
	 */
	@Test
	public void handleEventosApuradosEvent() throws Exception {
		EventosApuradosEvent event = new EventosApuradosEvent();
		event.setDataInicio(DATA_INICIO);
		event.setDataFim(DATA_FIM);
		event.setEventos(eventos());
		
		EventMessage<EventosApuradosEvent> message = new EventMessage<>();
		message.setEvent(event);
		message.setMetadata(defaultMetadata());
		
		eventHandler.handleEventosApuradosEvent(message);
		
		List<CadastroEvento> cadastros = IterableConverter.toList(cadastroEventoRepository.findAll());
    	assertThat(cadastros).hasSize(3);
    	assertThat(cadastros).extracting(CadastroEvento::getId).contains(EVENTO_ID_1, EVENTO_ID_2, EVENTO_ID_3);
    	assertThat(cadastros).extracting(CadastroEvento::getEstadoOperativo).contains("LIG", "DPR");
    	assertThat(cadastros).extracting(CadastroEvento::getCondicaoOperativa).contains("NOR", "-");
    	assertThat(cadastros).extracting(CadastroEvento::getClassificacaoOrigem).contains("-", "GTR");
    	assertThat(cadastros).extracting(CadastroEvento::getPotenciaDisponivel).contains(100.0, 0.0);
    	assertThat(cadastros).extracting(CadastroEvento::getMajorVersion).contains(DEFAULT_MAJOR_VERSION);
    	assertThat(cadastros).extracting(CadastroEvento::getMinorVersion).contains(DEFAULT_MINOR_VERSION);
    	assertThat(cadastros).allMatch(timeline -> timeline.getAggregateId().equals(DEFAULT_ID));
	}

	/**
	 * @return Metadata com valores default
	 */
	private EventMetadata defaultMetadata() {
		EventMetadata metadata = new EventMetadata();
		metadata.setAggregateId(DEFAULT_ID);
		metadata.setMajorVersion(DEFAULT_MAJOR_VERSION);
		metadata.setMinorVersion(DEFAULT_MINOR_VERSION);
		metadata.setTimelineDate(DATA_INICIO);
		metadata.setCorrelationId(DEFAULT_ID);
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

}
