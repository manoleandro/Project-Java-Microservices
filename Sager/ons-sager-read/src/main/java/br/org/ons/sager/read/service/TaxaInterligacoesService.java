package br.org.ons.sager.read.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import br.org.ons.sager.read.domain.QTaxaInterligacoes;
import br.org.ons.sager.read.domain.TaxaInterligacoes;
import br.org.ons.sager.read.repository.TaxaInterligacoesRepository;



/**
 * Service Implementation for managing TaxaInterligacoes.
 */
@Service
public class TaxaInterligacoesService {

    private final Logger log = LoggerFactory.getLogger(TaxaInterligacoesService.class);
    
    @Inject
    private TaxaInterligacoesRepository TaxaInterligacoesRepository;
//
//    /**
//     * Save a TaxaInterligacoes.
//     *
//     * @param TaxaInterligacoes the entity to save
//     * @return the persisted entity
//     */
//    public TaxaInterligacoes save(TaxaInterligacoes TaxaInterligacoes) {
//        log.debug("Request to save TaxaInterligacoes : {}", TaxaInterligacoes);
//        TaxaInterligacoes result = TaxaInterligacoesRepository.save(TaxaInterligacoes);
//        return result;
//    }
//
//    /**
//     *  Get all the TaxaInterligacoess.
//     *  
//     *  @param pageable the pagination information
//     *  @return the list of entities
//     */
//    public Page<TaxaInterligacoes> findAll(Pageable pageable) {
//        log.debug("Request to get all TaxaInterligacoess");
//        
//        Collection authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        log.info("Authorities: " + Arrays.toString(authorities.toArray()));
//        QTaxaInterligacoes ref= new QTaxaInterligacoes("taxa");
//        Page<TaxaInterligacoes> result = TaxaInterligacoesRepository.findAll(ref.cos.in(mapAuthorities2COSR(authorities)),pageable);
//        return result;
//    }
//    
//    private Collection<String> mapAuthorities2COSR(Collection<GrantedAuthority> authorities) {
//    	Collection<String> cRet = new ArrayList<String>();
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
//
//    /**
//     *  Get one TaxaInterligacoes by id.
//     *
//     *  @param id the id of the entity
//     *  @return the entity
//     */
//    public TaxaInterligacoes findOne(String id) {
//        log.debug("Request to get TaxaInterligacoes : {}", id);
//        TaxaInterligacoes TaxaInterligacoes = TaxaInterligacoesRepository.findOne(id);
//        return TaxaInterligacoes;
//    }
//
//    /**
//     *  Delete the  TaxaInterligacoes by id.
//     *
//     *  @param id the id of the entity
//     */
//    public void delete(String id) {
//        log.debug("Request to delete TaxaInterligacoes : {}", id);
//        TaxaInterligacoesRepository.delete(id);
//    }
}
