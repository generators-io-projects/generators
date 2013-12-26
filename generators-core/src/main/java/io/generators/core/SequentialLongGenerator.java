package io.generators.core;

import java.util.concurrent.atomic.AtomicLong;

public class SequentialLongGenerator implements Generator<Long> {

    private final AtomicLong value;

    public SequentialLongGenerator(long startAt) {
        this.value = new AtomicLong(startAt);
    }

    public SequentialLongGenerator() {
        this(1L);
    }

    @Override
    public Long next() {
        return value.getAndAdd(1L);
    }
}
