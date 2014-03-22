package io.generators.core;

import javax.annotation.Nonnull;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.propagate;
import static java.lang.invoke.MethodHandles.lookup;
import static java.lang.invoke.MethodType.methodType;

/**
 * Generates "Tiny Type"/"Value Object" with its value provided by supplied Generator
 *
 * @param <T> Generated Tiny Type
 * @param <V> Value type encapsulated by TinyType
 *
 * @author Tomas Klubal
 */
public class TypeGenerator<T, V> implements Generator<T> {
    private final Class<T> typeClass;
    private final Generator<V> valueGenerator;
    private MethodHandle constructor;

    /**
     * Creates Tiny Type generator that generates &lt;T&gt; using &lt;V&gt; Generator
     *
     * @param typeClass      class of the Tiny Type
     * @param valueGenerator generator for value wrapped by TinyType
     * @throws java.lang.NullPointerException when {@code typeCass}  or {@code valueGenerator }is null
     *
     */
    public TypeGenerator(@Nonnull Class<T> typeClass, @Nonnull Generator<V> valueGenerator) {
        this.typeClass = checkNotNull(typeClass, "typeClass");
        this.valueGenerator = checkNotNull(valueGenerator, "valueGenerator");
    }

    @Override
    @SuppressWarnings("unchecked")
    public T next() {
        V next = valueGenerator.next();
        lookupConstructor(typeClass, next.getClass());
        try {
            return (T) constructor.invoke(next);
        } catch (Throwable throwable) {
            throw propagate(throwable);
        }
    }

    private void lookupConstructor(Class<T> typeClass, Class<?> aClass) {
        MethodType methodType = methodType(void.class, aClass);
        try {
            constructor = lookup().findConstructor(typeClass, methodType);
        } catch (ReflectiveOperationException e) {
            throw propagate(e);
        }
    }
}
