package io.generators.core;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Generates positive longs (including zero) between from (inclusive) and to (exclusive).
 * Maximum up to {@see Long.MAX_VALUE}
 */
public class RandomPositiveLongGenerator implements Generator<Long> {
    private final long from;
    private final long to;
    private final PositiveLongRandom random = new PositiveLongRandom();

    /**
     * Creates generator that generates integers between from (inclusive) and to (exclusive).
     *
     * @param from inclusive lower bound
     * @param to exclusive upper bound
     *
     * @throws IllegalArgumentException when <code>from</code> is less then zero or <code>to</code> is less then or equal to <code>from</code>
     */
    public RandomPositiveLongGenerator(long from, long to) {
        checkArgument(from >= 0,"from must be >= 0");
        checkArgument(from < to,"from must be < to");
        this.from = from;
        this.to = to;
    }

    /**
     * Creates generator that generates integers between from 0 and {@see Integer.MAX_VALUE}.
     */
    public RandomPositiveLongGenerator() {
        this(0, Long.MAX_VALUE);
    }

    @Override
    public Long next() {
        return from + random.nextLong(to - from);
    }
}
