package io.generators.core;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generates randomly selected element from collection/array
 *
 * @param <T> type of the collection's elements
 * @author Tomas Klubal
 */
public class RandomFromCollectionGenerator<T> implements Generator<T> {
    private final List<T> items;
    private final Random random = new Random();
    private int exclusiveIndex;

    /**
     * Creates generator that selects values from <code>items</code> passed in
     *
     * @param items to select from
     * @throws NullPointerException when collection passed in is null
     */
    public RandomFromCollectionGenerator(@Nonnull Collection<T> items) {
        this.items = ImmutableList.copyOf(checkNotNull(items, "Collection for generation can't be null"));
        exclusiveIndex = this.items.size();
    }

    /**
     * Creates generator that selects values from <code>items</code> passed in
     *
     * @param items to select from
     * @throws NullPointerException when array passed in is nullF
     */
    @SafeVarargs
    public RandomFromCollectionGenerator(T... items) {
        this.items = ImmutableList.copyOf(checkNotNull(items, "Collection for generation can't be null"));
        exclusiveIndex = this.items.size();
    }

    @Override
    public T next() {
        return exclusiveIndex > 0 ? items.get(random.nextInt(exclusiveIndex)) : null;
    }
}
