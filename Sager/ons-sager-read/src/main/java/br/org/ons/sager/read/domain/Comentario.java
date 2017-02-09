package br.org.ons.sager.read.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

import br.org.ons.geracao.evento.ComentarioSituacao;

@QueryEntity
@KeySpace("comentario")
public class Comentario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String tipo;
	private String descricao;
	private Date dataInsercao;
	private Date dataInicio;
	private Date dataFim;
	private String statusObjeto;
	private String tipoObjeto;
	private String nomeObjeto;
	private String idInstalacao;
	
	public Comentario(ComentarioSituacao comentarioSituacao, String idInstalacao) {
		this.setDataFim(comentarioSituacao.getDataFim());
		this.setDataInicio(comentarioSituacao.getDataInicio());
		this.setDataInsercao(comentarioSituacao.getDataInsercao());
		this.setIdInstalacao(idInstalacao);
		this.setNomeObjeto(comentarioSituacao.getNomeObjeto());
		this.setStatusObjeto(comentarioSituacao.getStatusObjeto().toString());
		this.setTipo(comentarioSituacao.getTipo().toString());
		this.setTipoObjeto(comentarioSituacao.getTipoObjeto().toString());
		this.setDescricao(comentarioSituacao.getDescricao());
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataInsercao() {
		return dataInsercao;
	}
	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public String getNomeObjeto() {
		return nomeObjeto;
	}
	public void setNomeObjeto(String nomeObjeto) {
		this.nomeObjeto = nomeObjeto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getStatusObjeto() {
		return statusObjeto;
	}
	public void setStatusObjeto(String statusObjeto) {
		this.statusObjeto = statusObjeto;
	}
	public String getTipoObjeto() {
		return tipoObjeto;
	}
	public void setTipoObjeto(String tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}
	public String getIdInstalacao() {
		return idInstalacao;
	}
	public void setIdInstalacao(String idInstalacao) {
		this.idInstalacao = idInstalacao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((idInstalacao == null) ? 0 : idInstalacao.hashCode());
		result = prime * result + ((nomeObjeto == null) ? 0 : nomeObjeto.hashCode());
		result = prime * result + ((statusObjeto == null) ? 0 : statusObjeto.hashCode());
		result = prime * result + ((tipoObjeto == null) ? 0 : tipoObjeto.hashCode());
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
		Comentario other = (Comentario) obj;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (idInstalacao == null) {
			if (other.idInstalacao != null)
				return false;
		} else if (!idInstalacao.equals(other.idInstalacao))
			return false;
		if (nomeObjeto == null) {
			if (other.nomeObjeto != null)
				return false;
		} else if (!nomeObjeto.equals(other.nomeObjeto))
			return false;
		if (statusObjeto == null) {
			if (other.statusObjeto != null)
				return false;
		} else if (!statusObjeto.equals(other.statusObjeto))
			return false;
		if (tipoObjeto == null) {
			if (other.tipoObjeto != null)
				return false;
		} else if (!tipoObjeto.equals(other.tipoObjeto))
			return false;
		return true;
	}
	
	

}
