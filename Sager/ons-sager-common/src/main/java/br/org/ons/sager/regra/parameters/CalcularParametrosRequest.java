package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.modelagem.Periodo;

public class CalcularParametrosRequest {
	
	private Periodo janelaCalculo;
	private List<EventoMudancaEstadoOperativo> eventos = new ArrayList<>();
	private Equipamento equipamento;

	/**
	 * @return o campo janelaCalculo
	 */
	public Periodo getJanelaCalculo() {
		return janelaCalculo;
	}

	/**
	 * @param janelaCalculo
	 *            o campo janelaCalculo a ser definido
	 */
	public void setJanelaCalculo(Periodo janelaCalculo) {
		this.janelaCalculo = janelaCalculo;
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

	/**
	 * @return o campo equipamento
	 */
	public Equipamento getEquipamento() {
		return equipamento;
	}

	/**
	 * @param equipamento
	 *            o campo equipamento a ser definido
	 */
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalcularParametrosRequest [janelaCalculo=" + janelaCalculo + ", eventos=" + eventos + ", equipamento="
				+ equipamento + "]";
	}
}