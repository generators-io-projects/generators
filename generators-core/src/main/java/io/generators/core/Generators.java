package io.generators.core;

import com.google.common.collect.FluentIterable;
import io.generators.GeneratorIterable;

import java.util.List;
import java.util.Set;

/**
 * Utility class listing all generators for convenience
 */
public final class Generators {
    public static final Generator<Integer> positiveInts = new RandomPositiveIntegerGenerator();
    public static final Generator<String> alphabetic10 = new RandomAlphabeticStringGenerator(10);

    private Generators() {
    }

    public static Generator<Integer> positiveInts(int from, int to) {
        return new RandomPositiveIntegerGenerator(from, to);
    }

    public static Generator<String> alphabetic(int length) {
        return new RandomAlphabeticStringGenerator(length);
    }

    public static <T> Generator<T> ofInstance(T instance) {
        return new GeneratorOfInstance<>(instance);
    }

    public static <T, V> Generator<T> ofType(Class<T> type, Generator<V> valueGenerator) {
        return new TypeGenerator<>(type, valueGenerator);
    }

    public static <T> Iterable<T> iterable(int limit, Generator<T> generator) {
        return new GeneratorIterable<T>(limit, generator);
    }

    public static <T> Generator<T> biased(int percentageBiasTowardsFirst, Generator<T> firstGenerator, Generator<T> secondGenerator) {
        return new BiasedGenerator<T>(percentageBiasTowardsFirst, firstGenerator, secondGenerator);
    }

    public static <T> List<T> listFrom(int limit, Generator<T> generator) {
        return FluentIterable.from(new GeneratorIterable<T>(generator)).limit(limit).toList();
    }

    public static <T> Set<T> setFrom(int limit, Generator<T> generator) {
        return FluentIterable.from(new GeneratorIterable<T>(generator)).limit(limit).toSet();
    }
}
