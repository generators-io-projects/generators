package io.generators.core;

/**
 * Generates instance of &lt;T&gt;
 *
 * @author Tomas Klubal
 * @author David Bliss
 */
public interface Generator<T> {

    /**
     * Returns generated &lt;T&gt;
     *
     * @return generated <T>
     */
    public T next();
}
