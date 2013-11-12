package io.generators.core;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableList.copyOf;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Generates randomly picked element from collection
 *
 * @param <T>
 */
public class RandomFromCollectionGenerator<T> implements Generator<T> {
    private final List<T> items;
    private final Random random = new Random();

    public RandomFromCollectionGenerator(Collection<T> items) {
        this.items = copyOf(checkNotNull(items, "Collection for generation can't be null"));
    }

    public RandomFromCollectionGenerator(T[] items) {
        this.items = copyOf(checkNotNull(items, "Collection for generation can't be null"));
    }

    @Override
    public T next() {
        int maximumIndex = items.size() - 1;
        return maximumIndex > 0
                ? items.get(random.nextInt(maximumIndex))
                : maximumIndex == 0 ? items.get(0) : null;
    }
}
