package br.org.ons.sager.read.service;

import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.sager.instalacao.event.EquipamentoCadastradoEvent;
import br.org.ons.sager.read.domain.Equipamento;
import br.org.ons.sager.read.domain.QEquipamento;
import br.org.ons.sager.read.repository.EquipamentoRepository;

@Component
public class EquipamentoEventHandler {

	@Inject
	private EquipamentoRepository equipamentoCadastradoRepository;

	private final Logger log = LoggerFactory.getLogger(EquipamentoEventHandler.class);
	
	@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.EquipamentoCadastradoEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleEquipamentoCadastradoEvent(EventMessage<EquipamentoCadastradoEvent> eventMessage) throws JMSException {
		try{
			log.debug("handleEquipamentoCadastradoEvent: {}", eventMessage);

			/**
			 * Carrega os Equipamentos que serão utilizados nos Parametros/TaxasMensais/TaxasAcumuladas
			 */
			EquipamentoCadastradoEvent equipamentoCadastradoEvent = eventMessage.getEvent();
			EventMetadata metadata = eventMessage.getMetadata();
			
			br.org.ons.geracao.cadastro.Equipamento equip = equipamentoCadastradoEvent.getEquipamento();
			Equipamento equipRead = new Equipamento();
			equipRead.setDataDesativacao(equip.getDataDesativacao());
			equipRead.setDataEventoEOC(equip.getDataEventoEOC());
			equipRead.setDataImplantacao(equip.getDataImplantacao());
			equipRead.setDataRenovacaoProrrogacaoConcessao(equip.getDataRenovacaoProrrogacaoConcessao());
			equipRead.setFranquias(equip.getFranquias());
			equipRead.setId(equip.getId());
			equipRead.setIdInstalacao(metadata.getAggregateId());
			equipRead.setNome(equip.getNome());
			equipRead.setPotenciasCalculo(equip.getPotenciasCalculo());
			equipRead.setQuantidadeHorasServico(equip.getQuantidadeHorasServico());
			equipRead.setSuspensoes(equip.getSuspensoes());
			equipRead.setTipoInstalacao(equip.getTipoInstalacao());
			equipRead.setVersao(equip.getVersao());
			if ( equip instanceof UnidadeGeradora){
				UnidadeGeradora ug = (UnidadeGeradora)equip;
				equipRead.setCodid_dpp(ug.getCodigoIdDpp());
				equipRead.setIddpp(ug.getIdDpp());
				equipRead.setCodigoCcee(ug.getCodigoCcee());
			}
			equipamentoCadastradoRepository.save(equipRead);
			
		}catch(Exception e){
			log.error("handleEquipamentoCadastradoEvent",e);
		}
	}

    
    /**
     *  Get all the Equipamento by ids.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public List<Equipamento> findById(String[] ids, Pageable pageable) {
        log.debug("Request to get all EquipamentoCadastrado by id's");
        
        QEquipamento pred = new QEquipamento("equipamento");
        if(ids == null){
        	return IterableConverter.toList(equipamentoCadastradoRepository.findAll(null,pageable));
        }
        return IterableConverter.toList(equipamentoCadastradoRepository.findAll(pred.id.in(ids), pageable));
    }
		
}
