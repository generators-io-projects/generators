package io.generators.core;

import com.google.common.base.Function;

import java.util.Locale;

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
    public static Function<String, String> toUpperCase(Locale locale) {
        return new ToUpperCaseFunction(locale);
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
    public static Function<Long, Long> appendLuhnCheckDigit() {
        return new LuhnCheckDigitFunction();
    }
}
