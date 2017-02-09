package br.org.ons.geracao.evento.taxa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.geracao.modelagem.Variavel;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Taxa de indisponibilidade de uma determinada instala??o em um dado per?odo
 * (m?s).
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, 
include = JsonTypeInfo.As.PROPERTY, 
property = "_class", 
visible = false)
@JsonSubTypes({
@Type(value = Taxa.class, 
		name = "br.org.ons.geracao.evento.taxa.Taxa"),
@Type(value = TaxaAcumuladaEstendida.class, 
		name = "br.org.ons.geracao.evento.taxa.TaxaAcumuladaEstendida"),		
@Type(value = TaxaAcumulada.class, 
	name = "br.org.ons.geracao.evento.taxa.HorasIndisponibilidade"),
@Type(value = HorasIndisponibilidade.class, 
	name = "br.org.ons.geracao.evento.taxa.HorasIndisponibilidade")
}
) 
public class Taxa extends Variavel {
	private static final long serialVersionUID = 1L;
	
	private TipoTaxa tipo;
	private Periodo periodo;
	private List<String> memoriaCalculo = new ArrayList<String>();
	private List<Participacao> participacoesEquipamentos = new ArrayList<Participacao>();
	private List<Comentario> comentarios = new ArrayList<Comentario>();

	/**
	 * @return o campo tipo
	 */
	public TipoTaxa getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            o campo tipo a ser definido
	 */
	public void setTipo(TipoTaxa tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return o campo periodo
	 */
	public Periodo getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo
	 *            o campo periodo a ser definido
	 */
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	/**
	 * @return o campo memoriaCalculo
	 */
	public List<String> getMemoriaCalculo() {
		return memoriaCalculo;
	}

	/**
	 * @param memoriaCalculo o campo memoriaCalculo a ser definido
	 */
	public void setMemoriaCalculo(List<String> memoriaCalculo) {
		this.memoriaCalculo = memoriaCalculo;
	}

	/**
	 * @return o campo participacoesEquipamentos
	 */
	public List<Participacao> getParticipacoesEquipamentos() {
		return participacoesEquipamentos;
	}
	
	/**
	 * @param participacoesEquipamentos
	 *            o campo participacoesEquipamentos a ser definido
	 */
	public void setParticipacoesEquipamentos(List<Participacao> participacoesEquipamentos) {
		this.participacoesEquipamentos = participacoesEquipamentos;
	}
	
	/**
	 * @param descricao
	 *            a descrição da participação
	 * @param idEquipamento
	 *            o identificador do equipamento participante
	 * @param valor
	 *            o valor de participação
	 * @param potencia
	 *            o valor de potência
	 */
	public void atribuirParticipacao(String descricao, Equipamento equipamento, Variavel valor, Double potencia) {
		Participacao participacao = new Participacao();
		participacao.setId(br.org.ons.platform.common.util.IdGenerator.newId());
		String nomeEquipamento = equipamento instanceof UnidadeGeradora ? ((UnidadeGeradora)equipamento).getCodigoOns() : equipamento.getNome(); 
		participacao.setDescricao(descricao + " " + nomeEquipamento + " na " + this.getCodigo());
		participacao.setCodigo(participacao.getDescricao());
		participacao.setIdParticipante(equipamento.getId());
		participacao.setPotencia(new BigDecimal(potencia));
		participacao.adicionar(valor);
		participacoesEquipamentos.add(participacao);
	}
	
	public void atribuirNumeradorParticipacao(String descricao, Equipamento equipamento, Variavel numerador, Double potencia) {
		Participacao participacao = new Participacao();
		participacao.setId(br.org.ons.platform.common.util.IdGenerator.newId());
		String nomeEquipamento = equipamento instanceof UnidadeGeradora ? ((UnidadeGeradora)equipamento).getCodigoOns() : equipamento.getNome(); 
		participacao.setDescricao(descricao + " " + nomeEquipamento + " na " + this.getCodigo());
		participacao.setCodigo(participacao.getDescricao());
		participacao.setIdParticipante(equipamento.getId());
		participacao.setPotencia(new BigDecimal(potencia));
		participacao.adicionarNumerador(numerador);
		participacoesEquipamentos.add(participacao);
	}
	
	public void adicionarNumeradorParticipacao(String descricao, Equipamento equipamento, Variavel numerador, Double potencia) {
		Participacao participacao = null;
		java.util.Iterator<Participacao> itP = participacoesEquipamentos.iterator();
		while(itP.hasNext()) {
			Participacao p = itP.next();
			if(p.getIdParticipante().equals(equipamento.getId())) {
				participacao = p;
				break;
			}
		}
		if(participacao == null) {
			participacao = new Participacao();
			participacao.setId(br.org.ons.platform.common.util.IdGenerator.newId());
			String nomeEquipamento = equipamento instanceof UnidadeGeradora ? ((UnidadeGeradora)equipamento).getCodigoOns() : equipamento.getNome(); 
			participacao.setDescricao(descricao + " " + nomeEquipamento + " na " + this.getCodigo());
			participacao.setCodigo(participacao.getDescricao());
			participacao.setIdParticipante(equipamento.getId());
			participacao.setPotencia(new BigDecimal(potencia));
			participacao.adicionarNumerador(numerador);
			participacoesEquipamentos.add(participacao);
		}
		else
			participacao.adicionarNumerador(numerador);
	}
		
	public void atribuirDenominadorParticipacao(String descricao, Equipamento equipamento, Variavel denominador, Double potencia) {
		Participacao participacao = new Participacao();
		participacao.setId(br.org.ons.platform.common.util.IdGenerator.newId());
		String nomeEquipamento = equipamento instanceof UnidadeGeradora ? ((UnidadeGeradora)equipamento).getCodigoOns() : equipamento.getNome(); 
		participacao.setDescricao(descricao + " " + nomeEquipamento + " na " + this.getCodigo());
		participacao.setCodigo("Participação na taxa");
		participacao.setIdParticipante(equipamento.getId());
		participacao.setPotencia(new BigDecimal(potencia));
		participacao.adicionarDenominador(denominador);
		participacoesEquipamentos.add(participacao);
	}

	public void adicionarDenominadorParticipacao(String descricao, Equipamento equipamento, Variavel denominador, Double potencia) {
		Participacao participacao = null;
		java.util.Iterator<Participacao> itP = participacoesEquipamentos.iterator();
		while(itP.hasNext()) {
			Participacao p = itP.next();
			if(p.getIdParticipante().equals(equipamento.getId())) {
				participacao = p;
				break;
			}
		}
		if(participacao == null) {
			participacao = new Participacao();
			participacao.setId(br.org.ons.platform.common.util.IdGenerator.newId());
			String nomeEquipamento = equipamento instanceof UnidadeGeradora ? ((UnidadeGeradora)equipamento).getCodigoOns() : equipamento.getNome(); 
			participacao.setDescricao(descricao + " " + nomeEquipamento + " na " + this.getCodigo());
			participacao.setCodigo(participacao.getDescricao());
			participacao.setIdParticipante(equipamento.getId());
			participacao.setPotencia(new BigDecimal(potencia));
			participacao.adicionarNumerador(denominador);
			participacoesEquipamentos.add(participacao);
		}
		else
			participacao.adicionarDenominador(denominador);
	}
	
	public boolean existeParticipacao(Equipamento equipamento) {
		java.util.Iterator<Participacao> it = participacoesEquipamentos.iterator();
		while(it.hasNext())
			if(it.next().getIdParticipante().equals(equipamento.getId()))
				return true;
		return false;
	}
	
	/**
	 * @param taxa
	 *            a taxa cujos atributos devem ser atribuídos a this (menos o código)
	 */
	public void atribuirTaxa(Taxa taxa) {
		this.setMemoriaCalculo(taxa.getMemoriaCalculo());
		this.setParticipacoesEquipamentos(taxa.getParticipacoesEquipamentos());
		this.setTipo(taxa.getTipo());
		this.atribuir(taxa);
	}	
	
	
	public void addComentarios(List<Comentario> comentarios){
		this.comentarios.addAll(comentarios);
	}
	/**
	 * @return the comentarios
	 */
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public void adicionarMensagem(TipoComentario tipo, String mensagem){
		Comentario comentario = new Comentario();
		comentario.setDataInsercao(new Date());
		comentario.setOrigem(br.org.ons.geracao.evento.OrigemComentario.SISTEMA);
		comentario.setTipo(tipo);
		comentario.setDescricao(mensagem);
		this.comentarios.add(comentario);
	}

	public void adicionarParametro(ParametroTaxa parametro) {
		if(parametro != null && memoriaCalculo.contains(parametro.getId()) == false)
			memoriaCalculo.add(parametro.getId());
	}
	
	public void adicionarParametro(ParametroTaxa parametro1, ParametroTaxa parametro2) {
		adicionarParametro(parametro1);
		adicionarParametro(parametro2);
	}
	
	public void adicionarParametro(ParametroTaxa parametro1, ParametroTaxa parametro2, ParametroTaxa parametro3) {
		adicionarParametro(parametro1);
		adicionarParametro(parametro2);
		adicionarParametro(parametro3);
	}
	
	public void adicionarParametro(ParametroTaxa parametro1, ParametroTaxa parametro2, ParametroTaxa parametro3, ParametroTaxa parametro4) {
		adicionarParametro(parametro1);
		adicionarParametro(parametro2);
		adicionarParametro(parametro3);
		adicionarParametro(parametro4);
	}
	
	public void adicionarParametro(ParametroTaxa parametro1, ParametroTaxa parametro2, ParametroTaxa parametro3, ParametroTaxa parametro4, ParametroTaxa parametro5) {
		adicionarParametro(parametro1);
		adicionarParametro(parametro2);
		adicionarParametro(parametro3);
		adicionarParametro(parametro4);
		adicionarParametro(parametro5);
	}
	
	public void registrarTaxas(Taxa taxa1, Taxa taxa2) {
		memoriaCalculo.add(taxa1.getId());
		memoriaCalculo.add(taxa2.getId());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((periodo.getDataInicio() == null) ? 0 : periodo.getDataInicio().hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((this.getCodigo() == null) ? 0 : this.getCodigo().hashCode());
		result = prime * result + ((this.getValor() == null) ? 0 : this.getValor().hashCode());
		result = prime * result + ((memoriaCalculo == null) ? 0 : memoriaCalculo.hashCode());
		
		
		
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
		Taxa other = (Taxa) obj;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (periodo.getDataInicio().compareTo(other.periodo.getDataInicio()) != 0 ){
			return false;
		}
		
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		
		if (this.getCodigo() == null) {
			if (other.getCodigo() != null)
				return false;
		} else if (!this.getCodigo().equals(other.getCodigo()))
			return false;
		
		if (this.getValor() == null) {
			if (other.getValor() != null)
				return false;
		} else if (!this.getValor().equals(other.getValor()))
			return false;	
		
		if (memoriaCalculo == null) {
			if (other.memoriaCalculo != null)
				return false;
		} else if (!memoriaCalculo.equals(other.memoriaCalculo))
			return false;			

		return true;
	}	
}