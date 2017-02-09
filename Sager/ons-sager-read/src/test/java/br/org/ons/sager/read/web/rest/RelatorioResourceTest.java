package br.org.ons.sager.read.web.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;

import br.org.ons.geracao.cadastro.PotenciaCalculo;
import br.org.ons.geracao.cadastro.TipoInstalacao;
import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.TipoDisponibilidade;
import br.org.ons.geracao.evento.TipoOperacao;
import br.org.ons.geracao.evento.ComentarioSituacao.StatusObjeto;
import br.org.ons.geracao.evento.franquia.Franquia;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.sager.read.OnsSagerReadApp;
import br.org.ons.sager.read.config.mq.CommandBus;
import br.org.ons.sager.read.domain.Equipamento;
//import br.org.ons.sager.read.domain.EquipamentoCadastrado;
import br.org.ons.sager.read.domain.Evento;
import br.org.ons.sager.read.domain.InstalacaoTaxas;
import br.org.ons.sager.read.domain.Taxa;
import br.org.ons.sager.read.domain.Valores;
import br.org.ons.sager.read.repository.EquipamentoRepository;
//import br.org.ons.sager.read.repository.EquipamentoCadastradoRepository;
import br.org.ons.sager.read.repository.EventoRepository;
import br.org.ons.sager.read.repository.InstalacaoTaxasRepository;
import br.org.ons.sager.read.service.RelatorioService;
import br.org.ons.sager.read.web.rest.errors.ErrorDTO;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeResponse;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * REST controller for managing Gerar Relatório.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class RelatorioResourceTest {
        
	final String contentBody = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"RJUSCP\"," + "\"equipamentos\":[{"
			+ "\"id\":\"RJUSCP0UG1\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
			+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
			+ "\"minorVersion\":\"1\"}]";

	@Inject
	private AutowireCapableBeanFactory beanFactory;

	@Inject
	private InstalacaoTaxasRepository instalacaoTaxasRepository;

	@Inject
	private EventoRepository eventoRepository;		

	@Inject
    private EquipamentoRepository equipamentoRepository;

    @Inject
    private RelatorioService relatorioService;
       
	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
	
	@MockBean
	private CommandBus commandBus;
	
	private MockMvc restRelatMockMvc;
	
	@PostConstruct
	public void setup() {
		RelatorioResource parametrizacaoResource = new RelatorioResource(relatorioService);
		this.restRelatMockMvc = MockMvcBuilders.standaloneSetup(parametrizacaoResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter, new ByteArrayHttpMessageConverter())
				.build();	
	}
	
	@Before
	public void initTest() throws Exception {
		this.InsereEventos();
		this.InsereEquipamentos();
		this.AutenticaUsuario();
		this.InsereTaxas();
	}
	
	@Test
	public void CT010001() throws Exception {
		
		this.InsereDisponibilidadeseSituacoesInstalacao(1, null, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						// .param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
//				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getStatus()==HttpStatus.BAD_REQUEST.value()).isTrue();
	}

	
	@Test
	public void CT010002() throws Exception {

		this.InsereDisponibilidadeseSituacoesInstalacao(1, null, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						// .param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
//				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getStatus()==HttpStatus.BAD_REQUEST.value()).isTrue();
	}
	
	@Test
	public void CT010003() throws Exception {

		this.InsereDisponibilidadeseSituacoesInstalacao(1, null, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						// .content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
//				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getStatus()==HttpStatus.BAD_REQUEST.value()).isTrue();
	}
	

	@Test
	public void CT010004() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(1, null, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						// .param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
//				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getStatus()==HttpStatus.BAD_REQUEST.value()).isTrue();
	}
	

	@Test
	public void CT010005() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(1, null, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						//.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
//				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getStatus()==HttpStatus.BAD_REQUEST.value()).isTrue();
	}
	
	@Test
	public void CT010006() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

    	System.out.println("CT010006: " + servletResponse.getContentAsString());
		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010007() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010008() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010009() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010010() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010011() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010012() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010013() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010014() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010015() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	@Test
	public void CT010016() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010017() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010018() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}	
	
	@Test
	public void CT010019() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010020() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010021() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010022() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010023() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010024() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010025() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	@Test
	public void CT010026() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010027() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010028() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}	
	
	@Test
	public void CT010029() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010030() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010031() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010032() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010033() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010034() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010035() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010036() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010037() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010038() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	
	@Test
	public void CT010039() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010040() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	
	@Test
	public void CT010041() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010042() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010043() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	
	@Test
	public void CT010044() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010045() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	@Test
	public void CT010046() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010047() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010048() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}	
	
	@Test
	public void CT010049() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010050() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	
	@Test
	public void CT010051() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010052() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010053() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	
	@Test
	public void CT010054() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010055() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	@Test
	public void CT010056() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010057() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010058() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}	
	
	@Test
	public void CT010059() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010060() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	
	@Test
	public void CT010061() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010062() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010063() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	
	@Test
	public void CT010064() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010065() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}

	
	@Test
	public void CT010066() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010067() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010068() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.zip")>0).isTrue();
		// assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010069() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010070() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.zip")>0).isTrue();
		//assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010071() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010072() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010073() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.zip")>0).isTrue();
		// assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010074() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010075() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.zip")>0).isTrue();
		// assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	@Test
	public void CT010076() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010077() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010078() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.zip")>0).isTrue();
		// assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}	
	
	@Test
	public void CT010079() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010080() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.zip")>0).isTrue();
		// assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010081() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010082() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010083() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.zip")>0).isTrue();
		// assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010084() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010085() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

    	// System.out.println("CT010085: " + servletResponse.getContentAsString());
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.zip")>0).isTrue();
		// assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	@Test
	public void CT010086() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010087() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010088() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.zip")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}	
	
	@Test
	public void CT010089() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010090() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.zip")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010091() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010092() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010093() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.DESATIVADO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.zip")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	
	@Test
	public void CT010094() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, false);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-11-29T00:00:00.000Z")
						.param("dtInicio", "2011-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		ObjectMapper mapper = new ObjectMapper();
		List<ErrorDTO> listaErros = mapper.readValue(servletResponse.getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ErrorDTO.class));
		assertThat(listaErros.size()==3).isTrue();
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("mensagensErro")>0).isTrue();
	}
	
	@Test
	public void CT010095() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.SUSPENSO, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.zip")>0).isTrue();
		// assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
		
	@Test
	public void CT010096() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"FAKE\"," + "\"equipamentos\":[{"
				+ "\"id\":\"FAKE1\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"1\"}]";

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("semDados.log")>0).isTrue();
		assertThat(servletResponse.getContentAsString().equals("[]")).isTrue();
	}
	
	@Test
	public void CT010097() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"FAKE\"," + "\"equipamentos\":[{"
				+ "\"id\":\"FAKE1\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"1\"}]";

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("semDados.log")>0).isTrue();
		assertThat(servletResponse.getContentAsString().equals("[]")).isTrue();
	}
	
	@Test
	public void CT010098() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"FAKE\"," + "\"equipamentos\":[{"
				+ "\"id\":\"FAKE1\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"1\"}]";

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("semDados.log")>0).isTrue();
		assertThat(servletResponse.getContentAsString().equals("[]")).isTrue();
	}
	
	@Test
	public void CT010099() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"FAKE\"," + "\"equipamentos\":[{"
				+ "\"id\":\"FAKE1\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"1\"}]";

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "dat")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("semDados.log")>0).isTrue();
		assertThat(servletResponse.getContentAsString().equals("[]")).isTrue();
	}
	
	@Test
	public void CT010100() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"FAKE\"," + "\"equipamentos\":[{"
				+ "\"id\":\"FAKE1\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"1\"}]";

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "xml")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("semDados.log")>0).isTrue();
		assertThat(servletResponse.getContentAsString().equals("[]")).isTrue();
	}
	
	@Test
	public void CT010101() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"FAKE\"," + "\"equipamentos\":[{"
				+ "\"id\":\"FAKE1\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"1\"}]";

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "ambos")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("semDados.log")>0).isTrue();
		assertThat(servletResponse.getContentAsString().equals("[]")).isTrue();
	}
	
	@Test
	public void CT010102() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"FAKE\"," + "\"equipamentos\":[{"
				+ "\"id\":\"FAKE1\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"1\"}]";

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("semDados.log")>0).isTrue();
		assertThat(servletResponse.getContentAsString().equals("[]")).isTrue();
	}
	
	@Test
	public void CT010103() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"FAKE\"," + "\"equipamentos\":[{"
				+ "\"id\":\"FAKE1\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"1\"}]";

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("semDados.log")>0).isTrue();
		assertThat(servletResponse.getContentAsString().equals("[]")).isTrue();
	}
	
	@Test
	public void CT010104() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"FAKE\"," + "\"equipamentos\":[{"
				+ "\"id\":\"FAKE1\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"1\"}]";

		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("semDados.log")>0).isTrue();
		assertThat(servletResponse.getContentAsString().equals("[]")).isTrue();
	}
	
	@Test
	public void CT010105() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2009-03-10T14:00:00.000Z")
						.param("dtInicio", "2009-03-07T11:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

    	System.out.println("CT010105: " + servletResponse.getContentAsString());
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	@Test
	public void CT010106() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	@Test
	public void CT010107() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "dispVer")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("dispVer.zip")>0).isTrue();
//		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	@Test
	public void CT010108() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

//    	System.out.println("CT010108: " + servletResponse.getContentAsString());
		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	@Test
	public void CT010109() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2008-11-29T00:00:00.000Z")
						.param("dtInicio", "2008-11-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	@Test
	public void CT010110() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "tipSinc")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("tipSinc.zip")>0).isTrue();
//		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	@Test
	public void CT010111() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "dat")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.dat")>0).isTrue();
		assertThat(servletResponse.getContentAsString().indexOf(",")>0).isTrue();
	}
	
	@Test
	public void CT010112() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "xml")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.xml")>0).isTrue();
		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	@Test
	public void CT010113() throws Exception {
		this.InsereDisponibilidadeseSituacoesInstalacao(3, StatusObjeto.FORA_OPERACAO_COMERCIAL, true);
		
		MockHttpServletResponse servletResponse = restRelatMockMvc
				.perform(post("/api/relatorio")
						.param("dtFim", "2016-11-29T00:00:00.000Z")
						.param("dtInicio", "2001-01-01T00:00:00.000Z")
						.param("tipoRelatorio", "indAcum")
						.param("tipoArquivo", "ambos")
						.content(contentBody)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isOk())
//				.andDo(print())
				.andReturn().getResponse();

		assertThat(servletResponse.getHeader("Content-Disposition").indexOf("indAcum.zip")>0).isTrue();
//		assertThat(servletResponse.getContentAsString().startsWith("<")).isTrue();
	}
	
	public void InsereEquipamentos()
	{
		Date data = new Date("2016/11/28 03:00:00");
		equipamentoRepository.deleteAll();
		
		Periodo vigencia = new Periodo();
		vigencia.setDataFim(data);
		vigencia.setDataInicio(data);
		
		PotenciaCalculo potencia = new PotenciaCalculo();
		potencia.setIdEquipamento("idEquip01");
		potencia.setValor(10.1);
		potencia.setVigencia(vigencia);
		
		List<PotenciaCalculo> potenciasCalculo = new ArrayList<PotenciaCalculo>();
		potenciasCalculo.add(potencia);
		
		Periodo periodoValidade = new Periodo();
		periodoValidade.setDataFim(data);
		periodoValidade.setDataInicio(data);
		
		Periodo periodoVigencia = new Periodo();
		periodoValidade.setDataFim(data);
		periodoValidade.setDataInicio(data);
			
		Franquia franquia = new Franquia();
		franquia.setCodigo("cod01");
		franquia.setIdEquipamento("equip01");
		franquia.setPeriodoValidade(periodoValidade);
		franquia.setPeriodoVigencia(periodoVigencia);
		franquia.setQtMinutosServicoParaUso(12);
		franquia.setValorDisponivel(13);
		franquia.setValorLimite(14);
		franquia.setVersao("01");
			
		List<Franquia> franquias = new ArrayList<Franquia>();
		franquias.add(franquia);
		
		List<Periodo> suspensoes = new ArrayList<Periodo>();
		suspensoes.add(vigencia);
			
		Equipamento equip = new Equipamento();
		equip.setCodid_dpp("codigoDpp01");
		equip.setCodigoCcee("codigoCcee01");
		equip.setDataDesativacao(data);
		equip.setDataEventoEOC(data);
		equip.setDataImplantacao(data);
		equip.setDataRenovacaoProrrogacaoConcessao(data);
		equip.setFranquias(franquias);
		equip.setId("RJUSCP0UG1");
		equip.setIddpp("idDpp01");
		equip.setIdInstalacao("idInstalacao01");
		equip.setNome("nome01");
		equip.setPotenciasCalculo(potenciasCalculo);
		equip.setQuantidadeHorasServico(12);
		equip.setSuspensoes(suspensoes);
		equip.setTipoInstalacao(TipoInstalacao.USINA);
		equip.setVersao("01");
		equipamentoRepository.save(equip);
			
		Franquia franquia2 = new Franquia();
		franquia2.setCodigo("cod02");
		franquia2.setIdEquipamento("equip02");
		franquia2.setPeriodoValidade(periodoValidade);
		franquia2.setPeriodoVigencia(periodoVigencia);
		franquia2.setQtMinutosServicoParaUso(12);
		franquia2.setValorDisponivel(13);
		franquia2.setValorLimite(14);
		franquia2.setVersao("02");
			
		List<Franquia> franquias2 = new ArrayList<Franquia>();
		franquias2.add(franquia2);
		
		List<Periodo> suspensoes2 = new ArrayList<Periodo>();
		suspensoes2.add(vigencia);
			
		equip = new Equipamento();
		equip.setCodid_dpp("codigoDpp02");
		equip.setCodigoCcee("codigoCcee02");
		equip.setDataDesativacao(data);
		equip.setDataEventoEOC(data);
		equip.setDataImplantacao(data);
		equip.setDataRenovacaoProrrogacaoConcessao(data);
		equip.setFranquias(franquias2);
		equip.setId("idEquip02");
		equip.setIddpp("idDpp02");
		equip.setIdInstalacao("idInstalacao02");
		equip.setNome("nome02");
		equip.setPotenciasCalculo(potenciasCalculo);
		equip.setQuantidadeHorasServico(12);
		equip.setSuspensoes(suspensoes);
		equip.setTipoInstalacao(TipoInstalacao.USINA);
		equip.setVersao("02");		
		equipamentoRepository.save(equip);
		
		equip = new Equipamento();
		equip.setCodid_dpp("codigoDpp03");
		equip.setCodigoCcee("codigoCcee03");
		equip.setDataDesativacao(data);
		equip.setDataEventoEOC(data);
		equip.setDataImplantacao(data);
		equip.setDataRenovacaoProrrogacaoConcessao(data);
		equip.setFranquias(franquias2);
		equip.setId("RJUSCP0UG1");
		equip.setIddpp("RJUSCP0UG1");
		equip.setIdInstalacao("idInstalacao03");
		equip.setNome("nome03");
		equip.setPotenciasCalculo(potenciasCalculo);
		equip.setQuantidadeHorasServico(12);
		equip.setSuspensoes(suspensoes);
		equip.setTipoInstalacao(TipoInstalacao.USINA);
		equip.setVersao("02");		
		equipamentoRepository.save(equip);
		
		equip = new Equipamento();
		equip.setCodid_dpp("codigoDpp04");
		equip.setCodigoCcee("codigoCcee04");
		equip.setDataDesativacao(data);
		equip.setDataEventoEOC(data);
		equip.setDataImplantacao(data);
		equip.setDataRenovacaoProrrogacaoConcessao(data);
		equip.setFranquias(franquias2);
		equip.setId("AJUSCP0UG1");
		equip.setIddpp("AJUSCP0UG1");
		equip.setIdInstalacao("idInstalacao04");
		equip.setNome("nome04");
		equip.setPotenciasCalculo(potenciasCalculo);
		equip.setQuantidadeHorasServico(12);
		equip.setSuspensoes(suspensoes);
		equip.setTipoInstalacao(TipoInstalacao.USINA);
		equip.setVersao("02");		
		equipamentoRepository.save(equip);
		
	}
	
	public void InsereEventos()
	{
		eventoRepository.deleteAll();
		
		EventoMudancaEstadoOperativo event1 = new EventoMudancaEstadoOperativo();
		Evento evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    Date dataVerificada1 = new Date("2016/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		evento1.setValorPotenciaDisponivel(1.0);
		evento1.setEstadoOperativo("LCS");
		evento1.setCondicaoOperativa("NOR");
		evento1.setOrigem("GUM");
		eventoRepository.save(evento1);		
		
		event1 = new EventoMudancaEstadoOperativo();
		evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    dataVerificada1 = new Date("2014/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		evento1.setValorPotenciaDisponivel(2.0);
		evento1.setEstadoOperativo("LCS");
		evento1.setCondicaoOperativa("NOR");
		evento1.setOrigem("GUM");
		eventoRepository.save(evento1);		
		
		event1 = new EventoMudancaEstadoOperativo();
		evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    dataVerificada1 = new Date("20012/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		evento1.setValorPotenciaDisponivel(3.0);
		evento1.setEstadoOperativo("LCS");
		evento1.setCondicaoOperativa("NOR");
		evento1.setOrigem("GUM");
		eventoRepository.save(evento1);		
		
		event1 = new EventoMudancaEstadoOperativo();
		evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    dataVerificada1 = new Date("2010/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		evento1.setValorPotenciaDisponivel(4.0);
		evento1.setEstadoOperativo("LCS");
		evento1.setCondicaoOperativa("NOR");
		evento1.setOrigem("GUM");
		eventoRepository.save(evento1);		
		
		event1 = new EventoMudancaEstadoOperativo();
		evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    dataVerificada1 = new Date("2008/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		evento1.setValorPotenciaDisponivel(5.0);
		evento1.setEstadoOperativo("LCS");
		evento1.setCondicaoOperativa("NOR");
		evento1.setOrigem("GUM");
		eventoRepository.save(evento1);		
		
		event1 = new EventoMudancaEstadoOperativo();
		evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    dataVerificada1 = new Date("2009/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		evento1.setValorPotenciaDisponivel(6.0);
		evento1.setEstadoOperativo("LCS");
		evento1.setCondicaoOperativa("NOR");
		evento1.setOrigem("GUM");
		eventoRepository.save(evento1);		
		
		event1 = new EventoMudancaEstadoOperativo();
		evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    dataVerificada1 = new Date("2006/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		evento1.setValorPotenciaDisponivel(7.0);
		evento1.setEstadoOperativo("DEM");
		evento1.setCondicaoOperativa("NOR");
		evento1.setOrigem("GUM");
		eventoRepository.save(evento1);		
		
		event1 = new EventoMudancaEstadoOperativo();
		evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    dataVerificada1 = new Date("2002/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		evento1.setValorPotenciaDisponivel(8.0);
		evento1.setEstadoOperativo("LCS");
		evento1.setCondicaoOperativa("NOR");
		evento1.setOrigem("GUM");
		eventoRepository.save(evento1);		
		
		event1 = new EventoMudancaEstadoOperativo();
		evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    dataVerificada1 = new Date("2004/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		evento1.setValorPotenciaDisponivel(9.0);
		evento1.setEstadoOperativo("LCS");
		evento1.setCondicaoOperativa("NOR");
		evento1.setOrigem("GUM");
		evento1.setTipoOperacao(TipoOperacao.C);
		eventoRepository.save(evento1);		
		
		event1 = new EventoMudancaEstadoOperativo();
		evento1 = new Evento(event1, "RJUSCP");
		evento1.setIdEquipamento("RJUSCP0UG1");
	    dataVerificada1 = new Date("2000/11/28 03:00:00");
		evento1.setDataVerificada(dataVerificada1);
		evento1.setValorPotenciaDisponivel(10.0);
		evento1.setEstadoOperativo("LCS");
		evento1.setCondicaoOperativa("NOR");		
		evento1.setOrigem("GUM");
		eventoRepository.save(evento1);		
	}
	
	private void AutenticaUsuario()
	{
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));		
	}
	

	@SuppressWarnings("unchecked")
	private void InsereDisponibilidadeseSituacoesInstalacao(int nComent, StatusObjeto statusObj, boolean prossegue)
	{
		
		when(commandBus.sendAndWait(any(CommandMessage.class)))
				.thenAnswer(new Answer<ResultMessage<?>>() {
					@Override
					public ResultMessage<?> answer(InvocationOnMock invocation)
							throws Throwable {
						if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
								.equals("br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand")) {
							ResultMessage<CalcularDisponibilidadeResponse> result = new ResultMessage<>();
							result.setStatusCode(HttpStatus.OK.value());
							result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
							CalcularDisponibilidadeResponse resp = new CalcularDisponibilidadeResponse();
							List<Disponibilidade> disponibilidades = new ArrayList<>();

							Disponibilidade disp1 = new Disponibilidade();
							@SuppressWarnings("deprecation")
							Date dataInicio = new Date("2016/11/28 03:00:00");
							disp1.setDataInicio(dataInicio);
							disp1.setIdEquipamento("RJUSCP0UG1");
							disp1.setValor(12.30);
							disp1.setVersao("1");
							disp1.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
							
							disponibilidades.add(disp1);

							resp.setDisponibilidades(disponibilidades);

							result.getResults().add(resp);
							return result;
						} else if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
								.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
							ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
							result.setStatusCode(HttpStatus.OK.value());
							result.setStatusMessage(HttpStatus.OK.getReasonPhrase());

							Date dataIni = new Date("2008/01/01 00:00:00");
							Date dataFim = new Date("2012/01/01 00:00:00");

							VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();
							List<ComentarioSituacao> comentarios = new ArrayList<>();

							ComentarioSituacao coment = null;
							for (int i=0;i<nComent;i++)
							{
								coment = new ComentarioSituacao();
								coment.setNomeObjeto("nome0" + (i+1));
								coment.setDescricao("Descricao0" + (i+1));
								coment.setDataFim(dataFim);
								coment.setDataInicio(dataIni);
								coment.setDataInsercao(dataFim);
								coment.setStatusObjeto(statusObj);
								comentarios.add(coment);								
							}
							
							resp.setComentarios(comentarios);
							resp.setProssegue(prossegue);
							result.getResults().add(resp);
							
							return result;
						} else {
							return null;
						}
					}
				});
		
	}
	
	private void InsereTaxas()
	{
		InstalacaoTaxas instalacaoTaxas = new InstalacaoTaxas();
		instalacaoTaxas.setInstalacao("RJUSCP");
		List<Taxa> taxas = new ArrayList<Taxa>();
		Taxa taxa = new Taxa();
		taxa.setMes(10);
		taxa.setAno(2010);
		List<Valores> valores = new ArrayList<Valores>();
		Valores valor = new Valores();
		valor.setNome("TEIFA MENSAL MENSAL");
		valor.setValor(BigDecimal.valueOf(123.45));
		valores.add(valor);
		valor = new Valores();
		valor.setNome("TEIFA ACUMULADA");
		valor.setValor(BigDecimal.valueOf(124.45));
		valores.add(valor);
		valor.setNome("TEIP MENSAL MENSAL");
		valor.setValor(BigDecimal.valueOf(125.45));
		valores.add(valor);
		valor.setNome("TEIP ACUMULADA");
		valor.setValor(BigDecimal.valueOf(126.45));
		valores.add(valor);
		
		taxa.setValores(valores);
		taxas.add(taxa);
		
		instalacaoTaxas.setTaxas(taxas);
		instalacaoTaxasRepository.save(instalacaoTaxas);
	}
}
