package io.generators.core;

/**
 * Consumer of the generated values. Can be guava EventBus, i/o stream or just another generator.
 *
 * @author Tomas Klubal
 */
public interface Consumer<T> {

    /**
     * Consumes generated {@code value}
     *
     * @param value generated by some generator
     */
    public void consume(T value);
}
