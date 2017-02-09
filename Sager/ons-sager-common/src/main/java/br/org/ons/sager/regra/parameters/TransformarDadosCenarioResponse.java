package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;

public class TransformarDadosCenarioResponse {	
	private List<Equipamento> equipamentos = new ArrayList<Equipamento>();
	private List<EventoMudancaEstadoOperativo> eventos = new ArrayList<EventoMudancaEstadoOperativo>();

	/**
	 * @return o campo equipamentos
	 */
	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	/**
	 * @param equipamentos o valor do campo equipamentos
	 */
	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}
	
	/**
	 * @return o campo eventos
	 */
	public List<EventoMudancaEstadoOperativo> getEventos() {
		return eventos;
	}

	/**
	 * @param eventos o valor do campo eventos
	 */
	public void setListaParametros(List<EventoMudancaEstadoOperativo> eventos) {
		this.eventos = eventos;
	}
}
