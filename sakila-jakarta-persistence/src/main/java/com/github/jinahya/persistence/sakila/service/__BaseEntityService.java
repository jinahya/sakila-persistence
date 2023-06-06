package com.github.jinahya.persistence.sakila.service;

import com.github.jinahya.persistence.sakila.__BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * An abstract base class for subclasses of {@link __BaseEntity}.
 *
 * @param <ENTITY> entity type parameter
 * @param <ID>     id type parameter
 */
abstract class __BaseEntityService<ENTITY extends __BaseEntity<ID>, ID extends Comparable<? super ID>>
        extends ___PersistenceService {

    /**
     * Creates a new instance with specified entity class and id class.
     *
     * @param entityClass the entity class.
     * @param idClass     the id class.
     */
    __BaseEntityService(final Class<ENTITY> entityClass, final Class<ID> idClass) {
        super();
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
        this.idClass = requireNonNull(idClass, "idClass is null");
    }

    /**
     * Invokes {@link EntityManager#flush() flush()} method on an injected instance of {@link EntityManager}.
     *
     * @see EntityManager#flush()
     */
    public void flush() {
        applyEntityManager(em -> {
            em.flush();
            return null;
        });
    }

    /**
     * Invokes {@link EntityManager#persist(Object)} method, on an injected instance of {@link EntityManager}, with
     * specified entity instance, and returns the specified value.
     *
     * @param entity the entity instance to persist.
     * @return given {@code entity}.
     * @see EntityManager#persist(Object)
     */
    public @Valid @NotNull ENTITY persist(final @Valid @NotNull ENTITY entity) {
        requireNonNull(entity, "entity is null");
        applyEntityManagerInTransaction(em -> {
            em.persist(entity);
            return null;
        });
        return entity;
    }

    /**
     * Invokes {@link EntityManager#merge(Object) merge(Object)} method, on an injected instance of
     * {@link EntityManager}, with specified entity instance, and returns the result.
     *
     * @param entity the entity instance to merge.
     * @return the result of the {@link EntityManager#merge(Object) merge} invocation.
     * @see EntityManager#merge(Object)
     */
    public @Valid @NotNull ENTITY merge(final @Valid @NotNull ENTITY entity) {
        requireNonNull(entity, "entity is null");
        return applyEntityManagerInTransaction(em -> em.merge(entity));
    }

    /**
     * Finds the entity identified by specified value.
     *
     * @param id the value identifies the entity.
     * @return an optional of found entity identified by {@code id}; empty if not found.
     * @see EntityManager#find(Class, Object)
     */
    public Optional<@Valid ENTITY> findById(final @NotNull ID id) {
        requireNonNull(id, "id is null");
        return Optional.ofNullable(
                applyEntityManager(
                        em -> em.find(entityClass, id) // null if not found
                )
        );
    }

    /**
     * Finds all entities whose {@link ID} attributes are greater than specified value, ordered by the <em>id></em>
     * attribute in ascending order.
     *
     * @param idExpressionMapper  a function for evaluating the path to the {@link ID} attribute.
     * @param idValueMinExclusive the lower exclusive value of the {@link ID} attribute to limit.
     * @param maxResults          a number of maximum results to limit
     * @return a list of found entities, ordered by the <em>id</em> attribute in ascending order.
     */
    @NotNull List<@Valid @NotNull ENTITY> findAll(
            @NotNull final Function<? super Root<ENTITY>, ? extends Expression<? extends ID>> idExpressionMapper,
            @NotNull final ID idValueMinExclusive, @Positive final int maxResults) {
        requireNonNull(idExpressionMapper, "idExpressionMapper is null");
        requireNonNull(idValueMinExclusive, "idValueMinExclusive is null");
        requireNonNull(maxResults, "maxResults is null");
        return applyEntityManager(em -> {
            final var builder = em.getCriteriaBuilder();
            final var query = builder.createQuery(entityClass);
            final var root = query.from(entityClass);                                                // FROM ENTITY AS e
            query.select(root);                                                                              // SELECT e
            final var idExpression = idExpressionMapper.apply(root);
            query.where(builder.greaterThan(idExpression, idValueMinExclusive));                     // WHERE e.ID = :id
            query.orderBy(builder.asc(idExpression));                                               // ORDER BY e.ID ASC
            return em.createQuery(query)
                    .setMaxResults(maxResults)
                    .getResultList();
        });
    }

    @NotNull <V> List<@Valid @NotNull ENTITY> findAllBy(
            @NotNull final Function<? super Root<ENTITY>, ? extends Expression<? extends ID>> idExpressionMapper,
            @NotNull final ID idValueMinExclusive, @Positive final int maxResults,
            @NotNull final Function<? super Root<ENTITY>, ? extends Expression<? extends V>> attributeExpressionMapper,
            @NotNull final V attributeValue) {
        requireNonNull(idExpressionMapper, "idExpressionMapper is null");
        requireNonNull(idValueMinExclusive, "idValueMinExclusive is null");
        requireNonNull(attributeExpressionMapper, "attributeExpressionMapper is null");
        requireNonNull(maxResults, "maxResults is null");
        return applyEntityManager(em -> {
            final var builder = em.getCriteriaBuilder();
            final var query = builder.createQuery(entityClass);
            final var root = query.from(entityClass);                                              // FROM <ENTITY> AS e
            query.select(root);                                                                              // SELECT e
            final var idExpression = idExpressionMapper.apply(root);
            final var attributeExpression = attributeExpressionMapper.apply(root);
            query.where(builder.and(
                    builder.equal(attributeExpression, attributeValue),           // WHERE e.ATTRIBUTE = :attributeValue
                    builder.greaterThan(idExpression, idValueMinExclusive)            // AND e.ID > :idValueMinExclusive
            ));
            query.orderBy(builder.asc(idExpression));                                               // ORDER BY e.ID ASC
            return em.createQuery(query)
                    .setMaxResults(maxResults)
                    .getResultList();
        });
    }

    /**
     * The entity class.
     */
    final Class<ENTITY> entityClass;

    /**
     * The type of id of {@link #entityClass}.
     */
    final Class<ID> idClass;
}
