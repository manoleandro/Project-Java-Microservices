
package br.org.ons.sager.read.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Date;
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

import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.sager.read.OnsSagerReadApp;
import br.org.ons.sager.read.domain.Equipamento;
import br.org.ons.sager.read.domain.Evento;
import br.org.ons.sager.read.domain.Usinas;
import br.org.ons.sager.read.repository.EventoRepository;
import br.org.ons.sager.read.service.EventoService;
import br.org.ons.sager.dto.UsinaDTO;
import br.org.ons.sager.read.web.rest.errors.ErrorDTO;
import br.org.ons.sager.read.web.rest.exceptions.BadRequestException;

/**
 * REST controller for managing Evento.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class InstalacaoEventoHandlerTest {
    @Inject
    private EventoService eventoService;
    
	@Inject
	private EventoRepository eventoRepository;
	
	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	private MockMvc restEventoMockMvc;
	
	@PostConstruct
	public void setup() {
		EventoResource parametrizacaoResource = new EventoResource(eventoService);
		this.restEventoMockMvc = MockMvcBuilders.standaloneSetup(parametrizacaoResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
	}
	
	@Before
	public void initTest() {
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("cnos", ""));
		eventoRepository.deleteAll();
		EventoMudancaEstadoOperativo event1 = new EventoMudancaEstadoOperativo();
		Evento evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    Date dataVerificada1 = new Date("2016/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		eventoRepository.save(evento1);

	}
	
	@Test
	public void buscarEventoSemId() throws Exception {
		
		restEventoMockMvc.perform(get("/api/eventos"))
				.andExpect(status().isOk());

		assertThat(eventoRepository.count() == 1).isTrue();

	}
	
	@Test
	public void buscarEventoComId() throws Exception {
		
		EventoMudancaEstadoOperativo event1 = new EventoMudancaEstadoOperativo();
		Evento evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    Date dataVerificada1 = new Date("2016/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		evento1.setId("hhhhhhhhhh");
		eventoRepository.save(evento1);
		
		restEventoMockMvc.perform(get("/api/eventos?ids=hhhhhhhhhh"))
				.andExpect(status().isOk());

		assertThat(eventoRepository.count() == 2).isTrue();

	}
	
	@Test
	public void deveBuscarEventosPorInstalacao() throws Exception {
		
		String content = "[{\"nome\":\"CAMPOS\","
		+ "\"id\":\"RJUSCP\","
		+ "\"equipamentos\":[{"
		+ "\"id\":\"RJUSCP0UG1\","
		+ "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\"}],"
		+ "\"minorVersion\":\"1\"}]";
		restEventoMockMvc.perform(post("/api/eventos")
				.param("dtFim", "2016-11-29T03:00:00.000Z")
				.param("dtInicio" , "2016-11-28T03:00:00.000Z")
				.param("sort" , "undefined,desc") 
				.param("sort" , "id" )
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString(); 

		assertThat(eventoRepository.count() == 1).isTrue();

	}

}

