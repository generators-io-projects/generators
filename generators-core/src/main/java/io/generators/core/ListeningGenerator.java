package io.generators.core;

/**
 * Generates values previously consumed
 */
public class ListeningGenerator<T> implements Generator<T>, Consumer<T>  {

    private T value;

    @Override
    public void consume(T value) {
        this.value = value;
    }

    @Override
    public T next() {
        return value;
    }
}
