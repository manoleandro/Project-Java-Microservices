package br.org.ons.sager.agendamento.domain;

import java.io.Serializable;
import java.util.List;

import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.sager.dto.UsinaDTO;

public class VerificaInstalacaoResponse implements Serializable {
		 
	private static final long serialVersionUID = 1081929146944119973L;
	private List<ComentarioSituacao> comentarios;
	private UsinaDTO usina;
	
	public List<ComentarioSituacao> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<ComentarioSituacao> comentarios) {
		this.comentarios = comentarios;
	}
	public UsinaDTO getUsina() {
		return usina;
	}
	public void setUsina(UsinaDTO usina) {
		this.usina = usina;
	}
	 
}
