package br.org.ons.geracao.cadastro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.ons.geracao.evento.taxa.Taxa;

/**
 * Conjunto de instala��es destinadas � produ��o e disponibiliza��o de energia
 * el�trica ao sistema. Os Empreendimentos de Gera��o t�m como principais
 * componentes as Unidades Geradoras, Transformadores Elevadores de Tens�o e
 * Ativos de Conex�o ao sistema.
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, 
		include = JsonTypeInfo.As.PROPERTY, 
		property = "_class", 
		visible = false)
@JsonSubTypes({
		@Type(value = Usina.class, 
				name = "br.org.ons.geracao.cadastro.Usina"),
		@Type(value = InterligacaoInternacional.class, 
				name = "br.org.ons.geracao.cadastro.InterligacaoInternacional")
})
public class Instalacao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nomeCurto;

	/**
	 * A data de implanta��o do primeiro equipamento da instala��o
	 */
	private Date dataOutorgaImplantacao;

	private List<Taxa> taxasReferencia = new ArrayList<>();
	private List<Taxa> taxasAjustadas = new ArrayList<>();

	/**
	 * @return o campo nomeCurto
	 */
	public String getNomeCurto() {
		return nomeCurto;
	}

	/**
	 * @param nomeCurto
	 *            o campo nomeCurto a ser definido
	 */
	public void setNomeCurto(String nomeCurto) {
		this.nomeCurto = nomeCurto;
	}

	/**
	 * @return o campo dataOutorgaImplantacao
	 */
	public Date getDataOutorgaImplantacao() {
		return dataOutorgaImplantacao;
	}

	/**
	 * @param dataOutorgaImplantacao
	 *            o campo dataOutorgaImplantacao a ser definido
	 */
	public void setDataOutorgaImplantacao(Date dataOutorgaImplantacao) {
		this.dataOutorgaImplantacao = dataOutorgaImplantacao;
	}

	/**
	 * @return o campo taxasReferencia
	 */
	public List<Taxa> getTaxasReferencia() {
		return taxasReferencia;
	}

	/**
	 * @param taxasReferencia
	 *            o campo taxasReferencia a ser definido
	 */
	public void setTaxasReferencia(List<Taxa> taxasReferencia) {
		this.taxasReferencia = taxasReferencia;
	}

	/**
	 * @return o campo taxasAjustadas
	 */
	public List<Taxa> getTaxasAjustadas() {
		return taxasAjustadas;
	}

	/**
	 * @param taxasAjustadas
	 *            o campo taxasAjustadas a ser definido
	 */
	public void setTaxasAjustadas(List<Taxa> taxasAjustadas) {
		this.taxasAjustadas = taxasAjustadas;
	}
	
	/**
	 * @param codigo (TEIFa ou TEIP)
	 * @param periodo
	 * @return a taxa de referência vigente na 'data'
	 */
	public Taxa taxaReferenciaNaData(String codigo, Date data) {
		java.util.Iterator<Taxa> it = taxasReferencia.iterator();
		while(it.hasNext()) {
			Taxa taxaRef = it.next();	
			if(taxaRef.getCodigo().equals(codigo) && taxaRef.getPeriodo().contem(data))
				return taxaRef;
		}
		return null;
	}
	
	/**
	 * @param codigo (TEIFa ou TEIP)
	 * @param periodo
	 * @return a taxa ajustada vigente na 'data'
	 */
	public Taxa taxaAjustadaNaData(String codigo, Date data) {
		java.util.Iterator<Taxa> it = taxasAjustadas.iterator();
		while(it.hasNext()) {
			Taxa taxaAjustada = it.next();
			if(taxaAjustada.getCodigo().equals(codigo) && taxaAjustada.getPeriodo().contem(data))
				return taxaAjustada;
		}
		return null;
	}
}
