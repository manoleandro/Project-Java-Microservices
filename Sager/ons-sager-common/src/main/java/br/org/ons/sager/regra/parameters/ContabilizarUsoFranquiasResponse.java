package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;

public class ContabilizarUsoFranquiasResponse {
	
	// Dois tipos de atualização de eventos: comentários de eventos (críticas) e/ou inserção de novos eventos para
	// interromper franquias sem saldo (modo auto-correção)
	private List<EventoMudancaEstadoOperativo> eventos = new ArrayList<EventoMudancaEstadoOperativo>();
	
	// Adição de um objeto Franquia vigente em 'janelaCalculo' na lista de franquias do equipamento
	private Equipamento equipamento;

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
}