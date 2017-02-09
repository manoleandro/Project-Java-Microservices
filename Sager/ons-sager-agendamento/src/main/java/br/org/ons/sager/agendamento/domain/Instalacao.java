package br.org.ons.sager.agendamento.domain;

import static com.querydsl.collections.CollQueryFactory.from;
import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.evento.LogComentarios;
import br.org.ons.sager.agendamento.JavaUtil;

public class Instalacao implements Serializable {
	
	private static final long serialVersionUID = 4649772491853324169L;

	private String id;
	
	private String nome;
	
	private String nomeCenario;
	
	private long minorVersion;
	
	private String situacao;
	
	private String resultado;
	
	private List<LogComentarios> comentario = new ArrayList<>();
	

	public Instalacao(String id, String nome, long minorVersion) {
		super();
		this.id = id;
		this.nome = nome;
		this.minorVersion = minorVersion;
	}

	public Instalacao() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(long minorVersion) {
		this.minorVersion = minorVersion;
	}

	public String getNomeCenario() {
		return nomeCenario;
	}

	public void setNomeCenario(String nomeCenario) {
		this.nomeCenario = nomeCenario;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public List<LogComentarios> getComentario() {
		return comentario;
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

	public void addComentarios(List<LogComentarios> comentarios) {
		
		for(LogComentarios logComent : comentarios){
			this.addComentario(logComent);
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime * result + ((nomeCenario == null) ? 0 : nomeCenario.hashCode());
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
		Instalacao other = (Instalacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
//		if (nomeCenario == null) {
//			if (other.nomeCenario != null)
//				return false;
//		} else if (!nomeCenario.equals(other.nomeCenario))
//			return false;		
		
		return true;
	}
	
	

}
