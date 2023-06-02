package com.github.jinahya.persistence.sakila.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LocaleUtils2Test {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static Stream<Locale> getLocaleStream() {
        return Stream.of(Locale.getAvailableLocales());
    }

    public static Stream<Locale> getLocaleWithNonBlankDisplayCountryStream() {
        return getLocaleStream()
                .filter(l -> !l.getDisplayCountry(Locale.ENGLISH).isBlank());
    }

    public static Stream<Locale> getLocaleWithNonBlankDisplayLanguageStream() {
        return getLocaleStream()
                .filter(l -> !l.getDisplayLanguage(Locale.ENGLISH).isBlank());
    }

    @MethodSource({"getLocaleWithNonBlankDisplayCountryStream"})
    @ParameterizedTest
    void valuesOfDisplayCountry(final Locale locale) {
        final var displayCountry = locale.getDisplayCountry(locale);
        final var values = LocaleUtils2.valuesOfDisplayCountry(displayCountry, locale);
        assertThat(values)
                .extracting(v -> v.getDisplayCountry(locale))
                .containsOnly(displayCountry);
    }

    @MethodSource({"getLocaleWithNonBlankDisplayCountryStream"})
    @ParameterizedTest
    void valuesOfDisplayCountryInEnglish(final Locale locale) {
        final var displayCountryInEnglish = locale.getDisplayCountry(Locale.ENGLISH);
        final var values = LocaleUtils2.valuesOfDisplayCountryInEnglish(displayCountryInEnglish);
        assertThat(values)
                .extracting(v -> v.getDisplayCountry(Locale.ENGLISH))
                .containsOnly(displayCountryInEnglish);
    }

    @MethodSource({"getLocaleWithNonBlankDisplayLanguageStream"})
    @ParameterizedTest
    void valuesOfDisplayLanguage(final Locale locale) {
        final var displayLanguage = locale.getDisplayLanguage(locale);
        final var values = LocaleUtils2.valuesOfDisplayLanguage(displayLanguage, locale);
        assertThat(values)
                .extracting(v -> v.getDisplayLanguage(locale))
                .containsOnly(displayLanguage);
    }

    @MethodSource({"getLocaleWithNonBlankDisplayLanguageStream"})
    @ParameterizedTest
    void valuesOfDisplayLanguageInEnglish(final Locale locale) {
        final var displayLanguageInEnglish = locale.getDisplayLanguage(Locale.ENGLISH);
        final var values = LocaleUtils2.valuesOfDisplayLanguageInEnglish(displayLanguageInEnglish);
        assertThat(values)
                .extracting(v -> v.getDisplayLanguage(Locale.ENGLISH))
                .containsOnly(displayLanguageInEnglish);
    }
}
