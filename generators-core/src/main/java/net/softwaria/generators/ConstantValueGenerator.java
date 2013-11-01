package net.softwaria.generators;

/**
 * Generates the object/value passed into the constructor.
 */
public class ConstantValueGenerator<T> implements Generator<T> {
    private final T constantValue;

    public ConstantValueGenerator(T constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    public T next() {
        return constantValue;
    }
}
