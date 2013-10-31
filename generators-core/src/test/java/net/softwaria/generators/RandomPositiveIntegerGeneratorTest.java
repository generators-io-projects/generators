package net.softwaria.generators;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;

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
        for (int i = 0; i < 100; i++) {
            assertThat(randomPositiveIntegerGenerator.next(), greaterThanOrEqualTo(from));
            assertThat(randomPositiveIntegerGenerator.next(), lessThan(to));
        }
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
