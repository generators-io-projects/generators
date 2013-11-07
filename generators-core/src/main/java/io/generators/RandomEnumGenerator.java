package io.generators;

import io.generators.core.Generator;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generates Enum randomly selected from set of all Enums of type <code>T</code>
 */
public class RandomEnumGenerator<T extends Enum<T>> implements Generator<T> {
    private final Object[] enumSet;
    private final ThreadLocalRandom random;

    /**
     * @param enumClass
     * @throws NullPointerException when <code>enumClass</code> is null
     */
    public RandomEnumGenerator(Class<T> enumClass) {
        this.enumSet = EnumSet.allOf(checkNotNull(enumClass, "enumClass must not be null")).toArray();
        this.random = ThreadLocalRandom.current();
    }

    @Override
    @SuppressWarnings("unchecked") //generated class is always of type T
    public T next() {
        return (T) enumSet[random.nextInt(enumSet.length)];
    }
}
