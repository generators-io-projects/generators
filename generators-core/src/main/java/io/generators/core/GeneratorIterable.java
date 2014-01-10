package io.generators.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Iterable that encapsulates generator.
 * <p/>
 * Iterating over this iterable multiple times may not yield same results depending on backing generator
 *
 * @author Tomas Klubal
 */
public class GeneratorIterable<T> implements Iterable<T> {
    private static final int INFINITE_SIZE = -1;
    private final int size;
    private final Generator<T> generator;

    /**
     * Creates iterable size of {@code limit} using {@code generator} to provide elements
     *
     * @param limit     size of iterable
     * @param generator for element generation
     * @throws java.lang.IllegalArgumentException when limit is &lt; 0
     * @throws java.lang.NullPointerException     when generator is null
     */
    public GeneratorIterable(int limit, Generator<T> generator) {
        checkArgument(limit >= 0, "limit must be >= 0 but it was %s", limit);
        this.size = limit;
        this.generator = checkNotNull(generator, "generator");
    }

    /**
     * Creates iterable of infinite size using {@code generator} to provide elements
     *
     * @param generator for element generation
     * @throws java.lang.IllegalArgumentException when limit is &lt; 0
     * @throws java.lang.NullPointerException     when generator is null
     */
    public GeneratorIterable(Generator<T> generator) {
        this.size = INFINITE_SIZE;
        this.generator = checkNotNull(generator, "generator");
    }

    /**
     * Provides iterator over this iterable
     *
     * @return iterator over this iterable
     */
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
