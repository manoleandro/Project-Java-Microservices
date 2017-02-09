package br.org.ons.sager.read.repository;

import java.io.Serializable;

import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.keyvalue.repository.support.QuerydslKeyValueRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.util.Assert;

import com.mysema.codegen.ECJEvaluatorFactory;
import com.querydsl.collections.AbstractCollQuery;
import com.querydsl.collections.CollQuery;
import com.querydsl.collections.CollQueryTemplates;
import com.querydsl.collections.DefaultEvaluatorFactory;
import com.querydsl.collections.DefaultQueryEngine;
import com.querydsl.collections.QueryEngine;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;

/**
 * 
 *
 */
public class ECJCompiledQuerydslKeyValueRepository<T, I extends Serializable> extends QuerydslKeyValueRepository<T, I>
		implements ECJCompiled<T, I> {

	private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

	private final EntityPath<T> path;
	private final PathBuilder<T> builder;

	private final QueryEngine queryEngine;

	public ECJCompiledQuerydslKeyValueRepository(EntityInformation<T, I> entityInformation,
			KeyValueOperations operations) {
		this(entityInformation, operations, DEFAULT_ENTITY_PATH_RESOLVER);
	}

	public ECJCompiledQuerydslKeyValueRepository(EntityInformation<T, I> entityInformation,
			KeyValueOperations operations, EntityPathResolver resolver) {
		super(entityInformation, operations);

		Assert.notNull(resolver, "EntityPathResolver must not be null!");

		this.path = resolver.createPath(entityInformation.getJavaType());
		this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
		this.queryEngine = new DefaultQueryEngine(new DefaultEvaluatorFactory(CollQueryTemplates.DEFAULT,
				new ECJEvaluatorFactory(getClass().getClassLoader())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.keyvalue.repository.support.
	 * QuerydslKeyValueRepository#prepareQuery(com.querydsl.core.types.
	 * Predicate)
	 */
	@Override
	protected AbstractCollQuery<T, ?> prepareQuery(Predicate predicate) {
		CollQuery<T> query = new CollQuery<>(queryEngine);
		query.from(builder, findAll());

		return predicate != null ? query.where(predicate) : query;
	}
}
