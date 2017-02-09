package br.org.ons.exemplo.common;

/**
 * Representa uma usina
 */
public class Usina {

	private String id;
	private String nomeCurto;
	private String tipoUsina;
	private Double potenciaCalculo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomeCurto() {
		return nomeCurto;
	}

	public void setNomeCurto(String nomeCurto) {
		this.nomeCurto = nomeCurto;
	}

	public String getTipoUsina() {
		return tipoUsina;
	}

	public void setTipoUsina(String tipoUsina) {
		this.tipoUsina = tipoUsina;
	}

	public Double getPotenciaCalculo() {
		return potenciaCalculo;
	}

	public void setPotenciaCalculo(Double potenciaCalculo) {
		this.potenciaCalculo = potenciaCalculo;
	}

	@Override
    public String toString() {
        return "Usina{" +
            "id='" + id + "'" +
            ", nomeCurto='" + nomeCurto + "'" +
            ", tipoUsina='" + tipoUsina + "'" +
            ", potenciaCalculo='" + potenciaCalculo + "'" +
            '}';
    }
}
