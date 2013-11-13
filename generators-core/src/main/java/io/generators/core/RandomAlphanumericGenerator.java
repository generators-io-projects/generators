package io.generators.core;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Generates random alphanumeric string of specified <code>length</code>
 */
public class RandomAlphanumericGenerator implements Generator<String> {
    private final int length;

    /**
     * Creates alphanumeric generator that generates strings of specified <code>length</code>
     *
     * @param length length of a generated string
     */
    public RandomAlphanumericGenerator(int length) {
        this.length = checkNotNull(length, "length must be >= 0");
    }

    @Override
    public String next() {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
