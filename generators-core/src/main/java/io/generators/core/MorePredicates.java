package io.generators.core;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import java.util.Collection;

/**
 * Utility class with convenience methods for creation of predicates
 */
public final class MorePredicates {

    private MorePredicates() {
    }

    /**
     * Predicate that returns true when input value is memeber of set consisting of {@code first} and {@code rest}
     * @param first element
     * @param rest of the elements
     * @param <T> type of elements
     * @return the in predicate
     */
    @SafeVarargs
    public static <T> Predicate<T> in(T first, T... rest) {
        Collection<T> allowed = ImmutableList.<T>builder()
                .add(first)
                .add(rest)
                .build();

        return Predicates.in(allowed);
    }

    /**
     * Predicate that returns true when input value is memeber of set consisting of {@code first} and {@code rest}
     * @param first element
     * @param rest of the elements
     * @param <T> type of elements
     * @return the not in predicate
     */
    @SafeVarargs
    public static <T> Predicate<T> notIn(T first, T... rest) {
        return Predicates.not(in(first, rest));
    }
}
