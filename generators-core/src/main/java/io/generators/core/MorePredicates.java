package io.generators.core;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * Utility class with convenience methods for creation of predicates
 */
public final class MorePredicates {

    private MorePredicates() {
    }

    public static <T> Predicate<T> in(T first, T... rest) {
        return new InPredicate<>(first, rest);
    }

    public static <T> Predicate<T> notIn(T first, T... rest) {
        return Predicates.not(in(first, rest));
    }
}
