package io.generators.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RandomPositiveIntegerGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldReturnPositiveIntegerBetweenZeroAndIntegerMAX() {
        Generator<Integer> randomPositiveIntegerGenerator = new RandomPositiveIntegerGenerator();
        for (int i = 0; i < 100; i++) {
            assertThat(randomPositiveIntegerGenerator.next(), greaterThanOrEqualTo(0));
        }
    }

    @Test
    public void shouldReturnPositiveIntegerBetweenFromInclusiveAndToExclusive() {
        int from = 13;
        int to = 20;
        Generator<Integer> randomPositiveIntegerGenerator = new RandomPositiveIntegerGenerator(from, to);
        Set<Integer> generatedNumbers = newHashSet();
        for (int i = 0; i < 100; i++) {
            Integer integer = randomPositiveIntegerGenerator.next();
            assertThat(integer, greaterThanOrEqualTo(from));
            assertThat(integer, lessThan(to));
            generatedNumbers.add(integer);
        }

        assertThat(generatedNumbers, hasSize(greaterThan(5))); //with 100 numbers it "should" be more than five
    }

    @Test
    public void shouldThrowExceptionWhenFromIsLessThenZero() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("from must be >= 0");

        new RandomPositiveIntegerGenerator(-1, 20);
    }

    @Test
    public void shouldThrowExceptionWhenFromEqualToTo() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("from must be < to");

        new RandomPositiveIntegerGenerator(5, 5);
    }

    @Test
    public void shouldThrowExceptionWhenFromGreaterThanTo() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("from must be < to");

        new RandomPositiveIntegerGenerator(12, 11);
    }

}
