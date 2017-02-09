package br.org.ons.geracao.modelagem;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Representa um per�odo de tempo de apura��o, suspens�o, etc.
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, 
	include = JsonTypeInfo.As.PROPERTY, 
	property = "_class", 
	visible = false)
@JsonSubTypes({
	@Type(value = Periodo.class, 
			name = "br.org.ons.geracao.modelagem.Periodo"),	
	@Type(value = JanelaCalculo.class, 
		name = "br.org.ons.geracao.modelagem.JanelaCalculo"),
	@Type(value = PeriodoApuracao.class, 
		name = "br.org.ons.geracao.modelagem.PeriodoApuracao")
})
public class Periodo  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Date dataInicio;
	private Date dataFim = null;

	public Periodo() {
		super();
	}
	
	public Periodo(Date dataInicio, Date dataFim) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	/**
	 * @return o campo dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio
	 *            o campo dataInicio a ser definido
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return o campo dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim
	 *            o campo dataFim a ser definido
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	/**
	 * @return true se this conter uma determinada data
	 */
	public boolean contem(Date data) {
		return (data != null) ?
				(dataInicio.compareTo(data)<=0 && ((dataFim != null && dataFim.compareTo(data)>=0) || dataFim == null)) : false;
	}
	
	/**
	 * @return true se this conter um determinado período
	 */
	public boolean contem(Periodo periodo) {
		return (periodo != null) ?
				(dataInicio.compareTo(periodo.dataInicio)<=0 &&
				 ((dataFim != null && periodo.dataFim != null && dataFim.compareTo(periodo.dataFim)>=0)
				  || dataFim == null || periodo.dataFim == null)) : false;
	}
	
	/**
	 * @return true se a data de início de this for a data de inicio de periodo e
	 * 				se a data de fim de this for a data de fim de periodo
	 */
	public boolean igual(Periodo periodo) {		
		return (periodo != null) ?
				(dataInicio.compareTo(periodo.dataInicio)==0 &&
				 ((dataFim != null && periodo.dataFim != null && dataFim.compareTo(periodo.dataFim)==0)
				  || dataFim == null || periodo.dataFim == null)) : false;
	}
	
	public String toString() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(dataInicio);
		String log = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
		if(dataFim != null) {
			cal.setTime(dataFim);
			log += " - " + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
		}
		return log;
	}
}