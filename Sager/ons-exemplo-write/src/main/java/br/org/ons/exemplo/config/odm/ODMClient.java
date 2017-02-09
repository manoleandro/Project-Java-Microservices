package br.org.ons.exemplo.config.odm;

import br.org.ons.exemplo.rule.RuleClient;
import br.org.ons.exemplo.rule.RuleRequest;
import br.org.ons.exemplo.rule.RuleResponse;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import ilog.rules.res.model.IlrPath;
import ilog.rules.res.session.IlrSessionFactory;
import ilog.rules.res.session.IlrSessionRequest;

/**
 * Cliente para execução de regras no IBM ODM
 */
public class ODMClient implements RuleClient {

	/**
	 * Session factory para conexão com IBM ODM
	 */
	private IlrSessionFactory sessionFactory;

	/**
	 * @param sessionFactory Session factory para conexão com IBM ODM
	 */
	public ODMClient(IlrSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public RuleResponse invoke(RuleRequest ruleRequest) {
		try {
			IlrSessionRequest request = sessionFactory.createRequest();
			request.setRulesetPath(IlrPath.parsePath(ruleRequest.getRulePath()));
			request.setInputParameters(ruleRequest.getInputParameters());
			return new RuleResponse(
					sessionFactory.createStatelessSession().execute(request).getOutputParameters());
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		}
	}
}
