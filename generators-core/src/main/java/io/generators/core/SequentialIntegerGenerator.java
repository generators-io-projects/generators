package io.generators.core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates integers in ascending order
 */
public class SequentialIntegerGenerator implements Generator<Integer> {

    private final AtomicInteger value;

    /**
     * Creates generator generating integers starting from {@code start}
     *
     * @param startAt starting number
     */
    public SequentialIntegerGenerator(int startAt) {
        this.value = new AtomicInteger(startAt);
    }

    /**
     * Creates generator generating integers starting from 1
     */
    public SequentialIntegerGenerator() {
        this(1);
    }

    @Override
    public Integer next() {
        return value.getAndAdd(1);
    }
}
