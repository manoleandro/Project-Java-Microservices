package br.org.ons.sager.read.web.rest.dto;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.org.ons.sager.dto.EquipamentoDTO;
import br.org.ons.sager.read.domain.Disp;

public class DispDTO {
	
	private ZonedDateTime data;
	private boolean isDispConsolidada;
	private Map<String, String> valores;

    
    
    @JsonCreator
    public DispDTO() {
    	
    }
    
    public DispDTO(Date data, Date hora, List<EquipamentoDTO>equipamentos){
    	Calendar calData = Calendar.getInstance();
    	calData.setTime(data);
    	Calendar calHora = Calendar.getInstance();
    	calHora.setTime(hora);
    	
    	calData.set(Calendar.HOUR, calHora.get(Calendar.HOUR));
    	calData.set(Calendar.MINUTE, calHora.get(Calendar.MINUTE));
    	calData.set(Calendar.SECOND, calHora.get(Calendar.SECOND));
    	
    	this.data = ZonedDateTime.ofInstant(calData.toInstant(), ZoneId.systemDefault());
    	
    	this.valores = new LinkedHashMap<String, String>();
    	
    	//Inicializar os Mapas
    	for (int i =0; i < equipamentos.size(); i++){
    		EquipamentoDTO equipamento = equipamentos.get(i);
    		this.valores.put(equipamento.getId() + "_O", "-");
    		this.valores.put(equipamento.getId() + "_C", "-");
    		this.valores.put(equipamento.getId() + "_E", "-");
    	}    	
    }
    public DispDTO(Date data, List<EquipamentoDTO>equipamentos, boolean isDispConsolidada, String[] tipos){
    	
    	this.data = ZonedDateTime.ofInstant(data.toInstant(), ZoneId.systemDefault());
    	this.isDispConsolidada = isDispConsolidada;
    	
    	this.valores = new LinkedHashMap<String, String>();
    	
    	//Inicializar os Mapas
    	for (int i =0; i < equipamentos.size(); i++){
    		EquipamentoDTO equipamento = equipamentos.get(i);
    		for (String tipo : tipos) {
    			switch (tipo) {
					case "O":
						this.valores.put(equipamento.getId() + "_O", "-");					
						break;
					case "E":
						this.valores.put(equipamento.getId() + "_E", "-");
						break;
					case "C":
						this.valores.put(equipamento.getId() + "_C", "-");
						break;
					default:
						this.valores.put(equipamento.getId() + "_O", "-");
						this.valores.put(equipamento.getId() + "_E", "-");
						this.valores.put(equipamento.getId() + "_C", "-");
						break;
				}
			}
    	}    	
    }

	


	public ZonedDateTime getData() {
		return data;
	}

	public void setData(ZonedDateTime data) {
		this.data = data;
	}

	public Map<String, String> getValores() {
		return valores;
	}

	public void setValores(Map<String, String> valores) {
		this.valores = valores;
	}
	
	public void setValor(Disp disp){
		if (disp.getNum_do() != null)
			this.valores.replace(disp.getEquipamento() + "_O", Double.toString(disp.getNum_do()) );
		if (disp.getNum_dc() != null)
			this.valores.replace(disp.getEquipamento() + "_C", Double.toString(disp.getNum_dc()) );
		if (disp.getNum_de() != null)
			this.valores.replace(disp.getEquipamento() + "_E", Double.toString(disp.getNum_de()) );  
	}

	public boolean isDispConsolidada() {
		return isDispConsolidada;
	}

	public void setDispConsolidada(boolean isDispConsolidada) {
		this.isDispConsolidada = isDispConsolidada;
	}

	@Override
    public String toString() {
        return "DispDTO{" +       	
        
            '}';
    }
}
