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
public class ToUpperCaseFunction implements Function<String, String> {
    private final Locale locale;

    /**
     * Creates function that uses provided {@link java.util.Locale} for case conversion
     */
    public ToUpperCaseFunction(@Nonnull Locale locale) {
        this.locale = checkNotNull(locale);
    }

    /**
     * Creates function that uses default {@link java.util.Locale} for case conversion
     */
    public ToUpperCaseFunction() {
        this(Locale.getDefault());
    }

    @Override
    @Nullable
    public String apply(@Nullable String input) {
        return input == null ? null : input.toUpperCase(locale);
    }
}
