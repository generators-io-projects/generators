package io.generators.core;

import com.google.common.base.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Converts string to upper case. Handles null <code>input</code>s
 *
 * @author Tomas Klubal
 */
public class ToLowerCaseFunction implements Function<String, String> {
    private Locale locale;

    /**
     * Creates function that uses specified {@link java.util.Locale} for case conversion
     *
     * @param locale to used during case conversion
     */
    public ToLowerCaseFunction(@Nonnull Locale locale) {
        this.locale = checkNotNull(locale);
    }

    /**
     * Creates function that uses default {@link java.util.Locale} for case conversion
     */
    public ToLowerCaseFunction() {
        this(Locale.getDefault());
    }

    @Override
    @Nullable
    public String apply(@Nullable String input) {
        return input == null ? null : input.toLowerCase(locale);
    }
}
