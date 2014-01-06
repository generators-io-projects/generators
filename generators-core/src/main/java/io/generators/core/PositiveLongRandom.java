package io.generators.core;

import java.security.SecureRandom;

/**
 * Provides method to generate positive longs in range [0,n)
 */
class PositiveLongRandom extends SecureRandom {

    /**
     * Returns a pseudorandom, uniformly distributed {@code int} value
     * between 0 (inclusive) and the specified value (exclusive), drawn from
     * this random number generator's sequence.  The general contract of
     * {@code nextLong} is that one {@code long} value in the specified range
     * is pseudorandomly generated and returned.
     */
    public long nextLong(long n) {
        long bits, val;
        do {
            bits = ((long) (next(31)) << 31) + next(32); //generating positive long + int
            val = bits % n;
        } while (bits - val + (n - 1) < 0);
        return val;
    }
}
