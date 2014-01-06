package io.generators.core;

import java.util.Random;

/**
 * Generates true and false randomly
 */
public class RandomBooleanGenerator implements Generator<Boolean> {

    private static final Random RANDOM = new Random();

    @Override
    public Boolean next() {
        return RANDOM.nextBoolean();
    }
}
