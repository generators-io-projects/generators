package io.generators.core;

import org.joda.time.DateTime;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Generates random {@link org.joda.time.DateTime}
 */
public class RandomDateTimeGenerator implements Generator<DateTime> {
    private Random random = new SecureRandom();

    @Override
    public DateTime next() {
        return DateTime.now().plusDays(random.nextInt(365 * 100) * (random.nextBoolean() ? 1 : -1)).plusMillis(random.nextInt());
    }
}
