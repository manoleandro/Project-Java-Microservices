package br.org.ons.exemplo.service;

import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import br.org.ons.exemplo.common.Evento;
import br.org.ons.exemplo.common.EventosApuradosEvent;
import br.org.ons.exemplo.domain.CadastroEvento;
import br.org.ons.exemplo.repository.CadastroEventoRepository;
import br.org.ons.platform.common.EventMessage;

/**
 * Exemplo de serviço EventHandler, que recebe eventos publicados no barramento
 * e atualiza o repositório de dados de leitura
 */
@Component
public class CadastroEventoEventHandler {

	private final Logger log = LoggerFactory.getLogger(CadastroEventoEventHandler.class);

	@Inject
	private CadastroEventoRepository cadastroEventoRepository;

	/**
	 * Recebe eventos de EventosApuradosEvent e adiciona/atualiza registros de
	 * CadastroEvento ao repositório de dados de leitura
	 * 
	 * @param eventMessage
	 *            Evento
	 * @throws JMSException
	 */
	@JmsListener(destination = "platform/EventTopic/br.org.ons.exemplo.common.EventosApuradosEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleEventosApuradosEvent(EventMessage<EventosApuradosEvent> eventMessage) throws JMSException {
		log.debug("handleEventosApuradosEvent: {}", eventMessage);
		
		List<Evento> eventos = eventMessage.getEvent().getEventos();
		for (Evento evento : eventos) {
			CadastroEvento cadastro = new CadastroEvento();
			cadastro.setId(evento.getId());
			cadastro.setDataVerificada(evento.getDataVerificada());
			cadastro.setEstadoOperativo(evento.getEstadoOperativo());
			cadastro.setCondicaoOperativa(evento.getCondicaoOperativa());
			cadastro.setClassificacaoOrigem(evento.getClassificacaoOrigem());
			cadastro.setPotenciaDisponivel(evento.getPotenciaDisponivel());
			cadastro.setAggregateId(eventMessage.getMetadata().getAggregateId());
			cadastro.setMajorVersion(eventMessage.getMetadata().getMajorVersion());
			cadastro.setMinorVersion(eventMessage.getMetadata().getMinorVersion());
			cadastro.setTimelineDate(eventMessage.getMetadata().getTimelineDate());
			cadastro.setCorrelationId(eventMessage.getMetadata().getCorrelationId());
			log.debug("Novo CadastroEvento: {}", cadastro);
			cadastroEventoRepository.save(cadastro);
		}
	}
}
