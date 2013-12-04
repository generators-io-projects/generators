package io.generators.core;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

/**
 * Returns true if {@code input} is one of objects passed into constructor
 */
public class InPredicate<T> implements Predicate<T> {

    private final ImmutableSet<Object> acceptableValues;

    /**
     * Acceptable objects
     *
     * @param first acceptable object
     * @param rest  rest of the acceptable objects
     */
    @SafeVarargs
    public InPredicate(T first, T... rest) {
        acceptableValues = ImmutableSet.builder().add(first).add(rest).build();
    }

    @Override
    public boolean apply(T input) {
        return acceptableValues.contains(input);
    }
}
