package io.generators.core;

import org.joda.time.DateTime;

import java.security.SecureRandom;

import static com.google.common.base.Objects.firstNonNull;
import static org.joda.time.DateTime.now;

/**
 * Generates random {@link org.joda.time.DateTime}
 */
public class RandomDateTimeGenerator implements Generator<DateTime> {
    private static final int YEAR_BOUND = 100;
    private final PositiveLongRandom random = new PositiveLongRandom();
    private final Long from;
    private final Long to;

    public RandomDateTimeGenerator() {
        this.from = null;
        this.to = null;
    }

    public RandomDateTimeGenerator(DateTime from, DateTime to) {
        this.from = from.getMillis();
        this.to = to.getMillis();

    }

    @Override
    public DateTime next() {
        DateTime now = now();
        long lowerBound = firstNonNull(from, now.minusYears(YEAR_BOUND).getMillis());
        long upperBound = firstNonNull(to, now.plusYears(YEAR_BOUND).getMillis());

        long millis = random.nextLong(upperBound - lowerBound + 1) + lowerBound;
        return new DateTime(millis);
    }

    /**
     * Provides method to generate positive longs in range [0,n)
     */
    private class PositiveLongRandom extends SecureRandom {

        public long nextLong(long n) {
            long bits, val;
            do {
                bits = ((long) (next(31)) << 31) + next(32); //generating positive long + int
                val = bits % n;
            } while (bits - val + (n - 1) < 0);
            return val;
        }
    }
}
