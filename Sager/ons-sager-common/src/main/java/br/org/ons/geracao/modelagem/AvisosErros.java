package br.org.ons.geracao.modelagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma lista de avisos e erros.
 *
 */
public class AvisosErros implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<AvisoErro> avisosErros = new ArrayList<>();

	/**
	 * @return o campo avisosErros
	 */
	public List<AvisoErro> getAvisosErros() {
		return avisosErros;
	}

	/**
	 * @param avisosErros
	 *            o campo avisosErros a ser definido
	 */
	public void setAvisosErros(List<AvisoErro> avisosErros) {
		this.avisosErros = avisosErros;
	}

	/**
	 * @param mensagem
	 *            a mensagem de aviso a ser adicionado a this
	 */
	public void adicionarAviso(String mensagem) {
		AvisoErro ae = new AvisoErro();
		ae.setMensagem(mensagem);
		ae.setTipo(AvisoErro.Tipo.Aviso);
		avisosErros.add(ae);
	}
	/**
	 * @param mensagem
	 *            a mensagem de erro a ser adicionado a this
	 */
	public void adicionarErro(String mensagem) {
		AvisoErro ae = new AvisoErro();
		ae.setMensagem(mensagem);
		ae.setTipo(AvisoErro.Tipo.Erro);
		avisosErros.add(ae);
	}
}
