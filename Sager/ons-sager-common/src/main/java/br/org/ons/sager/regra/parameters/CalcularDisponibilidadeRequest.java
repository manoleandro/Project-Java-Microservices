package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.TipoDisponibilidade;
import br.org.ons.geracao.modelagem.Periodo;

public class CalcularDisponibilidadeRequest {

	private List<EventoMudancaEstadoOperativo> eventos = new ArrayList<>();
	private Equipamento equipamento;
	private Periodo periodo;
	private List<TipoDisponibilidade> tipoDisponibilidades = new ArrayList<>();

	public CalcularDisponibilidadeRequest() {
		super();
	}

	public List<EventoMudancaEstadoOperativo> getEventos() {
		return eventos;
	}

	public void setEventos(List<EventoMudancaEstadoOperativo> eventos) {
		this.eventos = eventos;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public List<TipoDisponibilidade> getTipoDisponibilidades() {
		return tipoDisponibilidades;
	}

	public void setTipoDisponibilidades(List<TipoDisponibilidade> tipoDisponibilidades) {
		this.tipoDisponibilidades = tipoDisponibilidades;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalcularDisponibilidadeRequest [eventos=" + eventos + ", equipamento=" + equipamento + ", periodo="
				+ periodo + ", tipoDisponibilidades=" + tipoDisponibilidades + "]";
	}
	
}
