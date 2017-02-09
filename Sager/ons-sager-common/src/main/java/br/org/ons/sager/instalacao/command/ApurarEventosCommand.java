package br.org.ons.sager.instalacao.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.platform.common.Command;

/**
 * Comando para apurar eventos de mudança de estado operativo de uma instalação
 * em um determinado período
 */
public class ApurarEventosCommand extends Command {

	private Date dataInicio;
	private Date dataFim;
	private List<EventoMudancaEstadoOperativo> eventos = new ArrayList<>();

	/**
	 * @return o campo dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio
	 *            o campo dataInicio a ser definido
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return o campo dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim
	 *            o campo dataFim a ser definido
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return o campo eventos
	 */
	public List<EventoMudancaEstadoOperativo> getEventos() {
		return eventos;
	}

	/**
	 * @param eventos
	 *            o campo eventos a ser definido
	 */
	public void setEventos(List<EventoMudancaEstadoOperativo> eventos) {
		this.eventos = eventos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApurarEventosCommand [dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", eventos=" + eventos + "]";
	}
}
