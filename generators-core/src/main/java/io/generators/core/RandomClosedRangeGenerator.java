package io.generators.core;

import com.google.common.collect.Ordering;
import com.google.common.collect.Range;

import javax.annotation.Nonnull;

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
    public RandomClosedRangeGenerator(@Nonnull Generator<T> delegate) {
        this.delegate = checkNotNull(delegate, "Delegate generator can't be null");
    }

    @Override
    public Range<T> next() {
        T a = delegate.next();
        T b = delegate.next();
        T lower = Ordering.natural().min(a, b);
        T upper = Ordering.natural().max(a, b);

        return closed(lower, upper);
    }
}
