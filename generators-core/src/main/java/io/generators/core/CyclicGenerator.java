package io.generators.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;

import java.util.Iterator;

public class CyclicGenerator<T> implements Generator<T> {
    private final Iterator<T> iterator;

    public CyclicGenerator(T first, T... rest) {
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
