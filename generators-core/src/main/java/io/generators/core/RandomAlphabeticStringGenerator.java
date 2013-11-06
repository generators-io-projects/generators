package io.generators.core;

import org.apache.commons.lang.RandomStringUtils;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Generates a random string whose length is the <code>length</code> of characters
 * specified.
 *
 * <p>Characters will be chosen from the set of alphabetic
 * characters.</p>
 */
public class RandomAlphabeticStringGenerator implements Generator<String> {
    private final int length;

    public RandomAlphabeticStringGenerator(int length) {
        checkArgument(length >= 0,"length must be >= 0");
        this.length = length;
    }

    @Override
    public String next() {
        return RandomStringUtils.randomAlphabetic(length);
    }
}
