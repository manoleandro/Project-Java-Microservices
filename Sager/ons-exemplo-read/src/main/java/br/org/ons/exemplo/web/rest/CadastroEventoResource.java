package br.org.ons.exemplo.web.rest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.exemplo.common.ApurarEventosCommand;
import br.org.ons.exemplo.common.ApurarEventosCommandModification;
import br.org.ons.exemplo.common.Evento;
import br.org.ons.exemplo.config.mq.CommandBus;
import br.org.ons.exemplo.domain.CadastroEvento;
import br.org.ons.exemplo.repository.CadastroEventoRepository;
import br.org.ons.exemplo.web.rest.dto.CadastroEventoRetificado;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CommandModification;
import br.org.ons.platform.common.ReprocessCommand;
import br.org.ons.platform.common.util.IdGenerator;

/**
 * REST controller for managing CadastroEvento.
 */
@RestController
@RequestMapping("/api")
public class CadastroEventoResource {

    private final Logger log = LoggerFactory.getLogger(CadastroEventoResource.class);
        
    @Inject
    private CadastroEventoRepository cadastroEventoRepository;

	@Inject
	private CommandBus commandBus;

	@RequestMapping(value = "/cadastro-eventos/apuracao", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> apurarEventos(@RequestParam String usinaId, @RequestParam String usinaVersion,
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime mesAnoApuracao) {
        log.debug("REST request to post apuracao " + usinaId + " " + usinaVersion + " " + mesAnoApuracao);
		ZonedDateTime dataInicio = ZonedDateTime.of(mesAnoApuracao.getYear(), mesAnoApuracao.getMonthValue(), 1, 0, 0, 0, 0, ZoneId.systemDefault());
		ZonedDateTime dataFim = dataInicio.plusMonths(1).minusDays(1);
		
		List<Evento> eventos = new ArrayList<>();
		eventos.add(new Evento(IdGenerator.newId(), dataInicio, "LIG", "NOR", "-", 100.0));
		eventos.add(new Evento(IdGenerator.newId(), dataInicio.plusDays(15).plusHours(10), "DPR", "-", "GTR", 0.0));
		eventos.add(new Evento(IdGenerator.newId(), dataInicio.plusDays(15).plusHours(12), "LIG", "NOR", "-", 100.0));
		
		ApurarEventosCommand command = new ApurarEventosCommand();
		command.setDataInicio(dataInicio);
		command.setDataFim(dataFim);
		command.setEventos(eventos);
		
		CommandMessage<ApurarEventosCommand> commandMessage = new CommandMessage<>();
		commandMessage.setCommand(command);
		commandMessage.getMetadata().setId(IdGenerator.newId());
		commandMessage.getMetadata().setCorrelationId(null);
		commandMessage.getMetadata().setAggregateId(usinaId);
		commandMessage.getMetadata().setMajorVersion(Long.valueOf(usinaVersion.split("\\.")[0]));
		commandMessage.getMetadata().setMinorVersion(Long.valueOf(usinaVersion.split("\\.")[1]));
		commandMessage.getMetadata().setTimelineDate(ZonedDateTime.now());
		commandBus.send(commandMessage);
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/cadastro-eventos/retificacao", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> retificarEventos(@RequestParam String usinaId,
			@RequestBody List<CadastroEventoRetificado> cadastroEventos) {
        log.debug("REST request to post retificacao " + usinaId + " " + cadastroEventos);
		cadastroEventos.sort(Comparator.comparing(CadastroEventoRetificado::getDataVerificada));
        
        List<ApurarEventosCommandModification> modifications = new ArrayList<>();
        
        ApurarEventosCommandModification modification = new ApurarEventosCommandModification();
    	modification.setCorrelationId(cadastroEventos.get(0).getCorrelationId());
    	modifications.add(modification);
    	log.debug("**** firstModificationCorrelationId: " + modification.getCorrelationId());
        
    	ZonedDateTime firstModificationDate = null;
    	ZonedDateTime modificationDate = cadastroEventos.get(0).getTimelineDate();
    	for (CadastroEventoRetificado cadastroEvento : cadastroEventos) {
    		log.debug("**** cadastroEvento: " + cadastroEvento);
			if (!cadastroEvento.getCorrelationId().equals(modification.getCorrelationId())) {
	    		log.debug("**** otherCorrelationId: " + cadastroEvento.getCorrelationId());
				modification = new ApurarEventosCommandModification();
				modification.setCorrelationId(cadastroEvento.getCorrelationId());
				modifications.add(modification);
				modificationDate = cadastroEvento.getTimelineDate();
			}
        	if (cadastroEvento.getId() == null) {
        		log.debug("**** cadastroEvento novo");
        		modification.getEventosAdicionados()
						.add(eventoFromCadastroEventoRetificado(cadastroEvento));
				firstModificationDate = firstModificationDate == null ? modificationDate : firstModificationDate;
        	} else if (cadastroEvento.isDeleted()) {
        		log.debug("**** cadastroEvento excluido");
				modification.getEventosExcluidos()
						.add(eventoFromCadastroEventoRetificado(cadastroEvento));
				firstModificationDate = firstModificationDate == null ? modificationDate : firstModificationDate;
        	} else if (cadastroEvento.isDirty()) {
        		log.debug("**** cadastroEvento modificado");
				modification.getEventosModificados()
						.add(eventoFromCadastroEventoRetificado(cadastroEvento));
				firstModificationDate = firstModificationDate == null ? modificationDate : firstModificationDate;
        	} else {
        		log.debug("**** cadastroEvento limpo");
        	}
        }
        
        // Adiciona ao comando somente os modificadores não vazios
		ReprocessCommand<CommandModification> command = new ReprocessCommand<>();
		// Se o modificador estiver vazio, não adiciona ao comando
		modifications.forEach(mod -> {
			if (!isEmpty(mod)) {
				log.debug("**** Adding mod: " + mod);
				command.addModification(mod);
			}
		});

		CommandMessage<ReprocessCommand<CommandModification>> commandMessage = new CommandMessage<>();
		commandMessage.setCommand(command);
		commandMessage.getMetadata().setId(IdGenerator.newId());
		commandMessage.getMetadata().setCorrelationId(null);
		commandMessage.getMetadata().setAggregateId(usinaId);
		commandMessage.getMetadata().setMajorVersion(null);
		commandMessage.getMetadata().setMinorVersion(null);
		// Seta o branch point para a timelineDate do primeiro evento retificado
		commandMessage.getMetadata().setTimelineDate(firstModificationDate);
		commandBus.send(commandMessage);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private boolean isEmpty(ApurarEventosCommandModification mod) {
		return mod.getEventosAdicionados().isEmpty() && mod.getEventosModificados().isEmpty()
				&& mod.getEventosExcluidos().isEmpty();
	}

	private Evento eventoFromCadastroEventoRetificado(CadastroEventoRetificado cadastroEvento) {
		return new Evento(IdGenerator.newId(), cadastroEvento.getDataVerificada(),
				cadastroEvento.getEstadoOperativo(), cadastroEvento.getCondicaoOperativa(),
				cadastroEvento.getClassificacaoOrigem(), cadastroEvento.getPotenciaDisponivel());
	}
	
    @RequestMapping(value = "/cadastro-eventos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CadastroEvento> getCadastroEventosByUsina(@RequestParam(required = false) String usinaId) {
        log.debug("REST request to get CadastroEventos " + usinaId);
        List<CadastroEvento> cadastroEventos;
        if (usinaId == null) {
        	cadastroEventos = IterableConverter.toList(cadastroEventoRepository.findAll());
        } else {
        	cadastroEventos = cadastroEventoRepository.findByAggregateIdOrderByDataVerificada(usinaId);
        }
        return cadastroEventos;
    }
	
    /**
     * GET  /cadastro-eventos/:id : get the "id" cadastroEvento.
     *
     * @param id the id of the cadastroEvento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cadastroEvento, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cadastro-eventos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CadastroEvento> getCadastroEvento(@PathVariable String id) {
        log.debug("REST request to get CadastroEvento : {}", id);
        CadastroEvento cadastroEvento = cadastroEventoRepository.findOne(id);
        return Optional.ofNullable(cadastroEvento)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
