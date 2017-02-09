package br.org.ons.sager.parametrizacao.service;

import java.time.LocalTime;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.OrderSpecifier;

import br.org.ons.sager.exception.BadRequestException;
import br.org.ons.sager.exception.NotFoundException;
import br.org.ons.sager.parametrizacao.domain.QRetificacoesParam;
import br.org.ons.sager.parametrizacao.domain.RetificacoesParam;
import br.org.ons.sager.parametrizacao.repository.RetificacoesParamRepository;


/**
 * Service Implementation for managing RetificacoesParam.
 */
@Service
public class RetificacoesParamService {
	
	private final Logger log = LoggerFactory.getLogger(RetificacoesParamService.class);
	
	private static final String ERROR_KEY_INVALID_DAY = "invalidDay";
	private static final String ERROR_KEY_INVALID_HOUR = "invalidHour";
	private static final String ERROR_KEY_DUPLICATE_RECORD = "duplicateRecord";
	private static final String ERROR_KEY_LAST_RECORD = "lastRecord";
	private static final String ERROR_KEY_NOT_FOUND = "notFound";
	
	private static final String ERROR_MESSAGE_INVALID_DAY = "Dia inválido.";
	private static final String ERROR_MESSAGE_INVALID_HOUR = "Hora inválida.";
	private static final String ERROR_MESSAGE_DUPLICATE_RECORD = "Registro duplicado.";
	private static final String ERROR_MESSAGE_LAST_RECORD = "Tem que existir pelo menos um agendamento.";
	private static final String ERROR_MESSAGE_NOT_FOUND = "Registro não encontrado.";
    
    @Inject
    private RetificacoesParamRepository retificacoesParamRepository;
    
    /**
     *  Get all the RetificacoesParam.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public List<RetificacoesParam> findAll() {
        log.debug("Request to get all RetificacoesParam");
        
        QRetificacoesParam q = new QRetificacoesParam("retificacoesParam");
        OrderSpecifier<Integer> sortOrder1 = q.dia.asc();
        OrderSpecifier<LocalTime> sortOrder2 = q.hora.asc();

        return IterableConverter.toList(retificacoesParamRepository.findAll(sortOrder1, sortOrder2)) ;
    }
    
    /**
     * Save a RetificacoesParam.
     *
     * @param Interligacoes the entity to save
     * @return the persisted entity
     */
    public RetificacoesParam save(RetificacoesParam retificacoesParam) throws BadRequestException {
        log.debug("Request to save RetificacoesParam : {}", retificacoesParam);
        
        if (retificacoesParam.getHora() == null) {
            throw new BadRequestException( ERROR_KEY_INVALID_HOUR, ERROR_MESSAGE_INVALID_HOUR);
        }
        
        if (retificacoesParam.getDia() < 0 || retificacoesParam.getDia() > 18) {
            throw new BadRequestException( ERROR_KEY_INVALID_DAY, ERROR_MESSAGE_INVALID_DAY);
        }
        
        //OBS - jcardoso - RN Qdo o dia for 0 somente podemos ter hora = 00:00
        if (retificacoesParam.getDia() == 0 && (retificacoesParam.getHora().getHour() != 0 || retificacoesParam.getHora().getMinute() != 0)) {
        	throw new BadRequestException( ERROR_KEY_INVALID_DAY, ERROR_MESSAGE_INVALID_DAY);
        }
        
        //OBS - jcardoso - RN não incluir registros duplicados
        if (this.findOne(retificacoesParam.getDia() , retificacoesParam.getHora()) != null ){
        	throw new BadRequestException( ERROR_KEY_DUPLICATE_RECORD, ERROR_MESSAGE_DUPLICATE_RECORD);
        	
        }
        
        retificacoesParam.setHora(retificacoesParam.getHora().withNano(0));
        
        return retificacoesParamRepository.save(retificacoesParam);
    }
    
    /**
     *  Get one RetificacoesParam by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public RetificacoesParam findOne(String id) {
        log.debug("Request to get RetificacoesParam : {}", id);
        return retificacoesParamRepository.findOne(id);
    }
    
    /**
     *  Get one RetificacoesParam by day and hour.
     *
     *  @param day the day of the entity and hour the hour of the entity
     *  @return the entity
     */
    public RetificacoesParam findOne(int day, LocalTime hour) {
        log.debug("Request to get RetificacoesParam : {} day: " + day + " hour: " + hour);
        return retificacoesParamRepository.findOneByDiaAndHora(day, hour.withNano(0));
    }

    /**
     *  Delete the  RetificacoesParam by id.
     *
     *  @param id the id of the entity
     * @throws NotFoundException 
     * @throws BadRequestException 
     */
    public void delete(String id) throws NotFoundException, BadRequestException {
        log.debug("Request to delete RetificacoesParam : {}", id);
        
        
        if ( retificacoesParamRepository.count() > 1 ){
        	if ( retificacoesParamRepository.findOne(id) == null){
        		throw new NotFoundException(ERROR_KEY_NOT_FOUND, ERROR_MESSAGE_NOT_FOUND);
        	}
        	//OBS - jcardoso - RN - nao excluir o último dia/hora de agendamento.
        	retificacoesParamRepository.delete(id);
        	return;
        }
        
    	throw new BadRequestException( ERROR_KEY_LAST_RECORD, ERROR_MESSAGE_LAST_RECORD);
    	
    }
}
