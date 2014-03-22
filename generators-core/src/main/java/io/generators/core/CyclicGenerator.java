package io.generators.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * Generator that cycles over the given elements infinitely always in the same order.
 *
 * @param <T> type of the elements
 *
 * @author David Bliss
 */
public class CyclicGenerator<T> implements Generator<T> {
    private final Iterator<T> iterator;

    /**
     * Creates generator that cycles over provided {@code first} and {@code rest} elements
     *
     * @param first first element in the set of elements to cycle over
     * @param rest  rest of the elements
     * @throws java.lang.NullPointerException when first or one of the rest elements is null
     */
    @SafeVarargs
    public CyclicGenerator(@Nonnull T first, @Nonnull T... rest) {
        ImmutableList<T> values = ImmutableList.<T>builder()
                .add(first)
                .add(rest)
                .build();

        this.iterator = Iterators.cycle(values);
    }

    @Override
    public T next() {
        return iterator.next();
    }
}
