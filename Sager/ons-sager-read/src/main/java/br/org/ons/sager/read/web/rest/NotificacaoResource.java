package br.org.ons.sager.read.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.CollectionExpression;

import br.org.ons.platform.common.ResultMessage;
import br.org.ons.sager.read.domain.Notificacao;
import br.org.ons.sager.read.domain.NotificacoesLidas;
import br.org.ons.sager.read.domain.QNotificacao;
import br.org.ons.sager.read.domain.QNotificacoesLidas;
import br.org.ons.sager.read.repository.NotificacaoRepository;
import br.org.ons.sager.read.repository.NotificacoesLidasRepository;

@Component
@RestController
@RequestMapping("/api")
public class NotificacaoResource {
	private final Logger log = LoggerFactory.getLogger(UsinasResource.class);
	
	@Inject
	NotificacaoRepository notificacaoRepository;
	
	@Inject
	NotificacoesLidasRepository notificacoesLidasRepository;
	
	@Inject
	public NotificacaoResource(NotificacaoRepository notificacaoRepository, NotificacoesLidasRepository notificacoesLidasRepository) {
		super();
		this.notificacaoRepository = notificacaoRepository;
		this.notificacoesLidasRepository = notificacoesLidasRepository;
	}

	QNotificacao qNotificacao = new QNotificacao("notificacao");
	
	QNotificacoesLidas qNotificacoesLidas = new QNotificacoesLidas("notificacoesLidas");
	
//    @RequestMapping(value = "/notificacao",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<Notificacao>> getAllEvents(){
//    	
//    	List <Notificacao> ev = IterableConverter.toList(notificacaoRepository.findAll());
//    	return new ResponseEntity<>(ev, HttpStatus.OK);
//    	
//    }
    

//    @RequestMapping(value = "/notificacaoByUser",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Notificacao>> getEventsByUser(@RequestParam String user){
//    	
//    	List <Notificacao> ev = IterableConverter.toList(notificacaoRepository.findAll(qNotificacao.usuario.in(user)));
//    	return new ResponseEntity<>(ev, HttpStatus.OK);
//    }
    
    
    @RequestMapping(value = "/notificacaoByRoles",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Notificacao>> getEventsByRole(@RequestParam List<String> roles, String usuario){

    	List<String> idsFound = new ArrayList<String>();
		List<Notificacao> notif = new ArrayList<Notificacao>();
		
		//filtrando as notificações por Roles e evitando que não se repita usando o .and(qNotificacao.id.notIn(idsFound))
		for (String role : roles) {
			Iterable<Notificacao> notificacoes = notificacaoRepository.findAll(
					qNotificacao.roles.contains(role)
					.and(qNotificacao.id.notIn(idsFound)));
			for (Notificacao notificacao : notificacoes) {
				idsFound.add(notificacao.getId());
			}
//			
			if(notificacoes != null){
				notif.addAll(IterableConverter.toList(notificacoes));
			}
			
		}
		
//		filtrando por "não Lidas"
		List<Notificacao> notificacao = new ArrayList<Notificacao>();
		if(notif.size()>0){
			for(Notificacao not : notif){
				NotificacoesLidas notLida = notificacoesLidasRepository.findOne(qNotificacoesLidas.id.eq(not.getId()).and(qNotificacoesLidas.usuario.eq(usuario)));
				if(notLida == null){
					notificacao.add(not);
				}
			}
		}
		
//		
    	
    	return new ResponseEntity<>(notificacao, HttpStatus.OK);
    	
    }
    
//    @RequestMapping(value = "/inserirNotificacao",
//    		method = RequestMethod.POST,
//    		produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResultMessage<Void>> inserirNotificacao(@RequestBody Notificacao notificacaoLida){
//		try{
//			notificacaoRepository.save(notificacaoLida);
//	    	
//	    	return new ResponseEntity<>(HttpStatus.OK);
//		}catch(Exception e){
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//    	
//    	
//    }
    
    
    
    
    @RequestMapping(value = "/lerNotificacao",
    		method = RequestMethod.POST,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultMessage<Void>> lerNotificacao(@RequestBody List<NotificacoesLidas> notificacaoLida){
		try{
			notificacoesLidasRepository.save(notificacaoLida);
	    	
	    	return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    	
    }
    
//    @RequestMapping(value = "/getNotificacoesLidasAll",
//    		method = RequestMethod.GET,
//    		produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<NotificacoesLidas>> getNotificacoesLidas(){
//		try{
//			List<NotificacoesLidas> allNotificacoes = IterableConverter.toList(notificacoesLidasRepository.findAll());
//			
//	    	return new ResponseEntity<>(allNotificacoes, HttpStatus.OK);
//		}catch(Exception e){
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//    	
//    	
//    }
    
    @RequestMapping(value = "/getNotificacoesLidasByUser",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotificacoesLidas>> getNotificacoesLidasByUser(@RequestParam(required = false) String usuario){
		try{
			List<NotificacoesLidas> allNotificacoes = IterableConverter.toList(notificacoesLidasRepository.findAll(
					qNotificacoesLidas.usuario.eq(usuario)
					));
			
//			Retirando a duplicidade
			List<NotificacoesLidas> notificacoesFiltred = new ArrayList<NotificacoesLidas>();
			List<String> idFounds = new ArrayList<String>();
			for(NotificacoesLidas not : allNotificacoes){
				if(!idFounds.contains(not.getId())){
					notificacoesFiltred.add(not);
					idFounds.add(not.getId());
				}
			}
			
	    	return new ResponseEntity<>(notificacoesFiltred, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    	
    }
    
}
