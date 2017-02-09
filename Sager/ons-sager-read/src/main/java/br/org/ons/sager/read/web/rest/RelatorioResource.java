package br.org.ons.sager.read.web.rest;

import br.org.ons.sager.read.config.IIBusProperties;
import br.org.ons.sager.read.service.RelatorioService;
import br.org.ons.sager.dto.UsinaDTO;
import br.org.ons.sager.read.web.rest.errors.ErrorDTO;
import br.org.ons.sager.read.web.rest.exceptions.BadRequestException; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController; 

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse; 


/**
 * REST controller for managing GerarRelatorio.
 */
@EnableConfigurationProperties(IIBusProperties.class)
@RestController
@RequestMapping("/api") 
public class RelatorioResource {

	 private final Logger log = LoggerFactory.getLogger(RelatorioResource.class);
     
	    private RelatorioService relatorioService;

	    @Inject
	    public RelatorioResource(RelatorioService relatorioService) {
			super();
			this.relatorioService = relatorioService;
		}
	    
	    @RequestMapping(value = "/relatorio",
	    		method = RequestMethod.POST,
	    		produces = "application/*")
	    public ResponseEntity<byte[]> getRelatorio(
	    		@RequestBody List<UsinaDTO> instalacoes,
	    		@RequestParam String tipoRelatorio ,
	    		@RequestParam String tipoArquivo,
	    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtInicio,
	    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtFim, 
	    		HttpServletResponse response
	    		
	    		) throws URISyntaxException, BadRequestException {
	    	log.debug("REST request for get Relatorio from the insid: " );
	        
	        
	    	// System.out.println("getRelatorio dtInicio: " + dtInicio);
	    	byte[] respByte = relatorioService.gerarRelatorio(instalacoes, tipoRelatorio, tipoArquivo, dtInicio, dtFim, "download");
	        HttpHeaders header = new HttpHeaders();
	    	switch(respByte[0])
	    	{
		    	case 0:			// XML
		    	case 1:			// DAT
			        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + tipoRelatorio + "." + tipoArquivo);
		    		break;
		    	case 2:			// Ambos (zip)
			        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + tipoRelatorio + ".zip");
			        break;
		    	case 3:			// Erro
			        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mensagensErro.log");
			        break;
		    	case 4:			// Erro
			        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=semDados.log");
			        break;
	    	}
	        header.setContentLength(respByte.length-1);
	    	System.out.println("Resposta: " + respByte.length + " " + respByte.toString());
	        return new ResponseEntity<>(Arrays.copyOfRange(respByte, 1, respByte.length), header, HttpStatus.OK);	    		
	    		    	
	    }	    	    
	    
	    @RequestMapping(value = "/relatorioFS",
	    		method = RequestMethod.POST,
	    		produces = "application/*")
	    public ResponseEntity<byte[]> getRelatorioFS(
	    		@RequestBody List<UsinaDTO> instalacoes,
	    		@RequestParam String tipoRelatorio ,
	    		@RequestParam String tipoArquivo,
	    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtInicio,
	    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtFim, 
	    		HttpServletResponse response
	    		
	    		) throws URISyntaxException, BadRequestException {
	    	log.debug("REST request for get Relatorio from the insid: " );
	        
	        
	    	// System.out.println("getRelatorio dtInicio: " + dtInicio);
	    	byte[] respByte = relatorioService.gerarRelatorio(instalacoes, tipoRelatorio, tipoArquivo, dtInicio, dtFim, "filesystem");
	        HttpHeaders header = new HttpHeaders();
	        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=semDados.log");
		    header.setContentLength(0);
	    	System.out.println("RespostaFS: " + respByte.length + " " + respByte.toString());
	        return new ResponseEntity<>(Arrays.copyOfRange(respByte, 1, respByte.length), header, HttpStatus.OK);	    		
	    		    	
	    }	    	    
	    
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(BadRequestException.class)
	    public ErrorDTO handleError(BadRequestException ex) {
	    	log.error(" raised " + ex);

	    	ErrorDTO err = new ErrorDTO(ex.getLocalizedMessage(), ex.getDesc());
	    	return err;
	    }
	    
}

