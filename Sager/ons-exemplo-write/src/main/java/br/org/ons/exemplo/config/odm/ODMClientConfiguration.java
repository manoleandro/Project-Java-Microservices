package br.org.ons.exemplo.config.odm;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.naming.Context;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.support.MBeanServerFactoryBean;

import br.org.ons.exemplo.rule.RuleClient;
import ilog.rules.res.session.IlrEJB3SessionFactory;
import ilog.rules.res.session.IlrSessionFactory;

/**
 * Configuração da conexão com o IBM ODM
 */
@Configuration
@EnableConfigurationProperties(ODMProperties.class)
public class ODMClientConfiguration {

	@Inject
	private ODMProperties properties;

	private static final String JNDI_EJB_REMOTE_NAME = "ilog.rules.res.session.impl.ejb3.IlrStatelessSessionRemote";

	@Bean
	public RuleClient ruleClient() {
		return new ODMClient(getIlrEJB3SessionFactory());
	}

	private IlrSessionFactory getIlrEJB3SessionFactory() {
		Map<String, String> jndi = new HashMap<>();
		jndi.put(Context.PROVIDER_URL, "corbaloc:iiop:" + properties.getHost() + ":" + properties.getPort());
		jndi.put(Context.SECURITY_PRINCIPAL, properties.getUsername());
		jndi.put(Context.SECURITY_CREDENTIALS, properties.getPassword());

		IlrEJB3SessionFactory factory = new IlrEJB3SessionFactory(new Hashtable<>(jndi));
		factory.setRemote(true);
		factory.setStatelessRemoteJndiName(JNDI_EJB_REMOTE_NAME);
		return factory;
	}

	@Profile({ "dev", "test" })
	@Bean
	public MBeanServer mbeanServer() {
		MBeanServerFactoryBean factory = new MBeanServerFactoryBean();
		factory.setLocateExistingServerIfPossible(true);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
}
