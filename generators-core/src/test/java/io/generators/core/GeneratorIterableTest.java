package io.generators.core;

import com.google.common.collect.FluentIterable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class GeneratorIterableTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldIterateOverGenerator5Times() {
        //Given
        Iterable<Integer> generatorIterable = new GeneratorIterable<>(5, Generators.positiveInts);

        Set<Integer> integers = FluentIterable.from(generatorIterable).toSet();
        assertThat(integers, hasSize(5));
    }

    @Test
    public void shouldCreateInfiniteIterable() {
        //Given
        Iterable<Integer> generatorIterable = new GeneratorIterable<>(Generators.positiveInts);

        //When
        Set<Integer> integers = FluentIterable.from(generatorIterable).limit(30).toSet();
        assertThat(integers, hasSize(30));
    }

    @Test
    public void shouldFailIfThereAreNoMoreItems() {
        //Given
        expectedException.expect(NoSuchElementException.class);
        Iterable<Integer> generatorIterable = new GeneratorIterable<>(0, Generators.positiveInts);

        Iterator<Integer> iterator = generatorIterable.iterator();
        //When
        iterator.next();
    }

    @Test
    public void shouldFailIfSizeIsLessThanZero() {
        //Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("limit must be >= 0 but it was -2");
        new GeneratorIterable<>(-2, Generators.positiveInts);
    }

    @Test
    public void shouldFailIfRemoveCalled() {
        //Given
        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage("remove() operation is not supported");
        new GeneratorIterable<>(2, Generators.positiveInts).iterator().remove();
    }

    @Test
    public void shouldFailIfGeneratorIsNull() {
        //Given
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("generator");

        new GeneratorIterable<>(12, null);
    }
}
