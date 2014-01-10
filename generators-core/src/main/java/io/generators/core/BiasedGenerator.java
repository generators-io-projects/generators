package io.generators.core;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generates values using <code>firstGenerator</code> or <code>secondGenerator</code> depending on percentage bias specified
 *
 * @param <T> type of the generated instance
 *
 * @author Tomas Klubal
 */
public class BiasedGenerator<T> implements Generator<T> {
    private final int percentageBiasTowardsFirst;
    private final Generator<T> firstGenerator;
    private final Generator<T> secondGenerator;
    private final Random random = new Random();


    /**
     * Creates generator
     *
     * @param percentageBiasTowardsFirst percentage how often is the first generator selected
     * @param firstGenerator             generator that is selected according to  {@code percentageBiasTowardsFirst}
     * @param secondGenerator            second generator
     * @throws java.lang.IllegalArgumentException if the percentage bias is not between 0 and 100 inclusive
     * @throws java.lang.NullPointerException     if first or second generator is null
     */
    public BiasedGenerator(int percentageBiasTowardsFirst, Generator<T> firstGenerator, Generator<T> secondGenerator) {
        checkArgument(percentageBiasTowardsFirst >= 0 && percentageBiasTowardsFirst <= 100, "bias must be between 0 and 100");
        this.percentageBiasTowardsFirst = percentageBiasTowardsFirst;
        this.firstGenerator = checkNotNull(firstGenerator, "First generator must not be null");
        this.secondGenerator = checkNotNull(secondGenerator, "Second generator must not be null");
    }

    @Override
    public T next() {
        return random.nextInt(100) + 1 > percentageBiasTowardsFirst ? secondGenerator.next() : firstGenerator.next();
    }
}
