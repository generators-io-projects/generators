package io.generators.core;

import com.google.common.base.Predicate;

/**
 * Utility class with convenience methods for creation of predicates
 */
public final class Predicates {

    private Predicates() {
    }

    public static <T> Predicate<T> in(T first, T... rest) {
        return new InPredicate<>(first, rest);
    }

    public static <T> Predicate<T> notIn(T first, T... rest) {
        return com.google.common.base.Predicates.not(in(first, rest));
    }
}
