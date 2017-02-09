package br.org.ons.platform.common;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Classe abstrata que deve ser estendida por todos os comandos do sistema.
 * 
 * Um comando representa uma solicitação para que o aggregate execute uma ação
 * que pode alterar seu estado. Um comando pode ser emitido por um usuário do
 * sistema, por um sistema externo ou por um serviço interno ao sistema.
 * 
 * Servi�os do tipo AggregateService são responsáveis por executar as ações
 * representadas pelos comandos, sendo que um comando deve ser executado por uma
 * e somente uma versão do serviço.
 * 
 * O nome do comando deve denotar a ação a ser executada, sendo iniciado por um
 * verbo no infinitivo. Ex: Calcular Taxas, Alterar Usina.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, 
		include = JsonTypeInfo.As.EXISTING_PROPERTY, 
		property = "name")
public abstract class Command {

	/**
	 * @return Nome completo da classe do comando
	 */
	public String getName() {
		return getClass().getName();
	}
}
