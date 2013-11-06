package io.generators.core;

/**
 * Generates the instance passed into the constructor.
 */
public class GeneratorOfInstance<T> implements Generator<T> {
    private final T constantValue;

    public GeneratorOfInstance(T constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    public T next() {
        return constantValue;
    }
}
