package br.org.ons.sager.instalacao.service;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.sager.instalacao.command.ApurarEventosCommand;
import br.org.ons.sager.instalacao.command.CadastrarEquipamentoCommand;
import br.org.ons.sager.instalacao.command.CadastrarInstalacaoCommand;
import br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand;
import br.org.ons.sager.instalacao.command.CalcularTaxasCommand;
import br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeResponse;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoResponse;

/**
 * Serviço REST para o aggregate InstalacaoAggregate
 */
@RestController
@RequestMapping("/api/instalacao-aggregate")
public class InstalacaoAggregateService extends AggregateService<InstalacaoAggregate> {

	@RequestMapping(
			value = "/cadastrar-instalacao-command", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage<Void>> cadastrarInstalacao(@RequestBody CommandMessage<CadastrarInstalacaoCommand> message) {
		log.debug("POST cadastrarInstalacao : {}", message);
		return execute(message);
	}
	
	@RequestMapping(
			value = "/cadastrar-equipamento-command", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage<Void>> cadastrarEquipamento(@RequestBody CommandMessage<CadastrarEquipamentoCommand> message) {
		log.debug("POST cadastrarEquipamento : {}", message);
		return execute(message);
	}
	
	@RequestMapping(
			value = "/apurar-eventos-command", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage<Void>> apurarEventos(@RequestBody CommandMessage<ApurarEventosCommand> message) {
		log.debug("POST apurarEventos : {}", message);
		return execute(message);
	}

	@RequestMapping(
			value = "/calcular-taxas-command", 
			method = RequestMethod.POST,
		    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage<Void>> calcularTaxas(@RequestBody CommandMessage<CalcularTaxasCommand> message) {
		log.debug("POST calcularTaxas : {}", message);
		return execute(message);
	}
	
	@RequestMapping(
			value = "/calcular-disponibilidades-command", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage<CalcularDisponibilidadeResponse>> calcularDisponibilidades(
			@RequestBody CommandMessage<CalcularDisponibilidadesCommand> message) {
		log.debug("POST calcularDisponibilidades : {}", message);
		return execute(message);
	}
	
	@RequestMapping(
			value = "/verificar-situacao-instalacao-command", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage<VerificarSituacaoInstalacaoResponse>> virificarSituacaoInstalacao(
			@RequestBody CommandMessage<VerificarSituacaoInstalacaoCommand> message) {
		log.debug("POST virificarSituacaoInstalacao : {}", message);
		return execute(message);
	}
}
