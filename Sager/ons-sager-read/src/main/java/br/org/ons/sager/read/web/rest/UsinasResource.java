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

import br.org.ons.sager.read.domain.Usinas;
import br.org.ons.sager.read.service.UsinasService;
import br.org.ons.sager.read.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Usinas.
 */
@RestController
@RequestMapping("/api")
public class UsinasResource {

    private final Logger log = LoggerFactory.getLogger(UsinasResource.class);
        
    private UsinasService usinasService;

    
    @Inject
    public UsinasResource(UsinasService usinasService) {
		super();
		this.usinasService = usinasService;
	}

	/**
     * POST  /taxa-refs : Create a new usinas.
     *
     * @param usinas the usinas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usinas, or with status 400 (Bad Request) if the usinas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
//    @RequestMapping(value = "/usinas",
//        method = RequestMethod.POST,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Usinas> createUsinas(@RequestBody Usinas usinas) throws URISyntaxException {
//        log.debug("REST request to save Usinas : {}", usinas);
//        if (usinas.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("usinas", "idexists", "A new usinas cannot already have an ID")).body(null);
//        }
//        Usinas result = usinasService.save(usinas);
//        return ResponseEntity.created(new URI("/api/taxa-refs/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert("usinas", result.getId().toString()))
//            .body(result);
//    }

    /**
     * PUT  /taxa-refs : Updates an existing usinas.
     *
     * @param usinas the usinas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usinas,
     * or with status 400 (Bad Request) if the usinas is not valid,
     * or with status 500 (Internal Server Error) if the usinas couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
//    @RequestMapping(value = "/usinas",
//        method = RequestMethod.PUT,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Usinas> updateUsinas(@RequestBody Usinas usinas) throws URISyntaxException {
//        log.debug("REST request to update Usinas : {}", usinas);
//        if (usinas.getId() == null) {
//            return createUsinas(usinas);
//        }
//        Usinas result = usinasService.save(usinas);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert("usinas", usinas.getId().toString()))
//            .body(result);
//    }

    /**
     * GET  /taxa-refs : get all the usinass.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of usinass in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/usinas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Usinas>> getAllUsinass()
        throws URISyntaxException {
        log.debug("REST request to get a page of Usinass");
        List<Usinas> list = usinasService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * GET  /taxa-refs/:id : get the "id" usinas.
     *
     * @param id the id of the usinas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usinas, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/usinas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usinas> getUsinas(@PathVariable String id) {
        log.debug("REST request to get Usinas : {}", id);
        Usinas usinas = usinasService.findOne(id);
        return Optional.ofNullable(usinas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxa-refs/:id : delete the "id" usinas.
     *
     * @param id the id of the usinas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
//    @RequestMapping(value = "/usinas/{id}",
//        method = RequestMethod.DELETE,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> deleteUsinas(@PathVariable String id) {
//        log.debug("REST request to delete Usinas : {}", id);
//        usinasService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("usinas", id.toString())).build();
//    }

}
