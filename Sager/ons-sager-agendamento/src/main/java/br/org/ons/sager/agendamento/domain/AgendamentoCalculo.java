package br.org.ons.sager.agendamento.domain;

import static com.querydsl.collections.CollQueryFactory.from;
import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

import br.org.ons.geracao.evento.LogComentarios;
import br.org.ons.sager.agendamento.JavaUtil;

/**
 * Representa o agendamento de uma execução de cálculo de taxas para uma
 * instalação em um determinado período
 */
@QueryEntity
@KeySpace("AgendamentoCalculo")
public class AgendamentoCalculo extends Agendamento {

	private static final long serialVersionUID = 1L;
	private ZonedDateTime mesInicial;
	private ZonedDateTime mesFinal;
	private int anoCriacao;
	private String jobId;
	private int protocolo;
	private String protocoloStr;
	private List<LogComentarios> comentario = new ArrayList<>();
	
	public AgendamentoCalculo() {
	}
	
	public List<LogComentarios> getComentario() {
		return comentario;
	}
	
	public void addComentarios(List<LogComentarios> comentarios){
		
		for(LogComentarios logComent : comentarios){
			this.addComentario(logComent);
		}
		
	}
	
	
	public void addComentario(LogComentarios comentario){
		
		if(comentario.getMes() != null && !comentario.getListaComentarios().isEmpty()){
			//Verifica se já existe uma logComentario para o mes a inserir
			LogComentarios qLogComentarios = alias(LogComentarios.class, "logComentarios");
			List<LogComentarios> listComentariosMes = from($(qLogComentarios), JavaUtil.emptyIfNull(this.comentario))
					.where($(qLogComentarios.getMes()).eq(comentario.getMes()))
					.fetch();
			//Caso já exista uma lista de comentarios para o mes atual, adiciona os comentarios na mesma lista
			if(listComentariosMes != null && !listComentariosMes.isEmpty()){
				listComentariosMes.add(comentario);
			}else{
				//Senão adiciona a lista de comentario;
				this.comentario.add(comentario);
			}
		}
		
	}

	/**
	 * @return o campo mesInicial
	 */
	public ZonedDateTime getMesInicial() {
		return mesInicial;
	}

	/**
	 * @param mesInicial
	 *            o campo mesInicial a ser definido
	 */
	public void setMesInicial(ZonedDateTime mesInicial) {
		this.mesInicial = mesInicial;
	}

	/**
	 * @return o campo mesFinal
	 */
	public ZonedDateTime getMesFinal() {
		return mesFinal;
	}

	/**
	 * @param mesFinal
	 *            o campo mesFinal a ser definido
	 */
	public void setMesFinal(ZonedDateTime mesFinal) {
		this.mesFinal = mesFinal;
	}

	public int getAnoCriacao() {
		return anoCriacao;
	}

	public void setAnoCriacao(int anoCriacao) {
		this.anoCriacao = anoCriacao;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	/**
	 * @return o campo protocolo
	 */
	public int getProtocolo() {
		return protocolo;
	}

	/**
	 * @param protocolo
	 *            o campo protocolo a ser definido
	 */
	public void setProtocolo(int protocolo) {
		this.protocolo = protocolo;
	}
	
	public String getProtocoloStr() {
		return protocoloStr;
	}

	public void setProtocoloStr(String protocoloStr) {
		this.protocoloStr = protocoloStr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AgendamentoCalculo [mesInicial=" + mesInicial + ", mesFinal=" + mesFinal
				+ ", getId()=" + getId() + ", getIdInstalacao()=" +  ", getDataAgendamento()="
				+ getDataAgendamento() + ", getSituacao()=" + getSituacao() + ", getResultado()=" + getResultado()
				+ ", getSolicitante()=" + getSolicitante() + "]";
	}


}
