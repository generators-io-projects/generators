package io.generators.core;

/**
 * Generates instance of &lt;T&gt;
 */
public interface Generator<T> {

    /**
     * Returns generated &lt;T&gt;
     * @return generated <T>
     */
    public T next();
}
