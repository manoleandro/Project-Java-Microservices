package br.org.ons.sager.read.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.sager.read.domain.TaxaRef;
import br.org.ons.sager.read.service.TaxaRefService;

/**
 * REST controller for managing TaxaRef.
 */
@RestController
@RequestMapping("/api")
public class TaxaRefResource {

    private final Logger log = LoggerFactory.getLogger(TaxaRefResource.class);
        
    
    private TaxaRefService taxaRefService;


    @Inject
	public TaxaRefResource(TaxaRefService taxaRefService) {
		super();
		this.taxaRefService = taxaRefService;
	}

    /**
     * GET  /taxa-refs : get all the taxaRefs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxaRefs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/taxa-refs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaxaRef>> getAllTaxaRefs() throws URISyntaxException {
        List<TaxaRef> list = taxaRefService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK); 	
    }
    
    @RequestMapping(value = "/taxaReferenciaByTipoInstalacao",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaxaRef>> getAllTaxaRefsByTipoInstalacao(@RequestParam String tipoInstalacao) 
    		throws URISyntaxException {
        List<TaxaRef> list = taxaRefService.getAllTaxaRefsByTipoInstalacao(tipoInstalacao);
        return new ResponseEntity<>(list, HttpStatus.OK); 	
    }   

    /**
     * GET  /taxa-refs/:id : get the "id" taxaRef.
     *
     * @param id the id of the taxaRef to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxaRef, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/taxa-refs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaxaRef> getTaxaRef(@PathVariable String id) {
        log.debug("REST request to get TaxaRef : {}", id);
        TaxaRef taxaRef = taxaRefService.findOne(id);
        return Optional.ofNullable(taxaRef)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(value="/taxa-refs/excel", method = RequestMethod.POST, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> getExcel() {
    	log.debug("Resquest generate Excel for All Taxa Ref");
    	
    	byte[] generateExcel = taxaRefService.generateExcel();
    	HttpHeaders header = new HttpHeaders();
    	header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=TaxasReferencia.xlsx");
        header.setContentLength(generateExcel.length);
        header.add("Content-Transfer-Encoding", "binary");
    	System.out.println("Resposta excelGeral:  " + generateExcel.length + " " + generateExcel.toString());
    	
    	return new ResponseEntity<>(generateExcel, header, HttpStatus.OK);
    }

}
