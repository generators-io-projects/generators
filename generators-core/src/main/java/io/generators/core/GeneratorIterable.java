package io.generators.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Iterable that encapsulates generator
 */
public class GeneratorIterable<T> implements Iterable<T> {
    public static final int INFINITE_SIZE = -1;
    private final int size;
    private final Generator<T> generator;

    public GeneratorIterable(int limit, Generator<T> generator) {
        checkArgument(limit >= 0, "limit must be >= 0 but it was %s", limit);
        this.size = limit;
        this.generator = checkNotNull(generator, "generator");
    }

    public GeneratorIterable(Generator<T> generator) {
        this.size = INFINITE_SIZE;
        this.generator = checkNotNull(generator, "generator");
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return size == INFINITE_SIZE || cursor < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                cursor++;
                return generator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove() operation is not supported");
            }
        };
    }
}
