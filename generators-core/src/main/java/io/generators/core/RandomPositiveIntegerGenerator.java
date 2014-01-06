package io.generators.core;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Generates positive integers (including zero) between from (inclusive) and to (exclusive).
 * Maximum up to {@link Integer#MAX_VALUE}
 */
public class RandomPositiveIntegerGenerator implements Generator<Integer> {
    private final int from;
    private final int to;
    private final Random random = ThreadLocalRandom.current();

    /**
     * Creates generator that generates integers between from (inclusive) and to (exclusive).
     *
     * @param from inclusive lower bound
     * @param to   exclusive upper bound
     * @throws IllegalArgumentException when <code>from</code> is less then zero or <code>to</code> is less then or equal to <code>from</code>
     */
    public RandomPositiveIntegerGenerator(int from, int to) {
        checkArgument(from >= 0, "from must be >= 0");
        checkArgument(from < to, "from must be < to");
        this.from = from;
        this.to = to;
    }

    /**
     * Creates generator that generates integers between from 0 and {@link Integer#MAX_VALUE}.
     */
    public RandomPositiveIntegerGenerator() {
        this(0, Integer.MAX_VALUE);
    }

    @Override
    public Integer next() {
        return from + random.nextInt(to - from);
    }
}
