package br.org.ons.sager.read.web.rest;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.sager.read.domain.Interligacoes;
import br.org.ons.sager.read.service.InterligacoesService;
import br.org.ons.sager.read.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Interligacoes.
 */
@RestController
@RequestMapping("/api")
public class InterligacoesResource {

    private final Logger log = LoggerFactory.getLogger(InterligacoesResource.class);
        
    
    private InterligacoesService interligacoesService;


    @Inject
	public InterligacoesResource(InterligacoesService interligacoesService) {
		super();
		this.interligacoesService = interligacoesService;
	}

//	/**
//     * POST  /interligacoes : Create a new interligacoes.
//     *
//     * @param interligacoes the interligacoes to create
//     * @return the ResponseEntity with status 201 (Created) and with body the new interligacoes, or with status 400 (Bad Request) if the interligacoes has already an ID
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @RequestMapping(value = "/interligacoes",
//        method = RequestMethod.POST,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Interligacoes> createInterligacoes(@RequestBody Interligacoes interligacoes) throws URISyntaxException {
//        log.debug("REST request to save Interligacoes : {}", interligacoes);
//        if (interligacoes.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("interligacoes", "idexists", "A new interligacoes cannot already have an ID")).body(null);
//        }
//        Interligacoes result = interligacoesService.save(interligacoes);
//        return ResponseEntity.created(new URI("/api/interligacoes/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert("interligacoes", result.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * PUT  /interligacoes : Updates an existing interligacoes.
//     *
//     * @param interligacoes the interligacoes to update
//     * @return the ResponseEntity with status 200 (OK) and with body the updated interligacoes,
//     * or with status 400 (Bad Request) if the interligacoes is not valid,
//     * or with status 500 (Internal Server Error) if the interligacoes couldnt be updated
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @RequestMapping(value = "/interligacoes",
//        method = RequestMethod.PUT,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Interligacoes> updateInterligacoes(@RequestBody Interligacoes interligacoes) throws URISyntaxException {
//        log.debug("REST request to update Interligacoes : {}", interligacoes);
//        if (interligacoes.getId() == null) {
//            return createInterligacoes(interligacoes);
//        }
//        Interligacoes result = interligacoesService.save(interligacoes);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert("interligacoes", interligacoes.getId().toString()))
//            .body(result);
//    }

    /**
     * GET  /interligacoes : get all the interligacoess.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of interligacoess in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/interligacoes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Interligacoes>> getAllInterligacoess()
        throws URISyntaxException {
        log.debug("REST request to get a page of Interligacoess");
        List<Interligacoes> list = interligacoesService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * GET  /interligacoes/:id : get the "id" interligacoes.
     *
     * @param id the id of the interligacoes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the interligacoes, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/interligacoes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Interligacoes> getInterligacoes(@PathVariable String id) {
        log.debug("REST request to get Interligacoes : {}", id);
        Interligacoes interligacoes = interligacoesService.findOne(id);
        return Optional.ofNullable(interligacoes)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    /**
//     * DELETE  /interligacoes/:id : delete the "id" interligacoes.
//     *
//     * @param id the id of the interligacoes to delete
//     * @return the ResponseEntity with status 200 (OK)
//     */
//    @RequestMapping(value = "/interligacoes/{id}",
//        method = RequestMethod.DELETE,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> deleteInterligacoes(@PathVariable String id) {
//        log.debug("REST request to delete Interligacoes : {}", id);
//        interligacoesService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("interligacoes", id)).build();
//    }

}
