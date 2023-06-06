package com.github.jinahya.persistence.sakila.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utilities related to {@link Locale}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class LocaleUtils2 {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final Map<Locale, Map<String, List<Locale>>> DISPLAY_COUNTRIES_AND_LOCALES
            = new ConcurrentHashMap<>(new WeakHashMap<>());

    static List<Locale> valuesOfDisplayCountry(final String displayCountry, final Locale inLocale) {
        if (Objects.requireNonNull(displayCountry, "displayCountry is null").isBlank()) {
            throw new IllegalArgumentException("displayCountry is blank");
        }
        Objects.requireNonNull(inLocale, "inLocale is null");
        return Collections.unmodifiableList(
                DISPLAY_COUNTRIES_AND_LOCALES
                        .computeIfAbsent((Locale) inLocale.clone(), k -> new HashMap<>())
                        .computeIfAbsent(
                                displayCountry,
                                k -> Arrays.stream(Locale.getAvailableLocales())
                                        .filter(l -> Objects.equals(l.getDisplayCountry(inLocale), k))
                                        .toList()
                        )
        );
    }

    /**
     * Returns an unmodifiable list of locales which each {@link Locale#getDisplayCountry(Locale) displayCountry},
     * represented in {@link Locale#ENGLISH ENGLISH}, matches specified value.
     *
     * @param displayCountryInEnglish the value of {@link Locale#getDisplayLanguage(Locale) displayLanguage(ENGLISH)} to
     *                                match.
     * @return a list of matched values.
     */
    public static List<Locale> valuesOfDisplayCountryInEnglish(final String displayCountryInEnglish) {
        return valuesOfDisplayCountry(displayCountryInEnglish, Locale.ENGLISH);
    }

    private static final Map<Locale, Map<String, List<Locale>>> DISPLAY_LANGUAGES_AND_LOCALES
            = new ConcurrentHashMap<>(new WeakHashMap<>());

    static List<Locale> valuesOfDisplayLanguage(final String displayLanguage, final Locale inLocale) {
        if (Objects.requireNonNull(displayLanguage, "displayLanguage is null").isBlank()) {
            throw new IllegalStateException("displayLanguage is blank");
        }
        Objects.requireNonNull(inLocale, "inLocale is null");
        return Collections.unmodifiableList(
                DISPLAY_LANGUAGES_AND_LOCALES
                        .computeIfAbsent((Locale) inLocale.clone(), k -> new HashMap<>())
                        .computeIfAbsent(
                                displayLanguage,
                                k -> Arrays.stream(Locale.getAvailableLocales())
                                        .filter(l -> {
                                            final var displayLanguageInLocale = l.getDisplayLanguage(inLocale);
                                            if (displayLanguageInLocale.isBlank()) {
                                                return false;
                                            }
                                            return Objects.equals(displayLanguageInLocale, k);
                                        })
                                        .toList()
                        )
        );
    }

    /**
     * Returns an unmodifiable list of locales which each {@link Locale#getDisplayLanguage(Locale) displayLanguage},
     * represented in {@link Locale#ENGLISH ENGLISH}, matches specified value.
     *
     * @param displayLanguageInEnglish the value of {@link Locale#getDisplayLanguage(Locale) displayLanguage(ENGLISH)}
     *                                 to match.
     * @return an optional of matched value; {@link Optional#empty() empty} if none matches.
     */
    public static List<Locale> valuesOfDisplayLanguageInEnglish(final String displayLanguageInEnglish) {
        return valuesOfDisplayLanguage(displayLanguageInEnglish, Locale.ENGLISH);
    }

    private LocaleUtils2() {
        throw new AssertionError("instantiation is not allowed");
    }
}
