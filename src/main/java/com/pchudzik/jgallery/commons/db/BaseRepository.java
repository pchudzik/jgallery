package com.pchudzik.jgallery.commons.db;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

public abstract class BaseRepository<T extends BaseEntity> {
	@PersistenceContext
	protected EntityManager entityManager;

	protected final Class<T> entityClass;

	protected BaseRepository() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public T save(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	public Optional<T> findOne(String id) {
		return Optional.ofNullable(entityManager.find(entityClass, id));
	}

	public T load(String id) {
		return findOne(id)
				.orElseThrow(() -> new NoResultException("No entity of type " + entityClass.getCanonicalName() + " with id " + id));
	}

	public void remove(T entity) {
		entityManager.remove(entity);
	}

	public void remove(String id) {
		entityManager.remove(load(id));
	}
}
