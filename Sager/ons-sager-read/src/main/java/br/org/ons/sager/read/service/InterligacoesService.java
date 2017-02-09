package br.org.ons.sager.read.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.org.ons.sager.read.domain.Interligacoes;
import br.org.ons.sager.read.domain.QInterligacoes;
import br.org.ons.sager.read.repository.InterligacoesRepository;



/**
 * Service Implementation for managing Interligacoes.
 */
@Service
public class InterligacoesService {

    private final Logger log = LoggerFactory.getLogger(InterligacoesService.class);
    
    @Inject
    private InterligacoesRepository interligacoesRepository;

    /**
     * Save a Interligacoes.
     *
     * @param interligacoes the entity to save
     * @return the persisted entity
     */
//    public Interligacoes save(Interligacoes interligacoes) {
//        log.debug("Request to save Interligacoes : {}", interligacoes);
//        return interligacoesRepository.save(interligacoes);
//    }

    /**
     *  Get all the Interligacoess.
     * @param pageable 
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public List<Interligacoes> findAll() {
        log.debug("Request to get all Interligacoess");
        
        Collection authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        log.info("Authorities: " + Arrays.toString(authorities.toArray()));
        QInterligacoes ref= new QInterligacoes("interligacoes");
        return IterableConverter.toList(interligacoesRepository.findAll(ref.cosr.in(mapAuthorities2COSR(authorities))));
    }
    
//    private Collection<String> mapAuthorities2COSR(Collection<GrantedAuthority> authorities) {
//    	Collection<String> cRet = new ArrayList<>();
//    	for (GrantedAuthority auth : authorities) {
//    		switch (auth.getAuthority()) {
//			case "ROLE_COSR_S":
//			case "COSR-S":
//				cRet.add("COSR-S");
//				break;
//			case "ROLE_COSR_NE":
//			case "COSR-NE":
//				cRet.add("COSR-NE");
//				break;
//			case "ROLE_COSR_SE":
//			case "COSR-SE":
//				cRet.add("COSR-SE");
//				break;
//			case "ROLE_COSR_NCO":
//			case "COSR-NCO":
//				cRet.add("COSR-NCO");
//				break;
//			case "ROLE_CNOS":
//			case "CNOS":
//				cRet.add("COSR-NCO");
//				cRet.add("COSR-NE");
//				cRet.add("COSR-SE");
//				cRet.add("COSR-S");
//				break;
//			default:
//				break;
//			}
//    	}
//    	
//    	return cRet;
//    	
//    }
    
    private Collection<String> mapAuthorities2COSR(Collection<GrantedAuthority> authorities) {
    	Collection<String> cRet = new ArrayList<>();
    	for (GrantedAuthority auth : authorities) {
    		
    		if(auth.getAuthority().equals("ROLE_COSR_S") || auth.getAuthority().equals("COSR-S")){
    			
    			cRet.add("COSR-S");
    			
 			}else if(auth.getAuthority().equals("ROLE_COSR_NE") || auth.getAuthority().equals("COSR-NE")){
 				
				cRet.add("COSR-NE");
				
			}else if(auth.getAuthority().equals("ROLE_COSR_SE") || auth.getAuthority().equals("COSR-SE")){
				
				cRet.add("COSR-SE");
				
			}else if(auth.getAuthority().equals("ROLE_COSR_NCO") || auth.getAuthority().equals("COSR-NCO")){
				
				cRet.add("COSR-NCO");
				
			}else if(auth.getAuthority().equals("ROLE_CNOS") || auth.getAuthority().equals("COSR-S")){
				
				cRet.add("COSR-NCO");
				cRet.add("COSR-NE");
				cRet.add("COSR-SE");
				cRet.add("COSR-S");
				
			}
    		
    	}
    	
    	return cRet;
    	
    }

    /**
     *  Get one Interligacoes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Interligacoes findOne(String id) {
        log.debug("Request to get Interligacoes : {}", id);
        return interligacoesRepository.findOne(id);
    }

    /**
     *  Delete the  Interligacoes by id.
     *
     *  @param id the id of the entity
     */
//    public void delete(String id) {
//        log.debug("Request to delete Interligacoes : {}", id);
//        interligacoesRepository.delete(id);
//    }
}
