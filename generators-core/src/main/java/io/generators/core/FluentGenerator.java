package io.generators.core;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides fluent interface for creating generators
 *
 * @author Tomas Klubal
 */
public class FluentGenerator<T> implements Generator<T> {

    private final Generator<T> delegate;

    private FluentGenerator(@Nonnull Generator<T> delegate) {
        this.delegate = checkNotNull(delegate, "delegate can't be null");
    }

    /**
     * Creates FLuentGenerator delegating generation to {@code delegate}
     *
     * @param generator generator to delegate generation to
     * @param <T>       type of the generators
     * @return FluentGenerator
     * @throws java.lang.NullPointerException when {@code delegate} is null
     */
    @CheckReturnValue
    public static <T> FluentGenerator<T> from(@Nonnull Generator<T> generator) {
        return new FluentGenerator<>(generator);
    }

    /**
     * Wraps the delegate generator in {@link io.generators.core.UniqueGenerator}
     *
     * @return FluentGenerator generating only unique values
     */
    @CheckReturnValue
    public FluentGenerator<T> unique() {
        return from(new UniqueGenerator<>(delegate));
    }

    /**
     * Wraps generated values in a specified type.
     *
     * @param type type to wrap generated values with
     * @param <K>  the type
     * @return FluentGenerator generating <K>
     * @see io.generators.core.TypeGenerator
     */
    @CheckReturnValue
    public <K> FluentGenerator<K> ofType(@Nonnull Class<K> type) {
        return from(Generators.ofType(checkNotNull(type), delegate));
    }

    @Override
    public T next() {
        return delegate.next();
    }

}
