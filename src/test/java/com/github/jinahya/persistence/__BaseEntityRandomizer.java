package com.github.jinahya.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.Randomizer;

import java.util.Objects;

abstract class __BaseEntityRandomizer<T extends __BaseEntity<?>>
        implements Randomizer<T> {

    __BaseEntityRandomizer(final Class<T> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
    }

    protected EasyRandomParameters parameters() {
        return new EasyRandomParameters();
    }

    protected EasyRandom random() {
        return new EasyRandom(parameters());
    }

    @Override
    public T getRandomValue() {
        return random().nextObject(entityClass);
    }

    protected final Class<T> entityClass;
}
