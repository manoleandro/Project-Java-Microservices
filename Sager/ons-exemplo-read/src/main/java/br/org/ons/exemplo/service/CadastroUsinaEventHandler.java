package br.org.ons.exemplo.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.exemplo.common.Apuracao;
import br.org.ons.exemplo.common.EventosApuradosEvent;
import br.org.ons.exemplo.common.ParametrosCalculadosEvent;
import br.org.ons.exemplo.common.TaxasCalculadasEvent;
import br.org.ons.exemplo.common.UsinaAtualizadaEvent;
import br.org.ons.exemplo.common.UsinaCriadaEvent;
import br.org.ons.exemplo.common.UsinaExcluidaEvent;
import br.org.ons.exemplo.domain.CadastroUsina;
import br.org.ons.exemplo.repository.CadastroUsinaRepository;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;

/**
 * Exemplo de serviço EventHandler, que recebe eventos publicados no barramento
 * e atualiza o repositório de dados de leitura
 */
@Component
public class CadastroUsinaEventHandler {

	private final Logger log = LoggerFactory.getLogger(CadastroUsinaEventHandler.class);

	@Inject
	private CadastroUsinaRepository cadastroUsinaRepository;

	@Inject
	private ObjectMapper objectMapper;

	/**
	 * Recebe eventos de UsinaCriadaEvent e insere novos registros de
	 * CadastroUsina no repositório de dados de leitura
	 * 
	 * @param eventMessage
	 *            Evento
	 * @throws JMSException
	 */
	@JmsListener(destination = "platform/EventTopic/br.org.ons.exemplo.common.UsinaCriadaEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleUsinaCriadaEvent(EventMessage<UsinaCriadaEvent> eventMessage) throws JMSException {
		log.debug("handleUsinaCriadaEvent: {}", eventMessage);
		
		UsinaCriadaEvent usinaCriada = eventMessage.getEvent();

		if (!cadastroUsinaRepository.exists(usinaCriada.getUsina().getId())) {
			CadastroUsina cadastro = new CadastroUsina();
			cadastro.setId(usinaCriada.getUsina().getId());
			cadastro.setNomeCurto(usinaCriada.getUsina().getNomeCurto());
			cadastro.setTipoUsina(usinaCriada.getUsina().getTipoUsina());
			cadastro.setPotenciaCalculo(usinaCriada.getUsina().getPotenciaCalculo());
			cadastro.setVersion(
					eventMessage.getMetadata().getMajorVersion() + "." + eventMessage.getMetadata().getMinorVersion());
			try {
				cadastro.setApuracoes(objectMapper.writeValueAsString(new ArrayList<Apuracao>()));
			} catch (JsonProcessingException e) {
				log.error("Erro ao serializar apurações", e);
			}
			cadastroUsinaRepository.save(cadastro);
		} else {
			log.error("Usina já existente: {}", usinaCriada.getUsina().getId());
		}
	}
	
	/**
	 * Recebe eventos de UsinaAtualizadaEvent e atualiza registros existentes de
	 * CadastroUsina no repositório de dados de leitura
	 * 
	 * @param eventMessage
	 *            Evento
	 * @throws JMSException
	 */
	@JmsListener(destination = "platform/EventTopic/br.org.ons.exemplo.common.UsinaAtualizadaEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleUsinaAtualizadaEvent(EventMessage<UsinaAtualizadaEvent> eventMessage) throws JMSException {
		log.debug("handleUsinaAtualizadaEvent: {}", eventMessage);
		
		UsinaAtualizadaEvent usinaAtualizada = eventMessage.getEvent();
		
		if (cadastroUsinaRepository.exists(usinaAtualizada.getUsina().getId())) {
			CadastroUsina cadastro = cadastroUsinaRepository.findOne(eventMessage.getMetadata().getAggregateId());
			cadastro.setNomeCurto(usinaAtualizada.getUsina().getNomeCurto());
			cadastro.setTipoUsina(usinaAtualizada.getUsina().getTipoUsina());
			cadastro.setPotenciaCalculo(usinaAtualizada.getUsina().getPotenciaCalculo());
			cadastro.setVersion(
					eventMessage.getMetadata().getMajorVersion() + "." + eventMessage.getMetadata().getMinorVersion());
			cadastroUsinaRepository.save(cadastro);
		} else {
			log.error("Usina não existente ao atualizar: {}", usinaAtualizada.getUsina().getId());
		}
	}
	
	/**
	 * Recebe eventos de UsinaExcluidaEvent e exclui registros existentes de
	 * CadastroUsina do repositório de dados de leitura
	 * 
	 * @param eventMessage
	 *            Evento
	 * @throws JMSException
	 */
	@JmsListener(destination = "platform/EventTopic/br.org.ons.exemplo.common.UsinaExcluidaEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleUsinaExcluidaEvent(EventMessage<UsinaExcluidaEvent> eventMessage) throws JMSException {
		log.debug("handleUsinaExcluidaEvent: {}", eventMessage);
		
		if (cadastroUsinaRepository.exists(eventMessage.getMetadata().getAggregateId())) {
			cadastroUsinaRepository.delete(eventMessage.getMetadata().getAggregateId());
		} else {
			log.warn("Usina não existente ao excluir: {}", eventMessage.getMetadata().getAggregateId());
		}
	}
	
	/**
	 * Recebe eventos de EventosApuradosEvent e adiciona os eventos apurados a
	 * um CadastroUsina existente no repositório de leitura
	 * 
	 * @param eventMessage
	 *            Evento
	 * @throws JMSException
	 */
	@JmsListener(destination = "platform/EventTopic/br.org.ons.exemplo.common.EventosApuradosEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleEventosApuradosEvent(EventMessage<EventosApuradosEvent> eventMessage) throws JMSException {
		log.debug("handleEventosApuradosEvent: {}", eventMessage);
		
		if (cadastroUsinaRepository.exists(eventMessage.getMetadata().getAggregateId())) {
			CadastroUsina cadastro = cadastroUsinaRepository.findOne(eventMessage.getMetadata().getAggregateId());
			List<Apuracao> apuracoes = readApuracoes(cadastro);
			boolean achou = false;
			for (Apuracao apuracao : apuracoes) {
				if (apuracao.getDataInicio().equals(eventMessage.getEvent().getDataInicio())
						&& apuracao.getDataFim().equals(eventMessage.getEvent().getDataFim())) {
					apuracao.setStatus(Apuracao.Status.EVENTOS_APURADOS);
					achou = true;
					break;
				}
			}
			if (!achou) {
				Apuracao apuracao = new Apuracao();
				apuracao.setDataInicio(eventMessage.getEvent().getDataInicio());
				apuracao.setDataFim(eventMessage.getEvent().getDataFim());
				apuracao.setStatus(Apuracao.Status.EVENTOS_APURADOS);
				apuracoes.add(apuracao);
			}
			cadastro.setApuracoes(writeApuracoes(apuracoes));
			cadastro.setVersion(eventMessage.getMetadata().getMajorVersion() + "."
					+ eventMessage.getMetadata().getMinorVersion());
			cadastroUsinaRepository.save(cadastro);
		} else {
			log.warn("Usina não existente ao apurar eventos: {}", eventMessage.getMetadata().getAggregateId());
		}
	}

	/**
	 * Recebe eventos de ParametrosCalculadosEvent e adiciona os parâmetros
	 * calculados a um CadastroUsina existente no repositório de leitura
	 * 
	 * @param eventMessage
	 *            Evento
	 * @throws JMSException
	 */
	@JmsListener(destination = "platform/EventTopic/br.org.ons.exemplo.common.ParametrosCalculadosEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleParametrosCalculadosEvent(EventMessage<ParametrosCalculadosEvent> eventMessage) throws JMSException {
		log.debug("handleParametrosCalculadosEvent: {}", eventMessage);
		
		if (cadastroUsinaRepository.exists(eventMessage.getMetadata().getAggregateId())) {
			CadastroUsina cadastro = cadastroUsinaRepository.findOne(eventMessage.getMetadata().getAggregateId());
			List<Apuracao> apuracoes = readApuracoes(cadastro);
			for (Apuracao apuracao : apuracoes) {
				if (apuracao.getDataInicio().equals(eventMessage.getEvent().getDataInicio())
						&& apuracao.getDataFim().equals(eventMessage.getEvent().getDataFim())) {
					apuracao.setStatus(Apuracao.Status.PARAMETROS_CALCULADOS);
					break;
				}
			}
			cadastro.setApuracoes(writeApuracoes(apuracoes));
			cadastro.setVersion(eventMessage.getMetadata().getMajorVersion() + "."
					+ eventMessage.getMetadata().getMinorVersion());
			cadastroUsinaRepository.save(cadastro);
		} else {
			log.warn("Usina não existente ao calcular parâmetros: {}", eventMessage.getMetadata().getAggregateId());
		}
	}

	/**
	 * Recebe eventos de TaxasCalculadasEvent e adiciona as taxas calculadas a
	 * um CadastroUsina existente no repositório de leitura
	 * 
	 * @param eventMessage Evento
	 * @throws JMSException
	 */
	@JmsListener(destination = "platform/EventTopic/br.org.ons.exemplo.common.TaxasCalculadasEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleTaxasCalculadasEvent(EventMessage<TaxasCalculadasEvent> eventMessage) throws JMSException {
		log.debug("handleTaxasCalculadasEvent: {}", eventMessage);
		
		if (cadastroUsinaRepository.exists(eventMessage.getMetadata().getAggregateId())) {
			CadastroUsina cadastro = cadastroUsinaRepository.findOne(eventMessage.getMetadata().getAggregateId());
			List<Apuracao> apuracoes = readApuracoes(cadastro);
			for (Apuracao apuracao : apuracoes) {
				if (apuracao.getDataInicio().equals(eventMessage.getEvent().getDataInicio())
						&& apuracao.getDataFim().equals(eventMessage.getEvent().getDataFim())) {
					apuracao.setStatus(Apuracao.Status.TAXAS_CALCULADAS);
					apuracao.setTaxas(eventMessage.getEvent().getTaxas());
					break;
				}
			}
			cadastro.setApuracoes(writeApuracoes(apuracoes));
			cadastro.setVersion(eventMessage.getMetadata().getMajorVersion() + "."
					+ eventMessage.getMetadata().getMinorVersion());
			cadastroUsinaRepository.save(cadastro);
		} else {
			log.warn("Usina não existente ao calcular taxas: {}", eventMessage.getMetadata().getAggregateId());
		}
	}

	private List<Apuracao> readApuracoes(CadastroUsina cadastro) {
		try {
			return objectMapper.readValue(cadastro.getApuracoes(), new TypeReference<List<Apuracao>>() {});
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		}
	}

	private String writeApuracoes(List<Apuracao> apuracoes) {
		try {
			return objectMapper.writeValueAsString(apuracoes);
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		}
	}
}
