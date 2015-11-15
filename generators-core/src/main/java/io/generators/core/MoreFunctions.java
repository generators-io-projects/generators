package io.generators.core;


import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Convenience utility class that simplifies construction of {@link com.google.common.base.Function}s used in generators
 *
 * @author Tomas Klubal
 */
public final class MoreFunctions {

    private MoreFunctions() {
        //prevent instantiation
    }

    /**
     * Creates function that converts inputs to uppercase using provided {@code locale}
     *
     * @param locale to use for case conversion
     * @return the function
     */
    public static Function<String, String> toUpperCase(@Nonnull Locale locale) {
        return new ToUpperCaseFunction(checkNotNull(locale));
    }

    /**
     * Creates function that converts inputs to uppercase using default {@link java.util.Locale}
     *
     * @return the function
     */
    public static Function<String, String> toUpperCase() {
        return new ToUpperCaseFunction();
    }

    /**
     * Creates function that converts inputs to lowercase using default {@link java.util.Locale}
     *
     * @return the function
     */
    public static Function<String, String> toLowerCase() {
        return new ToLowerCaseFunction();
    }

    /**
     * Creates function that converts inputs to lowercase using provided {@code locale}
     *
     * @param locale to use for case conversion
     * @return the function
     */
    public static Function<String, String> toLowerCase(Locale locale) {
        return new ToLowerCaseFunction(locale);
    }

    /**
     * Function that appends Luhn Check Digit to the provided input number
     *
     * @return Function that appends Luhn Check Digit to the provided input number
     */
    public static java.util.function.Function<Long, Long> appendLuhnCheckDigit() {
        return new LuhnCheckDigitFunction();
    }

    /**
     * Creates simple unique filter that discards all already generated values. It may lead to infinite cycle inside generator.
     * @param <T> Type that's filtered
     * @return the filter/predicate
     */
    public static <T> Predicate<T> infiniteUniqueFilter() {
        return new InfiniteUniqueFilter<T>();
    }
}
