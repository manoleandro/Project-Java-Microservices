package br.org.ons.sager.read.web.rest;

import java.time.ZonedDateTime;
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

import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CommandModification;
import br.org.ons.platform.common.ReplayCommand;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.sager.read.config.mq.CommandBus;
import br.org.ons.sager.read.domain.MCEquipamentoParametro;
import br.org.ons.sager.read.domain.MemoriasCalculo;
import br.org.ons.sager.read.domain.QMCEquipamentoParametro;
import br.org.ons.sager.read.domain.QReprodutibilidade;
import br.org.ons.sager.read.domain.Reprodutibilidade;
import br.org.ons.sager.read.repository.MCEquipamentoParametroRepository;
import br.org.ons.sager.read.repository.ReprodutibilidadeRepository;

@RestController
@RequestMapping("/api")
public class TaxaResource {
	
	@Inject
	private MCEquipamentoParametroRepository mcEquipParamRepository;
	
	@Inject
	private ReprodutibilidadeRepository repordutibilidadeRepository;
	
	@Inject
	private CommandBus commandBus; 
	
	private final Logger log = LoggerFactory.getLogger(TaxaResource.class);
	
    @RequestMapping(value = "/compararTaxa",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<MemoriasCalculo> compararTaxa(@RequestParam String instalacao,
        		@RequestParam int versaoTaxa,
        		@RequestParam int versaoCenario,
        		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dataApuracao, 
        		@RequestParam String taxaMemoriaCalculo)
            throws InterruptedException  {
    	
        log.debug("reproduzirTaxa (/reproduzirTaxa) instalacao:["+instalacao+"] "
        		+"versaoTaxa["+versaoTaxa+"] versaoCenario:["+versaoCenario+"]"
        		+ "Taxa:["+taxaMemoriaCalculo+"] Data:["+dataApuracao+"]");
        
        //Caso for a primeira versão, não existe versão anterior para compara-la.
        if( versaoTaxa < 2 ){
        	throw new OnsRuntimeException("Nao existe versão anterior para a taxa selecionada");
        }
        
        //Cria o objeto com as duas memorias de Calculos.
        MemoriasCalculo memoriasCalc = new MemoriasCalculo();
        
      //Recupera a Taxa Principal.
        QMCEquipamentoParametro qMCequipParam = new QMCEquipamentoParametro("mCEquipamentoParametro");
        MCEquipamentoParametro mcPrincipal = mcEquipParamRepository.findOne(
        		qMCequipParam.versaoCenario.eq(versaoCenario).and(
        		qMCequipParam.versaoTaxa.eq(versaoTaxa).and(
				qMCequipParam.instalacao.eq(instalacao).and(
				qMCequipParam.dataApuracao.eq(Date.from(dataApuracao.toInstant())).and(
				qMCequipParam.taxaMemoriaCalculo.equalsIgnoreCase(taxaMemoriaCalculo))))));
        if(mcPrincipal != null){   
        	//Seta a Taxa Principal no MC V1.
        	memoriasCalc.setMemoriaCalculoV1(mcPrincipal.getMemoriaCalculo());
        }
    
        MCEquipamentoParametro mcVersaoAnterior = mcEquipParamRepository.findOne(
        		qMCequipParam.versaoCenario.eq(versaoCenario).and(
        		qMCequipParam.versaoTaxa.eq(versaoTaxa-1).and(
				qMCequipParam.instalacao.eq(instalacao).and(
				qMCequipParam.dataApuracao.eq(Date.from(dataApuracao.toInstant())).and(
				qMCequipParam.taxaMemoriaCalculo.equalsIgnoreCase(taxaMemoriaCalculo))))));
        if(mcVersaoAnterior != null){   
        	//Seta a Taxa Versao Anterior no MC V2
        	memoriasCalc.setMemoriaCalculoV2(mcVersaoAnterior.getMemoriaCalculo());
        }        

        return new ResponseEntity<>(memoriasCalc, HttpStatus.OK);
    }
	
    @RequestMapping(value = "/reproduzirTaxa",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<MemoriasCalculo> reproduzirTaxa(@RequestParam String instalacao,
        		@RequestParam int versaoTaxa,
        		@RequestParam int versaoCenario,
        		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dataApuracao, 
        		@RequestParam String taxaMemoriaCalculo)
            throws InterruptedException  {
    	
            log.debug("reproduzirTaxa (/reproduzirTaxa) instalacao:["+instalacao+"] "
            		+"versaoTaxa["+versaoTaxa+"] versaoCenario:["+versaoCenario+"]"
            		+ "Taxa:["+taxaMemoriaCalculo+"] Data:["+dataApuracao+"]");

            //Cria o objeto com as duas memorias de Calculos.
            MemoriasCalculo memoriasCalc = new MemoriasCalculo();
   
            //Recupera a MC já salva
            QMCEquipamentoParametro qMCequipParam = new QMCEquipamentoParametro("mCEquipamentoParametro");
            MCEquipamentoParametro mcEquipParam = mcEquipParamRepository.findOne(
            		qMCequipParam.versaoCenario.eq(versaoCenario).and(
            		qMCequipParam.versaoTaxa.eq(versaoTaxa).and(
    				qMCequipParam.instalacao.eq(instalacao).and(
    				qMCequipParam.dataApuracao.eq(Date.from(dataApuracao.toInstant())).and(
    				qMCequipParam.taxaMemoriaCalculo.equalsIgnoreCase(taxaMemoriaCalculo))))));
            
            if(mcEquipParam != null){
            	
            	//Seta a primeira memoria de calculo com o valor já calculado.
            	memoriasCalc.setMemoriaCalculoV1(mcEquipParam.getMemoriaCalculo());

	            //chama a Reprodutibilidade 
            	CommandMessage<ReplayCommand<CommandModification>> commandMessage = new CommandMessage<>();
	     		ReplayCommand<CommandModification> replayCommand = new ReplayCommand<>();
	     		replayCommand.setCorrelationId(mcEquipParam.getCorrelationId());
	     		commandMessage.setCommand(replayCommand);
				commandBus.sendAndWait(commandMessage);
				
				//Busca a o resultado da reprodutibilidade
				QReprodutibilidade qReprodt = new QReprodutibilidade("reprodutibilidade");
				Reprodutibilidade reprodutibilidade = null;
				int segundos = 0;
				while(reprodutibilidade == null && segundos < 100){
					reprodutibilidade = repordutibilidadeRepository.findOne(
							qReprodt.correlationId.eq(mcEquipParam.getCorrelationId()).
							and(qReprodt.instalacao.eq(mcEquipParam.getInstalacao()).
							and(qReprodt.dataApuracao.eq(Date.from(dataApuracao.toInstant())).
							and(qReprodt.taxaMemoriaCalculo.equalsIgnoreCase(taxaMemoriaCalculo)))));
					Thread.sleep(10000); // 1 segundo
					segundos++;
				}
				
				if( reprodutibilidade != null ){
					//Seta a segunda memoria de Calculo com o valor da reprodutibilidade
					memoriasCalc.setMemoriaCalculoV2(reprodutibilidade.getMemoriaCalculo());
					
					//Remove do XS
					repordutibilidadeRepository.deleteAll();
					
				}else{
					throw new OnsRuntimeException("O Calculo de reprodutibilidade não chegou em 10 segundos.");
				}
            }

            return new ResponseEntity<>(memoriasCalc, HttpStatus.OK);
        }
    
    
//    @RequestMapping(value = "/reprodutibilidade",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//        public List<Reprodutibilidade> reprodutibilidade() throws InterruptedException  {
//
//					return IterableConverter.toList(repordutibilidadeRepository.findAll());
//					
//        }    
    

}
