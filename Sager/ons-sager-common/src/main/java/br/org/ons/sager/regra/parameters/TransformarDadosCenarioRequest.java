package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.geracao.evento.cenario.Cenario;

public class TransformarDadosCenarioRequest {	
	private Cenario cenario;
	private List<Equipamento> equipamentos = new ArrayList<Equipamento>();
	private List<EventoMudancaEstadoOperativo> eventos = new ArrayList<EventoMudancaEstadoOperativo>();
	private Periodo janelaCalculo;	

	/**
	 * @return o campo cenario
	 */
	public Cenario getCenario() {
		return cenario;
	}

	/**
	 * @param cenario o valor do campo cenario
	 */
	public void setCenario(Cenario cenario) {
		this.cenario = cenario;
	}

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
	 * @return o campo janelaCalculo
	 */
	public Periodo getJanelaCalculo() {
		return janelaCalculo;
	}
	
	/**
	 * @param janelaCalculo o valor do campo janelaCalculo
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
	 * @param eventos o valor do campo eventos
	 */
	public void setListaParametros(List<EventoMudancaEstadoOperativo> eventos) {
		this.eventos = eventos;
	}
}
