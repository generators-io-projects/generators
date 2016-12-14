package io.generators.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RandomPositiveLongGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldReturnPositiveLongBetweenZeroAndIntegerMAX() {
        Generator<Long> randomPositiveLongGenerator = Generators.positiveLongs;
        for (int i = 0; i < 100; i++) {
            assertThat(randomPositiveLongGenerator.next(), greaterThanOrEqualTo(0L));
        }
    }

    @Test
    public void shouldReturnPositiveIntegerBetweenFromInclusiveAndToExclusive() {
        long from = 13;
        long to = 20;
        Generator<Long> randomPositiveLongGenerator = Generators.positiveLongs(from, to);
        Set<Long> generatedNumbers = newHashSet();
        for (int i = 0; i < 100; i++) {
            Long generatedLong = randomPositiveLongGenerator.next();
            assertThat(generatedLong, greaterThanOrEqualTo(from));
            assertThat(generatedLong, lessThan(to));
            generatedNumbers.add(generatedLong);
        }

        assertThat(generatedNumbers, hasSize(greaterThan(5))); //with 100 numbers it "should" be more than five
    }

    @Test
    public void shouldThrowExceptionWhenFromIsLessThenZero() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("from must be >= 0");

        Generators.positiveLongs(-1, 20);
    }

    @Test
    public void shouldThrowExceptionWhenFromEqualToTo() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("from must be < to");

        Generators.positiveLongs(5, 5);
    }

    @Test
    public void shouldThrowExceptionWhenFromGreaterThanTo() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("from must be < to");

        Generators.positiveLongs(12, 11);
    }

}
