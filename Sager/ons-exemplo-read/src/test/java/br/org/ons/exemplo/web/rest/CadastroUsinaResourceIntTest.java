package br.org.ons.exemplo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.exemplo.OnsExemploReadApp;
import br.org.ons.exemplo.common.Apuracao;
import br.org.ons.exemplo.common.AtualizarUsinaCommand;
import br.org.ons.exemplo.common.CriarUsinaCommand;
import br.org.ons.exemplo.common.ExcluirUsinaCommand;
import br.org.ons.exemplo.config.mq.CommandBus;
import br.org.ons.exemplo.domain.CadastroUsina;
import br.org.ons.exemplo.repository.CadastroUsinaRepository;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.ResultMessage;


/**
 * Test class for the CadastroUsinaResource REST controller.
 *
 * @see CadastroUsinaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnsExemploReadApp.class)
@WebAppConfiguration
@IntegrationTest
public class CadastroUsinaResourceIntTest {

	private static final String DEFAULT_ID = "AAAAA";

    private static final String DEFAULT_VERSION = "1.1";
    private static final String UPDATED_VERSION = "2.2";
    private static final String DEFAULT_NOME_CURTO = "AAAAA";
    private static final String UPDATED_NOME_CURTO = "BBBBB";
    private static final String DEFAULT_TIPO_USINA = "AAAAA";
    private static final String UPDATED_TIPO_USINA = "BBBBB";

	/**
	 * Repositório real com base de dados de teste em memória
	 */
    @Inject
    private CadastroUsinaRepository cadastroUsinaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    /**
     * Mock para emular acesso ao barramento
     */
    @Mock
	private CommandBus commandBus;
    
    @Inject
    private ObjectMapper objectMapper;
	
    private MockMvc restCadastroUsinaMockMvc;

    private CadastroUsina cadastroUsina;

    @SuppressWarnings("unchecked")
	@PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CadastroUsinaResource cadastroUsinaResource = new CadastroUsinaResource();
        Mockito.when(commandBus.sendAndWait(Mockito.any(CommandMessage.class))).thenAnswer(new Answer<ResultMessage<Void>>() {
			@Override
			public ResultMessage<Void> answer(InvocationOnMock invocation) throws Throwable {
				ResultMessage<Void> result = new ResultMessage<>();
				result.setStatusCode(HttpStatus.OK.value());
				result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
				return result;
			}
		});
        ReflectionTestUtils.setField(cadastroUsinaResource, "cadastroUsinaRepository", cadastroUsinaRepository);
        ReflectionTestUtils.setField(cadastroUsinaResource, "commandBus", commandBus);
        ReflectionTestUtils.setField(cadastroUsinaResource, "objectMapper", objectMapper);
        this.restCadastroUsinaMockMvc = MockMvcBuilders.standaloneSetup(cadastroUsinaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() throws Exception {
        cadastroUsina = new CadastroUsina();
        cadastroUsina.setId(DEFAULT_ID);
        cadastroUsina.setVersion(DEFAULT_VERSION);
        cadastroUsina.setNomeCurto(DEFAULT_NOME_CURTO);
        cadastroUsina.setTipoUsina(DEFAULT_TIPO_USINA);
        cadastroUsina.setApuracoes(objectMapper.writeValueAsString(new ArrayList<Apuracao>()));
    }

    @SuppressWarnings("unchecked")
	@Test
    public void createCadastroUsina() throws Exception {
        // Create the CadastroUsina
    	cadastroUsina.setId(null);
        restCadastroUsinaMockMvc.perform(post("/api/cadastro-usinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cadastroUsina)))
                .andExpect(status().isOk());

		ArgumentCaptor<CommandMessage<CriarUsinaCommand>> messageCaptor = (ArgumentCaptor<CommandMessage<CriarUsinaCommand>>) ArgumentCaptor
				.forClass(new CommandMessage<CriarUsinaCommand>().getClass());
        Mockito.verify(commandBus, Mockito.times(1)).sendAndWait(messageCaptor.capture());

        // Validate the sent message
        assertThat(messageCaptor.getValue().getCommand().getUsina().getNomeCurto()).isEqualTo(DEFAULT_NOME_CURTO);
        assertThat(messageCaptor.getValue().getCommand().getUsina().getTipoUsina()).isEqualTo(DEFAULT_TIPO_USINA);
    }
    
    @Test
    public void createCadastroUsinaError() throws Exception {
    	// Create the CadastroUsina
    	restCadastroUsinaMockMvc.perform(post("/api/cadastro-usinas")
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
    			.content(TestUtil.convertObjectToJsonBytes(cadastroUsina)))
    			.andExpect(status().isBadRequest());
    }

    @Test
    public void getAllCadastroUsinas() throws Exception {
        // Initialize the database
        cadastroUsinaRepository.save(cadastroUsina);

        // Get all the cadastroUsinas
        restCadastroUsinaMockMvc.perform(get("/api/cadastro-usinas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cadastroUsina.getId())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].nomeCurto").value(hasItem(DEFAULT_NOME_CURTO.toString())))
                .andExpect(jsonPath("$.[*].tipoUsina").value(hasItem(DEFAULT_TIPO_USINA.toString())));
    }

    @Test
    public void getCadastroUsina() throws Exception {
        // Initialize the database
        cadastroUsinaRepository.save(cadastroUsina);

        // Get the cadastroUsina
        restCadastroUsinaMockMvc.perform(get("/api/cadastro-usinas/{id}", cadastroUsina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cadastroUsina.getId()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.nomeCurto").value(DEFAULT_NOME_CURTO.toString()))
            .andExpect(jsonPath("$.tipoUsina").value(DEFAULT_TIPO_USINA.toString()));
    }

    @Test
    public void getNonExistingCadastroUsina() throws Exception {
        // Get the cadastroUsina
        restCadastroUsinaMockMvc.perform(get("/api/cadastro-usinas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @SuppressWarnings("unchecked")
	@Test
    public void updateCadastroUsina() throws Exception {
        // Initialize the database
        cadastroUsinaRepository.save(cadastroUsina);

        // Update the cadastroUsina
        CadastroUsina updatedCadastroUsina = new CadastroUsina();
        updatedCadastroUsina.setId(cadastroUsina.getId());
        updatedCadastroUsina.setVersion(UPDATED_VERSION);
        updatedCadastroUsina.setNomeCurto(UPDATED_NOME_CURTO);
        updatedCadastroUsina.setTipoUsina(UPDATED_TIPO_USINA);

        restCadastroUsinaMockMvc.perform(put("/api/cadastro-usinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCadastroUsina)))
                .andExpect(status().isOk());

		ArgumentCaptor<CommandMessage<AtualizarUsinaCommand>> messageCaptor = (ArgumentCaptor<CommandMessage<AtualizarUsinaCommand>>) ArgumentCaptor
				.forClass(new CommandMessage<AtualizarUsinaCommand>().getClass());
        Mockito.verify(commandBus, Mockito.times(1)).sendAndWait(messageCaptor.capture());

        // Validate the sent message
        assertThat(messageCaptor.getValue().getMetadata().getAggregateId()).isEqualTo(DEFAULT_ID);
        assertThat(messageCaptor.getValue().getMetadata().getMajorVersion() + "." + messageCaptor.getValue().getMetadata().getMajorVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(messageCaptor.getValue().getCommand().getUsina().getNomeCurto()).isEqualTo(UPDATED_NOME_CURTO);
        assertThat(messageCaptor.getValue().getCommand().getUsina().getTipoUsina()).isEqualTo(UPDATED_TIPO_USINA);
    }
    
    @Test
    public void updateCadastroUsinaError() throws Exception {
    	cadastroUsina.setId(null);
    	
    	restCadastroUsinaMockMvc.perform(put("/api/cadastro-usinas")
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
    			.content(TestUtil.convertObjectToJsonBytes(cadastroUsina)))
    			.andExpect(status().isBadRequest());
    }

    @SuppressWarnings("unchecked")
	@Test
    public void deleteCadastroUsina() throws Exception {
        // Initialize the database
        cadastroUsinaRepository.save(cadastroUsina);

        // Delete the cadastroUsina
        restCadastroUsinaMockMvc.perform(delete("/api/cadastro-usinas/{id}", cadastroUsina.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

		ArgumentCaptor<CommandMessage<ExcluirUsinaCommand>> messageCaptor = (ArgumentCaptor<CommandMessage<ExcluirUsinaCommand>>) ArgumentCaptor
				.forClass(new CommandMessage<ExcluirUsinaCommand>().getClass());
        Mockito.verify(commandBus, Mockito.times(1)).sendAndWait(messageCaptor.capture());

        // Validate the sent message
        assertThat(messageCaptor.getValue().getMetadata().getAggregateId()).isEqualTo(DEFAULT_ID);
    }
    
    @Test
    public void getApuracoes() throws Exception {
        // Initialize the database
        cadastroUsinaRepository.save(cadastroUsina);
        
        // Get the cadastroUsina
        restCadastroUsinaMockMvc.perform(get("/api/cadastro-usinas/{id}/apuracoes", cadastroUsina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
