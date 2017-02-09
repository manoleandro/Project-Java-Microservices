package br.org.ons.platform.config;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Named;
import javax.sql.DataSource;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import br.org.ons.platform.scheduler.AutowiringSpringBeanJobFactory;
import liquibase.integration.spring.SpringLiquibase;

/**
 * Classe de configuração do serviço de agendamento de tarefas
 *
 */
@Configuration
public class SchedulerConfiguration {

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext,
			// injecting SpringLiquibase to ensure liquibase is already
			// initialized and created the quartz tables:
			SpringLiquibase springLiquibase) {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory,
			@Named("QuartzProperties") Properties properties) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		// this allows to update triggers in DB when updating settings in config
		// file:
		factory.setOverwriteExistingJobs(true);
		factory.setDataSource(dataSource);
		factory.setJobFactory(jobFactory);

		factory.setQuartzProperties(properties);

		return factory;
	}

	@Profile("!cucumber")
	@Bean(name = "QuartzProperties")
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}
	
	@Profile("cucumber")
	@Bean(name = "QuartzProperties")
	public Properties testQuartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz-cucumber.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}
}
