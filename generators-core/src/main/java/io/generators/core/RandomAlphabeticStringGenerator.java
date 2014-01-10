package io.generators.core;

import org.apache.commons.lang.RandomStringUtils;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Generates a random string whose length is the <code>length</code> of characters
 * specified.
 * <p/>
 * <p>Characters will be chosen from the set of alphabetic
 * characters.</p>
 *
 * @author Tomas Klubal
 */
public class RandomAlphabeticStringGenerator implements Generator<String> {
    private final int length;

    /**
     * Creates generator of random string of specified {@code length}
     *
     * @param length of the generated string
     * @throws java.lang.IllegalArgumentException when length is &lt; 0
     */
    public RandomAlphabeticStringGenerator(int length) {
        checkArgument(length >= 0, "length must be >= 0");
        this.length = length;
    }

    @Override
    public String next() {
        return RandomStringUtils.randomAlphabetic(length);
    }
}
