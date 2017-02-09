package br.org.ons.exemplo.config.wxs;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.keyvalue.core.KeyValueAdapter;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.keyvalue.core.KeyValueTemplate;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import com.ibm.websphere.objectgrid.ObjectGrid;
import com.ibm.websphere.objectgrid.ObjectGridException;
import com.ibm.websphere.objectgrid.ObjectGridManager;
import com.ibm.websphere.objectgrid.ObjectGridManagerFactory;
import com.ibm.websphere.objectgrid.security.config.ClientSecurityConfiguration;

/**
 * 
 *
 */
@Configuration
@EnableConfigurationProperties(WXSProperties.class)
@EnableMapRepositories("br.org.ons.exemplo.repository")
public class WXSConfiguration {

	@Inject
	private WXSProperties wxsProperties;

	@Bean
	@Profile("prod")
	public KeyValueOperations keyValueTemplate() throws ObjectGridException {
		return new KeyValueTemplate(keyValueAdapter());
	}

	@Bean
	@Profile("prod")
	public KeyValueAdapter keyValueAdapter() throws ObjectGridException {
		return new WXSKeyValueAdapter(objectGrid());
	}

	@Bean
	@Profile("prod")
	public ObjectGrid objectGrid() throws ObjectGridException {
		ObjectGridManager ogm = ObjectGridManagerFactory.getObjectGridManager();
		return ogm.getObjectGrid(
				ogm.connect(wxsProperties.getCatalogServerAddresses(), (ClientSecurityConfiguration) null, (URL) null),
				wxsProperties.getObjectGridName());
	}

	@Bean
	public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() throws IOException {
		Jackson2RepositoryPopulatorFactoryBean populatorFactory = new Jackson2RepositoryPopulatorFactoryBean();
		populatorFactory
				.setResources(new PathMatchingResourcePatternResolver().getResources("classpath:/initdb/*.json"));
		return populatorFactory;
	}
}
