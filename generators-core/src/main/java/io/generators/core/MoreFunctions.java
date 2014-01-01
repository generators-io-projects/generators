package io.generators.core;

import com.google.common.base.Function;

import java.util.Locale;

/**
 * Convenience utility class that simplifies construction of {@link com.google.common.base.Function}s used in generators
 */
public final class MoreFunctions {

    private MoreFunctions() {
        //prevent instantiation
    }

    public static Function<String, String> toUpperCase(Locale locale) {
        return new ToUpperCaseFunction(locale);
    }

    public static Function<String, String> toUpperCase() {
        return new ToUpperCaseFunction();
    }

    public static Function<String, String> toLowerCase() {
        return new ToLowerCaseFunction();
    }

    public static Function<String, String> toLowerCase(Locale locale) {
        return new ToLowerCaseFunction(locale);
    }
}
