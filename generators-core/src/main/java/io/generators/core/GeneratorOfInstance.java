package io.generators.core;

/**
 * Generates the instance passed into the constructor.Null value is allowed.
 */
public class GeneratorOfInstance<T> implements Generator<T> {
    private final T constantValue;

    /**
     * Creates Generator always generating the {@code constantValue}
     *
     * @param constantValue to be generated
     */
    public GeneratorOfInstance(T constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    public T next() {
        return constantValue;
    }
}
