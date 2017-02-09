package br.org.ons.exemplo.common;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import br.org.ons.platform.common.Event;

/**
 * Evento publicado quando os parâmetros de um período de apuraação são calculados
 */
public class ParametrosCalculadosEvent extends Event {

	private ZonedDateTime dataInicio;
	private ZonedDateTime dataFim;
	private List<Parametro> parametros = new ArrayList<>();

	public ParametrosCalculadosEvent() {
		super();
	}
	
	public ParametrosCalculadosEvent(ZonedDateTime dataInicio, ZonedDateTime dataFim,
			List<Parametro> parametros) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.parametros = parametros;
	}

	public ZonedDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(ZonedDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public ZonedDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(ZonedDateTime dataFim) {
		this.dataFim = dataFim;
	}

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	@Override
    public String toString() {
        return "ParametrosCalculadosEvent{" +
            "dataInicio='" + dataInicio + "'" +
            ", dataFim='" + dataFim + "'" +
            ", parametros='" + parametros + "'" +
            '}';
    }
}
