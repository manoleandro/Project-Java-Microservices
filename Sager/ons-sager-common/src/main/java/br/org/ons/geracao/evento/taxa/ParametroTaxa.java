package br.org.ons.geracao.evento.taxa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.modelagem.Variavel;

/**
 * Par�metro de c�lculo para as taxas mensais.
 */
public class ParametroTaxa extends Variavel {
	private static final long serialVersionUID = 496604218777222029L;
	private Integer mes;
	private Integer ano;
	private String idEquipamento;
	private List<String> idsEventos = new ArrayList<>();

	/**
	 * @return o campo mes
	 */
	public Integer getMes() {
		return mes;
	}

	/**
	 * @param mes
	 *            o campo mes a ser definido
	 */
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	
	/**
	 * @return o campo idEquipamento
	 */
	public String getIdEquipamento() {
		return idEquipamento;
	}

	/**
	 * @param idEquipamento
	 *            o campo idEquipamento a ser definido
	 */
	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}

	/**
	 * @return o campo ano
	 */
	public Integer getAno() {
		return ano;
	}

	/**
	 * @param ano
	 *            o campo ano a ser definido
	 */
	public void setAno(Integer ano) {
		this.ano = ano;
	}

	/**
	 * @return o campo idsEventos
	 */
	public List<String> getIdsEventos() {
		return idsEventos;
	}

	/**
	 * @param idsEventos
	 *            o campo idsEventos a ser definido
	 */
	public void setIdsEventos(List<String> idsEventos) {
		this.idsEventos = idsEventos;
	}
		
	/**
	 * @param evento
	 *            o evento de mudança de estado operativo a associar a this (se já não está)
	 * @param valor
	 *            duracao a adicionar ao valor de this
	 */
	public void atualizar(EventoMudancaEstadoOperativo evento, Double duracao) {
		if(idsEventos.contains(evento.getId()) == false)
			idsEventos.add(evento.getId());
		adicionar(new BigDecimal(duracao));
	}

	@Override
	public String toString() {
		return "ParametroTaxa [mes=" + mes + ", ano=" + ano + ", idEquipamento=" + idEquipamento + ", idsEventos="
				+ idsEventos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
		result = prime * result + ((idEquipamento == null) ? 0 : idEquipamento.hashCode());
		result = prime * result + ((mes == null) ? 0 : mes.hashCode());
		result = prime * result + ((this.getCodigo() == null) ? 0 : this.getCodigo().hashCode());
		result = prime * result + ((this.getValor() == null) ? 0 : this.getValor().hashCode());
		result = prime * result + ((idsEventos == null) ? 0 : idsEventos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParametroTaxa other = (ParametroTaxa) obj;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		if (idEquipamento == null) {
			if (other.idEquipamento != null)
				return false;
		} else if (!idEquipamento.equals(other.idEquipamento))
			return false;
		if (mes == null) {
			if (other.mes != null)
				return false;
		} else if (!mes.equals(other.mes))
			return false;
		if (this.getCodigo() == null) {
			if (other.getCodigo() != null)
				return false;
		} else if (!this.getCodigo().equals(other.getCodigo()))
			return false;		
		if (this.getValor() == null) {
			if (other.getValor() != null)
				return false;
		} else if (!this.getValor().equals(other.getValor()))
			return false;
		if (idsEventos == null) {
			if (other.idsEventos != null)
				return false;
		} else if (!idsEventos.equals(other.idsEventos))
			return false;		
		return true;	
	}
	
	
	
}
