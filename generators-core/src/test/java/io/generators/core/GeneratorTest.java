package io.generators.core;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Generator<Integer> integers;
    private AtomicInteger counter;

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
        Generator<Integer> gen = integers.flatMap(x -> () -> new Integer[]{1, 2, 3}[x % 3]);

        assertThat(gen.next(), is(2));
        assertThat(gen.next(), is(3));
        assertThat(gen.next(), is(1));
        assertThat(gen.next(), is(2));
        assertThat(gen.next(), is(3));
        assertThat(gen.next(), is(1));
    }


    @Test
    public void shouldConvertGeneratorToStream() {
        Stream<Integer> integerStream = integers.stream();

        List<Integer> integerList = integerStream.limit(100).collect(toList());

        ContiguousSet<Integer> expectedIntegers = ContiguousSet.create(Range.closed(1, 100), DiscreteDomain.integers());

        assertThat(integerList, contains(expectedIntegers.toArray()));
    }

    @Test
    public void shouldConvertStreamToGenerator() {
        Stream<String> names = Stream.of("Radek", "John", "Karel", "David");

        Generator<String> gen = Generator.from(names);

        assertThat(gen.next(), is("Radek"));
        assertThat(gen.next(), is("John"));
        assertThat(gen.next(), is("Karel"));
        assertThat(gen.next(), is("David"));
    }

    @Test
    public void shouldConvertGeneratorToSupplier() {
        Supplier<Integer> integerSupplier = integers.supplier();

        assertThat(integerSupplier.get(), is(1));
        assertThat(integerSupplier.get(), is(2));
        assertThat(integerSupplier.get(), is(3));
        assertThat(integerSupplier.get(), is(4));
    }

    @Test
    public void shouldConvertSupplierToGenerator() {
        Supplier<Integer> supplier = counter::getAndDecrement;

        Generator<Integer> generator = Generator.from(supplier);

        assertThat(generator.next(), is(1));
        assertThat(generator.next(), is(0));
        assertThat(generator.next(), is(-1));
        assertThat(generator.next(), is(-2));
    }

    @Test
    public void shouldPeekIntoTheGeneratedValues() {
        List<Integer> seenValues = new ArrayList<>();
        List<Integer> generatedValues = new ArrayList<>();
        Generator<Integer> peekingGenerator = integers.peek(seenValues::add);

        generatedValues.add(peekingGenerator.next());
        generatedValues.add(peekingGenerator.next());
        generatedValues.add(peekingGenerator.next());
        generatedValues.add(peekingGenerator.next());
        generatedValues.add(peekingGenerator.next());
        generatedValues.add(peekingGenerator.next());

        assertThat(seenValues, is(generatedValues));
    }


    @Test
    public void shouldFailDuringCreationIfTheConsumerForPeekIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("action can't be null");
        integers.peek(null);
    }



}