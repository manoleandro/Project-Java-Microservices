package br.org.ons.sager.read.web.rest;

import java.io.File;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
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

import br.org.ons.sager.read.domain.Comentario;
import br.org.ons.sager.read.service.DispService;
import br.org.ons.sager.read.web.rest.dto.ComentarioResponse;
import br.org.ons.sager.read.web.rest.dto.DispInstalacoesDTO;
import br.org.ons.sager.dto.UsinaDTO;
import br.org.ons.sager.read.web.rest.dto.DisponibilidadesDTO;
import br.org.ons.sager.read.web.rest.dto.CheckDispInstalacoesResponse;
import br.org.ons.sager.read.web.rest.errors.ErrorDTO;
import br.org.ons.sager.read.web.rest.exceptions.BadRequestException;

/**
 * REST controller for managing Disp.
 */
@RestController
@RequestMapping("/api")
public class DispResource {

	private final Logger log = LoggerFactory.getLogger(DispResource.class);
	    
    private DispService dispService;
	    
    @Inject
    public DispResource(DispService dispService) {
		super();
		this.dispService = dispService;
	}

	@RequestMapping(value = "/comentarios",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComentarioResponse>getComentarios(){
    	List<Comentario> page = new ArrayList<Comentario>();
	    	
//	    	Comentario comentTeste = new Comentario();
//	    	comentTeste.setDescricao("TESTE");	    	
	    	
	    	
	    	/*
	    	 * 1 FORA_OPERACAO_COMERCIAL 1) dtInicioPesquisa e dtFimPesquisa antes do fim do periodo FORA_OPERACAO_COMERCIAL
	    	 * Retornar 1 comentario - 1
	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(101, 0, 01), new Date(101, 0, 31), "RJUSCP"));
//	    	page.add(comentTeste);
//	    	/*
//	    	 * 2 FORA_OPERACAO_COMERCIAL 2) dtInicioPesquisa antes do fim e e dtFimPesquisa após o fim do periodo FORA_OPERACAO_COMERCIAL
//	    	 * Retornar 1 comentario - 2
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(101, 0, 01), new Date(101, 6, 2), "RJUSCP"));
//	    	page.add(comentTeste);
//	    	/*
//	    	 * 3 FORA_OPERACAO_COMERCIAL 3) dtInicioPesquisa e dtFimPesquisa após o fim do periodo FORA_OPERACAO_COMERCIAL
//	    	 * Não Retornar comentario
//	    	 */
//	    	page.addAll(dispService.findAllComentarios(new Date(101, 6, 2), new Date(101, 6, 20), "RJUSCP"));
//	    	page.add(comentTeste);
//	    	
//	    	
//	    	/*
//	    	 * 4 SUSPENSAO USINA 1) dtInicioPesquisa e dtFimPesquisa antes do periodo SUSPENSAO
//	    	 * Não Retornar comentario
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(101, 6, 2), new Date(101, 6, 20), "RJUSCP"));
//	    	page.add(comentTeste);
//	    	
//	    	/*
//	    	 * 5 SUSPENSAO USINA 2) dtInicioPesquisa antes e dtFimPesquisa depois do periodo SUSPENSAO
//	    	 * Retornar 1 comentario - 5
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(101, 6, 2), new Date(113, 6, 20), "RJUSCP"));
//	    	page.add(comentTeste);
//	    	
//	    	/*
//	    	 * 6 SUSPENSAO USINA 3) dtInicioPesquisa e dtFimPesquisa durantedo o periodo SUSPENSAO
//	    	 * Retornar 1 comentario - 6
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(113, 6, 2), new Date(113, 6, 20), "RJUSCP"));
//	    	page.add(comentTeste);
//	    	
//	    	
//	    	
//	    	/*
//	    	 * 7 SUSPENSAO EQUIP 1) dtInicioPesquisa e dtFimPesquisa antes do 2 periodo SUSPENSAO
//	    	 * Não Retornar comentario
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(112, 6, 2), new Date(112, 6, 20), "RSUPME"));
//	    	page.add(comentTeste);
//	    	/*
//	    	 * 8 SUSPENSAO EQUIP 2) 
//	    	 * Retornar 1 comentario - 7
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(112, 6, 2), new Date(114, 0, 20), "RSUPME"));
//	    	page.add(comentTeste);
//	    	/*
//	    	 * 9 SUSPENSAO EQUIP 3) 
//	    	 * Retornar 2 comentario - 8 e 9
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(112, 6, 2), new Date(114, 7, 20), "RSUPME"));
//	    	page.add(comentTeste);
//	    	/*
//	    	 * 10 SUSPENSAO EQUIP 4) 
//	    	 * Retornar 2 comentario - 10 e 11
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(114, 0, 2), new Date(114, 7, 20), "RSUPME"));
//	    	page.add(comentTeste);
//	    	/*
//	    	 * 11 SUSPENSAO EQUIP 5) 
//	    	 * Retornar 2 comentario - 11 e 12
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(114, 7, 2), new Date(114, 8, 20), "RSUPME"));
//	    	page.add(comentTeste);
//	    	/*
//	    	 * 12 SUSPENSAO EQUIP 6) 
//	    	 * Retornar 2 comentario - 13 e 14
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(114, 7, 2), new Date(116, 8, 20), "RSUPME"));
//	    	page.add(comentTeste);
//	    	/*
//	    	 * 13 SUSPENSAO EQUIP 7) 
//	    	 * Retornar 1 comentario - 15
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(116, 7, 2), new Date(116, 8, 20), "RSUPME"));
//	    	page.add(comentTeste);
//	    	/*
//	    	 * 14 SUSPENSAO EQUIP 8) 
//	    	 * Retornar 2 comentario - 16 e 17
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(113, 7, 2), new Date(116, 8, 20), "RSUPME"));
//	    	page.add(comentTeste);
//	    	/*
//	    	 * 15 SUSPENSAO EQUIP 9) 
//	    	 * Retornar 2 comentario - 18 e 19
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(113, 7, 2), new Date(114, 8, 20), "RSUPME"));
//	    	page.add(comentTeste);
//	    	/*
//	    	 * 16 SUSPENSAO EQUIP 10) 
//	    	 * Retornar 2 comentario - 20 e 21
//	    	 */
//	    	page.addAll(dispService.findAllComentarios( new Date(114, 3, 2), new Date(116, 8, 20), "RSUPME"));
//	    	page.add(comentTeste);
	    	
	    	page.addAll(dispService.findAllComentarios());
	    	ComentarioResponse resp = new ComentarioResponse();
	    	resp.setComentarios(page);
	    	return new ResponseEntity<>(resp, HttpStatus.OK);
	    }
	    
	    /**
	     * POST  /disp/:id : get the "id" disp.
	     *
	     * @param id the id of the disp to retrieve
	     * @return the ResponseEntity with status 200 (OK) and with body the disp, or with status 404 (Not Found)
	     * @throws URISyntaxException 
	     */
	    @RequestMapping(value = "/disp",
	    		method = RequestMethod.POST,
	    		produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<DisponibilidadesDTO> getDisp(
	    		@RequestBody DispInstalacoesDTO instid,
	    		@RequestParam String[] tipos ,
	    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtInicio,
	    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtFim
	    		) throws URISyntaxException, BadRequestException {
	    	log.debug("REST request for get Disp from the insid: " );
	    	dtInicio = dtInicio.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
	    	dtFim = dtFim.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));

	    	DisponibilidadesDTO page = dispService.calcularDisponibilidades(instid, dtInicio, dtFim, tipos);
	    	 
//	    	CheckDispInstalacoesRequest req = new CheckDispInstalacoesRequest();
//	    	req.setInstalacoes(page);
	    	return new ResponseEntity<>(page, HttpStatus.OK);
	    }
	    
	    @RequestMapping(value = "/disp",
	    		method = RequestMethod.PUT,
	    		produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<CheckDispInstalacoesResponse> checkDispInstalacoes(
	    		@RequestBody List<UsinaDTO> instid,
	    		@RequestParam String[] tipos,
	    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtInicio,
	    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtFim
	    		
	    		) throws URISyntaxException, BadRequestException {
	    	log.debug("REST request to check if All Instalacoes are able to calculate Disponibildades." );
	    	
	    	dtInicio = dtInicio.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
	    	dtFim = dtFim.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
	    	CheckDispInstalacoesResponse page = dispService.checkDispInstalacoes(dtInicio, dtFim, instid, tipos);
	    	
	    	return new ResponseEntity<>(page, HttpStatus.OK);
	    }
	    
	    @RequestMapping(value = "/excel-disp",
	    		method = RequestMethod.POST,
	    		produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	    public ResponseEntity<byte[]> getExcelDisp(
	    		@RequestBody List<DispInstalacoesDTO> instalacoes,
	    		@RequestParam String[] tipos,
	    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtInicio,
	    		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime dtFim
	    		
	    		) throws URISyntaxException, BadRequestException {
	    	log.debug("REST request to check if All Instalacoes are able to calculate Disponibildades." );
	    	
	    	
//	    	File file = new File("C:\\LAS\\JavaHonk\\Restful\\test.xlsx");
//	    	
//	    	HttpHeaders header = new HttpHeaders();
//	    	header.setContentDispositionFormData("attachment", "new-excel-file.xlsx");
//	    	
//	    	return new ResponseEntity<>(file.getA, header, HttpStatus.OK);	
	    	
	    	dtInicio = dtInicio.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
	    	dtFim = dtFim.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
	    
	    	byte[] excelGeral = dispService.generateExcel(dtInicio, dtFim, instalacoes, tipos);
	    	
	    	HttpHeaders header = new HttpHeaders();
	    	header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CalculoDisponibilidade.xlsx");
	        header.setContentLength(excelGeral.length);
	        header.add("Content-Transfer-Encoding", "binary");
	    	System.out.println("Resposta excelGeral:  " + excelGeral.length + " " + excelGeral.toString());
	    	
	        return new ResponseEntity<>(excelGeral, header, HttpStatus.OK);	
	    }
	    
	    
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(BadRequestException.class)
	    public ErrorDTO handleError(BadRequestException ex) {
	    	log.error(" raised " + ex);
	    	return new ErrorDTO(ex.getLocalizedMessage(), ex.getDesc());
	    }
}

