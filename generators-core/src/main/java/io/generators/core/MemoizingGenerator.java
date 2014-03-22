package io.generators.core;

import javax.annotation.Nonnull;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generator that memorizes generated value indefinitely and returns it
 *
 * @author Tomas Klubal
 */
public class MemoizingGenerator<T> implements Generator<T> {
    private final ReentrantLock lock = new ReentrantLock();
    private final Generator<T> delegate;
    private T value;
    private boolean initialized;

    /**
     * Creates generator that memorizes first value returned by the delegate and then always returns that same value
     *
     * @param delegate the delegate generator
     * @throws NullPointerException when delegate is null
     */
    public MemoizingGenerator(@Nonnull Generator<T> delegate) {
        this.delegate = checkNotNull(delegate, "Delegate generator can't be null");
    }

    @Override
    public T next() {
        lock.lock();
        try {
            if (!initialized) {
                value = delegate.next();
                initialized = true;
            }
        } finally {
            lock.unlock();
        }
        return value;
    }
}
