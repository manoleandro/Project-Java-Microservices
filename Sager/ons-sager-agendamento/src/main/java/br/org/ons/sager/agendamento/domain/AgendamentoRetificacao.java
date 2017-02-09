package br.org.ons.sager.agendamento.domain;

import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

/**
 * Representa o agendamento de uma execução de reprocessamento em cascata
 * resultante da retificação de eventos de mudança de estado operativo de uma
 * instalação
 */
@QueryEntity
@KeySpace("AgendamentoRetificacao")
public class AgendamentoRetificacao extends Agendamento {

	private String numeroTarefa;
	private String tarefa;

	/**
	 * @return o campo numeroTarefa
	 */
	public String getNumeroTarefa() {
		return numeroTarefa;
	}

	/**
	 * @param numeroTarefa o campo numeroTarefa a ser definido
	 */
	public void setNumeroTarefa(String numeroTarefa) {
		this.numeroTarefa = numeroTarefa;
	}

	public String getTarefa() {
		return tarefa;
	}

	public void setTarefa(String tarefa) {
		this.tarefa = tarefa;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AgendamentoRetificacao [numeroTarefa=" + numeroTarefa + ", getId()=" + getId() + ", getIdInstalacao()="
				+  ", getDataAgendamento()=" + getDataAgendamento() + ", getSituacao()="
				+ getSituacao() + ", getResultado()=" + getResultado() + ", getSolicitante()=" + getSolicitante() + "]";
	}
}
