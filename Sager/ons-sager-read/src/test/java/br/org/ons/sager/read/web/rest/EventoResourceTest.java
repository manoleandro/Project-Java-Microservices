
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
import org.springframework.web.util.NestedServletException;

import com.jayway.jsonpath.JsonPath;

import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.OrigemComentario;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.evento.TipoDisponibilidade;
import br.org.ons.geracao.evento.ComentarioSituacao.StatusObjeto;
import br.org.ons.geracao.evento.ComentarioSituacao.TipoObjeto;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.sager.instalacao.event.DisponibilidadesCalculadasEvent;
import br.org.ons.sager.instalacao.event.EventosApuradosEvent;
import br.org.ons.sager.instalacao.event.InstalacaoCadastradaEvent;
import br.org.ons.sager.read.OnsSagerReadApp;
import br.org.ons.sager.read.domain.Equipamento;
import br.org.ons.sager.read.domain.Evento;
import br.org.ons.sager.read.domain.Usinas;
import br.org.ons.sager.read.repository.EventoRepository;
import br.org.ons.sager.read.service.EventoService;
import br.org.ons.sager.read.service.InstalacaoEventHandler;
import br.org.ons.sager.dto.UsinaDTO;
import br.org.ons.sager.read.web.rest.errors.ErrorDTO;
import br.org.ons.sager.read.web.rest.exceptions.BadRequestException;

/**
 * REST controller for managing Evento.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class EventoResourceTest {
    @Inject
    private EventoService eventoService;
    
	@Inject
	private EventoRepository eventoRepository;
	
	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	private MockMvc restEventoMockMvc;
	
	private InstalacaoEventHandler instalacaoEventHandler;
	
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
		Evento evento1 = new Evento(event1, "RJUSCP1");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    Date dataVerificada1 = new Date("2016/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		eventoRepository.save(evento1);
		
		EventoMudancaEstadoOperativo event2 = new EventoMudancaEstadoOperativo();
		Evento evento2 = new Evento(event2, "RJUSCP2");
		evento2.setIdEquipamento("RJUSCP0UG2");
	    Date dataVerificada2 = new Date("2016/11/28 04:00:00");
	    evento2.setDataVerificada(dataVerificada2);
		eventoRepository.save(evento2);
		
		EventoMudancaEstadoOperativo event3 = new EventoMudancaEstadoOperativo();
		Evento evento3 = new Evento(event3, "RJUSCP3");
		evento3.setIdEvento("id01");
	    Date dataVerificada3 = new Date("2016/11/28 14:00:00");
	    evento3.setDataVerificada(dataVerificada3);
		eventoRepository.save(evento3);

	}
	
    /**  Caso de teste - Consultar todos os equipamentos cadastrados sem ID.
     *   Resultado esperado: retorno de todos os equipamentos no repositorio.
     **/
	
	@Test
	public void ct010001() throws Exception {
		
		String response = restEventoMockMvc
				.perform(get("/api/eventos"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertThat(eventoRepository.count() == 3).isTrue();
	}
	
    /**  Caso de teste - Consultar equipamento por id.
     *   Resultado esperado: retornar somente o equipamento passado por id.
     **/
	
	@Test
	public void ct010002() throws Exception {

		String idEquip1 = "RJUSCP0UG1";
		
		String content = "[{\"nome\":\"CAMPOS\","
						+ "\"id\":\"RJUSCP1\","
						+ "\"equipamentos\":["
						+ "],"
						+ "\"minorVersion\":\"1\"}]";
		
		String response = restEventoMockMvc.perform(post("/api/eventos")
							.param("dtFim", "2016-11-29T03:00:00.000Z")
							.param("dtInicio" , "2016-11-28T03:00:00.000Z")
							.param("sort" , "id" )
							.content(content)
							.contentType(MediaType.APPLICATION_JSON_UTF8))
							.andExpect(status().isOk())
							.andReturn().getResponse().getContentAsString(); 

		System.out.println(response);
		
		String[] conteudo = response.split(":");
		
		String retorno1 = conteudo[5];
		
		String[] conteudo1 = retorno1.split("\"");
		
		System.out.println(conteudo1[1]);
		
		assertThat(conteudo1[1].equals(idEquip1)).isTrue();

	}
	
    /**  Caso de teste - Consultar equipamento por id com id errado.
     *   Resultado esperado: Resultado esperado: não localizar o equipamento (404).
     **/
	
	@Test
	public void ct010003() throws Exception {
		
		String idEquip1 = "RJUSCP0UG1";
		
		String content = "[{\"nome\":\"CAMPOS\","
						+ "\"id\":\"TESTE\","
						+ "\"equipamentos\":[{"
						+ "\"id\":\"TESTE\","
						+ "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\"}],"
						+ "\"minorVersion\":\"1\"}]";
		
		String response = restEventoMockMvc.perform(post("/api/eventos")
				.param("dtFim", "2016-11-29T03:00:00.000Z")
				.param("dtInicio" , "2016-11-28T03:00:00.000Z")
				.param("sort" , "undefined,desc") 
				.param("sort" , "id" )
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString(); 

		System.out.println(response);
		
		assertThat(response.equals("[]")).isTrue();

	}
	
	
    /**  Caso de teste - Consultar todos os equipamentos cadastrados com ID.
     *   Resultado esperado: retorno de todos os equipamentos no repositorio do ID informado.
     **/
	
	@Test
	public void ct010004() throws Exception {
		
		String idInstal = "RJUSCP3";
		
		String response = restEventoMockMvc
				.perform(get("/api/eventos?ids=id01"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		System.out.println(response);
		
		String[] conteudo = response.split(":");
		
		String retorno1 = conteudo[4];
		
		String[] conteudo1 = retorno1.split("\"");
		
		System.out.println(conteudo1[1]);
		
		assertThat(conteudo1[1].equals(idInstal)).isTrue();

	}
	
    /**  Caso de teste - Consultar todos os equipamentos cadastrados com ID.
     *   Resultado esperado: retorno de todos os equipamentos no repositorio do ID informado.
     **/
	
	@Test
	public void ct010005() throws Exception {
		
		String content = "[{\"nome\":\"CAMPOS\","
						+ "\"id\":\"TESTE\","
						+ "\"equipamentos\":[{"
						+ "\"id\":\"TESTE\","
						+ "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\"}],"
						+ "\"minorVersion\":\"1\"}]";
		
		restEventoMockMvc.perform(post("/api/eventos")
			.param("dtFim", "")
			.param("dtInicio" , "")
			.param("sort" , "undefined,desc") 
			.param("sort" , "id" )
			.content(content)
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isBadRequest())
			.andReturn().getResponse().getContentAsString(); 

	}
	
    /**  Caso de teste - Consultar equipamento por id sem data inicio.
     *   Resultado esperado: retorno de todos os equipamentos no repositorio do ID informado.
     **/
	
	@Test
	public void ct010006() throws Exception {
		
		String content = "[{\"nome\":\"CAMPOS\","
						+ "\"id\":\"TESTE\","
						+ "\"equipamentos\":[{"
						+ "\"id\":\"TESTE\","
						+ "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\"}],"
						+ "\"minorVersion\":\"1\"}]";
		
		restEventoMockMvc.perform(post("/api/eventos")
			.param("dtFim", "2016-11-28T03:00:00.000Z")
			.param("dtInicio" , "")
			.param("sort" , "undefined,desc") 
			.param("sort" , "id" )
			.content(content)
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isBadRequest())
			.andReturn().getResponse().getContentAsString(); 

	}
	
    /**  Caso de teste - Consultar equipamento por id sem data fim.
     *   Resultado esperado: retorno de todos os equipamentos no repositorio do ID informado.
     **/
	
	@Test
	public void ct010007() throws Exception {
		
		String content = "[{\"nome\":\"CAMPOS\","
						+ "\"id\":\"TESTE\","
						+ "\"equipamentos\":[{"
						+ "\"id\":\"TESTE\","
						+ "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\"}],"
						+ "\"minorVersion\":\"1\"}]";
		
		restEventoMockMvc.perform(post("/api/eventos")
				.param("dtFim", "")
				.param("dtInicio" , "2016-11-28T03:00:00.000Z")
				.param("sort" , "undefined,desc") 
				.param("sort" , "id" )
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString(); 

	}
	
    /**  Caso de teste - Consultar equipamento passando id vazio.
     *   Resultado esperado: Erro na requisição.
     **/
	
	@Test
	public void ct010008() throws Exception {
		
		String content = "";
		
		restEventoMockMvc.perform(post("/api/eventos")
				.param("dtFim", "2016-11-29T03:00:00.000Z")
				.param("dtInicio" , "2016-11-28T03:00:00.000Z")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString(); 

	}
	
    /**  Caso de teste - Consultar equipamento passando as datas nulas.
     *   Resultado esperado: Gerar uma exceção.
     **/
	
	@Test(expected=IllegalArgumentException.class)
	public void ct010009() throws Exception {
		
		String content = "[{\"nome\":\"CAMPOS\","
						+ "\"id\":\"TESTE\","
						+ "\"equipamentos\":[{"
						+ "\"id\":\"TESTE\","
						+ "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\"}],"
						+ "\"minorVersion\":\"1\"}]";
		
		restEventoMockMvc.perform(post("/api/eventos")
				.param("dtFim", null)
				.param("dtInicio" , null)
				.param("sort" , "undefined,desc") 
				.param("sort" , "id" )
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString(); 

	}
	
    /**  Caso de teste - Consultar equipamento passando a data inicio nula.
     *   Resultado esperado: Gerar uma exceção.
     **/
	
	@Test(expected=IllegalArgumentException.class)
	public void ct010010() throws Exception {
		
		String content = "[{\"nome\":\"CAMPOS\","
						+ "\"id\":\"TESTE\","
						+ "\"equipamentos\":[{"
						+ "\"id\":\"TESTE\","
						+ "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\"}],"
						+ "\"minorVersion\":\"1\"}]";
		
		restEventoMockMvc.perform(post("/api/eventos")
				.param("dtFim", "2016-11-29T03:00:00.000Z")
				.param("dtInicio" , null)
				.param("sort" , "undefined,desc") 
				.param("sort" , "id" )
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString(); 

	}
	
    /**  Caso de teste - Consultar equipamento passando a data fim nula.
     *   Resultado esperado: Gerar uma exceção.
     **/
	
	@Test(expected=IllegalArgumentException.class)
	public void ct010011() throws Exception {
		
		String content = "[{\"nome\":\"CAMPOS\","
						+ "\"id\":\"TESTE\","
						+ "\"equipamentos\":[{"
						+ "\"id\":\"TESTE\","
						+ "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\"}],"
						+ "\"minorVersion\":\"1\"}]";
		
		restEventoMockMvc.perform(post("/api/eventos")
			.param("dtFim", null)
			.param("dtInicio" , "2016-11-29T03:00:00.000Z")
			.param("sort" , "undefined,desc") 
			.param("sort" , "id" )
			.content(content)
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isBadRequest())
			.andReturn().getResponse().getContentAsString(); 

	}
	
}

