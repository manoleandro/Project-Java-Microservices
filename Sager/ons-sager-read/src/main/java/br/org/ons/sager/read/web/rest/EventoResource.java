
package br.org.ons.sager.read.web.rest;

import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.sager.read.service.EventoService;
import br.org.ons.sager.read.web.rest.dto.EventoDTO;
import br.org.ons.sager.dto.UsinaDTO;
import br.org.ons.sager.read.web.rest.errors.ErrorDTO;
import br.org.ons.sager.read.web.rest.exceptions.BadRequestException;

/**
 * REST controller for managing Evento.
 */
@RestController
@RequestMapping("/api")
public class EventoResource {

	private final Logger log = LoggerFactory.getLogger(DispResource.class);
    
    
    private EventoService eventoService;
    
//    @RequestMapping(value = "/eventos",
//    		method = RequestMethod.GET,
//    		produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Evento>> getAllEventos(Pageable pageable) throws URISyntaxException{
//        Page<Evento> page = eventoService.findAll(null, pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eventos");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
    
    /**
     * GET  /eventos/:id : get an Evento by id.
     *
     * @param id the id of the entity to get
     * @return the ResponseEntity with status 200 (OK) and the AuditEvent in body, or status 404 (Not Found)
     * @throws URISyntaxException 
     */
    @RequestMapping(value = "/eventos",
        method = RequestMethod.PUT)
    public ResponseEntity<List<EventoDTO>> get(
    		@RequestParam(required = false) String[] ids, 
    		@RequestBody List<UsinaDTO> instalacoes,
    		Pageable pageable) throws URISyntaxException {
    	List<EventoDTO> page = eventoService.findById(ids, instalacoes, pageable);
    	
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eventos");
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    
    @Inject
    public EventoResource(EventoService eventoService) {
	super();
	this.eventoService = eventoService;
}


	@RequestMapping(value = "/eventos",
    		method = RequestMethod.POST,
    		produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<EventoDTO>> getEventosByInstalacao(
    		@RequestBody List<UsinaDTO> instalacoes,
    		@RequestParam (required = true)@DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtInicio,
    		@RequestParam (required = true)@DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtFim) throws URISyntaxException{
    	log.debug("REST request for get Eventos By Instalacao: " );
    	if(dtInicio != null && dtFim != null){
        	dtInicio = dtInicio.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
        	dtFim = dtFim.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
        	List<EventoDTO> page = eventoService.findAll(dtInicio, dtFim, instalacoes, null);
        	
        	return new ResponseEntity<>(page, HttpStatus.OK);
        	
    	}else{
    		
    		throw new BadRequestException("Erro ao chamar", "RN_XXXX_XX");
    		
    	}

    }
    
	
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorDTO handleError(BadRequestException ex) {
    	log.error(" raised " + ex);

    	ErrorDTO err = new ErrorDTO(ex.getLocalizedMessage(), ex.getDesc());
    	return err;
    }
}

