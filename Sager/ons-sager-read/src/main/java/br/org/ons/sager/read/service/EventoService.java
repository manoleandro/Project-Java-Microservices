package br.org.ons.sager.read.service;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.TipoOperacao;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.sager.instalacao.event.EventosApuradosEvent;
import br.org.ons.sager.read.domain.Evento;
import br.org.ons.sager.read.domain.QEvento;
import br.org.ons.sager.read.repository.EventoRepository;
import br.org.ons.sager.dto.EquipamentoDTO;
import br.org.ons.sager.read.web.rest.dto.EventoDTO;
import br.org.ons.sager.dto.UsinaDTO;

@Service
public class EventoService {
	
	private final Logger log = LoggerFactory.getLogger(EventoService.class);
	
	@Inject
	private EventoRepository eventoRepository;
	/**
	 * Recebe eventos de EventosApuradosEvent e insere novos registros de
	 * Eventos no repositório de dados de leitura
	 * 
	 * @param eventMessage
	 *            Evento
	 * @throws JMSException
	 */
	@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.EventosApuradosEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleEventosApuradosEvent(EventMessage<EventosApuradosEvent> eventMessage) throws JMSException {
		try{
			log.debug("handleEventosApuradosEvent: {}", eventMessage);
			
			EventosApuradosEvent eventosApuradosEvent = eventMessage.getEvent();
			
			if ( eventosApuradosEvent.getApuracao() != null && eventosApuradosEvent.getApuracao().getEventos() != null){
				for (EventoMudancaEstadoOperativo event : eventosApuradosEvent.getApuracao().getEventos()) {
					Evento evento = new Evento(event, eventMessage.getMetadata().getAggregateId());
					this.save(evento);
				}
			}
		}catch(Exception e){
			log.error("handleEventosApuradosEvent: ",e);
		}
	}
	
	/**
     * Save a Evento.
     *
     * @param evento the entity to save
     * @return the persisted entity
     */
    public Evento save(Evento evento) {
        log.debug("Request to save Evento : {}", evento);
        return eventoRepository.save(evento);
    }
    
    /**
     *  Get all the Evento by ids.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public List<EventoDTO> findById(String[] ids,  List<UsinaDTO> instalacoes, Pageable pageable) {
        log.debug("Request to get all Evento by id's");
        
        QEvento pred = new QEvento("evento");
        List<Evento> eventos;
        if(ids == null){
        	eventos =  IterableConverter.toList(eventoRepository.findAll(null,pageable));
        }
        eventos = IterableConverter.toList(eventoRepository.findAll(pred.idEvento.in(ids), pageable));
        
        List<EventoDTO> eventosResult = new ArrayList<>();
        for (Evento evento : eventos) {
        	String nomeInstalacao = "";
        	String codigoONSEquipamento = "";
        	for (UsinaDTO usinaDTO : instalacoes) {
        		if (usinaDTO.getId().equalsIgnoreCase(evento.getIdInstalacao())){
        			nomeInstalacao = usinaDTO.getNome();
        			
        			for (EquipamentoDTO equipamentoDTO : usinaDTO.getEquipamentos()) {
						if (equipamentoDTO.getId().equalsIgnoreCase(evento.getIdEquipamento()))
							codigoONSEquipamento = equipamentoDTO.getCodigoOns();
					}
        		}
    		}
        	eventosResult.add(new EventoDTO(evento, nomeInstalacao, codigoONSEquipamento));
		}
        
        return eventosResult;
    }
    
	public List<EventoDTO> findAll(ZonedDateTime dtInicio, ZonedDateTime dtFim, List<UsinaDTO> instalacoes, Pageable pageable) {
		log.debug("Request to get all Evento by period and instalacoes");
		
		List<String> idInstlacoes = new ArrayList<>();
		for (UsinaDTO usinaDTO : instalacoes) {
			idInstlacoes.add(usinaDTO.getId());
		}
        
        QEvento pred = new QEvento("evento");
        
        List<Evento> eventos = IterableConverter.toList(eventoRepository.findAll(
        		pred.idInstalacao.in(idInstlacoes)
        		.and(pred.dataVerificada.between(Date.from(dtInicio.toInstant()), Date.from(dtFim.toInstant()))), pageable));
        
        List<EventoDTO> eventosResult = new ArrayList<>();
        for (Evento evento : eventos) {
        	String nomeInstalacao = "";
        	String codigoONSEquipamento = "";
        	for (UsinaDTO usinaDTO : instalacoes) {
        		if (usinaDTO.getId().equalsIgnoreCase(evento.getIdInstalacao())){
        			nomeInstalacao = usinaDTO.getNome();
        			
        			for (EquipamentoDTO equipamentoDTO : usinaDTO.getEquipamentos()) {
						if (equipamentoDTO.getId().equalsIgnoreCase(evento.getIdEquipamento()))
							codigoONSEquipamento = equipamentoDTO.getCodigoOns();
					}
        		}
    		}
        	eventosResult.add(new EventoDTO(evento, nomeInstalacao, codigoONSEquipamento));
		}
        
        return eventosResult;
	}
    
	public List<Evento> findEventosDispVer(ZonedDateTime dtInicio, ZonedDateTime dtFim, List<UsinaDTO> instalacoes) {
		log.debug("Request to get all Evento by period and instalacoes");
		
		List<String> idInstlacoes = new ArrayList<>();
		for (UsinaDTO usinaDTO : instalacoes) {
			idInstlacoes.add(usinaDTO.getId());
			System.out.println("findEventosDispVer: " + usinaDTO.getId());
		}
        
        QEvento pred = new QEvento("evento");
        
        return IterableConverter.toList(eventoRepository.findAll(
        		pred.idInstalacao.in(idInstlacoes)
        		.and(pred.dataVerificada.between(Date.from(dtInicio.toInstant()), Date.from(dtFim.toInstant())))
        		.and(pred.origem.eq("GOO").not()).and(pred.origem.eq("GOU").not()).and(pred.origem.eq("GRB").not()), 
        		sortByDataIdEquipAsc()));
	}
    
	public Evento findEventoEspelhoDispVer(Date dataEventoEspelho, String idEquipamento) {
		log.debug("Request to get all Evento by period and instalacoes");

        QEvento pred = new QEvento("evento");
        
        List<Evento> eventos = IterableConverter.toList(eventoRepository.findAll(
        		pred.idEquipamento.in(idEquipamento)
        		.and(pred.dataVerificada.before(dataEventoEspelho))
        		.and(pred.origem.eq("GOO").not()).and(pred.origem.eq("GOU").not()).and(pred.origem.eq("GRB").not()) 
        		.and(pred.tipoOperacao.ne(TipoOperacao.C)),
        		// .and(pred.tipoOperacao.ne(TipoOperacao.O)),
        		sortByDataDesc()));
        
        if (eventos.isEmpty()) return null;
    	// System.out.println("findEventoEspelhoDispVer dataEventoEspelho: " + dataEventoEspelho);
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(dataEventoEspelho);
        localCalendar.set(Calendar.DAY_OF_MONTH, 1);
        localCalendar.set(Calendar.HOUR_OF_DAY, 0);
        localCalendar.set(Calendar.MINUTE, 0);
        localCalendar.set(Calendar.SECOND, 0);
        Evento eventoEspelho = new Evento();
        eventoEspelho.setIdEvento(eventos.get(0).getIdEvento());
        eventoEspelho.setVersion(eventos.get(0).getVersion());
        eventoEspelho.setIdInstalacao(eventos.get(0).getIdInstalacao());
        eventoEspelho.setIdEquipamento(eventos.get(0).getIdEquipamento());
        eventoEspelho.setDataVerificada(localCalendar.getTime());
        eventoEspelho.setComentarios(eventos.get(0).getComentarios());
        eventoEspelho.setJustificativaRestricaoDesligamento(eventos.get(0).getJustificativaRestricaoDesligamento());
        eventoEspelho.setStatus(eventos.get(0).getStatus());
        eventoEspelho.setDataUltimaConsolidacao(eventos.get(0).getDataUltimaConsolidacao());
        eventoEspelho.setTipoOperacao(eventos.get(0).getTipoOperacao());
        eventoEspelho.setDataCriacao(eventos.get(0).getDataCriacao());
        eventoEspelho.setValorPotenciaDisponivel(eventos.get(0).getValorPotenciaDisponivel());
        eventoEspelho.setEstadoOperativo(eventos.get(0).getEstadoOperativo());
        eventoEspelho.setCondicaoOperativa(eventos.get(0).getCondicaoOperativa());
        eventoEspelho.setOrigem(eventos.get(0).getOrigem());                
        return eventoEspelho;        
	}
    
	public List<Evento> findEventosTipSinc(ZonedDateTime dtInicio, ZonedDateTime dtFim, List<UsinaDTO> instalacoes) {
		log.debug("Request to get all Evento by period and instalacoes");
		
		List<String> idInstlacoes = new ArrayList<>();
		for (UsinaDTO usinaDTO : instalacoes) {
			idInstlacoes.add(usinaDTO.getId());
			System.out.println("findEventosDispVer: " + usinaDTO.getId());
		}
        
        QEvento pred = new QEvento("evento");
        
        return IterableConverter.toList(eventoRepository.findAll(
        		pred.idInstalacao.in(idInstlacoes)
        		.and(pred.dataVerificada.between(Date.from(dtInicio.toInstant()), Date.from(dtFim.toInstant())))
//        		.and(pred.origem.eq("GOO").not()).and(pred.origem.eq("GOU").not()).and(pred.origem.eq("GRB").not())
        		, sortByDataIdEquipAsc()));
	}
    
	public Evento findEventoEspelhoTipSinc(Date dataEventoEspelho, String idEquipamento) {
		log.debug("Request to get all Evento by period and instalacoes");

        QEvento pred = new QEvento("evento");
        
        List<Evento> eventos = IterableConverter.toList(eventoRepository.findAll(
        		pred.idEquipamento.in(idEquipamento)
        		.and(pred.dataVerificada.before(dataEventoEspelho))
        		//.and(pred.origem.eq("GOO").not()).and(pred.origem.eq("GOU").not()).and(pred.origem.eq("GRB").not()) 
        		.and(pred.tipoOperacao.ne(TipoOperacao.C)),
        		// .and(pred.tipoOperacao.ne(TipoOperacao.O)),
        		sortByDataDesc()));
        
        if (eventos.isEmpty()) return null;
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(dataEventoEspelho);
        localCalendar.set(Calendar.DAY_OF_MONTH, 1);
        localCalendar.set(Calendar.HOUR, 0);
        localCalendar.set(Calendar.MINUTE, 0);
        localCalendar.set(Calendar.SECOND, 0);
    	// System.out.println("findEventoEspelhoDispVer localCalendar.getTime(): " + localCalendar.getTime());
        Evento eventoEspelho = new Evento();
        eventoEspelho.setIdEvento(eventos.get(0).getIdEvento());
        eventoEspelho.setVersion(eventos.get(0).getVersion());
        eventoEspelho.setIdInstalacao(eventos.get(0).getIdInstalacao());
        eventoEspelho.setIdEquipamento(eventos.get(0).getIdEquipamento());
        eventoEspelho.setDataVerificada(localCalendar.getTime());
        eventoEspelho.setComentarios(eventos.get(0).getComentarios());
        eventoEspelho.setJustificativaRestricaoDesligamento(eventos.get(0).getJustificativaRestricaoDesligamento());
        eventoEspelho.setStatus(eventos.get(0).getStatus());
        eventoEspelho.setDataUltimaConsolidacao(eventos.get(0).getDataUltimaConsolidacao());
        eventoEspelho.setTipoOperacao(eventos.get(0).getTipoOperacao());
        eventoEspelho.setDataCriacao(eventos.get(0).getDataCriacao());
        eventoEspelho.setValorPotenciaDisponivel(eventos.get(0).getValorPotenciaDisponivel());
        eventoEspelho.setEstadoOperativo(eventos.get(0).getEstadoOperativo());
        eventoEspelho.setCondicaoOperativa(eventos.get(0).getCondicaoOperativa());
        eventoEspelho.setOrigem(eventos.get(0).getOrigem());                
        return eventoEspelho;        
	}

    private Sort sortByDataIdEquipAsc() {
        return new Sort(Sort.Direction.ASC, "dataVerificada").and(new Sort(Sort.Direction.ASC, "idEquipamento"));
    }

    private Sort sortByDataAsc() {
        return new Sort(Sort.Direction.ASC, "dataVerificada");
    }

    private Sort sortByDataDesc() {
        return new Sort(Sort.Direction.DESC, "dataVerificada");
    }
	
}
