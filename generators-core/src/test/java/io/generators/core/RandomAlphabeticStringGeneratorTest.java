package io.generators.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Set;

import static com.google.common.base.CharMatcher.JAVA_LETTER;
import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class RandomAlphabeticStringGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldReturnPositiveIntegerBetweenFromInclusiveAndToExclusive() {
        int length = 50;

        Generator<String> randomAlphabeticStringGenerator = new RandomAlphabeticStringGenerator(length);

        Set<String> generatedStrings = newHashSet();
        for (int i = 0; i < 100; i++) {
            String nextString = randomAlphabeticStringGenerator.next();
            generatedStrings.add(nextString);

            assertThat(nextString.length(), is(50));
            assertThat(JAVA_LETTER.matchesAllOf(nextString), is(true));
        }

        assertThat(generatedStrings, hasSize(100)); //with length 50 no string should be the same
    }

    @Test
    public void shouldThrowExceptionWhenLengthIsLessThenZero() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("length must be >= 0");

        new RandomAlphabeticStringGenerator(-1);
    }

    @Test
    public void shouldGenerateBlankString() {
        assertThat(new RandomAlphabeticStringGenerator(0).next(), is(""));
    }
}
