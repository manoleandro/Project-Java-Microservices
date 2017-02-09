package br.org.ons.sager.read.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.org.ons.sager.read.domain.QUsinas;
import br.org.ons.sager.read.domain.Usinas;
import br.org.ons.sager.read.repository.UsinasRepository;



/**
 * Service Implementation for managing Usinas.
 */
@Service
public class UsinasService {

    private final Logger log = LoggerFactory.getLogger(UsinasService.class);
    
    private static final String COSR_NCO = "COSR-NCO";
    private static final String COSR_NE = "COSR-NE";
    private static final String COSR_S = "COSR-S";
    private static final String COSR_SE = "COSR-SE";
    
    @Inject
    private UsinasRepository usinasRepository;

    /**
     * Save a Usinas.
     *
     * @param Usinas the entity to save
     * @return the persisted entity
     */

    /**
     *  Get all the Usinass.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public List<Usinas> findAll() {
        log.debug("Request to get all Usinass");
        
        Collection authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        log.info("Authorities: " + Arrays.toString(authorities.toArray()));
        QUsinas ref= new QUsinas("usinas");
        Iterable<Usinas> result = usinasRepository.findAll(ref.cosr.in(mapAuthorities2COSR(authorities)));
        List<Usinas> usinasRefList = new ArrayList<>();
        result.forEach(usinasRefList::add);
        return usinasRefList;
    }
    

    private Collection<String> continuarVerificacaoAuth(GrantedAuthority auth , Collection<String> cRet){
    	if("ROLE_COSR_SE".equals(auth.getAuthority()) || COSR_SE.equals(auth.getAuthority())){
			
			cRet.add(COSR_SE);
			
		}else if("ROLE_COSR_NCO".equals(auth.getAuthority()) || COSR_NCO.equals(auth.getAuthority())){
			
			cRet.add(COSR_NCO);
			
		}else if("ROLE_CNOS".equals(auth.getAuthority()) || COSR_S.equals(auth.getAuthority())){
			
			cRet.add(COSR_NCO);
			cRet.add(COSR_NE);
			cRet.add(COSR_SE);
			cRet.add(COSR_S);
			
		}
    	return cRet;
    }
    
    private Collection<String> mapAuthorities2COSR(Collection<GrantedAuthority> authorities) {
    	Collection<String> cRet = new ArrayList<>();
    	for (GrantedAuthority auth : authorities) {
    		if("ROLE_COSR_S".equals(auth.getAuthority()) || COSR_S.equals(auth.getAuthority())){
    			
    			cRet.add(COSR_S);
    			
 			}else if("ROLE_COSR_NE".equals(auth.getAuthority()) || COSR_NE.equals(auth.getAuthority())){
 				
				cRet.add(COSR_NE);
				
			}else {
				cRet = continuarVerificacaoAuth(auth, cRet);
			}
    	}
    	
    	return cRet;
    	
    }

    /**
     *  Get one Usinas by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Usinas findOne(String id) {
        log.debug("Request to get Usinas : {}", id);
        return usinasRepository.findOne(id);
    }

    /**
     *  Delete the  Usinas by id.
     *
     *  @param id the id of the entity
     */
}
