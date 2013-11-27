package io.generators.core;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generates type <code>&lt;T&gt;</code> using builder passed in
 */
public class FromBuilderGenerator<T> implements Generator<T> {
    private final Builder<T> builder;

    /**
     * Creates generator that delegates to builder
     * @param builder builder building the &lt;T&gt;
     * @throws NullPointerException when the builder is null
     */
    public FromBuilderGenerator(Builder<T> builder) {
        this.builder = checkNotNull(builder, "Builder can't be null");
    }

    @Override
    public T next() {
        return builder.build();
    }
}
