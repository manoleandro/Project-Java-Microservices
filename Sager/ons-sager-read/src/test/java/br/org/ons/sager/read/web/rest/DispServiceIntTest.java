package br.org.ons.sager.read.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
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

import com.jayway.jsonpath.JsonPath;
import com.querydsl.core.types.OrderSpecifier;

import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.ComentarioSituacao.StatusObjeto;
import br.org.ons.geracao.evento.ComentarioSituacao.TipoObjeto;
import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.geracao.evento.OrigemComentario;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.evento.TipoDisponibilidade;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand;
import br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand;
import br.org.ons.sager.instalacao.event.DisponibilidadesCalculadasEvent;
import br.org.ons.sager.read.OnsSagerReadApp;
import br.org.ons.sager.read.config.mq.CommandBus;
import br.org.ons.geracao.evento.Comentario;
import br.org.ons.sager.read.domain.Disp;
import br.org.ons.sager.read.domain.DispComparator;
import br.org.ons.sager.read.domain.QComentario;
import br.org.ons.sager.read.domain.QDisp;
import br.org.ons.sager.read.repository.ComentarioRepository;
import br.org.ons.sager.read.repository.DispRepository;
import br.org.ons.sager.read.service.DispService;
import br.org.ons.sager.read.web.rest.dto.DispDTO;
import br.org.ons.sager.dto.EquipamentoDTO;
import br.org.ons.sager.dto.UsinaDTO;
import br.org.ons.sager.read.web.rest.dto.DisponibilidadesDTO;
import br.org.ons.sager.read.web.rest.exceptions.BadRequestException;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeResponse;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoResponse;

/**
 * Service Implementation for managing Disp.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class DispServiceIntTest {
    
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

	}

	@SuppressWarnings("null")
	@Test
	public void inserirComentariosComDataFimDataInicio() throws Exception {
		EventMessage<DisponibilidadesCalculadasEvent> eventMessage = new EventMessage<>();
		Date data = new Date("2016/11/28 03:00:00");
		
		List<Disponibilidade> disponibilidades = new ArrayList<>();
		Disponibilidade disponibilidade = new Disponibilidade();
			disponibilidade.setDataInicio(data);
			disponibilidade.setId("id01");
			disponibilidade.setIdEquipamento("idEquipamento01");
			disponibilidade.setTipoDisponibilidade(TipoDisponibilidade.OPERACIONAL);
			disponibilidade.setValor(23.12);
			disponibilidade.setVersao("1");
		disponibilidades.add(disponibilidade);
		
		List<Comentario> comentarios = new ArrayList<>();
		ComentarioSituacao comentario1 = new ComentarioSituacao();
			comentario1.setDataInsercao(data);
			comentario1.setDescricao("Descricao");
			comentario1.setOrigem(OrigemComentario.ONS);
			comentario1.setTipo(TipoComentario.COMENTARIO);
			comentario1.setDataFim(data);
			comentario1.setDataInicio(data);
			comentario1.setNomeObjeto("nome01");
			comentario1.setStatusObjeto(StatusObjeto.FORA_OPERACAO_COMERCIAL);
			comentario1.setTipoObjeto(TipoObjeto.USINA);
		comentarios.add(comentario1);
		
		DisponibilidadesCalculadasEvent disp1 = new DisponibilidadesCalculadasEvent();
			disp1.setDataFim(data);
			disp1.setDataInicio(data);
			disp1.setIdEquipamento("idEquipamento01");
			disp1.setComentarios(comentarios);
			disp1.setDisponibilidades(disponibilidades);
		
		eventMessage.setEvent(disp1);
		EventMetadata metadata= new EventMetadata();
		metadata.setAggregateId("Usina01");
		
		eventMessage.setMetadata(metadata);
		
		dispService.handleDisponibilidadesCalculadasEvent(eventMessage);

	}
	
	@SuppressWarnings("null")
	@Test
	public void inserirComentariosComDataInicio() throws Exception {
		EventMessage<DisponibilidadesCalculadasEvent> eventMessage = new EventMessage<>();
		Date data = new Date("2016/11/28 03:00:00");
		
		List<Disponibilidade> disponibilidades = new ArrayList<>();
		Disponibilidade disponibilidade = new Disponibilidade();
			disponibilidade.setDataInicio(data);
			disponibilidade.setId("id01");
			disponibilidade.setIdEquipamento("idEquipamento01");
			disponibilidade.setTipoDisponibilidade(TipoDisponibilidade.OPERACIONAL);
			disponibilidade.setValor(23.12);
			disponibilidade.setVersao("1");
		disponibilidades.add(disponibilidade);
		
		List<Comentario> comentarios = new ArrayList<>();
		ComentarioSituacao comentario1 = new ComentarioSituacao();
			comentario1.setDataInsercao(data);
			comentario1.setDescricao("Descricao");
			comentario1.setOrigem(OrigemComentario.ONS);
			comentario1.setTipo(TipoComentario.COMENTARIO);
			comentario1.setDataInicio(data);
			comentario1.setNomeObjeto("nome01");
			comentario1.setStatusObjeto(StatusObjeto.FORA_OPERACAO_COMERCIAL);
			comentario1.setTipoObjeto(TipoObjeto.USINA);
		comentarios.add(comentario1);
		
		DisponibilidadesCalculadasEvent disp1 = new DisponibilidadesCalculadasEvent();
			disp1.setDataFim(data);
			disp1.setDataInicio(data);
			disp1.setIdEquipamento("idEquipamento01");
			disp1.setComentarios(comentarios);
			disp1.setDisponibilidades(disponibilidades);
		
		eventMessage.setEvent(disp1);
		EventMetadata metadata= new EventMetadata();
		metadata.setAggregateId("Usina01");
		
		eventMessage.setMetadata(metadata);
		
		dispService.handleDisponibilidadesCalculadasEvent(eventMessage);

	}

	@SuppressWarnings("null")
	@Test
	public void inserirComentariosComDataFim() throws Exception {
		EventMessage<DisponibilidadesCalculadasEvent> eventMessage = new EventMessage<>();
		Date data = new Date("2016/11/28 03:00:00");
		
		List<Disponibilidade> disponibilidades = new ArrayList<>();
		Disponibilidade disponibilidade = new Disponibilidade();
			disponibilidade.setDataInicio(data);
			disponibilidade.setId("id01");
			disponibilidade.setIdEquipamento("idEquipamento01");
			disponibilidade.setTipoDisponibilidade(TipoDisponibilidade.OPERACIONAL);
			disponibilidade.setValor(23.12);
			disponibilidade.setVersao("1");
		disponibilidades.add(disponibilidade);
		
		List<Comentario> comentarios = new ArrayList<>();
		ComentarioSituacao comentario1 = new ComentarioSituacao();
			comentario1.setDataInsercao(data);
			comentario1.setDescricao("Descricao");
			comentario1.setOrigem(OrigemComentario.ONS);
			comentario1.setTipo(TipoComentario.COMENTARIO);
			comentario1.setDataFim(data);
			comentario1.setNomeObjeto("nome01");
			comentario1.setStatusObjeto(StatusObjeto.FORA_OPERACAO_COMERCIAL);
			comentario1.setTipoObjeto(TipoObjeto.USINA);
		comentarios.add(comentario1);
		
		DisponibilidadesCalculadasEvent disp1 = new DisponibilidadesCalculadasEvent();
			disp1.setDataFim(data);
			disp1.setDataInicio(data);
			disp1.setIdEquipamento("idEquipamento01");
			disp1.setComentarios(comentarios);
			disp1.setDisponibilidades(disponibilidades);
		
		eventMessage.setEvent(disp1);
		EventMetadata metadata= new EventMetadata();
		metadata.setAggregateId("Usina01");
		
		eventMessage.setMetadata(metadata);
		
		dispService.handleDisponibilidadesCalculadasEvent(eventMessage);

	}
	
	@SuppressWarnings("null")
	@Test
	public void inserirComentariosComDataFimErro() throws Exception {
		EventMessage<DisponibilidadesCalculadasEvent> eventMessage = new EventMessage<>();
		Date data = new Date("2016/11/28 03:00:00");
		
		List<Disponibilidade> disponibilidades = new ArrayList<>();
		Disponibilidade disponibilidade = new Disponibilidade();
			disponibilidade.setDataInicio(data);
			disponibilidade.setId("id01");
			disponibilidade.setIdEquipamento("idEquipamento01");
			disponibilidade.setTipoDisponibilidade(TipoDisponibilidade.OPERACIONAL);
			disponibilidade.setValor(23.12);
			disponibilidade.setVersao("1");
		disponibilidades.add(disponibilidade);
		
		List<Comentario> comentarios = new ArrayList<>();
		ComentarioSituacao comentario1 = new ComentarioSituacao();
			comentario1.setDataInsercao(data);
			comentario1.setDescricao("Descricao");
			comentario1.setOrigem(OrigemComentario.ONS);
			comentario1.setTipo(TipoComentario.COMENTARIO);
			comentario1.setDataFim(data);
			comentario1.setNomeObjeto("nome01");
			comentario1.setStatusObjeto(StatusObjeto.FORA_OPERACAO_COMERCIAL);
			comentario1.setTipoObjeto(TipoObjeto.USINA);
		comentarios.add(comentario1);
		
		DisponibilidadesCalculadasEvent disp1 = new DisponibilidadesCalculadasEvent();
			disp1.setDataFim(data);
			disp1.setDataInicio(data);
			disp1.setIdEquipamento("idEquipamento01");
			disp1.setComentarios(comentarios);
			disp1.setDisponibilidades(disponibilidades);
		
		eventMessage.setEvent(disp1);
		EventMetadata metadata= new EventMetadata();
		metadata.setAggregateId("Usina01");
		
		eventMessage.setMetadata(metadata);
		
		dispService.handleDisponibilidadesCalculadasEvent(eventMessage);

	}
    
    
}
