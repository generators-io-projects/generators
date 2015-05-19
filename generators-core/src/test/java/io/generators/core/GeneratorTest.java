package io.generators.core;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GeneratorTest {

    private AtomicInteger counter;
    private Generator<Integer> integers;

    @Before
    public void setUp() throws Exception {
        counter = new AtomicInteger(1);
        integers = counter::getAndIncrement;
    }


    @Test
    public void shouldMapToUsingSpecifiedFunction() {
        Generator<Integer> mapGen = integers.map(x -> x * x);

        assertThat(mapGen.next(), is(1));
        assertThat(mapGen.next(), is(4));
        assertThat(mapGen.next(), is(9));
        assertThat(mapGen.next(), is(16));
        assertThat(mapGen.next(), is(25));
    }

    @Test
    public void shouldFilterUsingGivenPredicate() {
        Generator<Integer> gen = integers.filter(x -> x % 2 == 0);

        assertThat(gen.next(), is(2));
        assertThat(gen.next(), is(4));
        assertThat(gen.next(), is(6));
        assertThat(gen.next(), is(8));
    }

    @Test
    public void shouldFlatMap() {
        Generator<Integer> gen = integers.flatMap(x -> () -> new Integer[]{1,2,3}[x % 3]);

        assertThat(gen.next(), is(2));
        assertThat(gen.next(), is(3));
        assertThat(gen.next(), is(1));
        assertThat(gen.next(), is(2));
        assertThat(gen.next(), is(3));
        assertThat(gen.next(), is(1));
    }

}