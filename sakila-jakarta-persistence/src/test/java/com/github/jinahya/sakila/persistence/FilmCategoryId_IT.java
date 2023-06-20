package com.github.jinahya.sakila.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Supplier;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static com.github.jinahya.sakila.persistence.Category_IT.newPersistedCategory;
import static com.github.jinahya.sakila.persistence.Film_IT.newPersistedFilm;
import static org.assertj.core.api.Assertions.assertThat;

class FilmCategoryId_IT
        extends _BaseEntityIT<FilmCategory, FilmCategoryId> {

    static FilmCategory newPersistedFilmCategory(final EntityManager entityManager, final Supplier<? extends Film> filmSupplier, final Supplier<? extends Category> categorySupplier) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(filmSupplier, "filmSupplier is null");
        Objects.requireNonNull(categorySupplier, "categorySupplier is null");
        final var instance = new FilmCategory_Randomizer().getRandomValue();
        instance.setFilm(filmSupplier.get());
        instance.setCategory(categorySupplier.get());
        assertThatBean(instance).isValid();
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    static FilmCategory newPersistedFilmCategory(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var instance = new FilmCategory_Randomizer().getRandomValue();
        instance.setFilm(newPersistedFilm(entityManager));
        instance.setCategory(newPersistedCategory(entityManager));
        assertThatBean(instance).isValid();
        entityManager.persist(instance);
        entityManager.flush();
        return instance;
    }

    FilmCategoryId_IT() {
        super(FilmCategory.class, FilmCategoryId.class);
    }

    @Test
    void persist__() {
        final var instance = applyEntityManager(FilmCategoryId_IT::newPersistedFilmCategory);
        assertThat(instance).isNotNull();
        assertThatBean(instance).isValid();
    }
}
