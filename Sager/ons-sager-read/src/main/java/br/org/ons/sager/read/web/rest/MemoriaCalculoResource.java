package br.org.ons.sager.read.web.rest;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.sager.read.domain.MCEquipamentoParametro;
import br.org.ons.sager.read.domain.QMCEquipamentoParametro;
import br.org.ons.sager.read.domain.UGParam;
import br.org.ons.sager.read.repository.MCEquipamentoParametroRepository;

/**
 * REST controller for managing mcEquipamentoParametro.
 */
@RestController
@RequestMapping("/api")
public class MemoriaCalculoResource {

    private final Logger log = LoggerFactory.getLogger(MemoriaCalculoResource.class);
        
    @Inject
    private MCEquipamentoParametroRepository mcEquipParamRepository;
    
	public MemoriaCalculoResource(MCEquipamentoParametroRepository mcEquipParamRepository) {
		super();
		this.mcEquipParamRepository = mcEquipParamRepository;
	}

    @RequestMapping(value = "/memoriacalculo",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UGParam>> getMemoriacalculo(@RequestParam String instalacao,
    		@RequestParam int versaoTaxa,
    		@RequestParam int versaoCenario,
    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dataApuracao, 
    		@RequestParam String taxaMemoriaCalculo)
        throws URISyntaxException {
        log.debug("getMemoriacalculo (/memoriacalculo) instalacao:["+instalacao+"] "
        		+ "Taxa:["+taxaMemoriaCalculo+"] Data:["+dataApuracao+"]");
        
        QMCEquipamentoParametro qMCequipParam = new QMCEquipamentoParametro("mCEquipamentoParametro");
        MCEquipamentoParametro mcEquipParam = mcEquipParamRepository.findOne(
        		qMCequipParam.versaoCenario.eq(versaoCenario).and(
        		qMCequipParam.versaoTaxa.eq(versaoTaxa).and(
				qMCequipParam.instalacao.eq(instalacao).and(
				qMCequipParam.dataApuracao.eq(Date.from(dataApuracao.toInstant())).and(
				qMCequipParam.taxaMemoriaCalculo.equalsIgnoreCase(taxaMemoriaCalculo))))));
        
        List<UGParam> list = new ArrayList<>();
        if(mcEquipParam != null){
        	list = mcEquipParam.getMemoriaCalculo();
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

	@RequestMapping(value = "/mcequipamentoParametro",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MCEquipamentoParametro>> getAllMCEquipamentoParametro()
        throws URISyntaxException {
        List<MCEquipamentoParametro> list = IterableConverter.toList(mcEquipParamRepository.findAll());
        return new ResponseEntity<>(list, HttpStatus.OK);
	}
        

}
