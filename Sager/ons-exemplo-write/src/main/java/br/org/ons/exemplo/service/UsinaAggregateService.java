package br.org.ons.exemplo.service;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.exemplo.common.ApurarEventosCommand;
import br.org.ons.exemplo.common.AtualizarUsinaCommand;
import br.org.ons.exemplo.common.CriarUsinaCommand;
import br.org.ons.exemplo.common.ExcluirUsinaCommand;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.ResultMessage;

/**
 * Serviço REST de exemplo para o aggregate UsinaAggregate
 */
@RestController
@RequestMapping("/api/usina-aggregates")
public class UsinaAggregateService extends AggregateService<UsinaAggregate> {

	@RequestMapping(
			value = "/criar-usina-command", 
			method = RequestMethod.POST,
		    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage<Void>> criarUsina(@RequestBody CommandMessage<CriarUsinaCommand> message) {
		log.debug("POST criarUsina : {}", message);
		return execute(message);
	}

	@RequestMapping(
			value = "/atualizar-usina-command", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage<Void>> atualizarUsina(
			@RequestBody CommandMessage<AtualizarUsinaCommand> message) {
		log.debug("POST atualizarUsina : {}", message);
		return execute(message);
	}

	@RequestMapping(
			value = "/excluir-usina-command", 
			method = RequestMethod.POST,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage<Void>> excluirUsina(@RequestBody CommandMessage<ExcluirUsinaCommand> message) {
		log.debug("POST excluirUsina : {}", message);
		return execute(message);
	}

	@RequestMapping(
			value = "/apurar-eventos-command", 
			method = RequestMethod.POST,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage<Void>> apurarEventos(
			@RequestBody CommandMessage<ApurarEventosCommand> message) {
		log.debug("POST apurarEventos : {}", message);
		return execute(message);
	}
}
