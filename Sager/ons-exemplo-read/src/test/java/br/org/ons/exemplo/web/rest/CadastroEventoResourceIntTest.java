package br.org.ons.exemplo.web.rest;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.org.ons.exemplo.OnsExemploReadApp;
import br.org.ons.exemplo.common.ApurarEventosCommand;
import br.org.ons.exemplo.common.ApurarEventosCommandModification;
import br.org.ons.exemplo.config.mq.CommandBus;
import br.org.ons.exemplo.domain.CadastroEvento;
import br.org.ons.exemplo.repository.CadastroEventoRepository;
import br.org.ons.exemplo.web.rest.dto.CadastroEventoRetificado;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.ReprocessCommand;


/**
 * Test class for the CadastroEventoResource REST controller.
 *
 * @see CadastroEventoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnsExemploReadApp.class)
@WebAppConfiguration
@IntegrationTest
public class CadastroEventoResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String EVENTO_ID = "AAAAA";

    private static final ZonedDateTime EVENTO_DATA_VERIFICADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final String EVENTO_DATA_VERIFICADA_STR = dateTimeFormatter.format(EVENTO_DATA_VERIFICADA);
    private static final String EVENTO_ESTADO_OPERATIVO = "AAAAA";
    private static final String EVENTO_CONDICAO_OPERATIVA = "AAAAA";
    private static final String EVENTO_CLASSIFICACAO_ORIGEM = "AAAAA";

    private static final Double EVENTO_POTENCIA_DISPONIVEL = 1D;
    private static final String EVENTO_AGGREGATE_ID = "AAAAA";

    private static final Long EVENTO_MAJOR_VERSION = 1L;
    private static final Long EVENTO_MINOR_VERSION = 1L;

    private static final ZonedDateTime EVENTO_TIMELINE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final String EVENTO_TIMELINE_DATE_STR = dateTimeFormatter.format(EVENTO_TIMELINE_DATE);
    private static final String EVENTO_CORRELATION_ID = "AAAAA";

	/**
	 * Repositório real com base de dados de teste em memória
	 */
    @Inject
    private CadastroEventoRepository cadastroEventoRepository;

    /**
     * Mock para emular acesso ao barramento
     */
	@Mock
	private CommandBus commandBus;
	
    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCadastroEventoMockMvc;

    private CadastroEvento cadastroEvento;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CadastroEventoResource cadastroEventoResource = new CadastroEventoResource();
        ReflectionTestUtils.setField(cadastroEventoResource, "cadastroEventoRepository", cadastroEventoRepository);
        ReflectionTestUtils.setField(cadastroEventoResource, "commandBus", commandBus);
        this.restCadastroEventoMockMvc = MockMvcBuilders.standaloneSetup(cadastroEventoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cadastroEvento = new CadastroEvento();
        cadastroEvento.setId(EVENTO_ID);
        cadastroEvento.setDataVerificada(EVENTO_DATA_VERIFICADA);
        cadastroEvento.setEstadoOperativo(EVENTO_ESTADO_OPERATIVO);
        cadastroEvento.setCondicaoOperativa(EVENTO_CONDICAO_OPERATIVA);
        cadastroEvento.setClassificacaoOrigem(EVENTO_CLASSIFICACAO_ORIGEM);
        cadastroEvento.setPotenciaDisponivel(EVENTO_POTENCIA_DISPONIVEL);
        cadastroEvento.setAggregateId(EVENTO_AGGREGATE_ID);
        cadastroEvento.setMajorVersion(EVENTO_MAJOR_VERSION);
        cadastroEvento.setMinorVersion(EVENTO_MINOR_VERSION);
        cadastroEvento.setTimelineDate(EVENTO_TIMELINE_DATE);
        cadastroEvento.setCorrelationId(EVENTO_CORRELATION_ID);
        
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user", ""));
    }

    @SuppressWarnings("unchecked")
	@Test
    public void apurarEventos() throws Exception {
        // Create the CadastroUsina
    	restCadastroEventoMockMvc.perform(post("/api/cadastro-eventos/apuracao?usinaId={usinaId}&usinaVersion={usinaVersion}&mesAnoApuracao={mesAnoApuracao}",
        		EVENTO_AGGREGATE_ID, EVENTO_MAJOR_VERSION + "." + EVENTO_MAJOR_VERSION, EVENTO_TIMELINE_DATE))
                .andExpect(status().isAccepted());

		ArgumentCaptor<CommandMessage<ApurarEventosCommand>> messageCaptor = (ArgumentCaptor<CommandMessage<ApurarEventosCommand>>) ArgumentCaptor
				.forClass(new CommandMessage<ApurarEventosCommand>().getClass());
        Mockito.verify(commandBus, Mockito.times(1)).send(messageCaptor.capture());

        // Validate the sent message
        assertThat(messageCaptor.getValue().getCommand().getEventos().size()).isEqualTo(3);
        assertThat(messageCaptor.getValue().getCommand().getDataInicio().getYear()).isEqualTo(EVENTO_TIMELINE_DATE.getYear());
        assertThat(messageCaptor.getValue().getCommand().getDataInicio().getMonth()).isEqualTo(EVENTO_TIMELINE_DATE.getMonth());
        assertThat(messageCaptor.getValue().getCommand().getDataFim().getYear()).isEqualTo(EVENTO_TIMELINE_DATE.getYear());
        assertThat(messageCaptor.getValue().getCommand().getDataFim().getMonth()).isEqualTo(EVENTO_TIMELINE_DATE.getMonth());
    }

    @SuppressWarnings("unchecked")
	@Test
    public void retificarEventos() throws Exception {
        List<CadastroEventoRetificado> eventos = new ArrayList<>();
        CadastroEventoRetificado eventoAdicionado = new CadastroEventoRetificado();
        eventoAdicionado.setCorrelationId(EVENTO_CORRELATION_ID);
        eventoAdicionado.setTimelineDate(EVENTO_TIMELINE_DATE);
        eventoAdicionado.setDataVerificada(EVENTO_TIMELINE_DATE);
		eventos.add(eventoAdicionado);
		
		CadastroEventoRetificado eventoExcluido = new CadastroEventoRetificado();
		eventoExcluido.setId(EVENTO_ID + "A");
		eventoExcluido.setCorrelationId(EVENTO_CORRELATION_ID + "A");
		eventoExcluido.setTimelineDate(EVENTO_TIMELINE_DATE);
		eventoExcluido.setDataVerificada(EVENTO_TIMELINE_DATE.plusDays(1));
		eventoExcluido.setDeleted(true);
		eventos.add(eventoExcluido);
		
		CadastroEventoRetificado eventoModificado = new CadastroEventoRetificado();
		eventoModificado.setId(EVENTO_ID + "AA");
		eventoModificado.setCorrelationId(EVENTO_CORRELATION_ID + "AA");
		eventoModificado.setTimelineDate(EVENTO_TIMELINE_DATE);
		eventoModificado.setDataVerificada(EVENTO_TIMELINE_DATE.plusDays(2));
		eventoModificado.setDirty(true);
		eventos.add(eventoModificado);
        
		CadastroEventoRetificado eventoLimpo = new CadastroEventoRetificado();
		eventoLimpo.setId(EVENTO_ID + "AAA");
		eventoLimpo.setCorrelationId(EVENTO_CORRELATION_ID + "AAA");
        eventoLimpo.setTimelineDate(EVENTO_TIMELINE_DATE);
        eventoLimpo.setDataVerificada(EVENTO_TIMELINE_DATE.plusDays(3));
        eventoLimpo.setId(EVENTO_ID);
		eventos.add(eventoLimpo);
        
		// Create the CadastroUsina
    	restCadastroEventoMockMvc.perform(post("/api/cadastro-eventos/retificacao?usinaId={usinaId}", EVENTO_AGGREGATE_ID)
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(eventos)))
                .andExpect(status().isOk());

		ArgumentCaptor<CommandMessage<ReprocessCommand<ApurarEventosCommandModification>>> messageCaptor = 
				(ArgumentCaptor<CommandMessage<ReprocessCommand<ApurarEventosCommandModification>>>) ArgumentCaptor
				.forClass(new CommandMessage<ReprocessCommand<ApurarEventosCommandModification>>().getClass());
        Mockito.verify(commandBus, Mockito.times(1)).send(messageCaptor.capture());

        // Validate the sent message
        assertThat(messageCaptor.getValue().getCommand().getModifications().size()).isEqualTo(3);
    }
    
    @Test
    public void getAllCadastroEventos() throws Exception {
        // Initialize the database
        cadastroEventoRepository.save(cadastroEvento);

        // Get all the cadastroEventos
        restCadastroEventoMockMvc.perform(get("/api/cadastro-eventos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cadastroEvento.getId())))
                .andExpect(jsonPath("$.[*].dataVerificada").value(hasItem(EVENTO_DATA_VERIFICADA_STR)))
                .andExpect(jsonPath("$.[*].estadoOperativo").value(hasItem(EVENTO_ESTADO_OPERATIVO.toString())))
                .andExpect(jsonPath("$.[*].condicaoOperativa").value(hasItem(EVENTO_CONDICAO_OPERATIVA.toString())))
                .andExpect(jsonPath("$.[*].classificacaoOrigem").value(hasItem(EVENTO_CLASSIFICACAO_ORIGEM.toString())))
                .andExpect(jsonPath("$.[*].potenciaDisponivel").value(hasItem(EVENTO_POTENCIA_DISPONIVEL.doubleValue())))
                .andExpect(jsonPath("$.[*].aggregateId").value(hasItem(EVENTO_AGGREGATE_ID.toString())))
                .andExpect(jsonPath("$.[*].majorVersion").value(hasItem(EVENTO_MAJOR_VERSION.intValue())))
                .andExpect(jsonPath("$.[*].minorVersion").value(hasItem(EVENTO_MINOR_VERSION.intValue())))
                .andExpect(jsonPath("$.[*].timelineDate").value(hasItem(EVENTO_TIMELINE_DATE_STR)))
                .andExpect(jsonPath("$.[*].correlationId").value(hasItem(EVENTO_CORRELATION_ID.toString())));
    }
    
    @Test
    public void getCadastroEventosByUsina() throws Exception {
    	// Initialize the database
    	cadastroEventoRepository.save(cadastroEvento);
    	
    	// Get all the cadastroEventos
    	restCadastroEventoMockMvc.perform(get("/api/cadastro-eventos?usinaId={usinaId}", EVENTO_AGGREGATE_ID))
		    	.andExpect(status().isOk())
		    	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		    	.andExpect(jsonPath("$.[*].id").value(hasItem(cadastroEvento.getId())))
		    	.andExpect(jsonPath("$.[*].dataVerificada").value(hasItem(EVENTO_DATA_VERIFICADA_STR)))
		    	.andExpect(jsonPath("$.[*].estadoOperativo").value(hasItem(EVENTO_ESTADO_OPERATIVO.toString())))
		    	.andExpect(jsonPath("$.[*].condicaoOperativa").value(hasItem(EVENTO_CONDICAO_OPERATIVA.toString())))
		    	.andExpect(jsonPath("$.[*].classificacaoOrigem").value(hasItem(EVENTO_CLASSIFICACAO_ORIGEM.toString())))
		    	.andExpect(jsonPath("$.[*].potenciaDisponivel").value(hasItem(EVENTO_POTENCIA_DISPONIVEL.doubleValue())))
		    	.andExpect(jsonPath("$.[*].aggregateId").value(hasItem(EVENTO_AGGREGATE_ID.toString())))
		    	.andExpect(jsonPath("$.[*].majorVersion").value(hasItem(EVENTO_MAJOR_VERSION.intValue())))
		    	.andExpect(jsonPath("$.[*].minorVersion").value(hasItem(EVENTO_MINOR_VERSION.intValue())))
		    	.andExpect(jsonPath("$.[*].timelineDate").value(hasItem(EVENTO_TIMELINE_DATE_STR)))
		    	.andExpect(jsonPath("$.[*].correlationId").value(hasItem(EVENTO_CORRELATION_ID.toString())));
    }

    @Test
    public void getCadastroEvento() throws Exception {
        // Initialize the database
        cadastroEventoRepository.save(cadastroEvento);

        // Get the cadastroEvento
        restCadastroEventoMockMvc.perform(get("/api/cadastro-eventos/{id}", cadastroEvento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cadastroEvento.getId()))
            .andExpect(jsonPath("$.dataVerificada").value(EVENTO_DATA_VERIFICADA_STR))
            .andExpect(jsonPath("$.estadoOperativo").value(EVENTO_ESTADO_OPERATIVO.toString()))
            .andExpect(jsonPath("$.condicaoOperativa").value(EVENTO_CONDICAO_OPERATIVA.toString()))
            .andExpect(jsonPath("$.classificacaoOrigem").value(EVENTO_CLASSIFICACAO_ORIGEM.toString()))
            .andExpect(jsonPath("$.potenciaDisponivel").value(EVENTO_POTENCIA_DISPONIVEL.doubleValue()))
            .andExpect(jsonPath("$.aggregateId").value(EVENTO_AGGREGATE_ID.toString()))
            .andExpect(jsonPath("$.majorVersion").value(EVENTO_MAJOR_VERSION.intValue()))
            .andExpect(jsonPath("$.minorVersion").value(EVENTO_MINOR_VERSION.intValue()))
            .andExpect(jsonPath("$.timelineDate").value(EVENTO_TIMELINE_DATE_STR))
            .andExpect(jsonPath("$.correlationId").value(EVENTO_CORRELATION_ID.toString()));
    }

    @Test
    public void getNonExistingCadastroEvento() throws Exception {
        // Get the cadastroEvento
        restCadastroEventoMockMvc.perform(get("/api/cadastro-eventos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }
}
