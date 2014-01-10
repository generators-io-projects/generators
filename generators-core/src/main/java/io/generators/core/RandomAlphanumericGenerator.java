package io.generators.core;

import org.apache.commons.lang.RandomStringUtils;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Generates random alphanumeric string of specified <code>length</code>
 *
 * @author Tomas Klubal
 */
public class RandomAlphanumericGenerator implements Generator<String> {
    private final int length;

    /**
     * Creates alphanumeric generator that generates strings of specified <code>length</code>
     *
     * @param length length of a generated string
     * @throws java.lang.IllegalArgumentException when length is &lt; 0
     */
    public RandomAlphanumericGenerator(int length) {
        checkArgument(length >= 0, "length must be >= 0");
        this.length = length;
    }

    @Override
    public String next() {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
