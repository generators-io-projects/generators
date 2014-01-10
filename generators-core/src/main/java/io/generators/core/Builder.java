package io.generators.core;

/**
 * Builder of <code>&lt;T&gt;</code>
 *
 * @param <T>
 *
 * @author Tomas Klubal
 */
public interface Builder<T> {
    /**
     * Builds <code>%lt;T&gt;</></code>
     *
     * @return built type %lt;T&gt;
     */
    T build();
}
