package io.generators.core;

import java.util.concurrent.atomic.AtomicInteger;

public class SequentialIntegerGenerator implements Generator<Integer> {

    private final AtomicInteger value;

    public SequentialIntegerGenerator(int startAt) {
        this.value = new AtomicInteger(startAt);
    }

    public SequentialIntegerGenerator() {
        this(1);
    }

    @Override
    public Integer next() {
        return value.getAndAdd(1);
    }
}
