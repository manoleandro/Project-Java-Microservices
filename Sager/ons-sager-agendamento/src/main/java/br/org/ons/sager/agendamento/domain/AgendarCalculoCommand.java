package br.org.ons.sager.agendamento.domain;

import br.org.ons.platform.common.Command;

public class AgendarCalculoCommand extends Command {
	
	private AgendamentoCalculo agendamentoCalculo;

	public AgendamentoCalculo getAgendamentoCalculo() {
		return agendamentoCalculo;
	}

	public void setAgendamentoCalculo(AgendamentoCalculo agendamentoCalculo) {
		this.agendamentoCalculo = agendamentoCalculo;
	}

	@Override
	public String toString() {
		return "AgendarCalculoCommand [agendamentoCalculo=" + agendamentoCalculo.toString() + "]";
	}

}
