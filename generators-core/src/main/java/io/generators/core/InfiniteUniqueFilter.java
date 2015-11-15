package io.generators.core;

import java.util.Set;
import java.util.function.Predicate;

import static com.google.common.collect.Sets.newHashSet;

/**
 * Simple unique filter that discards all already generated values. It may lead to infinite cycle inside generator.
 * @param <T> Type that's filtered
 * @author Tomas Klubal
 */
class InfiniteUniqueFilter<T> implements Predicate<T> {
    final Set<T> alreadyGenerated = newHashSet();

    @Override
    public boolean test(T x) {
        if (!alreadyGenerated.contains(x)) {
            alreadyGenerated.add(x);
            return true;
        } else return false;
    }
}
