package io.generators.core;

import com.google.common.collect.Range;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Range.closed;

/**
 * Generates random closed range of Comparable%lt;T%gt;
 *
 * @author Tomas Klubal
 */
public class RandomClosedRangeGenerator<T extends Comparable<T>> implements Generator<Range<T>> {
    private final Generator<T> delegate;

    /**
     * Creates range generator that delegates generation endpoints to its delegate
     *
     * @param delegate actual generator that generates values
     * @throws NullPointerException when <code>delegate</code> is null
     */
    public RandomClosedRangeGenerator(Generator<T> delegate) {
        this.delegate = checkNotNull(delegate, "Delegate generator can't be null");
    }

    @Override
    public Range<T> next() {
        T lower = delegate.next();
        T maybeUpper = delegate.next();
        T upper = lower.compareTo(maybeUpper) < 0 ? maybeUpper : lower;

        lower = upper == maybeUpper ? lower : maybeUpper;
        return closed(lower, upper);
    }
}
