package br.org.ons.sager.parametrizacao.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.sager.exception.BadRequestException;
import br.org.ons.sager.exception.NotFoundException;
import br.org.ons.sager.parametrizacao.domain.RetificacoesParam;
import br.org.ons.sager.parametrizacao.service.RetificacoesParamService;
import br.org.ons.sager.parametrizacao.web.rest.errors.ErrorDTO;
import br.org.ons.sager.parametrizacao.web.rest.util.HeaderUtil;


/**
 * REST controller for managing Parametrizacoes.
 */
@RestController
@RequestMapping("/api")
public class ParametrizacaoResource {

	 private final Logger log = LoggerFactory.getLogger(ParametrizacaoResource.class);
     
	    private RetificacoesParamService retificacoesParamService;
	    private static final String ENTITY_NAME = "retificacoesParam";
	    private static final String ERROR_KEY_ID_EXISTS = "idexists";
	    private static final String ERROR_MESSAGE_ID_EXISTS = "A new retificacoesParam cannot already have an ID";
	    
	    @Inject
	    public ParametrizacaoResource(RetificacoesParamService retificacoesParamService) {
			this.retificacoesParamService = retificacoesParamService;
		}
	    
	    
		/**
	     * POST  /parametrizacao-retificacoes : Create a new retificacaoParam.
	     *
	     * @param RetificacaoParam the RetificacaoParam to create
	     * @return the ResponseEntity with status 201 (Created) and with body the new RetificacaoParam, or with status 400 (Bad Request) if the RetificacaoParam has already an ID
	     * @throws URISyntaxException if the Location URI syntax is incorrect
		 * @throws BadRequestException 
	     */
	    @RequestMapping(value = "/parametrizacao-retificacoes",
	        method = RequestMethod.POST,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<RetificacoesParam> createRetificacaoParam(@RequestBody RetificacoesParam retificacoesParam) throws URISyntaxException, BadRequestException {
	        log.debug("REST request to save RetificacoesParam : {}", retificacoesParam);
	        
	        if (retificacoesParam.getId() != null) {
	        	throw new BadRequestException(ERROR_KEY_ID_EXISTS, ERROR_MESSAGE_ID_EXISTS);
	        }
	        
	        RetificacoesParam result;
			result = retificacoesParamService.save(retificacoesParam);
			return ResponseEntity.created(new URI("/api/parametrizacao-retificacoes/" + result.getId()))
					.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
					.body(result);
	    }
	    
	    /**
	     * PUT  /parametrizacao-retificacoes : Updates an existing retificacaoParam.
	     *
	     * @param retificacaoParam the retificacaoParam to update
	     * @return the ResponseEntity with status 200 (OK) and with body the updated retificacaoParam,
	     * or with status 400 (Bad Request) if the retificacaoParam is not valid,
	     * or with status 500 (Internal Server Error) if the retificacaoParam couldnt be updated
	     * @throws URISyntaxException if the Location URI syntax is incorrect
	     * @throws BadRequestException 
	     */
	    @RequestMapping(value = "/parametrizacao-retificacoes",
	        method = RequestMethod.PUT,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<RetificacoesParam> updateRetificacoesParam(@RequestBody RetificacoesParam retificacoesParam) throws URISyntaxException, BadRequestException {
	        log.debug("REST request to update RetificacoesParam : {}", retificacoesParam);
	        if (retificacoesParam.getId() == null || retificacoesParamService.findOne(retificacoesParam.getId()) == null ) {
	        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        
	        RetificacoesParam result;
				result = retificacoesParamService.save(retificacoesParam);
				return ResponseEntity.ok()
						.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, retificacoesParam.getId().toString()))
						.body(result);
	    }

	    /**
	     * GET  /parametrizacao-retificacoes : get all the retificacaoParam.
	     *
	     * @param pageable the pagination information
	     * @return the ResponseEntity with status 200 (OK) and the list of retificacaoParam in body
	     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
	     */
	    @RequestMapping(value = "/parametrizacao-retificacoes",
	        method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<List<RetificacoesParam>> getAllRetificacoesParam()
	        throws URISyntaxException {
	        log.debug("REST request to get a RetificacoesParam");
	        List<RetificacoesParam> list = retificacoesParamService.findAll();
	        return new ResponseEntity<>(list, HttpStatus.OK);
	    }
	    
	    /**
	     * GET  /parametrizacao-retificacoes/:id : get the "id" retificacaoParam.
	     *
	     * @param id the id of the retificacaoParam to retrieve
	     * @return the ResponseEntity with status 200 (OK) and with body the retificacaoParam, or with status 404 (Not Found)
	     */
	    @RequestMapping(value = "/parametrizacao-retificacoes/{id}",
	        method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<RetificacoesParam> getRetificacoesParam(@PathVariable String id) {
	        log.debug("REST request to get RetificacoesParam : {}", id);
	        RetificacoesParam usinas = retificacoesParamService.findOne(id);
	        return Optional.ofNullable(usinas)
	            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
	            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }
	    
	    /**
	     * DELETE  /parametrizacao-retificacoes/:id : delete the "id" usinas.
	     *
	     * @param id the id of the usinas to delete
	     * @return the ResponseEntity with status 200 (OK)
	     * @throws BadRequestException 
	     * @throws NotFoundException 
	     */
	    @RequestMapping(value = "/parametrizacao-retificacoes/{id}",
	        method = RequestMethod.DELETE,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Void> deleteRetificacoesParam(@PathVariable String id) throws NotFoundException, BadRequestException {
	        log.debug("REST request to delete RetificacoesParam : {}", id);
	        
			retificacoesParamService.delete(id);
			return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
	    }
	    
	    
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(BadRequestException.class)
	    public ErrorDTO handleError(BadRequestException ex) {
	    	log.error(" raised " + ex);

	    	return new ErrorDTO(ex.getMessage(), ex.getDesc());
	    }
	    
	    @ResponseStatus(HttpStatus.NOT_FOUND)
	    @ExceptionHandler(NotFoundException.class)
	    public ErrorDTO handleNotFoundError(NotFoundException ex) {
	    	log.error(" raised " + ex);

	    	return new ErrorDTO(ex.getMessage(), ex.getDesc());
	    }
}

