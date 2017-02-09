package br.org.ons.sager.instalacao.rule;

/**
 * Interface cliente para invocação de regras de negócio
 */
public interface RuleClient {

	public static final String DECISION_ID_KEY = "__DecisionID__";
	
	public  RuleResponse invoke(RuleRequest request) ;
}
