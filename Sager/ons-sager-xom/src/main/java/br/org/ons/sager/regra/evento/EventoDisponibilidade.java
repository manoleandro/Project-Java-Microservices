package br.org.ons.sager.regra.evento;

import java.io.Serializable;

import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;

public class EventoDisponibilidade  extends EventoMudancaEstadoOperativo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1615437405945882112L;
	
	private boolean eventoOrigemExternaNaTaxa;	
	private boolean eventoOrigemInternaNaTaxa;
	private boolean eventoOrigemInternaForaTaxa;
	private boolean ficticio;
	private boolean ignorado;
	private int qtHorasServico;
	private String tipo;
	private DuracaoEvento duracaoEvento;
	private ParEventosDispoAtivosConsecutivos eventoRetificados;
	private ParEventosDispoAtivosConsecutivos eventoSucessor;
	private ParEventosDispoAtivosConsecutivos eventoProdecessor;
	
	public EventoDisponibilidade() {
		super();
	}
	
	
	
	public EventoDisponibilidade(boolean eventoOrigemExternaNaTaxa, boolean eventoOrigemInternaNaTaxa,
			boolean eventoOrigemInternaForaTaxa, boolean ficticio, boolean ignorado, int qtHorasServico, String tipo) {
		super();
		this.eventoOrigemExternaNaTaxa = eventoOrigemExternaNaTaxa;
		this.eventoOrigemInternaNaTaxa = eventoOrigemInternaNaTaxa;
		this.eventoOrigemInternaForaTaxa = eventoOrigemInternaForaTaxa;
		this.ficticio = ficticio;
		this.ignorado = ignorado;
		this.qtHorasServico = qtHorasServico;
		this.tipo = tipo;
	}



	public boolean isEventoOrigemExternaNaTaxa() {
		return eventoOrigemExternaNaTaxa;
	}



	public void setEventoOrigemExternaNaTaxa(boolean eventoOrigemExternaNaTaxa) {
		this.eventoOrigemExternaNaTaxa = eventoOrigemExternaNaTaxa;
	}



	public boolean isEventoOrigemInternaNaTaxa() {
		return eventoOrigemInternaNaTaxa;
	}



	public void setEventoOrigemInternaNaTaxa(boolean eventoOrigemInternaNaTaxa) {
		this.eventoOrigemInternaNaTaxa = eventoOrigemInternaNaTaxa;
	}



	public boolean isEventoOrigemInternaForaTaxa() {
		return eventoOrigemInternaForaTaxa;
	}



	public void setEventoOrigemInternaForaTaxa(boolean eventoOrigemInternaForaTaxa) {
		this.eventoOrigemInternaForaTaxa = eventoOrigemInternaForaTaxa;
	}



	public boolean isFicticio() {
		return ficticio;
	}



	public void setFicticio(boolean ficticio) {
		this.ficticio = ficticio;
	}



	public boolean isIgnorado() {
		return ignorado;
	}



	public void setIgnorado(boolean ignorado) {
		this.ignorado = ignorado;
	}



	public int getQtHorasServico() {
		return qtHorasServico;
	}



	public void setQtHorasServico(int qtHorasServico) {
		this.qtHorasServico = qtHorasServico;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public DuracaoEvento getDuracaoEvento() {
		return duracaoEvento;
	}



	public void setDuracaoEvento(DuracaoEvento duracaoEvento) {
		this.duracaoEvento = duracaoEvento;
	}



	public ParEventosDispoAtivosConsecutivos getEventoRetificados() {
		return eventoRetificados;
	}



	public void setEventoRetificados(ParEventosDispoAtivosConsecutivos eventoRetificados) {
		this.eventoRetificados = eventoRetificados;
	}



	public ParEventosDispoAtivosConsecutivos getEventoSucessor() {
		return eventoSucessor;
	}



	public void setEventoSucessor(ParEventosDispoAtivosConsecutivos eventoSucessor) {
		this.eventoSucessor = eventoSucessor;
	}



	public ParEventosDispoAtivosConsecutivos getEventoProdecessor() {
		return eventoProdecessor;
	}



	public void setEventoProdecessor(ParEventosDispoAtivosConsecutivos eventoProdecessor) {
		this.eventoProdecessor = eventoProdecessor;
	}



	public void adicionarAviso(String mensagem){
		
	}
	public void adicionarCritica(String mensagem){
		
	}
	
	public boolean dataVerificada(){
		return true;
	}
	
	public EventoDisponibilidade eventoRetificado(EventoDisponibilidade evento){
		return new EventoDisponibilidade();
	}
	
	public boolean diferente(EventoDisponibilidade evento){
		return true;
	}
	
	public String horaVerificada(){
		return "";
	}
	
	public boolean igual(EventoDisponibilidade evento){
		return true;
	}
	
	public void remover(){
		
	}
	
	

}
