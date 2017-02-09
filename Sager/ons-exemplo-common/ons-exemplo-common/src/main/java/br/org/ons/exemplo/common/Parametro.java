package br.org.ons.exemplo.common;

import java.io.Serializable;
import java.util.List;

/**
 * Representa um parâmetro de taxa calculado a partir de eventos
 */
public class Parametro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private Double valor;
	private List<Evento> eventos;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	@Override
    public String toString() {
        return "Parametro{" +
            "codigo='" + codigo + "'" +
            ", valor='" + valor + "'" +
            ", eventos='" + eventos + "'" +
            '}';
    }
}
