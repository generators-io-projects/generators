package io.generators.core;

/**
 * Provides fluent interface for creating generators
 */
public class FluentGenerator<T> implements Generator<T> {

    private final Generator<T> delegate;

    private FluentGenerator(Generator<T> delegate) {
        this.delegate = delegate;
    }

    public static <T> FluentGenerator<T> from(Generator<T> generator) {
        return new FluentGenerator<>(generator);
    }

    public FluentGenerator<T> unique() {
        return from(new UniqueGenerator<>(delegate));
    }

    public <K> FluentGenerator<K> ofType(Class<K> type) {
        return from(Generators.ofType(type, delegate));
    }

    public Iterable<T> toIterable(int size) {
        return new GeneratorIterable<>(size, delegate);
    }

    @Override
    public T next() {
        return delegate.next();
    }
}
