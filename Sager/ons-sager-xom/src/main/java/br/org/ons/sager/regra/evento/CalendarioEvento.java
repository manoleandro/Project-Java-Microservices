package br.org.ons.sager.regra.evento;


import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;

public class CalendarioEvento {
	
	public static long diferencaHoras(Date data1, Date data2){		
		long retorno = 0;		
		retorno = (data1.getTime() - data2.getTime())/(60*60*1000);		
		return retorno;
	}
	
	public static long diferencaDias(Date data1, Date data2){		
		long retorno = 0;		
		retorno = (data1.getTime() - data2.getTime())/(24*60*60*1000);		
		return retorno;
	}
	
	public static long diferencaMes(Date data1, Date data2){		
		long retorno = 0;		
		retorno = (data1.getTime() - data2.getTime())/(24*60*60*1000)/30;		
		return retorno;
	}
	
	public static long diferencaAno(Date data1, Date data2){		
		long retorno = 0;		
		retorno = (data1.getTime() - data2.getTime())/(24*60*60*1000)/30/12;		
		return retorno;
	}
	static Comparator<EventoMudancaEstadoOperativo> cmp = new Comparator<EventoMudancaEstadoOperativo>() {
        public int compare(EventoMudancaEstadoOperativo o1, EventoMudancaEstadoOperativo o2) {
           if(o1.getDataVerificada().getTime() < o2.getDataVerificada().getTime()){
        	   return -1;
           } if(o1.getDataVerificada().getTime() > o2.getDataVerificada().getTime()){
        	   return 1;
           }
           return 0;          
        }
    };
    
    public static void ordenadaListaPelaDataverificada(List<EventoMudancaEstadoOperativo> lista){
    	System.out.println(" ooooo "+lista.size());
    	Collections.sort(lista, cmp);
    	for (EventoMudancaEstadoOperativo e : lista) {
			System.out.println(e.getId()+" -- "+e.getDataVerificada());
		}
    	
    }
	

}
