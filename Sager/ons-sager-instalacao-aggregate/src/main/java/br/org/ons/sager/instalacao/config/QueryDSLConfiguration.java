package br.org.ons.sager.instalacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysema.codegen.ECJEvaluatorFactory;
import com.querydsl.collections.CollQueryTemplates;
import com.querydsl.collections.DefaultEvaluatorFactory;
import com.querydsl.collections.DefaultQueryEngine;
import com.querydsl.collections.QueryEngine;

/**
 * 
 *
 */
@Configuration
public class QueryDSLConfiguration {

	@Bean
	public QueryEngine queryEngine() {
		return new DefaultQueryEngine(new DefaultEvaluatorFactory(CollQueryTemplates.DEFAULT,
				new ECJEvaluatorFactory(getClass().getClassLoader())));
	}
}
