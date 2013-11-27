package io.generators.core;

import java.util.Set;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Sets.newHashSet;

/**
 * Generates only unique values.
 * <p/>
 * If delegate generator can't generate unique value it retries the generation n times and then fails with {@link IllegalStateException}.
 * <p/>
 * Default value for n = 10, which is very small number for small collections
 */
public class UniqueGenerator<T> implements Generator<T> {
    public static final int DEFAULT_NUMBER_OF_RETRIES = 10;
    private final Generator<T> delegate;
    private final int numberOfRetries;
    private Set<T> alreadyGenerated = newHashSet();

    public UniqueGenerator(Generator<T> delegate) {
        this(delegate, DEFAULT_NUMBER_OF_RETRIES);
    }

    public UniqueGenerator(Generator<T> delegate, int numberOfRetries) {
        this.delegate = checkNotNull(delegate, "Delegate generator can't be null");
        checkArgument(numberOfRetries > 0, "Number of retries must be at least 1");
        this.numberOfRetries = numberOfRetries;
    }

    @Override
    public T next() {
        int retryCount = 0;
        do {
            T next = delegate.next();
            if (!alreadyGenerated.contains(next)) {
                alreadyGenerated.add(next);
                return next;
            }

            retryCount++;
        } while (retryCount <= numberOfRetries);

        throw new IllegalStateException(on(" ").join("Exhausted", numberOfRetries, "retries trying to generate unique value"));
    }
}