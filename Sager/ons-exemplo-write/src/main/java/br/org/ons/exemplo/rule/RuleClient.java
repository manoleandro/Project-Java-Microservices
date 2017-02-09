package br.org.ons.exemplo.rule;

/**
 * Interface cliente para invocação de regras de negócio
 */
public interface RuleClient {

	public RuleResponse invoke(RuleRequest request);
}
