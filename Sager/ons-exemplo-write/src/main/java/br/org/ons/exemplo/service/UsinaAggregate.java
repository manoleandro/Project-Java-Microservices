package br.org.ons.exemplo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import br.org.ons.exemplo.common.Apuracao;
import br.org.ons.exemplo.common.ApurarEventosCommand;
import br.org.ons.exemplo.common.ApurarEventosCommandModification;
import br.org.ons.exemplo.common.AtualizarUsinaCommand;
import br.org.ons.exemplo.common.CriarUsinaCommand;
import br.org.ons.exemplo.common.Evento;
import br.org.ons.exemplo.common.EventosApuradosEvent;
import br.org.ons.exemplo.common.ExcluirUsinaCommand;
import br.org.ons.exemplo.common.Parametro;
import br.org.ons.exemplo.common.ParametrosCalculadosEvent;
import br.org.ons.exemplo.common.Taxa;
import br.org.ons.exemplo.common.TaxasCalculadasEvent;
import br.org.ons.exemplo.common.Usina;
import br.org.ons.exemplo.common.UsinaAtualizadaEvent;
import br.org.ons.exemplo.common.UsinaCriadaEvent;
import br.org.ons.exemplo.common.UsinaExcluidaEvent;
import br.org.ons.platform.common.ScenarioCommandModification;

/**
 * Classe de exemplo de implementação de aggregate
 */
public class UsinaAggregate extends Aggregate {

	/**
	 * Estado da entidade usina
	 */
	private Usina usina;
	
	/**
	 * Estado das apurações de taxa da usina
	 */
	private List<Apuracao> apuracoes = new ArrayList<>();

	/**
	 * @param criarUsina
	 *            Comando para criar usina
	 */
	@SuppressWarnings("unused")
	private void handle(CriarUsinaCommand criarUsina) {
		Assert.isNull(usina, "Comando inválido: Usina já existente " + criarUsina.getUsina());
		play(new UsinaCriadaEvent(criarUsina.getUsina()));
	}

	/**
	 * @param atualizarUsina
	 *            Comando para atualizar usina
	 */
	@SuppressWarnings("unused")
	private void handle(AtualizarUsinaCommand atualizarUsina) {
		Assert.notNull(usina, "Comando inválido: Usina já excluída " + atualizarUsina.getUsina());
		play(new UsinaAtualizadaEvent(atualizarUsina.getUsina()));
	}

	/**
	 * @param excluirUsina
	 *            Comando para excluir usina
	 */
	@SuppressWarnings("unused")
	private void handle(ExcluirUsinaCommand excluirUsina) {
		Assert.notNull(usina, "Comando inválido: Usina já excluída");
		play(new UsinaExcluidaEvent());
	}

	/**
	 * Apura os eventos e dispara os cálculos de parâmetros e taxas
	 * 
	 * @param apurarEventos
	 *            Comando para apurar eventos
	 */
	@SuppressWarnings("unused")
	private void handle(ApurarEventosCommand apurarEventos) {
		Assert.notNull(usina, "Comando inválido: Usina já excluída");
		for (Apuracao apuracao : apuracoes) {
			Assert.state(
					!(apuracao.getDataInicio().equals(apurarEventos.getDataInicio())
							&& apuracao.getDataFim().equals(apurarEventos.getDataFim())),
					"Comando inválido: Eventos já apurados no período de " + apurarEventos.getDataInicio() + " a "
							+ apurarEventos.getDataFim());
		}
		play(new EventosApuradosEvent(apurarEventos.getDataInicio(), apurarEventos.getDataFim(),
				apurarEventos.getEventos()));
		for (Apuracao a : apuracoes) {
			log.debug("[CalcularParametrosCommand] apuracao?: {}", a);
			if (a.getDataInicio().equals(apurarEventos.getDataInicio())
					&& a.getDataFim().equals(apurarEventos.getDataFim())) {
				play(new ParametrosCalculadosEvent(apurarEventos.getDataInicio(),
						apurarEventos.getDataFim(), calcularParametros(a.getEventos())));

				play(new TaxasCalculadasEvent(apurarEventos.getDataInicio(), apurarEventos.getDataFim(),
						calcularTaxas(a.getParametros())));
				break;
			}
		}
	}

	/**
	 * @param event
	 *            Aplica evento de usina criada ao estado do aggregate
	 */
	@SuppressWarnings("unused")
	private void when(UsinaCriadaEvent event) {
		usina = event.getUsina();
		setName(event.getUsina().getNomeCurto());
	}
	
	/**
	 * @param event
	 *            Aplica evento de usina atualizada ao estado do aggregate
	 */
	@SuppressWarnings("unused")
	private void when(UsinaAtualizadaEvent event) {
		usina = event.getUsina();
	}
	
	/**
	 * @param event
	 *            Aplica evento de usina excluída ao estado do aggregate
	 */
	@SuppressWarnings("unused")
	private void when(UsinaExcluidaEvent event) {
		usina = null;
	}
	
	/**
	 * @param event
	 *            Aplica evento de eventos apurados ao estado do aggregate
	 */
	@SuppressWarnings("unused")
	private void when(EventosApuradosEvent event) {
		Apuracao apuracao = new Apuracao();
		apuracao.setDataInicio(event.getDataInicio());
		apuracao.setDataFim(event.getDataFim());
		apuracao.setEventos(event.getEventos());
		apuracao.setStatus(Apuracao.Status.EVENTOS_APURADOS);
		apuracao.getEventos().sort((p1, p2) -> p1.getDataVerificada().compareTo(p2.getDataVerificada()));
		apuracoes.add(apuracao);
		apuracoes.sort((p1, p2) -> p1.getDataInicio().compareTo(p2.getDataInicio()));
	}
	
	/**
	 * @param event
	 *            Aplica evento de parâmetros calculados ao estado do aggregate
	 */
	@SuppressWarnings("unused")
	private void when(ParametrosCalculadosEvent event) {
		for (Apuracao apuracao : apuracoes) {
			if (apuracao.getDataInicio().equals(event.getDataInicio()) &&
					apuracao.getDataFim().equals(event.getDataFim())) {
				apuracao.setEventos(null);
				apuracao.setParametros(event.getParametros());
				apuracao.setStatus(Apuracao.Status.PARAMETROS_CALCULADOS);
			}
		}
	}
	
	/**
	 * @param event
	 *            Aplica eventos de taxas calculadas ao estado do aggregate
	 */
	@SuppressWarnings("unused")
	private void when(TaxasCalculadasEvent event) {
		for (Apuracao apuracao : apuracoes) {
			if (apuracao.getDataInicio().equals(event.getDataInicio()) &&
					apuracao.getDataFim().equals(event.getDataFim())) {
				apuracao.setParametros(null);
				apuracao.setTaxas(event.getTaxas());
				apuracao.setStatus(Apuracao.Status.TAXAS_CALCULADAS);
			}
		}
	}

	/**
	 * Modifica comando para apurar eventos
	 * 
	 * @param command
	 *            Comando
	 * @param modification
	 *            Modificação
	 */
	@SuppressWarnings("unused")
	private void modify(ApurarEventosCommand command, ApurarEventosCommandModification modification) {
		command.getEventos().removeAll(modification.getEventosModificados());
		command.getEventos().addAll(modification.getEventosModificados());
		command.getEventos().removeAll(modification.getEventosExcluidos());
		command.getEventos().addAll(modification.getEventosAdicionados());
	}

	/**
	 * Modifica o comando através da regra de cenário
	 * 
	 * @param command
	 *            Comando
	 * @param modification
	 *            Modificação
	 */
	@SuppressWarnings("unused")
	private void modify(ApurarEventosCommand command, ScenarioCommandModification modification) {
		log.debug("-- modify(ApurarEventosCommand, ScenarioCommandModification)");
		log.debug("--- command: {}", command);
		log.debug("--- modification: {}", modification);
		// TODO: Invocar regra do IBM ODM
		if ("Cenario 1".equals(modification.getScenarioName())) {
			log.debug("--- Case Cenario 1 (GTR)");
			for (Evento evento : command.getEventos()) {
				log.debug("--- Evento.origem: {}", evento.getClassificacaoOrigem());
				if ("GTR".equals(evento.getClassificacaoOrigem())) {
					log.debug("--- modified: {}", evento);
					evento.setClassificacaoOrigem("GOT");
				}
			}
		}
	}

	/**
	 * @return o campo usina
	 */
	public Usina getUsina() {
		return usina;
	}

	/**
	 * @param usina o campo usina a ser definido
	 */
	public void setUsina(Usina usina) {
		this.usina = usina;
	}

	/**
	 * @return o campo apuracoes
	 */
	public List<Apuracao> getApuracoes() {
		return apuracoes;
	}

	/**
	 * @param apuracoes o campo apuracoes a ser definido
	 */
	public void setApuracoes(List<Apuracao> apuracoes) {
		this.apuracoes = apuracoes;
	}

	@Override
    public String toString() {
        return "UsinaAggregate{" +
	            "id='" + getId() + "'" +
	            ", majorVersion='" + getMajorVersion() + "'" +
	            ", minorVersion='" + getMinorVersion() + "'" +
	            ", usina='" + usina + "'" +
	            ", apuracoes='" + apuracoes + "'" +
	            '}';
    }

	private List<Parametro> calcularParametros(List<Evento> eventos) { //NOSONAR
		// TODO: Invocar regra do IBM ODM
		List<Parametro> parametros = new ArrayList<>();
		Parametro hp = new Parametro();
		hp.setCodigo("HP");
		hp.setValor(720.0);
		hp.setEventos(eventos);
		parametros.add(hp);
		Parametro hdp = new Parametro();
		hdp.setCodigo("HDP");
		hdp.setValor(2.5);
		hdp.setEventos(eventos);
		parametros.add(hdp);
		Parametro hedp = new Parametro();
		hedp.setCodigo("HEDP");
		hedp.setValor(2.5);
		hedp.setEventos(eventos);
		parametros.add(hedp);
		return parametros;
	}

	private List<Taxa> calcularTaxas(List<Parametro> parametros) { //NOSONAR
		// TODO: Invocar regra do IBM ODM
		List<Taxa> taxas = new ArrayList<>();
		Taxa teip = new Taxa();
		teip.setCodigo("TEIP");
		teip.setValor(0.12345678);
		teip.setParametros(parametros);
		taxas.add(teip);
		Taxa teipOper = new Taxa();
		teipOper.setCodigo("TEIP oper");
		teipOper.setValor(0.87654321);
		teipOper.setParametros(parametros);
		taxas.add(teipOper);
		return taxas;
	}
}
