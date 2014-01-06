package io.generators.core;

import com.google.common.base.Function;

import java.util.Locale;

/**
 * Converts string to upper case. Handles null <code>input</code>s
 */
public class ToLowerCaseFunction implements Function<String, String> {
    private Locale locale;

    /**
     * Creates function that uses specified {@link java.util.Locale} for case conversion
     * @param locale to used during case conversion
     */
    public ToLowerCaseFunction(Locale locale) {
        this.locale = locale;
    }

    /**
     * Creates function that uses default {@link java.util.Locale} for case conversion
     */
    public ToLowerCaseFunction() {
        this(Locale.getDefault());
    }

    @Override
    public String apply(String input) {
        return input == null ? null : input.toLowerCase(locale);
    }
}
