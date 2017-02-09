
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

import com.jayway.jsonpath.JsonPath;

import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.OrigemComentario;
import br.org.ons.geracao.evento.StatusEvento;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.evento.ComentarioSituacao.StatusObjeto;
import br.org.ons.geracao.evento.ComentarioSituacao.TipoObjeto;
import br.org.ons.geracao.evento.taxa.ParametroTaxa;
import br.org.ons.geracao.modelagem.PeriodoApuracao;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.sager.instalacao.event.DisponibilidadesCalculadasEvent;
import br.org.ons.sager.instalacao.event.EventosApuradosEvent;
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
public class EventoResourceHandlerTest {
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

	}
	
	@Test
	public void buscarEventoSemId() throws Exception {
		Date data = new Date("2016/11/28 03:00:00");

			EventoMudancaEstadoOperativo evento = new EventoMudancaEstadoOperativo();
				evento.setId("ID01");
				evento.setVersion("version01");
				evento.setIdInstalacao("idInstalacao01");
				evento.setIdEquipamento("idEquipamento01");
				evento.setDataVerificada(data);
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
					evento.setComentarios(comentarios);
					evento.setJustificativaRestricaoDesligamento("justificativaDesliagamento");
					evento.setStatus(StatusEvento.RETIFICADO_REMOVIDO);
					evento.setDataUltimaConsolidacao(data);
					evento.setValorPotenciaDisponivel(12.30);
					evento.setEstadoOperativo("estadoOperativo");
					evento.setCondicaoOperativa("CONDICAO");
				
			List<EventoMudancaEstadoOperativo> eventos = new ArrayList<>();
				eventos.add(evento);
				
			PeriodoApuracao apuracao = new PeriodoApuracao();
			apuracao.setDataFim(data);
			apuracao.setDataInicio(data);
			apuracao.setEventos(eventos);
			
		EventosApuradosEvent eventosApurados = new EventosApuradosEvent();
			eventosApurados.setApuracao(apuracao);
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("Usina01");
		
		EventMessage<EventosApuradosEvent> eventMessage = new EventMessage<>();
			eventMessage.setEvent(eventosApurados);
			eventMessage.setMetadata(metadata);
		
		eventoService.handleEventosApuradosEvent(eventMessage);

	}

}

