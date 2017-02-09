package br.org.ons.geracao.evento;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Comentário ou crítica sobre um evento
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, 
		include = JsonTypeInfo.As.PROPERTY, 
		property = "_class", 
		visible = false)
@JsonSubTypes({
@Type(value = Comentario.class, 
		name = "br.org.ons.geracao.evento.Comentario"),
@Type(value = ComentarioSituacao.class, 
		name = "br.org.ons.geracao.evento.ComentarioSituacao")
})
public class Comentario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private TipoComentario tipo;
	private String descricao;
	private Date dataInsercao;
	private OrigemComentario origem;

	/**
	 * @return o campo tipo
	 */
	public TipoComentario getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            o campo tipo a ser definido
	 */
	public void setTipo(TipoComentario tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return o campo descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            o campo descricao a ser definido
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return o campo dataInsercao
	 */
	public Date getDataInsercao() {
		return dataInsercao;
	}

	/**
	 * @param dataInsercao
	 *            o campo dataInsercao a ser definido
	 */
	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	/**
	 * @return o campo origem
	 */
	public OrigemComentario getOrigem() {
		return origem;
	}

	/**
	 * @param origem
	 *            o campo origem a ser definido
	 */
	public void setOrigem(OrigemComentario origem) {
		this.origem = origem; 
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Comentario [tipo=" + tipo + ", descricao=" + descricao + ", dataInsercao=" + dataInsercao + ", origem="
				+ origem + "]";
	}
	
}
