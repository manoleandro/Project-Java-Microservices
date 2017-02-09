package br.org.ons.sager.read.web.rest;

import static com.querydsl.collections.CollQueryFactory.from;
import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.sager.read.domain.InstalacaoTaxas;
import br.org.ons.sager.read.domain.QInstalacaoTaxas;
import br.org.ons.sager.read.domain.QTaxa;
import br.org.ons.sager.read.domain.Taxa;
import br.org.ons.sager.read.repository.InstalacaoTaxasRepository;
import br.org.ons.sager.read.web.rest.util.HeaderUtil;
import br.org.ons.sager.read.web.rest.util.JavaUtil;

/**
 * REST controller for managing InstalacaoTaxas.
 */
@RestController
@RequestMapping("/api")
public class InstalacaoTaxasResource {

    private final Logger log = LoggerFactory.getLogger(InstalacaoTaxasResource.class);
        
    @Inject
    private InstalacaoTaxasRepository instalacaoTaxasRepository;

    private final InstalacaoTaxas qInstalacaoTaxas = alias(InstalacaoTaxas.class, "instalacaoTaxas");
    
    private final String SCENARIO_PRINCIPAL = "Principal";

    /**
     * POST  /instalacaoTaxas : Create a new instalacaoTaxas.
     *
     * @param instalacaoTaxas the instalacaoTaxas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new instalacaoTaxas, or with status 400 (Bad Request) if the instalacaoTaxas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/instalacaoTaxas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstalacaoTaxas> createInstalacaoTaxas(@RequestBody InstalacaoTaxas instalacaoTaxas) throws URISyntaxException {
        log.debug("REST request to save InstalacaoTaxas : {}", instalacaoTaxas);
        if (instalacaoTaxas.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("instalacaoTaxas", "idexists", "A new instalacaoTaxas cannot already have an ID")).body(null);
        }
        InstalacaoTaxas result = instalacaoTaxasRepository.save(instalacaoTaxas);
        return ResponseEntity.created(new URI("/api/instalacaoTaxas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instalacaoTaxas", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instalacaoTaxas : Updates an existing instalacaoTaxas.
     *
     * @param instalacaoTaxas the instalacaoTaxas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated instalacaoTaxas,
     * or with status 400 (Bad Request) if the instalacaoTaxas is not valid,
     * or with status 500 (Internal Server Error) if the instalacaoTaxas couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/instalacaoTaxas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstalacaoTaxas> updateInstalacaoTaxas(@RequestBody InstalacaoTaxas instalacaoTaxas) throws URISyntaxException {
        log.debug("REST request to update InstalacaoTaxas : {}", instalacaoTaxas);
        if (instalacaoTaxas.getId() == null) {
            return createInstalacaoTaxas(instalacaoTaxas);
        }
        InstalacaoTaxas result = instalacaoTaxasRepository.save(instalacaoTaxas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instalacaoTaxas", instalacaoTaxas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instalacaoTaxas : get all the instalacaoTaxas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of InstalacaoTaxas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/instalacaoTaxas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InstalacaoTaxas>> getAllInstalacaoTaxas()
        throws URISyntaxException {
        log.debug("REST request to get a page of instalacaoTaxas");
        List<InstalacaoTaxas> list = IterableConverter.toList(instalacaoTaxasRepository.findAll());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    /**
     * GET  /instalacaoTaxas : get all the instalacaoTaxas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of InstalacaoTaxas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/instalacaoTaxasByInstalacaoPeriodo",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Taxa>> getAllInstalacaoTaxas(@RequestBody List<String> instalacoes, 
    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dataInicio, 
    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dataFim)
    		throws URISyntaxException {
        log.debug("REST request to get a page of instalacaoTaxas");
        QInstalacaoTaxas qInstTxMensais = new QInstalacaoTaxas("instalacaoTaxas");
                    
        //OBS: Retornando Null pois não carrega o taxas.any()
        // https://github.com/querydsl/querydsl/issues/1969
        List<InstalacaoTaxas> listByInstalacao = IterableConverter.toList(
        		instalacaoTaxasRepository.findAll(qInstTxMensais.instalacao.in(instalacoes)));
        
        List<InstalacaoTaxas> list = from($(qInstalacaoTaxas),listByInstalacao).
        where(qInstTxMensais.taxas.any().dataApuracao.between(
        		Date.from(dataInicio.toInstant())
        	  , Date.from(dataFim.toInstant()))).
        fetchResults().getResults();        
        
        //Retorna a lista de Taxas e nao a InstalacaoTaxa
        List<Taxa> listTaxa = new ArrayList<>();
        for(InstalacaoTaxas itm : JavaUtil.emptyIfNull(list)){
        	
        	//Ordena a lista de taxas
        	List<Taxa> taxasOrdenadas = itm.getTaxas();
        	Collections.sort(taxasOrdenadas, new Comparator<Taxa>() {
    			@Override
    			public int compare(Taxa t1, Taxa t2) {
    				//Se somente a Primeira taxa for scenarioPrincipal
    				if(SCENARIO_PRINCIPAL.equalsIgnoreCase(t1.getNomeCenario()) &&
					   !SCENARIO_PRINCIPAL.equalsIgnoreCase(t2.getNomeCenario())){
    					return -1;
    					//Se somente a Segunda taxa for scenarioPrincial
    				}else if(!SCENARIO_PRINCIPAL.equalsIgnoreCase(t1.getNomeCenario()) && 
    						SCENARIO_PRINCIPAL.equalsIgnoreCase(t2.getNomeCenario())){
    					return 1;
    					//Se os dois cenários forem princial
    				}else if(SCENARIO_PRINCIPAL.equalsIgnoreCase(t1.getNomeCenario()) && 
    						SCENARIO_PRINCIPAL.equalsIgnoreCase(t2.getNomeCenario())){
    					//Verifica por versão
    					return t2.getVersaoTaxa() - t1.getVersaoTaxa();
    					//Se os dois scenarios forem diferentes do Principal
    				}else{
    					//Se os scenarios forem iguais ordena por versao taxa
    					if(t1.getNomeCenario().equalsIgnoreCase(t2.getNomeCenario())){
    						return t2.getVersaoTaxa() - t1.getVersaoTaxa();
    					}else{
    						//Se os scenarios forem diferentes, ordena por nomeScenario
    						return t1.getNomeCenario().compareTo(t2.getNomeCenario());
    					}
    				}
    			}
    		});
        	listTaxa.addAll(taxasOrdenadas);
	
        }
        
    	//Ordenar por Data (independente da instalacao)
    	QTaxa qTaxa = new QTaxa("taxa");
    	List<Taxa> taxas = from(qTaxa,listTaxa).
    			orderBy(qTaxa.dataApuracao.desc())
        .fetchResults().getResults();
    	
        return new ResponseEntity<>(taxas, HttpStatus.OK);
    }    
    
    
    
    @RequestMapping(value = "/taxaByIdJob",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<Taxa>> taxaByIdJob(@RequestParam String jobId)
        		throws URISyntaxException {
            log.debug("REST request to get a page of instalacaoTaxas");
            QInstalacaoTaxas qInstTxMensais = new QInstalacaoTaxas("instalacaoTaxas");
            System.out.println("JobID");
            System.out.println(jobId);
            //Buscando Instalção taxa pelo JobId do cálculo-------falta trocar por JobId
            try{
        	List<InstalacaoTaxas> listByInstalacao = IterableConverter.toList(
        
        		instalacaoTaxasRepository.findAll());
        
        	List<InstalacaoTaxas> list = from($(qInstalacaoTaxas),listByInstalacao).
        	        where(qInstTxMensais.taxas.any().jobId.eq(jobId)).
        	        fetchResults().getResults(); 
        	
            //Buscando 
            List<Taxa> listTaxa = new ArrayList<Taxa>();
            Taxa taxaJob = new Taxa();
            if(listByInstalacao != null){
    	        for(InstalacaoTaxas itm : list){
    	        	List<Taxa> taxasList = itm.getTaxas();
    	        	for(Taxa tax : taxasList){
    	        		if(tax.getJobId().equals(jobId)){
    	        			taxaJob = tax;
    	        			listTaxa.add(tax);
    	        		}
    	        	}

    	        }
            }
            
            System.out.println("Executou sem erro");
            return new ResponseEntity<>(listTaxa, HttpStatus.OK);
            }catch(Exception e){
            	System.out.println("Error"+e.getMessage());
            	return new ResponseEntity<>( HttpStatus.OK);
            }
            
            
        }   
    
    

    /**
     * GET  /instalacaoTaxas/:id : get the "id" instalacaoTaxas.
     *
     * @param id the id of the instalacaoTaxas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the instalacaoTaxas, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/instalacaoTaxas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstalacaoTaxas> getInstalacaoTaxa(@PathVariable String id) {
        log.debug("REST request to get InstalacaoTaxas : {}", id);
        InstalacaoTaxas instalacaoTaxas = instalacaoTaxasRepository.findOne(id);
        return Optional.ofNullable(instalacaoTaxas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instalacaoTaxas/:id : delete the "id" instalacaoTaxas.
     *
     * @param id the id of the instalacaoTaxas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/instalacaoTaxas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteInstalacaoTaxa(@PathVariable String id) {
        log.debug("REST request to delete InstalacaoTaxas : {}", id);
        instalacaoTaxasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instalacaoTaxas", id.toString())).build();
    }

}
