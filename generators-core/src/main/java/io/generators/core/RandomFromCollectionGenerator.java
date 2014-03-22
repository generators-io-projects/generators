package io.generators.core;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableList.copyOf;

/**
 * Generates randomly selected element from collection/array
 *
 * @param <T> type of the collection's elements
 * @author Tomas Klubal
 */
public class RandomFromCollectionGenerator<T> implements Generator<T> {
    private final List<T> items;
    private final Random random = new Random();

    /**
     * Creates generator that selects values from <code>items</code> passed in
     *
     * @param items to select from
     * @throws NullPointerException when collection passed in is null
     */
    public RandomFromCollectionGenerator(@Nonnull Collection<T> items) {
        this.items = copyOf(checkNotNull(items, "Collection for generation can't be null"));
    }

    /**
     * Creates generator that selects values from <code>items</code> passed in
     *
     * @param items to select from
     * @throws NullPointerException when array passed in is null
     */
    @SafeVarargs
    public RandomFromCollectionGenerator(T... items) {
        this.items = copyOf(checkNotNull(items, "Collection for generation can't be null"));
    }

    @Override
    public T next() {
        int maximumIndex = items.size() - 1;
        if (maximumIndex > 0) {
            return items.get(random.nextInt(maximumIndex));
        } else if (maximumIndex == 0) {
            return items.get(0);
        } else {
            return null;
        }
    }
}
