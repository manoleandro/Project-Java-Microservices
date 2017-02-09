package br.org.ons.sager.read.web.rest.dto;

import java.util.Date;
import java.util.List;

import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.StatusEvento;
import br.org.ons.geracao.evento.TipoOperacao;
import br.org.ons.sager.read.domain.Evento;

public class EventoDTO {
	
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
	private String nomeInstalacao;
	private String codigoONSEquipamento;
	
	public EventoDTO(Evento evento, String nomeInstalacao, String codigoONSEquipamento){
		this.setId(evento.getId());
		this.setIdEvento(evento.getIdEvento());
		this.setVersion(evento.getVersion());
		this.setIdInstalacao(evento.getIdInstalacao());
		this.setIdEquipamento(evento.getIdEquipamento());
		this.setDataVerificada(evento.getDataVerificada());
		this.setComentarios(evento.getComentarios());
		this.setJustificativaRestricaoDesligamento(evento.getJustificativaRestricaoDesligamento());
		this.setStatus(evento.getStatus());
		this.setDataUltimaConsolidacao(evento.getDataUltimaConsolidacao());
		this.setTipoOperacao(evento.getTipoOperacao());
		this.setDataCriacao(evento.getDataCriacao());
		this.setValorPotenciaDisponivel(evento.getValorPotenciaDisponivel());
		this.setEstadoOperativo(evento.getEstadoOperativo());
		this.setCondicaoOperativa(evento.getCondicaoOperativa());
		this.setOrigem(evento.getOrigem());
		this.setNomeInstalacao(nomeInstalacao);
		this.setCodigoONSEquipamento(codigoONSEquipamento);
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
	public String getNomeInstalacao() {
		return nomeInstalacao;
	}
	public void setNomeInstalacao(String nomeInstalacao) {
		this.nomeInstalacao = nomeInstalacao;
	}
	public String getCodigoONSEquipamento() {
		return codigoONSEquipamento;
	}
	public void setCodigoONSEquipamento(String codigoONSEquipamento) {
		this.codigoONSEquipamento = codigoONSEquipamento;
	}

}
