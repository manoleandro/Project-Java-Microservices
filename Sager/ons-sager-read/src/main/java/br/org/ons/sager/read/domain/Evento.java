package br.org.ons.sager.read.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.StatusEvento;
import br.org.ons.geracao.evento.TipoOperacao;

@QueryEntity
@KeySpace("evento")
public class Evento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String idEvento;
	private String version;
	private String idInstalacao;
	private String idEquipamento;
	private Date dataVerificada;
	
	private List<Comentario> comentarios;
	private String justificativaRestricaoDesligamento;
	private StatusEvento status;
	private Date dataUltimaConsolidacao;
	private TipoOperacao tipoOperacao;
	private Date dataCriacao;
	private Double valorPotenciaDisponivel;
	private String estadoOperativo;
	private String condicaoOperativa;
	private String origem;


	public Evento(EventoMudancaEstadoOperativo event, String instalacaoId) {
		this.idEvento = event.getId();
		this.version = event.getVersion();
		this.idInstalacao = instalacaoId;
		this.idEquipamento = event.getIdEquipamento();
		this.dataVerificada = event.getDataVerificada();
		this.comentarios = event.getComentarios();
		this.justificativaRestricaoDesligamento = event.getJustificativaRestricaoDesligamento();
		this.status = event.getStatus();
		this.dataUltimaConsolidacao = event.getDataUltimaConsolidacao();
		this.tipoOperacao = event.getTipoOperacao();
		this.dataCriacao = event.getDataCriacao();
		this.valorPotenciaDisponivel = event.getValorPotenciaDisponivel();
		this.estadoOperativo = event.getEstadoOperativo();
		this.condicaoOperativa = event.getCondicaoOperativa();
		this.origem = event.getClassificacaoOrigem();
	}

	public Evento() {
		super();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getIdEvento() {
		return idEvento;
	}


	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getIdInstalacao() {
		return idInstalacao;
	}


	public void setIdInstalacao(String idInstalacao) {
		this.idInstalacao = idInstalacao;
	}


	public String getIdEquipamento() {
		return idEquipamento;
	}


	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}


	public Date getDataVerificada() {
		return dataVerificada;
	}


	public void setDataVerificada(Date dataVerificada) {
		this.dataVerificada = dataVerificada;
	}


	public List<Comentario> getComentarios() {
		return comentarios;
	}


	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}


	public String getJustificativaRestricaoDesligamento() {
		return justificativaRestricaoDesligamento;
	}


	public void setJustificativaRestricaoDesligamento(String justificativaRestricaoDesligamento) {
		this.justificativaRestricaoDesligamento = justificativaRestricaoDesligamento;
	}


	public StatusEvento getStatus() {
		return status;
	}


	public void setStatus(StatusEvento status) {
		this.status = status;
	}


	public Date getDataUltimaConsolidacao() {
		return dataUltimaConsolidacao;
	}


	public void setDataUltimaConsolidacao(Date dataUltimaConsolidacao) {
		this.dataUltimaConsolidacao = dataUltimaConsolidacao;
	}


	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}


	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}


	public Date getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}


	public Double getValorPotenciaDisponivel() {
		return valorPotenciaDisponivel;
	}


	public void setValorPotenciaDisponivel(Double valorPotenciaDisponivel) {
		this.valorPotenciaDisponivel = valorPotenciaDisponivel;
	}


	public String getEstadoOperativo() {
		return estadoOperativo;
	}


	public void setEstadoOperativo(String estadoOperativo) {
		this.estadoOperativo = estadoOperativo;
	}


	public String getCondicaoOperativa() {
		return condicaoOperativa;
	}


	public void setCondicaoOperativa(String condicaoOperativa) {
		this.condicaoOperativa = condicaoOperativa;
	}


	public String getOrigem() {
		return origem;
	}


	public void setOrigem(String origem) {
		this.origem = origem;
	}

	@Override
	public String toString() {
		return "Evento [id=" + id + ", version=" + version + ", idInstalacao=" + idInstalacao + ", idEquipamento="
				+ idEquipamento + ", dataVerificada=" + dataVerificada + ", comentarios=" + comentarios
				+ ", justificativaRestricaoDesligamento=" + justificativaRestricaoDesligamento + ", status=" + status
				+ ", dataUltimaConsolidacao=" + dataUltimaConsolidacao + ", tipoOperacao=" + tipoOperacao
				+ ", dataCriacao=" + dataCriacao + ", valorPotenciaDisponivel=" + valorPotenciaDisponivel
				+ ", estadoOperativo=" + estadoOperativo + ", condicaoOperativa=" + condicaoOperativa + ", origem="
				+ origem + "]";
	}

}
