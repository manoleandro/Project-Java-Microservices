package br.org.ons.geracao.modelagem;

import java.io.Serializable;
import java.math.BigDecimal;

public class Variavel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final int PRECISAO = 12;  

	private String id;
	private String versao;
	private String codigo = null;
	private BigDecimal valor = null;
	private BigDecimal numerador = null;
	private BigDecimal denominador = null;
	private String descricao;
	private String descricaoAuxiliar;

	/**
	 * @return o campo id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            o campo id a ser definido
	 */
	public void setId(String id) {
		if(this != null)
			this.id = id;
	}

	/**
	 * @return o campo versao
	 */
	public String getVersao() {
		return versao;
	}

	/**
	 * @param versao
	 *            o campo versao a ser definido
	 */
	public void setVersao(String versao) {
		if(this != null)
			this.versao = versao;
	}

	/**
	 * @return o campo codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            o campo codigo a ser definido
	 */
	public void setCodigo(String codigo) {
		if(this != null)
			this.codigo = codigo;
	}

	/**
	 * @return o campo valor
	 */
	public BigDecimal getValor() {
		calcular(PRECISAO);
		return valor;
	}

	/**
	 * @param valor
	 *            o campo valor a ser definido
	 */
	public void setValor(BigDecimal valor) {
		if(this != null)
			this.valor = valor;
	}

	/**
	 * @return o campo numerador
	 */
	public BigDecimal getNumerador() {
		return numerador;
	}

	/**
	 * @param numerador
	 *            o campo numerador a ser definido
	 */
	public void setNumerador(BigDecimal numerador) {
		if(this != null)
			this.numerador = numerador;
	}
	
	public void atribuirNumerador(Variavel var) {
		if(var != null) {
			setNumerador(var.getValor());
			if(var.getDescricao() != null)
				setDescricao(var.getDescricao());
			else
				setDescricao(var.getCodigo());			
		}
	}
	
	public void atribuirNumerador(Variavel var, String descricao) {
		if(var != null) {
			setNumerador(var.getValor());
			setDescricao(descricao);
		}
	}
	
	public void atribuirNumerador(BigDecimal numerador, String descricao) {
		setNumerador(numerador);
		setDescricao(descricao);
	}
	
	/**
	 * @return o campo denominador
	 */
	public BigDecimal getDenominador() {
		return denominador;
	}

	/**
	 * @param denominador
	 *            o campo denomninador a ser definido
	 */
	public void setDenominador(BigDecimal denominador) {
		if(this != null)
			this.denominador = denominador;
	}

	public void atribuirDenominador(Variavel var) {
		if(var != null) {
			setDenominador(var.getValor());
			if(var.getDescricao() != null)
				setDescricaoAuxiliar(var.getDescricao());
			else
				setDescricaoAuxiliar(var.getCodigo());
		}
	}

	public void atribuirDenominador(Variavel var, String descr) {
		if(var != null) {
			setDenominador(var.getValor());
			setDescricaoAuxiliar(descr);
		}
	}

	public void atribuirDenominador(BigDecimal denominador, String descricao) {
		setDenominador(denominador);
		setDescricaoAuxiliar(descricao);
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
		if(this != null)
			this.descricao = descricao;
	}

	/**
	 * @return o campo descricaoAuxiliar
	 */
	public String getDescricaoAuxiliar() {
		return descricaoAuxiliar;
	}

	/**
	 * @param descricao
	 *            o campo descricao a ser definido
	 */
	public void setDescricaoAuxiliar(String descricaoAuxiliar) {
		if(this != null)
			this.descricaoAuxiliar = descricaoAuxiliar;
	}
	
	/**
	 * @val descricao
	 *            o valor a adicionar à variável
	 */	
	public void adicionar(BigDecimal val) {
		if(this != null && val != null)
			if(valor != null)
				valor = valor.add(val);
			else
				valor = val;
	}
	
	/**
	 * @val descricao
	 *            o valor a adicionar ao numerador da variável
	 */	
	public void adicionarNumerador(BigDecimal val) {
		if(this != null && val != null)
			if(numerador != null)
				numerador = numerador.add(val);
			else
				numerador = val;
	}

	public void adicionarNumerador(Variavel var) {
		if(this != null && var != null) {
			var.calcular();
			adicionarNumerador(var.getValor());
		}
	}
	
	/**
	 * @val descricao
	 *            o valor a adicionar ao denominador da variável
	 */	
	public void adicionarDenominador(BigDecimal val) {
		if(this != null && val != null)
			if(denominador != null)
				denominador = denominador.add(val);
			else
				denominador = val;
	}
	
	public void adicionarDenominador(Variavel var) {
		if(this != null && var != null)
			adicionarDenominador(var.getValor());
	}
	
	/**
	 * @variavel descricao
	 *            a variavel cujos atributos devem ser atribuidos a this
	 */	
	public void atribuir(Variavel variavel) {	
		if(this != null && variavel != null) {
			descricao = variavel.descricao;
			descricaoAuxiliar = variavel.descricaoAuxiliar;
			numerador = variavel.numerador;
			denominador = variavel.denominador;
			valor = variavel.valor;
		}
	}
	
	/**
	 * @valor descricao
	 *            o valor a atribuir a this
	 */	
	public void atribuir(BigDecimal valor) {
		if(this != null && valor != null) {
			numerador = valor;
			denominador = BigDecimal.ONE;
			this.valor = valor;
		}
	}
	
	public void calcular(int casasDecimais) {
		if(this != null && valor == null && numerador != null && denominador != null && denominador.compareTo(BigDecimal.ZERO)!=0) {
			if(denominador.compareTo(BigDecimal.ONE)!=0) {
				if(numerador.compareTo(BigDecimal.ZERO)==0)
					valor = BigDecimal.ZERO;
				else
					valor = numerador.divide(denominador, casasDecimais, java.math.RoundingMode.HALF_UP);
			}
			else
				valor = numerador;
		}
	}
	
	public void calcular() {
		calcular(PRECISAO);
	}
	
	public void arredondar(int casasDecimais) {
		if(this != null) {
			if(valor != null)
				valor = valor.setScale(casasDecimais, java.math.RoundingMode.HALF_UP);
			if(numerador != null)
				numerador = numerador.setScale(casasDecimais, java.math.RoundingMode.HALF_UP);
			if(denominador != null)
				denominador = denominador.setScale(casasDecimais, java.math.RoundingMode.HALF_UP);
		}
	}
	
	public Variavel adicionar(Variavel var) {
		if(var == null || valor == null || var.getValor() == null || var.getValor().compareTo(BigDecimal.ZERO) == 0)
			return this;
		else {
			Variavel variavel = new Variavel();
			variavel.setId(br.org.ons.platform.common.util.IdGenerator.newId());
			variavel.setCodigo(null);
			if(numerador==null && denominador==null) // parâmetro
				variavel.setDescricao(codigo+"+"+var.getCodigo());
			else
				variavel.setDescricao(descricao+"+"+var.getDescricao());
			calcular(PRECISAO);
			variavel.atribuir(valor.add(var.getValor()));
			return variavel;
		}
	}
	
	public Variavel adicionar(Double n) {
		if(n == 0 || valor == null)
			return this;
		else {
			Variavel variavel = new Variavel();
			variavel.setId(br.org.ons.platform.common.util.IdGenerator.newId());
			variavel.setCodigo(codigo);
			if(numerador==null && denominador==null) // parâmetro
				variavel.setDescricao(codigo+"+"+n);
			else
				variavel.setDescricao(descricao+"+"+n);
			calcular(PRECISAO);
			variavel.atribuir(valor.add(new BigDecimal(n)));
			return variavel;
		}
	}
	
	public Variavel substrair(Variavel var) {
		if(var == null || var.getValor() == null || var.getValor().compareTo(BigDecimal.ZERO) == 0)
			return this;
		else {
			Variavel variavel = new Variavel();
			variavel.setId(br.org.ons.platform.common.util.IdGenerator.newId());
			variavel.setCodigo(null);
			if(numerador==null && denominador==null) // parâmetro
				variavel.setDescricao(codigo+"-"+var.getCodigo());
			else
				variavel.setDescricao(descricao+"-"+var.getDescricao());
			calcular(PRECISAO);
			variavel.atribuir(valor.subtract(var.getValor()));
			return variavel;
		}
	}
	
	public Variavel substrair(Double n) {
		if(valor == null || n == 0)
			return this;
		else {
			Variavel variavel = new Variavel();
			variavel.setId(br.org.ons.platform.common.util.IdGenerator.newId());
			variavel.setCodigo(codigo);
			if(numerador==null && denominador==null) // parâmetro
				variavel.setDescricao(codigo+"-"+n);
			else
				variavel.setDescricao(descricao+"-"+n);
			calcular(PRECISAO);
			variavel.atribuir(valor.subtract(new BigDecimal(n)));
			return variavel;
		}
	}
	
	public Variavel substrair2(Double n) {
		Variavel variavel = new Variavel();
		variavel.setId(br.org.ons.platform.common.util.IdGenerator.newId());
		variavel.setCodigo(codigo);
		if(numerador==null && denominador==null) // parâmetro
			variavel.setDescricao(n+"-"+codigo);
		else
			variavel.setDescricao(n+"-("+descricao+")");
		calcular(PRECISAO);
		if(valor != null)
			variavel.atribuir(new BigDecimal(n).subtract(valor));
		return variavel;
	}
	
	public Variavel multiplicar(Variavel var) {
		if(valor == null || var == null || var.getValor() == null || var.getValor().compareTo(BigDecimal.ONE) == 0)
			return this;
		else {
			Variavel variavel = new Variavel();
			variavel.setId(br.org.ons.platform.common.util.IdGenerator.newId());
			variavel.setCodigo(null);
			if(numerador==null && denominador==null) // parâmetro
				variavel.setDescricao(codigo+"*"+var.getCodigo());
			else
				variavel.setDescricao("("+descricao+")*"+var.getDescricao());
			calcular(PRECISAO);
			variavel.atribuir(valor.multiply(var.getValor()));
			return variavel;
		}
	}
	
	public Variavel multiplicar(Double n) {
		if(valor == null || n == 1)
			return this;
		else {
			Variavel variavel = new Variavel();
			variavel.setId(br.org.ons.platform.common.util.IdGenerator.newId());
			variavel.setCodigo(codigo);
			if(numerador==null && denominador==null) // parâmetro
				variavel.setDescricao(n+"*"+codigo);
			else
				variavel.setDescricao(n+"*("+descricao+")");				
			calcular(PRECISAO);
			BigDecimal val;
			if(n==0)
				val = BigDecimal.ZERO;
			else
				val = new BigDecimal(n);
			variavel.atribuir(valor.multiply(val));
			return variavel;
		}
	}
	
	public Variavel dividir(Variavel var) {
		if(var == null || var.getValor() == null || valor == null ||
		   var.getValor().compareTo(BigDecimal.ZERO) == 0 || var.getValor().compareTo(BigDecimal.ONE) == 0)
			return this;
		else {
			Variavel variavel = new Variavel();
			variavel.setId(br.org.ons.platform.common.util.IdGenerator.newId());
			variavel.setCodigo(null);
			if(numerador==null && denominador==null) // parâmetro
				variavel.setDescricao(codigo+"/"+var.getCodigo());
			else
				variavel.setDescricao("(" + descricao+")/"+var.getDescricao());
			calcular(PRECISAO);
			variavel.atribuir(valor.divide(var.getValor(), PRECISAO, java.math.RoundingMode.HALF_UP));
			return variavel;
		}
	}
	
	public Variavel dividir(Double n) {
		if(valor == null || n == 0 || n== 1)
			return this;
		else {
			Variavel variavel = new Variavel();
			variavel.setId(br.org.ons.platform.common.util.IdGenerator.newId());
			variavel.setCodigo(codigo);
			if(numerador==null && denominador==null) // parâmetro
				variavel.setDescricao(codigo+"/"+n);
			else
				variavel.setDescricao("("+descricao+")/"+n);
			calcular(PRECISAO);
			variavel.atribuir(valor.divide(new BigDecimal(n), PRECISAO, java.math.RoundingMode.HALF_UP));
			return variavel;
		}
	}
	
	public Variavel dividir2(Double n) {
		if(valor == null || valor.compareTo(BigDecimal.ZERO) == 0 || valor.compareTo(BigDecimal.ONE) == 0)
			return this;
		else {
			Variavel variavel = new Variavel();
			variavel.setId(br.org.ons.platform.common.util.IdGenerator.newId());
			variavel.setCodigo(codigo);
			if(numerador==null && denominador==null) // parâmetro
				variavel.setDescricao(n+"/"+codigo);
			else
				variavel.setDescricao(n+"/("+descricao+")");
			calcular(PRECISAO);
			variavel.atribuir(new BigDecimal(n).divide(valor, PRECISAO, java.math.RoundingMode.HALF_UP));
			return variavel;
		}
	}
}