package br.org.ons.exemplo.common;

import java.io.Serializable;
import java.util.List;

/**
 * Representa uma taxa calculada a partir de parâmetros 
 */
public class Taxa implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private Double valor;
	private List<Parametro> parametros;

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

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	@Override
    public String toString() {
        return "Parametro{" +
            "codigo='" + codigo + "'" +
            ", valor='" + valor + "'" +
            ", parametros='" + parametros + "'" +
            '}';
    }
}
