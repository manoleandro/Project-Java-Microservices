package br.org.ons.sager.agendamento.web.rest;

import static com.querydsl.collections.CollQueryFactory.from;
import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.ComentarioSituacao.TipoObjeto;
import br.org.ons.sager.agendamento.domain.AgendamentoCalculo;
import br.org.ons.sager.agendamento.domain.AgendamentoCenario;
import br.org.ons.sager.agendamento.domain.AgendamentoRetificacao;
import br.org.ons.sager.agendamento.domain.VerificaInstalacaoResponse;
import br.org.ons.sager.agendamento.service.AgendamentoService;
import br.org.ons.sager.agendamento.web.rest.util.HeaderUtil;
import br.org.ons.sager.agendamento.web.rest.util.PaginationUtil;
import br.org.ons.sager.dto.UsinaDTO;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoResponse;

/**
 * API REST para as telas e diretivas Manter Agendamento
 */
@Component
@RestController
@RequestMapping("/api")
public class AgendamentoResource {

	private final Logger log = LoggerFactory.getLogger(AgendamentoResource.class);
	
	private AgendamentoService agendamentoService;
	
	 @Inject
    public AgendamentoResource(AgendamentoService agendamentoService) {
		this.agendamentoService = agendamentoService;
	}
	
    /**
     * POST  /agendamentos-calculo : Create a new schedule.
     *
     * @param scheduleDTO the scheduleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scheduleDTO, or with status 400 (Bad Request) if the schedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
	 
    @RequestMapping(value = "/agendamentos-calculo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public synchronized ResponseEntity<AgendamentoCalculo> createAgendamentosCalculo(@RequestBody AgendamentoCalculo agendamentoCalculo, @RequestParam String acao) throws URISyntaxException {
        log.debug("REST request to create AgendamentoCalculo : {}", agendamentoCalculo.toString());
        AgendamentoCalculo result;
        
        switch (acao) {
		case "CANCELAR":
			agendamentoService.cancelarAgendamentoCalculoTaxa(agendamentoCalculo);
			break;
		case "REAGENDAR":
			agendamentoService.reagendarAgendamentoCalculoTaxa(agendamentoCalculo);
			break;
		case "SALVAR":
			result = agendamentoService.salvarAgendamentoCalculoTaxa(agendamentoCalculo);
			return ResponseEntity.created(new URI("/api/agendamentos-calculo/" + result.getId()))
		            .headers(HeaderUtil.createEntityCreationAlert("agendamentos-calculo", result.getId()))
		            .body(result);
		default:
			break;
		}

        return null;
    }
    @RequestMapping(value = "/agendamentos-calculo-by-instalacao", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE) 
    public List<AgendamentoCalculo> getAllAgendamentosCalculoByInstalacao(@RequestBody List<UsinaDTO> instalacoes) {
        log.debug("REST request to get all AgendamentosCalculo By Instalacao");
        
        return agendamentoService.getAllAgendamentosCalculoByInstalacao(instalacoes);
    }
	
    @RequestMapping(value = "/agendamentos-calculo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AgendamentoCalculo> getAllAgendamentosCalculo() {
        log.debug("REST request to get all AgendamentosCalculo");
    	return agendamentoService.getAllAgendamentosCalculo();
    }

    @RequestMapping(value = "/all-agendamentos-calculo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AgendamentoCalculo>> getAllAgendamentosCalculoPageble(Pageable pageable) throws URISyntaxException {
    	log.debug("REST request to get all AgendamentosCalculo pageable");
    	
    	Page <AgendamentoCalculo> page = agendamentoService.getAllAgendamentosCalculoPageble(pageable);
    	
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/all-agendamentos-calculo");
    	return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/agendamentos-calculo-by-date", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AgendamentoCalculo>> getAllAgendamentosCalculo(
    		@RequestBody List<UsinaDTO> instid,
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtInicio,
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtFim,
			Pageable pageable) throws URISyntaxException {
    	log.debug("REST request to AgendamentosCalculo by inst and date");
    	dtInicio = dtInicio.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
    	dtFim = dtFim.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
    	Page <AgendamentoCalculo> page = agendamentoService.getAllAgendamentosCalculo(instid, dtInicio, dtFim, pageable);
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agendamentos-calculo-by-date");
        
    	return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/agendamentos-retificacao", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AgendamentoRetificacao> getAllAgendamentosRetificacao() {
        log.debug("REST request to get all AgendamentosRetificacao");
    	return agendamentoService.getAllAgendamentosRetificacao();
    }
    
    @RequestMapping(value = "/agendamentos-retificacoes-by-date", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AgendamentoRetificacao>> getAgendamentosRetificacaoByDate(
    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtInicio,
    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtFim,
    		Pageable pageable) throws URISyntaxException {
    	
    	log.debug("REST request to AgendamentoRetificacoes by date");
    	
    	dtInicio = dtInicio.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
    	dtFim = dtFim.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
    	
    	Page<AgendamentoRetificacao> page = agendamentoService.getAllAgendamentosRetificacaoByDate(dtInicio, dtFim, pageable);
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agendamentos-retificacao-by-date");
    	
    	return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/agendamentos-cenario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) 
    public List<AgendamentoCenario> getAllAgendamentosCenario() {
        log.debug("REST request to get all AgendamentosCenario");
        
    	return agendamentoService.getAllAgendamentosCenario();
    }   
    
    @RequestMapping(value = "/agendamentos-cenario-by-date", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AgendamentoCenario>> getAgendamentosCenarioByDate(
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtInicio,
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtFim,
			Pageable pageable) throws URISyntaxException {
    	log.debug("REST request to AgendamentosCenario by date");
    	
    	dtInicio = dtInicio.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
    	dtFim = dtFim.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
    	
    	Page <AgendamentoCenario> page = agendamentoService.getAgendamentosCenarioByDate(dtInicio, dtFim, pageable);
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agendamentos-cenario-by-date");
        
    	return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/agendamentos-by-protocolo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgendamentoCalculo> getAllAgendamentosCalculoByProtocolo(@RequestParam String protocoloStr) throws URISyntaxException {
    	log.debug("REST request to AgendamentosCalculo by inst and date");
    	AgendamentoCalculo agendamento = agendamentoService.getAgendamentosCalculoByProtocolo(protocoloStr);
        
    	return new ResponseEntity<>(agendamento, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/agendamentos-verificarSituacaoInstalacao", 
    		method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VerificaInstalacaoResponse> verificarSituacaoInstalacao(@RequestBody List<UsinaDTO> usinas,
    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtInicio,
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtFim){

    	List<VerificaInstalacaoResponse> verifInstoesResp = new ArrayList<>(); 
    	for(UsinaDTO usinaDTO : usinas){
    		
    		VerificaInstalacaoResponse verifInstResp = new VerificaInstalacaoResponse();
    		verifInstResp.setUsina(usinaDTO);
    		
    		VerificarSituacaoInstalacaoResponse verificarSituacaoResponse = 
    		agendamentoService.verificarSituacaoInstalacao(usinaDTO, dtInicio, dtFim);
    		
    		//Não proceguir, então recuperar os comentários
    		if (verificarSituacaoResponse != null && !verificarSituacaoResponse.isProssegue() ){
    			//Filtrar somente os comentários do tipo Instalacao
    			ComentarioSituacao qComentario = alias(ComentarioSituacao.class, "comentario");
				List<ComentarioSituacao> comentarios = from($(qComentario), verificarSituacaoResponse.getComentarios())
						.where($(qComentario.getTipoObjeto()).eq(TipoObjeto.USINA).
								or($(qComentario.getTipoObjeto()).eq(TipoObjeto.INTERLIGACAO_INTERNACIONAL)))
				.fetch();
				
				//adiciona os comentarios do tipo instalacao a lista 
				verifInstResp.setComentarios(comentarios);
    		}
    		verifInstoesResp.add(verifInstResp);
    	}

    	return verifInstoesResp;
    }
 
}
