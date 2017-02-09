package br.org.ons.sager.read.web.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.jayway.jsonpath.JsonPath;
import com.querydsl.core.types.OrderSpecifier;

import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.ComentarioSituacao.StatusObjeto;
import br.org.ons.geracao.evento.ComentarioSituacao.TipoObjeto;
import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.OrigemComentario;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.evento.TipoDisponibilidade;
import br.org.ons.geracao.modelagem.AvisoErro.Tipo;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.sager.read.OnsSagerReadApp;
import br.org.ons.sager.read.config.mq.CommandBus;
import br.org.ons.sager.read.domain.Comentario;
import br.org.ons.sager.read.domain.Disp;
import br.org.ons.sager.read.domain.Evento;
import br.org.ons.sager.read.domain.Usinas;
import br.org.ons.sager.read.repository.ComentarioRepository;
import br.org.ons.sager.read.repository.DispRepository;
import br.org.ons.sager.read.service.DispService;
import br.org.ons.sager.read.web.rest.DispResource;
import br.org.ons.sager.read.web.rest.dto.ComentarioResponse;
import br.org.ons.sager.dto.UsinaDTO;
import br.org.ons.sager.read.web.rest.dto.DisponibilidadesDTO;
import br.org.ons.sager.read.web.rest.dto.CheckDispInstalacoesResponse;
import br.org.ons.sager.read.web.rest.errors.ErrorDTO;
import br.org.ons.sager.read.web.rest.exceptions.BadRequestException;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeResponse;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoResponse;

/**
 * REST controller for managing Disp.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class DispResourceTest {

	@Inject
	private DispService dispService;

	@Inject
	private DispRepository dispRepository;

	@Inject
	private ComentarioRepository comentarioRepository;

	@MockBean
	private CommandBus commandBus;

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	private MockMvc restDispMockMvc;

	@PostConstruct
	public void setup() {
//		dispService = new DispService(dispRepository, comentarioRepository, commandBus);
		DispResource parametrizacaoResource = new DispResource(dispService);
		this.restDispMockMvc = MockMvcBuilders.standaloneSetup(parametrizacaoResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
	}

	@SuppressWarnings("deprecation")
	@Before
	public void initTest() {
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("cnos", ""));
		dispRepository.deleteAll();
		comentarioRepository.deleteAll();

		Disp disp1 = new Disp();
			disp1.setTipo("O");
			disp1.setInstalacao("RJUSCP");
			Date dataDisp1 = new Date("2016/11/28 00:00:00");
			disp1.setData(dataDisp1);
			disp1.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp1);
		
		Disp disp2 = new Disp();
			disp2.setTipo("O");
			disp2.setInstalacao("RJUSCP");
			Date dataDisp2 = new Date("2016/11/28 01:00:00");
			disp2.setData(dataDisp2);
			disp2.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp2);
		
		Disp disp3 = new Disp();
			disp3.setTipo("O");
			disp3.setInstalacao("RJUSCP");
			Date dataDisp3 = new Date("2016/11/28 02:00:00");
			disp3.setData(dataDisp3);
			disp3.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp3);
		
		Disp disp4 = new Disp();
			disp4.setTipo("O");
			disp4.setInstalacao("RJUSCP");
			Date dataDisp4 = new Date("2016/11/28 03:00:00");
			disp4.setData(dataDisp4);
			disp4.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp4);
		
		Disp disp5 = new Disp();
			disp5.setTipo("O");
			disp5.setInstalacao("RJUSCP");
			Date dataDisp5 = new Date("2016/11/28 04:00:00");
			disp5.setData(dataDisp5);
			disp5.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp5);
		
		Disp disp6 = new Disp();
			disp6.setTipo("O");
			disp6.setInstalacao("RJUSCP");
			Date dataDisp6 = new Date("2016/11/28 05:00:00");
			disp6.setData(dataDisp6);
			disp6.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp6);
		
		Disp disp7 = new Disp();
			disp7.setTipo("O");
			disp7.setInstalacao("RJUSCP");
			Date dataDisp7 = new Date("2016/11/28 06:00:00");
			disp7.setData(dataDisp7);
			disp7.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp7);
		
		Disp disp8 = new Disp();
			disp8.setTipo("O");
			disp8.setInstalacao("RJUSCP");
			Date dataDisp8 = new Date("2016/11/28 07:00:00");
			disp8.setData(dataDisp8);
			disp8.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp8);
			
			
			Disp disp9 = new Disp();
			disp9.setTipo("O");
			disp9.setInstalacao("RJUSCP");
			Date dataDisp9 = new Date("2016/11/28 08:00:00");
			disp9.setData(dataDisp9);
			disp9.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp9);
			
			Disp disp10 = new Disp();
			disp10.setTipo("O");
			disp10.setInstalacao("RJUSCP");
			Date dataDisp10 = new Date("2016/11/28 09:00:00");
			disp10.setData(dataDisp10);
			disp10.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp10);
			
			Disp disp11 = new Disp();
			disp11.setTipo("O");
			disp11.setInstalacao("RJUSCP");
			Date dataDisp11 = new Date("2016/11/28 10:00:00");
			disp11.setData(dataDisp11);
			disp11.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp11);
			
			
			Disp disp12 = new Disp();
			disp12.setTipo("O");
			disp12.setInstalacao("RJUSCP");
			Date dataDisp12 = new Date("2016/11/28 11:00:00");
			disp12.setData(dataDisp12);
			disp12.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp12);
			
			Disp disp13 = new Disp();
			disp13.setTipo("O");
			disp13.setInstalacao("RJUSCP");
			Date dataDisp13 = new Date("2016/11/28 12:00:00");
			disp13.setData(dataDisp13);
			disp13.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp13);
			
			
			Disp disp14 = new Disp();
			disp14.setTipo("O");
			disp14.setInstalacao("RJUSCP");
			Date dataDisp14 = new Date("2016/11/28 13:00:00");
			disp14.setData(dataDisp14);
			disp14.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp14);
			
			
			Disp disp15 = new Disp();
			disp15.setTipo("O");
			disp15.setInstalacao("RJUSCP");
			Date dataDisp15 = new Date("2016/11/28 14:00:00");
			disp15.setData(dataDisp15);
			disp15.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp15);
			
			
			Disp disp16 = new Disp();
			disp16.setTipo("O");
			disp16.setInstalacao("RJUSCP");
			Date dataDisp16 = new Date("2016/11/28 15:00:00");
			disp16.setData(dataDisp16);
			disp16.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp16);
			
			
			Disp disp17 = new Disp();
			disp17.setTipo("O");
			disp17.setInstalacao("RJUSCP");
			Date dataDisp17 = new Date("2016/11/28 16:00:00");
			disp17.setData(dataDisp17);
			disp17.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp17);
			
			
			Disp disp18 = new Disp();
			disp18.setTipo("O");
			disp18.setInstalacao("RJUSCP");
			Date dataDisp18 = new Date("2016/11/28 17:00:00");
			disp18.setData(dataDisp18);
			disp18.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp18);
			
			
			Disp disp19 = new Disp();
			disp19.setTipo("O");
			disp19.setInstalacao("RJUSCP");
			Date dataDisp19 = new Date("2016/11/28 18:00:00");
			disp19.setData(dataDisp19);
			disp19.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp19);
			
			
			Disp disp20 = new Disp();
			disp20.setTipo("O");
			disp20.setInstalacao("RJUSCP");
			Date dataDisp20 = new Date("2016/11/28 19:00:00");
			disp20.setData(dataDisp20);
			disp20.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp20);
			
			
			Disp disp21 = new Disp();
			disp21.setTipo("O");
			disp21.setInstalacao("RJUSCP");
			Date dataDisp21 = new Date("2016/11/28 20:00:00");
			disp21.setData(dataDisp21);
			disp21.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp21);
			
			
			Disp disp22 = new Disp();
			disp22.setTipo("O");
			disp22.setInstalacao("RJUSCP");
			Date dataDisp22 = new Date("2016/11/28 21:00:00");
			disp22.setData(dataDisp22);
			disp22.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp22);
			
			
			Disp disp23 = new Disp();
			disp23.setTipo("O");
			disp23.setInstalacao("RJUSCP");
			Date dataDisp23 = new Date("2016/11/28 22:00:00");
			disp23.setData(dataDisp23);
			disp23.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp23);
			
			Disp disp24 = new Disp();
			disp24.setTipo("O");
			disp24.setInstalacao("RJUSCP");
			Date dataDisp24 = new Date("2016/11/28 23:00:00");
			disp24.setData(dataDisp24);
			disp24.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp24);	
			
			Disp disp25 = new Disp();
			disp25.setTipo("O");
			disp25.setInstalacao("RJUSCP");
			Date dataDisp25 = new Date("2016/11/29 00:00:00");
			disp25.setData(dataDisp25);
			disp25.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp25);
			
			Disp disp26 = new Disp();
			disp26.setTipo("O");
			disp26.setInstalacao("RJUSCP");
			Date dataDisp26 = new Date("2016/11/29 01:00:00");
			disp26.setData(dataDisp26);
			disp26.setEquipamento("RJUSCP0UG1");
			dispRepository.save(disp26);

		 ComentarioSituacao comentSituacao1 = new ComentarioSituacao();
			 comentSituacao1.setDataFim(dataDisp1);
			 comentSituacao1.setDataInicio(dataDisp1);
			 comentSituacao1.setDataInsercao(dataDisp1);
			 comentSituacao1.setDescricao("Comentario de teste");
			 comentSituacao1.setNomeObjeto("objeto01");
			 comentSituacao1.setOrigem(OrigemComentario.SISTEMA);
			 comentSituacao1.setStatusObjeto(StatusObjeto.SUSPENSO);
			 comentSituacao1.setTipo(TipoComentario.COMENTARIO);
			 comentSituacao1.setTipoObjeto(TipoObjeto.USINA);
			 
		ComentarioSituacao comentSituacao2 = new ComentarioSituacao();
			comentSituacao2.setDataFim(dataDisp1);
			comentSituacao2.setDataInicio(dataDisp1);
			comentSituacao2.setDataInsercao(dataDisp1);
			comentSituacao2.setDescricao("Comentario de teste");
			comentSituacao2.setNomeObjeto("objeto01");
			comentSituacao2.setOrigem(OrigemComentario.SISTEMA);
			comentSituacao2.setStatusObjeto(StatusObjeto.SUSPENSO);
			comentSituacao2.setTipo(TipoComentario.AVISO);
			comentSituacao2.setTipoObjeto(TipoObjeto.USINA);
			
		ComentarioSituacao comentSituacao3 = new ComentarioSituacao();
			comentSituacao3.setDataFim(dataDisp1);
			comentSituacao3.setDataInicio(dataDisp1);
			comentSituacao3.setDataInsercao(dataDisp1);
			comentSituacao3.setDescricao("Comentario de teste");
			comentSituacao3.setNomeObjeto("objeto01");
			comentSituacao3.setOrigem(OrigemComentario.SISTEMA);
			comentSituacao3.setStatusObjeto(StatusObjeto.SUSPENSO);
			comentSituacao3.setTipo(TipoComentario.AVISO);
			comentSituacao3.setTipoObjeto(TipoObjeto.USINA);
		
		 Comentario comentario1 = new Comentario(comentSituacao1, "RJUSCP");
			 comentario1.setIdInstalacao("RJUSCP0UG1");
			 comentario1.setDataInsercao(dataDisp1);
		
		 Comentario comentario2 = new Comentario(comentSituacao2, "RJUSCP");
			 comentario2.setIdInstalacao("RJUSCP0UG1");
			 comentario2.setDataInsercao(dataDisp1);
			 
		 Comentario comentario3 = new Comentario(comentSituacao3, "RJUSCP");
			 comentario3.setIdInstalacao("RJUSCP0UG1");
			 comentario3.setDataInsercao(dataDisp1);
			 
		 comentarioRepository.save(comentario1);
		 comentarioRepository.save(comentario2);
		 comentarioRepository.save(comentario3);

	}
	
	/*
	 * CT010001 - Consultar disponibilide sem informar data, unidade geradora e disponibilidade
	 * Resultado esperado: Resultado esperado: NullPointerException.
	 */
	
	@SuppressWarnings("unchecked")
	@Test(expected=NullPointerException.class)
	public void ct010001() throws Exception {
		
		when(commandBus.sendAndWait(any(CommandMessage.class)))
		.thenAnswer(new Answer<ResultMessage<?>>() {
			@Override
			public ResultMessage<?> answer(InvocationOnMock invocation)
					throws Throwable {
				if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand")) {

					@SuppressWarnings("deprecation")
					Date dataInicio = new Date("2016/11/28 03:00:00");
					
					Disponibilidade disp1 = new Disponibilidade();
						disp1.setDataInicio(dataInicio);
						disp1.setIdEquipamento("RJUSCP0UG1");
						disp1.setValor(12.30);
						disp1.setVersao("1");
						disp1.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					
					List<Disponibilidade> disponibilidades = new ArrayList<>();
						disponibilidades.add(disp1);

					CalcularDisponibilidadeResponse resp = new CalcularDisponibilidadeResponse();
						resp.setDisponibilidades(disponibilidades);

					ResultMessage<CalcularDisponibilidadeResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
					
					Date data = new Date("2016/11/28 03:00:00");
					
					ComentarioSituacao coment1 = new ComentarioSituacao();
						coment1.setNomeObjeto("nome01");
						coment1.setDescricao("Descricao01");
						coment1.setDataFim(data);
						coment1.setDataInicio(data);
						coment1.setDataInsercao(data);
					
					List<ComentarioSituacao> comentarios = new ArrayList<>();
						comentarios.add(coment1);
						
					VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();	
						resp.setComentarios(comentarios);
					
					ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
					
					return result;
					
				} else {
					
					return null;
					
				}
			}
		});
		
		String content = null;
		
		int response = restDispMockMvc
				.perform(post("/api/disp").param("dtFim", "")
						.param("dtInicio", "").param("page", "0").param("size", "20")
						.param("sort", "id,asc").param("tipos", "O").content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getStatus();
		
		assertThat(response == 400 ).isTrue();
		
	}
	
	/*
	 * CT010002 - Consultar disponibilide informando somente as datas
	 * Resultado esperado: Resultado esperado: NullPointerException.
	 */
	
	@SuppressWarnings("unchecked")
	@Test(expected=NullPointerException.class)
	public void ct010002() throws Exception {
		
		when(commandBus.sendAndWait(any(CommandMessage.class)))
		.thenAnswer(new Answer<ResultMessage<?>>() {
			@Override
			public ResultMessage<?> answer(InvocationOnMock invocation)
					throws Throwable {
				if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand")) {
					
					@SuppressWarnings("deprecation")
					Date dataInicio = new Date("2016/11/28 03:00:00");

					Disponibilidade disp1 = new Disponibilidade();
						disp1.setDataInicio(dataInicio);
						disp1.setIdEquipamento("RJUSCP0UG1");
						disp1.setValor(12.30);
						disp1.setVersao("1");
						disp1.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					
					List<Disponibilidade> disponibilidades = new ArrayList<>();
						disponibilidades.add(disp1);

					CalcularDisponibilidadeResponse resp = new CalcularDisponibilidadeResponse();
					resp.setDisponibilidades(disponibilidades);
					
					ResultMessage<CalcularDisponibilidadeResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
					
					Date data = new Date("2016/11/28 03:00:00");
					
					ComentarioSituacao coment1 = new ComentarioSituacao();
						coment1.setNomeObjeto("nome01");
						coment1.setDescricao("Descricao01");
						coment1.setDataFim(data);
						coment1.setDataInicio(data);
						coment1.setDataInsercao(data);
						coment1.setTipo(TipoComentario.ERRO);
						coment1.setTipoObjeto(TipoObjeto.USINA);
					
					List<ComentarioSituacao> comentarios = new ArrayList<>();
						comentarios.add(coment1);
					
					VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();
						resp.setComentarios(comentarios);
						
					ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else {
					
					return null;
					
				}
			}
		});
		
		String content = null;
		
		int response = restDispMockMvc
				.perform(post("/api/disp").param("dtFim", "2016-11-29T03:00:00.000Z")
						.param("dtInicio", "2016-10-28T03:00:00.000Z").param("page", "0").param("size", "20")
						.param("sort", "id,asc").param("tipos", "O").content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getStatus();
		
		assertThat(response == 400 ).isTrue();
		
	}
	
	/*
	 * CT010003 - Consultar disponibilidade informando data e unidade geradora
	 * Resultado esperado: Retorno da Unidade Geradora.
	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void ct010003() throws Exception {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		
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
					Disponibilidade disp2 = new Disponibilidade();
					Disponibilidade disp3 = new Disponibilidade();
					Disponibilidade disp4 = new Disponibilidade();
					Disponibilidade disp5 = new Disponibilidade();
					Disponibilidade disp6 = new Disponibilidade();
					Disponibilidade disp7 = new Disponibilidade();
					Disponibilidade disp8 = new Disponibilidade();
					Disponibilidade disp9 = new Disponibilidade();
					Disponibilidade disp10 = new Disponibilidade();
					Disponibilidade disp11 = new Disponibilidade();
					Disponibilidade disp12 = new Disponibilidade();
					Disponibilidade disp13 = new Disponibilidade();
					Disponibilidade disp14 = new Disponibilidade();
					Disponibilidade disp15 = new Disponibilidade();
					Disponibilidade disp16 = new Disponibilidade();
					Disponibilidade disp17 = new Disponibilidade();
					Disponibilidade disp18 = new Disponibilidade();
					Disponibilidade disp19 = new Disponibilidade();
					Disponibilidade disp20 = new Disponibilidade();
					Disponibilidade disp21 = new Disponibilidade();
					Disponibilidade disp22 = new Disponibilidade();
					Disponibilidade disp23 = new Disponibilidade();
					Disponibilidade disp24 = new Disponibilidade();
					
					@SuppressWarnings("deprecation")
					Date dataInicio = new Date("2016/11/28 01:00:00");
					disp1.setDataInicio(dataInicio);
					disp1.setIdEquipamento("RJUSCP0UG1");
					disp1.setValor(12.31);
					disp1.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp1.setVersao("1");
					
					Date dataInicio2 = new Date("2016/11/28 02:00:00");
					disp2.setDataInicio(dataInicio);
					disp2.setIdEquipamento("RJUSCP0UG1");
					disp2.setValor(12.31);
					disp2.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp2.setVersao("1");
					
					Date dataInicio3 = new Date("2016/11/28 03:00:00");
					disp3.setDataInicio(dataInicio);
					disp3.setIdEquipamento("RJUSCP0UG1");
					disp3.setValor(12.31);
					disp3.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp3.setVersao("1");
					
					Date dataInicio4 = new Date("2016/11/28 04:00:00");
					disp4.setDataInicio(dataInicio);
					disp4.setIdEquipamento("RJUSCP0UG1");
					disp4.setValor(12.31);
					disp4.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp4.setVersao("1");
					
					Date dataInicio5 = new Date("2016/11/28 05:00:00");
					disp5.setDataInicio(dataInicio);
					disp5.setIdEquipamento("RJUSCP0UG1");
					disp5.setValor(12.31);
					disp5.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp5.setVersao("1");
					

					Date dataInicio6 = new Date("2016/11/28 06:00:00");
					disp6.setDataInicio(dataInicio);
					disp6.setIdEquipamento("RJUSCP0UG1");
					disp6.setValor(12.31);
					disp6.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp6.setVersao("1");
					

					Date dataInicio7 = new Date("2016/11/28 07:00:00");
					disp7.setDataInicio(dataInicio);
					disp7.setIdEquipamento("RJUSCP0UG1");
					disp7.setValor(12.31);
					disp7.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp7.setVersao("1");
					

					Date dataInicio8 = new Date("2016/11/28 08:00:00");
					disp8.setDataInicio(dataInicio);
					disp8.setIdEquipamento("RJUSCP0UG1");
					disp8.setValor(12.31);
					disp8.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp8.setVersao("1");
					
					

					Date dataInicio9 = new Date("2016/11/28 09:00:00");
					disp9.setDataInicio(dataInicio);
					disp9.setIdEquipamento("RJUSCP0UG1");
					disp9.setValor(12.32);
					disp9.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp9.setVersao("1");
					

					Date dataInicio10 = new Date("2016/11/28 10:00:00");
					disp10.setDataInicio(dataInicio);
					disp10.setIdEquipamento("RJUSCP0UG1");
					disp10.setValor(12.31);
					disp10.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp10.setVersao("1");
					
					

					Date dataInicio11 = new Date("2016/11/28 11:00:00");
					disp11.setDataInicio(dataInicio);
					disp11.setIdEquipamento("RJUSCP0UG1");
					disp11.setValor(12.32);
					disp11.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp11.setVersao("1");
					
					

					Date dataInicio12 = new Date("2016/11/28 12:00:00");
					disp12.setDataInicio(dataInicio);
					disp12.setIdEquipamento("RJUSCP0UG1");
					disp12.setValor(12.32);
					disp12.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp12.setVersao("1");
					
					

					Date dataInicio13 = new Date("2016/11/28 13:00:00");
					disp13.setDataInicio(dataInicio);
					disp13.setIdEquipamento("RJUSCP0UG1");
					disp13.setValor(12.30);
					disp13.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp13.setVersao("1");
					
					

					Date dataInicio14 = new Date("2016/11/28 14:00:00");
					disp14.setDataInicio(dataInicio);
					disp14.setIdEquipamento("RJUSCP0UG1");
					disp14.setValor(12.30);
					disp14.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp14.setVersao("1");
					
					

					Date dataInicio15 = new Date("2016/11/28 15:00:00");
					disp15.setDataInicio(dataInicio);
					disp15.setIdEquipamento("RJUSCP0UG1");
					disp15.setValor(12.30);
					disp15.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp15.setVersao("1");

					Date dataInicio16 = new Date("2016/11/28 16:00:00");
					disp16.setDataInicio(dataInicio);
					disp16.setIdEquipamento("RJUSCP0UG1");
					disp16.setValor(12.30);
					disp16.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp16.setVersao("1");		

					Date dataInicio17 = new Date("2016/11/28 17:00:00");
					disp17.setDataInicio(dataInicio);
					disp17.setIdEquipamento("RJUSCP0UG1");
					disp17.setValor(12.30);
					disp17.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp17.setVersao("1");	

					Date dataInicio18 = new Date("2016/11/28 18:00:00");
					disp18.setDataInicio(dataInicio);
					disp18.setIdEquipamento("RJUSCP0UG1");
					disp18.setValor(12.30);
					disp18.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp18.setVersao("1");				

					Date dataInicio19 = new Date("2016/11/28 19:00:00");
					disp19.setDataInicio(dataInicio);
					disp19.setIdEquipamento("RJUSCP0UG1");
					disp19.setValor(12.30);
					disp19.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp19.setVersao("1");
					

					Date dataInicio20 = new Date("2016/11/28 20:00:00");
					disp20.setDataInicio(dataInicio);
					disp20.setIdEquipamento("RJUSCP0UG1");
					disp20.setValor(12.30);
					disp20.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp20.setVersao("1");
					

					Date dataInicio21 = new Date("2016/11/28 21:00:00");
					disp21.setDataInicio(dataInicio);
					disp21.setIdEquipamento("RJUSCP0UG1");
					disp21.setValor(12.30);
					disp21.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp21.setVersao("1");
					

					Date dataInicio22 = new Date("2016/11/28 22:00:00");
					disp22.setDataInicio(dataInicio);
					disp22.setIdEquipamento("RJUSCP0UG1");
					disp22.setValor(12.30);
					disp22.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp22.setVersao("1");
					

					Date dataInicio23 = new Date("2016/11/28 23:00:00");
					disp23.setDataInicio(dataInicio);
					disp23.setIdEquipamento("RJUSCP0UG1");
					disp23.setValor(12.30);
					disp23.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp23.setVersao("1");
					

					Date dataInicio24 = new Date("2016/11/28 00:00:00");
					disp24.setDataInicio(dataInicio);
					disp24.setIdEquipamento("RJUSCP0UG1");
					disp24.setValor(12.30);
					disp24.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					disp24.setVersao("1");
					
					disponibilidades.add(disp1);
					disponibilidades.add(disp2);
					disponibilidades.add(disp3);
					disponibilidades.add(disp4);
					disponibilidades.add(disp5);
					disponibilidades.add(disp6);
					disponibilidades.add(disp7);
					disponibilidades.add(disp8);
					disponibilidades.add(disp9);
					disponibilidades.add(disp10);
					disponibilidades.add(disp11);
					disponibilidades.add(disp12);
					disponibilidades.add(disp13);
					disponibilidades.add(disp14);
					disponibilidades.add(disp15);
					disponibilidades.add(disp16);
					disponibilidades.add(disp17);
					disponibilidades.add(disp18);
					disponibilidades.add(disp19);
					disponibilidades.add(disp20);
					disponibilidades.add(disp21);
					disponibilidades.add(disp22);
					disponibilidades.add(disp23);
					disponibilidades.add(disp24);

					resp.setDisponibilidades(disponibilidades);
						result.getResults().add(resp);
						
					return result;
					
				} else if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
					
					Date data = new Date("2016/11/28 03:00:00");
					
					ComentarioSituacao coment1 = new ComentarioSituacao();
						coment1.setNomeObjeto("nome01");
						coment1.setDescricao("Descricao01");
						coment1.setDataFim(data);
						coment1.setDataInicio(data);
						coment1.setDataInsercao(data);
						coment1.setTipo(TipoComentario.COMENTARIO);
					
					List<ComentarioSituacao> comentarios = new ArrayList<>();
						comentarios.add(coment1);
					
					VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();
						resp.setComentarios(comentarios);
						
					ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else {
					
					return null;
					
				}
			}
		});
		
		String idUnidade = "RJUSCP";
		String idEquip = "RJUSCP0UG1";
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"" + idUnidade + "\"," + "\"equipamentos\":[{"
				+ "\"id\":\"" + idEquip + "\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"\"}]";
		
		String response = restDispMockMvc
		.perform(post("/api/disp").param("dtFim", "2016-11-29T01:59:59.999Z")
				.param("dtInicio", "2016-11-28T02:00:00.000Z").param("page", "0").param("size", "20")
				.param("sort", "id,asc").param("tipos", "O").content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		String[] conteudo = response.split(":");
		conteudo = response.split("\"");

		assertThat(conteudo[5].equals(idUnidade)).isTrue();
		assertThat(conteudo[19].equals(idEquip) ).isTrue();
		
	}
	
	/*
	 * CT010004 - Consultar disponibilidade informando data e unidade geradora sem operação
	 * Resultado esperado: Resultado esperado: Bad Request.
	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void ct010004() throws Exception {
		
		when(commandBus.sendAndWait(any(CommandMessage.class)))
		.thenAnswer(new Answer<ResultMessage<?>>() {
			@Override
			public ResultMessage<?> answer(InvocationOnMock invocation)
					throws Throwable {
				if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand")) {
					
					@SuppressWarnings("deprecation")
					Date dataInicio = new Date("2016/11/28 03:00:00");

					Disponibilidade disp1 = new Disponibilidade();
						disp1.setDataInicio(dataInicio);
						disp1.setIdEquipamento("RJUSCP0UG1");
						disp1.setValor(12.30);
						disp1.setVersao("1");
						disp1.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					
					List<Disponibilidade> disponibilidades = new ArrayList<>();
						disponibilidades.add(disp1);

					CalcularDisponibilidadeResponse resp = new CalcularDisponibilidadeResponse();
					resp.setDisponibilidades(disponibilidades);
					
					ResultMessage<CalcularDisponibilidadeResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
					
					Date data = new Date("2016/11/28 03:00:00");
					
					ComentarioSituacao coment1 = new ComentarioSituacao();
						coment1.setNomeObjeto("nome01");
						coment1.setDescricao("Descricao01");
						coment1.setDataFim(data);
						coment1.setDataInicio(data);
						coment1.setDataInsercao(data);
						coment1.setTipo(TipoComentario.AVISO);
						coment1.setTipoObjeto(TipoObjeto.USINA);
					
					List<ComentarioSituacao> comentarios = new ArrayList<>();
						comentarios.add(coment1);
					
					VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();
						resp.setComentarios(comentarios);
						
					ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else {
					
					return null;
					
				}
			}
		});
		
		String idUnidade = "RJUSCP";
		String idEquip = "RJUSCP0UG1";
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"" + idUnidade + "\"," + "\"equipamentos\":[{"
				+ "\"id\":\"" + idEquip + "\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"\"}]";
		
		int response = restDispMockMvc
				.perform(post("/api/disp")
						.param("page", "0").param("size", "20")
						.param("sort", "id,asc").content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getStatus();
		
		assertThat(response == 400 ).isTrue();

	}
	
	/*
	 * CT010005 - Consultar disponibilidade unidade geradora sem operação e data
	 * Resultado esperado: Retorno da Unidade Geradora.
	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void ct010005() throws Exception {
		
		when(commandBus.sendAndWait(any(CommandMessage.class)))
		.thenAnswer(new Answer<ResultMessage<?>>() {
			@Override
			public ResultMessage<?> answer(InvocationOnMock invocation)
					throws Throwable {
				if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand")) {
					
					@SuppressWarnings("deprecation")
					Date dataInicio = new Date("2016/11/28 03:00:00");

					Disponibilidade disp1 = new Disponibilidade();
						disp1.setDataInicio(dataInicio);
						disp1.setIdEquipamento("RJUSCP0UG1");
						disp1.setValor(12.30);
						disp1.setVersao("1");
						disp1.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
					
					List<Disponibilidade> disponibilidades = new ArrayList<>();
						disponibilidades.add(disp1);

					CalcularDisponibilidadeResponse resp = new CalcularDisponibilidadeResponse();
					resp.setDisponibilidades(disponibilidades);
					
					ResultMessage<CalcularDisponibilidadeResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
					
					Date data = new Date("2016/11/28 03:00:00");
					
					ComentarioSituacao coment1 = new ComentarioSituacao();
						coment1.setNomeObjeto("nome01");
						coment1.setDescricao("Descricao01");
						coment1.setDataFim(data);
						coment1.setDataInicio(data);
						coment1.setDataInsercao(data);
					
					List<ComentarioSituacao> comentarios = new ArrayList<>();
						comentarios.add(coment1);
					
					VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();
						resp.setComentarios(comentarios);
						
					ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else {
					
					return null;
					
				}
			}
		});
		
		String idUnidade = "RJUSCP";
		String idEquip = "RJUSCP0UG1";
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"" + idUnidade + "\"," + "\"equipamentos\":[{"
				+ "\"id\":\"" + idEquip + "\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"\"}]";
		
		int response = restDispMockMvc
				.perform(post("/api/disp")
						.param("page", "0").param("size", "20")
						.param("sort", "id,asc").content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getStatus();
		
		assertThat(response == 400 ).isTrue();
		
	}
	
	/*
	 * ct010006 - Consultar disponibilidade sem passar o equipamento
	 * Resultado esperado: o retorno da Instalação sem unidades geradoras.
	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void ct010006() throws Exception {
		
		when(commandBus.sendAndWait(any(CommandMessage.class)))
		.thenAnswer(new Answer<ResultMessage<?>>() {
			@Override
			public ResultMessage<?> answer(InvocationOnMock invocation)
					throws Throwable {
				if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand")) {
					
					@SuppressWarnings("deprecation")
					Date dataInicio1 = new Date("2016/11/28 03:00:00");

					Disponibilidade disp1 = new Disponibilidade();
						disp1.setDataInicio(dataInicio1);
						disp1.setIdEquipamento("RJUSCP0UG1");
						disp1.setValor(12.30);
						disp1.setVersao("1");
						disp1.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
						disp1.setId("id01");
						
						
					List<Disponibilidade> disponibilidades = new ArrayList<>();
						disponibilidades.add(disp1);
						
					CalcularDisponibilidadeResponse resp = new CalcularDisponibilidadeResponse();
						resp.setDisponibilidades(disponibilidades);
					
					ResultMessage<CalcularDisponibilidadeResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
					
					Date data = new Date("2016/11/28 03:00:00");
					
					ComentarioSituacao coment1 = new ComentarioSituacao();
						coment1.setNomeObjeto("nome01");
						coment1.setDescricao("Descricao01");
						coment1.setDataFim(data);
						coment1.setDataInicio(data);
						coment1.setDataInsercao(data);
					
					List<ComentarioSituacao> comentarios = new ArrayList<>();
						comentarios.add(coment1);
					
					VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();
						resp.setComentarios(comentarios);
						
					ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else {
					
					return null;
					
				}
			}
		});
		
		String idUnidade = "RJUSCP";
		String idEquip = "RJUSCP0UG1";
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"" + idUnidade + "\"," + "\"equipamentos\":[{"
				+ "\"id\":\"" + idEquip + "\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"\"}]";
		
		String response = restDispMockMvc
				.perform(post("/api/disp").param("dtFim", "2016-11-28T04:00:00.000Z")
						.param("dtInicio", "2016-10-28T03:00:00.000Z").param("page", "0").param("size", "20")
						.param("sort", "id,asc").param("tipos", "O").content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		String[] conteudo = response.split(":");
		conteudo = response.split("\"");
		
		System.out.println(conteudo[5]);
		System.out.println(conteudo[19]);

		assertThat(conteudo[5].equals(idUnidade)).isTrue();
		assertThat(conteudo[19].equals(idEquip) ).isTrue();
		
	}
	
	/*
	 * ct010007 - Chamada com erro na execução do calculo.
	 * Resultado esperado: Dispara uma exceção.
	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void ct010007() throws Exception {
		
		when(commandBus.sendAndWait(any(CommandMessage.class)))
		.thenAnswer(new Answer<ResultMessage<?>>() {
			@Override
			public ResultMessage<?> answer(InvocationOnMock invocation)
					throws Throwable {
				if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
					
					Date data = new Date("2016/11/28 03:00:00");

					List<ComentarioSituacao> comentarios = new ArrayList<>();
					ComentarioSituacao coment1 = new ComentarioSituacao();
						coment1.setNomeObjeto("nome01");
						coment1.setDescricao("Descricao01");
						coment1.setDataFim(data);
						coment1.setDataInicio(data);
						coment1.setDataInsercao(data);
					
					comentarios.add(coment1);
					
					VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();
						resp.setComentarios(comentarios);
					
					ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
					
					return result;
					
				} else {
					
					return null;
					
				}
			}
		});
		
		String idUnidade = "RJUSCP";
		String idEquip = "RJUSCP0UG1";
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"" + idUnidade + "\"," + "\"equipamentos\":[{"
				+ "\"id\":\"" + idEquip + "\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"\"}]";
		
		int response = restDispMockMvc
				.perform(post("/api/disp").param("dtFim", "2016-11-28T04:00:00.000Z")
						.param("dtInicio", "2016-10-28T03:00:00.000Z").param("page", "0").param("size", "20")
						.param("sort", "id,asc").param("tipos", "O").content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getStatus();
		
		assertThat(response == 400 ).isTrue();

	}
	
	/*
	 * ct010008 - Chamar os comentarios de uma Instalação.
	 * Resultado esperado: Retornar os comentarios.
	 */
	
	@Test
	public void ct010008() throws Exception {
		
		String tipoComentario = "Comentario de teste";
		
		String response = restDispMockMvc
				.perform(get("/api/comentarios")
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		String[] conteudo = response.split(":");
				 conteudo = response.split("\"");
		
		System.out.println(conteudo[13]);
		
		assertThat(conteudo[13].equals(tipoComentario)).isTrue();
		
	}
	
	/*
	 * ct010009 - Consultar um comentario do tipo COMENTARIO 
	 * Resultado esperado: Resultado esperado: Retornar o comentario do tipo comentario.
	 */
	
	@Test
	public void ct010009() throws Exception {
	
	comentarioRepository.deleteAll();
	Date data = new Date("2016/11/28 00:00:00");
		
	ComentarioSituacao comentSituacao1 = new ComentarioSituacao();
		comentSituacao1.setDataFim(data);
		comentSituacao1.setDataInicio(data);
		comentSituacao1.setDataInsercao(data);
		comentSituacao1.setDescricao("Comentario de teste");
		comentSituacao1.setNomeObjeto("objeto01");
		comentSituacao1.setOrigem(OrigemComentario.SISTEMA);
		comentSituacao1.setStatusObjeto(StatusObjeto.SUSPENSO);
		comentSituacao1.setTipo(TipoComentario.COMENTARIO);
		comentSituacao1.setTipoObjeto(TipoObjeto.USINA);
	
	 Comentario comentario1 = new Comentario(comentSituacao1, "RJUSCP");
		 comentario1.setIdInstalacao("RJUSCP0UG1");
		 comentario1.setDataInsercao(data);
		 
	 comentarioRepository.save(comentario1);
	 
	String tipoComentario = "COMENTARIO";
	
	String response = restDispMockMvc
			.perform(get("/api/comentarios")
					.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
	
	String[] conteudo = response.split(":");
			 conteudo = response.split("\"");
	
	assertThat(conteudo[9].equals(tipoComentario)).isTrue();
		
	}

	/*
	 * ct010010 - Consultar um comentario do tipo AVISO 
	 * Resultado esperado: Resultado esperado: Retornar o comentario do tipo aviso
	 */
	
	@Test
	public void ct010010() throws Exception {
	
	comentarioRepository.deleteAll();
	Date data = new Date("2016/11/28 00:00:00");
		
	ComentarioSituacao comentSituacao1 = new ComentarioSituacao();
		comentSituacao1.setDataFim(data);
		comentSituacao1.setDataInicio(data);
		comentSituacao1.setDataInsercao(data);
		comentSituacao1.setDescricao("Comentario de teste");
		comentSituacao1.setNomeObjeto("objeto01");
		comentSituacao1.setOrigem(OrigemComentario.SISTEMA);
		comentSituacao1.setStatusObjeto(StatusObjeto.SUSPENSO);
		comentSituacao1.setTipo(TipoComentario.AVISO);
		comentSituacao1.setTipoObjeto(TipoObjeto.USINA);
	
	 Comentario comentario1 = new Comentario(comentSituacao1, "RJUSCP");
		 comentario1.setIdInstalacao("RJUSCP0UG1");
		 comentario1.setDataInsercao(data);
		 
	 comentarioRepository.save(comentario1);
	 
	String tipoComentario = "AVISO";
	
	String response = restDispMockMvc
			.perform(get("/api/comentarios")
					.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
	
	String[] conteudo = response.split(":");
			 conteudo = response.split("\"");
	
	assertThat(conteudo[9].equals(tipoComentario)).isTrue();
		
	}
	
	/*
	 * ct010011 - Consultar um comentario do tipo ERRO 
	 * Resultado esperado: Resultado esperado: Retornar o comentario do tipo erro.
	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void ct010011() throws Exception {
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
					disp1.setTipoDisponibilidade(TipoDisponibilidade.ELETROMECANICA);
	
					Disponibilidade disp2 = new Disponibilidade();
					disp2.setDataInicio(dataInicio);
					disp2.setIdEquipamento("RJUSCP0UG2");
					disp2.setValor(12.30);
					disp2.setVersao("1");
	
					disponibilidades.add(disp1);
					disponibilidades.add(disp2);
	
					resp.setDisponibilidades(disponibilidades);
	
					result.getResults().add(resp);
					return result;
				} else if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
					ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
					result.setStatusCode(HttpStatus.OK.value());
					result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
					
					VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();
					List<ComentarioSituacao> comentarios = new ArrayList<>();
					
					ComentarioSituacao coment1 = new ComentarioSituacao();
					ComentarioSituacao coment2 = new ComentarioSituacao();
					ComentarioSituacao coment3 = new ComentarioSituacao();
					
					Date data = new Date("2016/11/28 13:00:00");
					Date data1 = new Date("2016/11/29 13:00:00");
					
					coment1.setNomeObjeto("nome01");
					coment1.setDescricao("Descricao01");
					coment1.setDataFim(data1);
					coment1.setDataInicio(data);
					coment1.setDataInsercao(data);
					coment1.setTipoObjeto(TipoObjeto.USINA);
					coment1.setTipo(TipoComentario.COMENTARIO);
					coment1.setStatusObjeto(StatusObjeto.SUSPENSO);
					comentarios.add(coment1);
					resp.setComentarios(comentarios);
					result.getResults().add(resp);
					
					comentarios.add(coment1);
					
					data = new Date("2016/11/28 04:00:00");
					coment2.setNomeObjeto("nome02");
					coment2.setDescricao("Descricao02");
					coment2.setDataFim(data);
					coment2.setDataInicio(data);
					coment2.setDataInsercao(data);
					coment2.setTipoObjeto(TipoObjeto.USINA);
					coment2.setTipo(TipoComentario.AVISO);
					coment2.setStatusObjeto(StatusObjeto.DESATIVADO);
					comentarios.add(coment2);
					resp.setComentarios(comentarios);
					result.getResults().add(resp);
		
					comentarios.add(coment2);
					
					data = new Date("2016/11/28 05:00:00");
					coment3.setNomeObjeto("nome03");
					coment3.setDescricao("Descricao03");
					coment3.setDataFim(data);
					coment3.setDataInicio(data);
					coment3.setDataInsercao(data);
					coment3.setTipoObjeto(TipoObjeto.USINA);
					coment3.setTipo(TipoComentario.ERRO);
					coment3.setStatusObjeto(StatusObjeto.FORA_OPERACAO_COMERCIAL);
					comentarios.add(coment3);
					resp.setComentarios(comentarios);
					result.getResults().add(resp);
					
					comentarios.add(coment3);
					
					resp.setComentarios(comentarios);
					result.getResults().add(resp);
					
					return result;
				} else {
					return null;
				}
			}
		});
	
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"RJUSCP\"," + "\"equipamentos\":[{"
			+ "\"id\":\"RJUSCP0UG1\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
			+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
			+ "\"minorVersion\":\"1\"}]";
		
		String response = restDispMockMvc
			.perform(post("/api/disp").param("dtFim", "2016-11-29T03:00:00.000Z")
					.param("dtInicio", "2016-11-28T03:00:00.000Z").param("page", "0").param("size", "20")
					.param("sort", "id,asc").param("tipos", "O").content(content)
					.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		System.out.println("Response" + response);
			
	}
	
	/*
	 * ct010015 - Consultar disponibilidade Comercial sem passar o equipamento   
	 * Resultado esperado: Resultado esperado: o retorno da Instalação sem unidades geradoras.
	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void ct010015() throws Exception {
		
		when(commandBus.sendAndWait(any(CommandMessage.class)))
		.thenAnswer(new Answer<ResultMessage<?>>() {
			@Override
			public ResultMessage<?> answer(InvocationOnMock invocation)
					throws Throwable {
				if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand")) {
					
					@SuppressWarnings("deprecation")
					Date dataInicio1 = new Date("2016/11/28 03:00:00");

					Disponibilidade disp1 = new Disponibilidade();
						disp1.setDataInicio(dataInicio1);
						disp1.setIdEquipamento("RJUSCP0UG1");
						disp1.setValor(12.30);
						disp1.setVersao("1");
						disp1.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
						disp1.setId("id01");
						
					Date dataInicio2  = new Date("2016/11/28 04:00:00");
					
					Disponibilidade disp2 = new Disponibilidade();
						disp2.setDataInicio(dataInicio2);
						disp2.setIdEquipamento("RJUSCP0UG1");
						disp2.setValor(12.30);
						disp2.setVersao("2");
						disp2.setTipoDisponibilidade(TipoDisponibilidade.COMERCIAL);
						disp2.setId("id01");
						
						
					List<Disponibilidade> disponibilidades = new ArrayList<>();
						disponibilidades.add(disp1);
						disponibilidades.add(disp2);
						
					CalcularDisponibilidadeResponse resp = new CalcularDisponibilidadeResponse();
						resp.setDisponibilidades(disponibilidades);
					
					ResultMessage<CalcularDisponibilidadeResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
					
					Date data = new Date("2016/11/28 03:00:00");
					
					ComentarioSituacao coment1 = new ComentarioSituacao();
						coment1.setNomeObjeto("nome01");
						coment1.setDescricao("Descricao01");
						coment1.setDataFim(data);
						coment1.setDataInicio(data);
						coment1.setDataInsercao(data);
					
					List<ComentarioSituacao> comentarios = new ArrayList<>();
						comentarios.add(coment1);
					
					VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();
						resp.setComentarios(comentarios);
						
					ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else {
					
					return null;
					
				}
			}
		});
		
		String idUnidade = "RJUSCP";
		String idEquip = "RJUSCP0UG1";
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"" + idUnidade + "\"," + "\"equipamentos\":[{"
				+ "\"id\":\"" + idEquip + "\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"\"}]";
		
		String response = restDispMockMvc
				.perform(post("/api/disp").param("dtFim", "2016-11-28T04:00:00.000Z")
						.param("dtInicio", "2016-10-28T03:00:00.000Z").param("page", "0").param("size", "20")
						.param("sort", "id,asc").param("tipos", "O").content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		String[] conteudo = response.split(":");
		conteudo = response.split("\"");
		
		System.out.println(conteudo[5]);
		System.out.println(conteudo[19]);

		assertThat(conteudo[5].equals(idUnidade)).isTrue();
		assertThat(conteudo[19].equals(idEquip) ).isTrue();
		
	}
	
	/*
	 * ct010016 - Consultar disponibilidade Eletromecanica sem passar o equipamento   
	 * Resultado esperado: Resultado esperado: o retorno da Instalação sem unidades geradoras.
	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void ct01006() throws Exception {
		
		when(commandBus.sendAndWait(any(CommandMessage.class)))
		.thenAnswer(new Answer<ResultMessage<?>>() {
			@Override
			public ResultMessage<?> answer(InvocationOnMock invocation)
					throws Throwable {
				if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand")) {
					
						
					Date dataInicio3 = new Date("2016/11/28 03:00:00");	
					
					Disponibilidade disp3 = new Disponibilidade();
						disp3.setDataInicio(dataInicio3);
						disp3.setIdEquipamento("RJUSCP0UG1");
						disp3.setValor(12.30);
						disp3.setVersao("1");
						disp3.setTipoDisponibilidade(TipoDisponibilidade.ELETROMECANICA);
						disp3.setId("id02");
						
					Date dataInicio4 = new Date("2016/11/28 04:00:00");
					
					Disponibilidade disp4 = new Disponibilidade();
						disp4.setDataInicio(dataInicio4);
						disp4.setIdEquipamento("RJUSCP0UG1");
						disp4.setValor(12.30);
						disp4.setVersao("2");
						disp4.setTipoDisponibilidade(TipoDisponibilidade.ELETROMECANICA);
						disp4.setId("id02");
						
						
					List<Disponibilidade> disponibilidades = new ArrayList<>();
						disponibilidades.add(disp3);
						disponibilidades.add(disp4);
						
					CalcularDisponibilidadeResponse resp = new CalcularDisponibilidadeResponse();
						resp.setDisponibilidades(disponibilidades);
					
					ResultMessage<CalcularDisponibilidadeResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
					
					Date data = new Date("2016/11/28 03:00:00");
					
					ComentarioSituacao coment1 = new ComentarioSituacao();
						coment1.setNomeObjeto("nome01");
						coment1.setDescricao("Descricao01");
						coment1.setDataFim(data);
						coment1.setDataInicio(data);
						coment1.setDataInsercao(data);
					
					List<ComentarioSituacao> comentarios = new ArrayList<>();
						comentarios.add(coment1);
					
					VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();
						resp.setComentarios(comentarios);
						
					ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else {
					
					return null;
					
				}
			}
		});
		
		String idUnidade = "RJUSCP";
		String idEquip = "RJUSCP0UG1";
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"" + idUnidade + "\"," + "\"equipamentos\":[{"
				+ "\"id\":\"" + idEquip + "\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"\"}]";
		
		String response = restDispMockMvc
				.perform(post("/api/disp").param("dtFim", "2016-11-28T04:00:00.000Z")
						.param("dtInicio", "2016-10-28T03:00:00.000Z").param("page", "0").param("size", "20")
						.param("sort", "id,asc").param("tipos", "O").content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		String[] conteudo = response.split(":");
		conteudo = response.split("\"");
		
		System.out.println(conteudo[5]);
		System.out.println(conteudo[19]);

		assertThat(conteudo[5].equals(idUnidade)).isTrue();
		assertThat(conteudo[19].equals(idEquip) ).isTrue();
		
	}
	
	/*
	 * ct010017 - Consultar disponibilidade Operacional sem passar o equipamento   
	 * Resultado esperado: Resultado esperado: o retorno da Instalação sem unidades geradoras.
	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void ct010017() throws Exception {
		
		when(commandBus.sendAndWait(any(CommandMessage.class)))
		.thenAnswer(new Answer<ResultMessage<?>>() {
			@Override
			public ResultMessage<?> answer(InvocationOnMock invocation)
					throws Throwable {
				if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand")) {
					
						
					Date dataInicio5 = new Date("2016/11/28 03:00:00");
						
					Disponibilidade disp5 = new Disponibilidade();
						disp5.setDataInicio(dataInicio5);
						disp5.setIdEquipamento("RJUSCP0UG1");
						disp5.setValor(12.30);
						disp5.setVersao("1");
						disp5.setTipoDisponibilidade(TipoDisponibilidade.OPERACIONAL);
						disp5.setId("id03");

						
					Date dataInicio6 = new Date("2016/11/28 04:00:00");
					
					Disponibilidade disp6 = new Disponibilidade();
						disp6.setDataInicio(dataInicio6);
						disp6.setIdEquipamento("RJUSCP0UG1");
						disp6.setValor(12.30);
						disp6.setVersao("2");
						disp6.setTipoDisponibilidade(TipoDisponibilidade.OPERACIONAL);
						disp6.setId("id03");
						
						
					List<Disponibilidade> disponibilidades = new ArrayList<>();
						disponibilidades.add(disp5);
						disponibilidades.add(disp6);
						
					CalcularDisponibilidadeResponse resp = new CalcularDisponibilidadeResponse();
						resp.setDisponibilidades(disponibilidades);
					
					ResultMessage<CalcularDisponibilidadeResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else if (invocation.getArgumentAt(0, CommandMessage.class).getCommand().getName()
						.equals("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand")) {
					
					Date data = new Date("2016/11/28 03:00:00");
					
					ComentarioSituacao coment1 = new ComentarioSituacao();
						coment1.setNomeObjeto("nome01");
						coment1.setDescricao("Descricao01");
						coment1.setDataFim(data);
						coment1.setDataInicio(data);
						coment1.setDataInsercao(data);
					
					List<ComentarioSituacao> comentarios = new ArrayList<>();
						comentarios.add(coment1);
					
					VerificarSituacaoInstalacaoResponse resp = new VerificarSituacaoInstalacaoResponse();
						resp.setComentarios(comentarios);
						
					ResultMessage<VerificarSituacaoInstalacaoResponse> result = new ResultMessage<>();
						result.setStatusCode(HttpStatus.OK.value());
						result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
						result.getResults().add(resp);
						
					return result;
					
				} else {
					
					return null;
					
				}
			}
		});
		
		String idUnidade = "RJUSCP";
		String idEquip = "RJUSCP0UG1";
		
		String content = "[{\"nome\":\"CAMPOS\"," + "\"id\":\"" + idUnidade + "\"," + "\"equipamentos\":[{"
				+ "\"id\":\"" + idEquip + "\"," + "\"nome\":\"UG   13 MW USI CAMPOS                1 RJ\","
				+ "\"valorPotencia\":null," + "\"participacao\":null," + "\"equipamentosEventosId\":null}],"
				+ "\"minorVersion\":\"\"}]";
		
		String response = restDispMockMvc
				.perform(post("/api/disp").param("dtFim", "2016-11-28T04:00:00.000Z")
						.param("dtInicio", "2016-10-28T03:00:00.000Z").param("page", "0").param("size", "20")
						.param("sort", "id,asc").param("tipos", "O").content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		String[] conteudo = response.split(":");
		conteudo = response.split("\"");
		
		System.out.println(conteudo[5]);
		System.out.println(conteudo[19]);

		assertThat(conteudo[5].equals(idUnidade)).isTrue();
		assertThat(conteudo[19].equals(idEquip) ).isTrue();
		
	}
}
