package br.org.ons.geracao.evento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Registro de Mudan�a de Estado Operativo de Unidade Geradora ou Interliga��o
 * Internacional
 */
public class EventoMudancaEstadoOperativo implements Serializable {

	private static final long serialVersionUID = 2231647218542171954L;
	
	private String id;
	private String version;
	private String idInstalacao;
	private String idEquipamento;
	private Date dataVerificada;
	/** usar  valor do estado operativo buscando do banco */
	private String estadoOperativo;
	/** usar  valor do condicao operativo buscando do banco */
	private String condicaoOperativa;
	/** usar  valor do classificacao operativo buscando do banco */
	private String classificacaoOrigem;
	private Double valorPotenciaDisponivel;
	/**
	 * Data de cria��o do evento (pode ter sido criado pelo SAGER para limitar um evento de franquia)
	 */
	private Date dataCriacao;
	private TipoOperacao tipoOperacao;
	private Date dataUltimaConsolidacao;
	private StatusEvento status;
	private String justificativaRestricaoDesligamento;
	
	private List<Comentario> comentarios = new ArrayList<>();
	
	private boolean ficticio = false; // um evento fictício é criado pelo SAGER para limitar uma franquia
	
	private boolean eventoEspelho = false;

	public EventoMudancaEstadoOperativo() {
		super();
	}

	public EventoMudancaEstadoOperativo(String id, Date dataVerificada) {
		super();
		this.id = id;
		this.dataVerificada = dataVerificada;
	}

	/**
	 * @return o campo id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            o campo id a ser definido
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return o campo version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            o campo version a ser definido
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * @return o campo idInstalacao
	 */
	@JsonIgnore
	public String getIdInstalacao() {
		return idInstalacao;
	}

	/**
	 * @param idInstalacao o campo idInstalacao a ser definido
	 */
	@JsonSetter
	public void setIdInstalacao(String idInstalacao) {
		this.idInstalacao = idInstalacao;
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
	 * @return o campo dataVerificada
	 */
	public Date getDataVerificada() {
		return dataVerificada;
	}

	/**
	 * @param dataVerificada
	 *            o campo dataVerificada a ser definido
	 */
	public void setDataVerificada(Date dataVerificada) {
		this.dataVerificada = dataVerificada;
		setStatus(StatusEvento.RETIFICADO_ALTERADO);
	}

	/**
	 * @return o campo estadoOperativo
	 */
	public String getEstadoOperativo() {
		return estadoOperativo;
	}

	/**
	 * @param estadoOperativo
	 *            o campo estadoOperativo a ser definido
	 */
	public void setEstadoOperativo(String estadoOperativo) {
		this.estadoOperativo = estadoOperativo;
		setStatus(StatusEvento.RETIFICADO_ALTERADO);
	}

	/**
	 * @return o campo condicaoOperativa
	 */
	public String getCondicaoOperativa() {
		return condicaoOperativa;
	}

	/**
	 * @param condicaoOperativa
	 *            o campo condicaoOperativa a ser definido
	 */
	public void setCondicaoOperativa(String condicaoOperativa) {
		this.condicaoOperativa = condicaoOperativa;
		setStatus(StatusEvento.RETIFICADO_ALTERADO);
	}

	/**
	 * @return o campo classificacaoOrigem
	 */
	public String getClassificacaoOrigem() {
		return classificacaoOrigem;
	}

	/**
	 * @param classificacaoOrigem
	 *            o campo classificacaoOrigem a ser definido
	 */
	public void setClassificacaoOrigem(String classificacaoOrigem) {
		this.classificacaoOrigem = classificacaoOrigem;
		setStatus(StatusEvento.RETIFICADO_ALTERADO);
	}

	/**
	 * @return o campo valorPotenciaDisponivel
	 */
	public Double getValorPotenciaDisponivel() {
		return valorPotenciaDisponivel;
	}

	/**
	 * @param valorPotenciaDisponivel
	 *            o campo valorPotenciaDisponivel a ser definido
	 */
	public void setValorPotenciaDisponivel(Double valorPotenciaDisponivel) {
		this.valorPotenciaDisponivel = valorPotenciaDisponivel;
		setStatus(StatusEvento.RETIFICADO_ALTERADO);
	}

	/**
	 * @return o campo dataCriacao
	 */
	public Date getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao
	 *            o campo dataCriacao a ser definido
	 */
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return o campo tipoOperacao
	 */
	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	/**
	 * @param tipoOperacao
	 *            o campo tipoOperacao a ser definido
	 */
	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	/**
	 * @return o campo dataUltimaConsolidacao
	 */
	public Date getDataUltimaConsolidacao() {
		return dataUltimaConsolidacao;
	}

	/**
	 * @param dataUltimaConsolidacao
	 *            o campo dataUltimaConsolidacao a ser definido
	 */
	public void setDataUltimaConsolidacao(Date dataUltimaConsolidacao) {
		this.dataUltimaConsolidacao = dataUltimaConsolidacao;
	}

	/**
	 * @return o campo status
	 */
	public StatusEvento getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            o campo status a ser definido
	 */
	public void setStatus(StatusEvento status) {
		this.status = status;
	}

	/**
	 * @return o campo justificativaRestricaoDesligamento
	 */
	public String getJustificativaRestricaoDesligamento() {
		return justificativaRestricaoDesligamento;
	}

	/**
	 * @param justificativaRestricaoDesligamento
	 *            o campo justificativaRestricaoDesligamento a ser definido
	 */
	public void setJustificativaRestricaoDesligamento(String justificativaRestricaoDesligamento) {
		this.justificativaRestricaoDesligamento = justificativaRestricaoDesligamento;
	}

	/**
	 * @return o campo comentarios
	 */
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios o campo comentarios a ser definido
	 */
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public boolean isFicticio() {
		return ficticio;
	}

	public void setFicticio(boolean ficticio) {
		this.ficticio = ficticio;
	}
	
	public boolean isEventoEspelho() {
		return eventoEspelho;
	}

	public void setEventoEspelho(boolean eventoEspelho) {
		this.eventoEspelho = eventoEspelho;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classificacaoOrigem == null) ? 0 : classificacaoOrigem.hashCode());
		result = prime * result + ((condicaoOperativa == null) ? 0 : condicaoOperativa.hashCode());
		result = prime * result + ((dataVerificada == null) ? 0 : dataVerificada.hashCode());
		result = prime * result + ((estadoOperativo == null) ? 0 : estadoOperativo.hashCode());
		result = prime * result + ((valorPotenciaDisponivel == null) ? 0 : valorPotenciaDisponivel.hashCode());
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
		EventoMudancaEstadoOperativo other = (EventoMudancaEstadoOperativo) obj;
		if (classificacaoOrigem == null) {
			if (other.classificacaoOrigem != null)
				return false;
		} else if (!classificacaoOrigem.equals(other.classificacaoOrigem))
			return false;
		if (condicaoOperativa == null) {
			if (other.condicaoOperativa != null)
				return false;
		} else if (!condicaoOperativa.equals(other.condicaoOperativa))
			return false;
		if (dataVerificada == null) {
			if (other.dataVerificada != null)
				return false;
		} else if (!dataVerificada.equals(other.dataVerificada))
			return false;
		if (estadoOperativo == null) {
			if (other.estadoOperativo != null)
				return false;
		} else if (!estadoOperativo.equals(other.estadoOperativo))
			return false;
		if (valorPotenciaDisponivel == null) {
			if (other.valorPotenciaDisponivel != null)
				return false;
		} else if (!valorPotenciaDisponivel.equals(other.valorPotenciaDisponivel))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EventoMudancaEstadoOperativo [id=" + id + ", version=" + version + ", idInstalacao=" + idInstalacao
				+ ", idEquipamento=" + idEquipamento + ", dataVerificada=" + dataVerificada + ", estadoOperativo="
				+ estadoOperativo + ", condicaoOperativa=" + condicaoOperativa + ", classificacaoOrigem="
				+ classificacaoOrigem + ", valorPotenciaDisponivel=" + valorPotenciaDisponivel + ", dataCriacao="
				+ dataCriacao + ", tipoOperacao=" + tipoOperacao
				+ ", dataUltimaConsolidacao=" + dataUltimaConsolidacao + ", status=" + status
				+ ", justificativaRestricaoDesligamento=" + justificativaRestricaoDesligamento + ", comentarios="
				+ comentarios + ", ficticio=" + ficticio + ", evento espelho=" + eventoEspelho + "]";
	}
}
