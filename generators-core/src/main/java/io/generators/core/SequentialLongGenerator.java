package io.generators.core;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates {@code Long}s in sequential order.
 *
 * @author David Bliss
 */
public class SequentialLongGenerator implements Generator<Long> {

    private final AtomicLong value;

    public SequentialLongGenerator(long startAt) {
        this.value = new AtomicLong(startAt);
    }


    /**
     * Creates generator generating integers starting from 1
     */
    public SequentialLongGenerator() {
        this(1L);
    }

    @Override
    public Long next() {
        return value.getAndAdd(1L);
    }
}
