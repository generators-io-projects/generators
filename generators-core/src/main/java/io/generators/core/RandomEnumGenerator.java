package io.generators.core;

import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generates Enum randomly selected from set of all Enums of type <code>T</code>
 *
 * @author Tomas Klubal
 */
public class RandomEnumGenerator<T extends Enum<T>> implements Generator<T> {
    private final T[] enums;
    private final ThreadLocalRandom random;

    /**
     * @param enumClass class of the enum for which to generate the enums
     * @throws NullPointerException when <code>enumClass</code> is null
     */
    public RandomEnumGenerator(Class<T> enumClass) {
        this.enums = checkNotNull(enumClass, "enumClass must not be null").getEnumConstants();
        this.random = ThreadLocalRandom.current();
    }

    @Override
    @SuppressWarnings("unchecked") //generated class is always of type T
    public T next() {
        return enums[random.nextInt(enums.length)];
    }
}
