package io.generators.core;

import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

public class LowerCaseGenerator implements Generator<String> {

    private final Generator<String> delegate;
    private final Locale locale;

    public LowerCaseGenerator(Generator<String> delegate, Locale locale) {
        this.delegate = checkNotNull(delegate, "delegate");
        this.locale = checkNotNull(locale, "locale");
    }

    public LowerCaseGenerator(Generator<String> delegate) {
        this(delegate, Locale.getDefault());
    }

    @Override
    public String next() {
        return delegate.next().toLowerCase(locale);
    }
}
