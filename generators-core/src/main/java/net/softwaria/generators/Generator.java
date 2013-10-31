package net.softwaria.generators;

/**
 * Generates instance of <T>.
 */
public interface Generator<T> {

    /**
     * @return generated <T>
     */
    public T next();
}
