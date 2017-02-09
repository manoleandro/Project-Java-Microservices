package br.org.ons.geracao.evento.taxa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.geracao.modelagem.Variavel;

/**
 * Horas indisponíveis de equipamento, utilizadas para as taxas acumuladas (após Resolução 614).
 */
public class HorasIndisponibilidade extends Taxa {
	public enum CodigoHorasIndisponibilidade {
		PARAMETRO, TAXA_AJUSTADA_CONVERTIDA_HORAS, TAXA_REFERENCIA_CONVERTIDA_HORAS
	}

	private static final long serialVersionUID = -523862289464850127L;
	private Integer mes; // 1..11
	private Integer ano;
	private String idEquipamento;
	private Double potencia = new Double(0);
	private List<String> memoriaCalculo = new ArrayList<>(); // id de parâmetros ou de taxas (ajustada / referência)

	/**
	 * @return o campo mes
	 */
	public Integer getMes() {
		return mes;
	}

	/**
	 * @param mes
	 *            o campo mes a ser definido
	 */
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	
	/**
	 * @return o campo ano
	 */
	public Integer getAno() {
		return ano;
	}

	/**
	 * @param ano
	 *            o campo ano a ser definido
	 */
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	/**
	 * @return o campo idEquipamento
	 */
	public String getIdEquipamento() {
		return idEquipamento;
	}

	/**
	 * @param idEquipamento
	 *            o campo idEquipamento a ser definido
	 */
	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}

	/**
	 * @return o campo potencia
	 */
	public Double getPotencia() {
		return potencia;
	}

	/**
	 * @param potencia
	 *            o campo potencia a ser definido
	 */
	public void setPotencia(Double potencia) {
		this.potencia = potencia;
	}

	/**
	 * @return o campo memoriaCalculo
	 */
	public List<String> getMemoriaCalculo() {
		return memoriaCalculo;
	}

	/**
	 * @param idsEventos
	 *            o campo memoriaCalculo a ser definido
	 */
	public void setMemoriaCalculo(List<String> memoriaCalculo) {
		this.memoriaCalculo = memoriaCalculo;
	}
	
	public Periodo periodo() {
		Periodo periodo = new Periodo();
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, mes-1); // 0..11
		cal.set(Calendar.YEAR, ano);
		periodo.setDataInicio(cal.getTime());
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		periodo.setDataFim(cal.getTime());
		return periodo;
	}
	
	public CodigoHorasIndisponibilidade origem() {
		if(getCodigo() != null)
			if(getCodigo().equals(CodigoHorasIndisponibilidade.PARAMETRO.toString()))
				return CodigoHorasIndisponibilidade.PARAMETRO;
			else
				if(getCodigo().equals(TipoTaxa.AJUSTADA.toString()))
					return CodigoHorasIndisponibilidade.TAXA_AJUSTADA_CONVERTIDA_HORAS;
				else
					if(getCodigo().equals(TipoTaxa.REFERENCIA.toString()))
						return CodigoHorasIndisponibilidade.TAXA_REFERENCIA_CONVERTIDA_HORAS;
		return null;
	}
	
	public void atribuir(Taxa taxa) {
		super.atribuir(taxa);
		if(taxa.getTipo() != null)
			setCodigo(taxa.getTipo().toString());
	}
	
	public void converter() {
		if(this != null && getCodigo() != null)
			if(getCodigo().equals(TipoTaxa.AJUSTADA))
				setCodigo(CodigoHorasIndisponibilidade.TAXA_AJUSTADA_CONVERTIDA_HORAS.toString());
			else
				if(getCodigo().equals(TipoTaxa.REFERENCIA))
					setCodigo(CodigoHorasIndisponibilidade.TAXA_REFERENCIA_CONVERTIDA_HORAS.toString());
	}
	
	/**
	 * @param variavel
	 *            a variavel (taxa ou parâmetro) a associar a this (se já não está)
	 */
	public void registrar(Variavel variavel) {
		if(variavel != null && memoriaCalculo.contains(variavel.getId()) == false)
			memoriaCalculo.add(variavel.getId());
	}

	public void registrar(Variavel variavel1, Variavel variavel2) {
		registrar(variavel1);
		registrar(variavel2);
	}
	
	public void registrar(Variavel variavel1, Variavel variavel2, Variavel variavel3) {
		registrar(variavel1);
		registrar(variavel2, variavel3);
	}
	
	public void registrar(Variavel variavel1, Variavel variavel2, Variavel variavel3, Variavel variavel4) {
		registrar(variavel1);
		registrar(variavel2, variavel3, variavel4);
	}
	
	public void registrar(Variavel variavel1, Variavel variavel2, Variavel variavel3, Variavel variavel4, Variavel variavel5) {
		registrar(variavel1);
		registrar(variavel2, variavel3, variavel4, variavel5);
	}
	
	@Override
	public String toString() {
		return "HorasIndisponiveis [mes (1..12)=" + mes + ", ano=" + ano + ", idEquipamento=" + idEquipamento + ", memoriaCalculo="
				+ memoriaCalculo + "]";
	}
}