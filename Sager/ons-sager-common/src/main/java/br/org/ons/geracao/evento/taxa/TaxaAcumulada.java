package br.org.ons.geracao.evento.taxa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.TimeZone;

import br.org.ons.geracao.cadastro.Instalacao;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.modelagem.Periodo;

/**
 * Taxa acumulada
 * 
 */
public class TaxaAcumulada extends Taxa {
	private static final long serialVersionUID = -3156661105183244739L;
	private List<Taxa> taxasPassadas = new ArrayList<Taxa>();
	
	/**
	 * @return o campo taxasPassadas
	 */
	public List<Taxa> getTaxasPassadas() {
		return taxasPassadas;
	}

	/**
	 * @param taxasPassadas
	 *            o campo taxasPassadas a ser definido
	 */
	public void setTaxasPassadas(List<Taxa> taxasPassadas) {
		this.taxasPassadas = taxasPassadas;
	}
	
	public void adicionarTaxaPassada(Taxa taxaPassada) {
		taxasPassadas.add(taxaPassada);
	}
	
	public List<Taxa> taxasPassadasNoPeriodo(Periodo periodo) {
		List<Taxa> taxasPassadasNoPeriodo = new ArrayList<Taxa>();
		Iterator<Taxa> it = taxasPassadas.iterator();
		while(it.hasNext()) {
			Taxa t = it.next();
			if(t.getPeriodo().igual(periodo))
				taxasPassadasNoPeriodo.add(t);
		}
		return taxasPassadasNoPeriodo;
	}
	
	public boolean temTodasTaxasPassadas(int qtdeAlvo) {
		return taxasPassadas.size() >= qtdeAlvo;
	}
	
	public void completarTaxasPassadas(Instalacao instalacao, int nbPeriodos, String taxaReferencia,
									   TipoComentario tipoComentario, String comentario) {
		
		// Procura a taxa passada mais antiga
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		Date data = new Date();
		cal.setTime(data);
		Iterator<Taxa> it = taxasPassadas.iterator();
		while(it.hasNext()) {
			Periodo p = it.next().getPeriodo();
			if(p.getDataFim().compareTo(data) < 0)
				data = p.getDataInicio();
		}
		cal.setTime(data);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		data = cal.getTime();
//		System.out.println(" n periodos "+nbPeriodos + " com taxa " + taxaReferencia + " a partir de " + data);
		for(int i=0; i<nbPeriodos; i++) {
			// Volta um mês no tempo
			cal.setTime(data);
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
			data = cal.getTime();			
			// Procura a taxa de referência da instalação no período
			Taxa taxaRef = instalacao.taxaReferenciaNaData(taxaReferencia, data);			
			// Adiciona a nova taxa de referência como taxa passada
			if(taxaRef != null) {
				Taxa taxaRefClone = new Taxa();
				taxaRefClone.setId(br.org.ons.platform.common.util.IdGenerator.newId());
				taxaRefClone.setCodigo(taxaRef.getCodigo());
				taxaRefClone.atribuirTaxa(taxaRef);				
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum (Calendar.DAY_OF_MONTH));
				taxaRefClone.setPeriodo(new Periodo(data, cal.getTime()));
				taxasPassadas.add(taxaRefClone);
			}
			else {
				adicionarMensagem(tipoComentario, comentario);
				break;
			}
		}
	}
	
	public void calcularMediaTaxasPassadas(int casasDecimais) {
		Iterator<Taxa> it = taxasPassadas.iterator();
		while(it.hasNext()) {
			adicionarNumerador(it.next().getValor());
			adicionarDenominador(BigDecimal.ONE);
		}
		calcular(casasDecimais); // valor = numerador / denominador
	}
}