package net.softwaria.generators;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import static com.google.common.base.Throwables.propagate;
import static java.lang.invoke.MethodHandles.lookup;
import static java.lang.invoke.MethodType.methodType;

/**
 * Generates "Tiny Type"/"Value Object" with its value provided by supplied Generator
 *
 * @param <T> Generated Tiny Type
 * @param <V> Value type encapsulated by TinyType
 */
public class TypeGenerator<T, V> implements Generator<T> {

    private final MethodHandle constructor;
    private final Generator<V> valueGenerator;

    /**
     * Creates Tiny Type generator that generates &lt;T&gt; using &lt;V&gt; Generator
     *
     * @param tinyTypeClass  class of the Tiny Type
     * @param valueGenerator generator for value wrapped by TinyType
     */
    public TypeGenerator(Class<T> tinyTypeClass, Generator<V> valueGenerator) {
        this.valueGenerator = valueGenerator;
        Class<?> constructorParameterClass = findConstructorParameterType(valueGenerator);
        constructor = findConstructor(tinyTypeClass, constructorParameterClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T next() {
        try {
            V next = valueGenerator.next();
            return (T) constructor.invoke(next);
        } catch (Throwable throwable) {
            throw propagate(throwable);
        }
    }

    private MethodHandle findConstructor(Class<T> tinyTypeClass, Class<?> aClass) {
        try {
            MethodType methodType = methodType(void.class, aClass);
            return lookup().findConstructor(tinyTypeClass, methodType);
        } catch (ReflectiveOperationException e) {
            throw propagate(e);
        }
    }

    private Class<?> findConstructorParameterType(Generator<V> valueGenerator) {
        try {
            Method method = valueGenerator.getClass().getMethod("next");
            return method.getReturnType();
        } catch (NoSuchMethodException e) {
            throw propagate(e);
        }
    }


}
