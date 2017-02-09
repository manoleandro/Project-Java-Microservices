package br.org.ons.geracao.evento;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LogComentarios implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Comentario> listaComentarios;
	 
	 private Date mes;
	 
	 private String nomeCenario;

	public LogComentarios() {
		super();
	}

	public LogComentarios(List<Comentario> listaComentarios, Date mes) {
		super();
		this.listaComentarios = listaComentarios;
		this.mes = mes;
	}

	public List<Comentario> getListaComentarios() {
		return listaComentarios;
	}

	public void setListaComentarios(List<Comentario> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}

	public Date getMes() {
		return mes;
	}

	public void setMes(Date mes) {
		this.mes = mes;
	}

	public String getNomeCenario() {
		return nomeCenario;
	}

	public void setNomeCenario(String nomeCenario) {
		this.nomeCenario = nomeCenario;
	}

	@Override
	public String toString() {
		return "Comentarios [listaComentarios=" + listaComentarios + "]";
	}


}
