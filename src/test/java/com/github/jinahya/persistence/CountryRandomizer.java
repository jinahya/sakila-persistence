package com.github.jinahya.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import static org.jeasy.random.FieldPredicates.named;

class CountryRandomizer
        extends _BaseEntityRandomizer<Country> {

    CountryRandomizer() {
        super(Country.class);
    }

    @Override
    protected EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Country_.countryId.getName()))
                .randomize(named(Country_.country.getName()), new StringRandomizer(50))
                ;
    }

    @Override
    protected EasyRandom random() {
        return super.random();
    }

    @Override
    public Country getRandomValue() {
        return super.getRandomValue();
    }
}
