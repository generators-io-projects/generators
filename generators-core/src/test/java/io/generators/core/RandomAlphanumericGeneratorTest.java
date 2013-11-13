package io.generators.core;

import static com.google.common.base.CharMatcher.JAVA_LETTER_OR_DIGIT;
import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.Set;

import com.google.common.base.CharMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RandomAlphanumericGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldReturnRandomAlphaNumericString() {
        int length = 33;

        Generator<String> randomAlphaNumericGenerator = new RandomAlphanumericGenerator(length);
        int letters = 0;
        int numbers = 0;

        Set<String> generatedStrings = newHashSet();
        for (int i = 0; i < 100; i++) {
            String nextString = randomAlphaNumericGenerator.next();
            generatedStrings.add(nextString);

            if (CharMatcher.JAVA_LETTER.matchesAnyOf(nextString)) {
                letters++;
            }
            if (CharMatcher.JAVA_DIGIT.matchesAnyOf(nextString)) {
                numbers++;
            }
            assertThat(nextString.length(), is(33));
            assertThat(JAVA_LETTER_OR_DIGIT.matchesAllOf(nextString), is(true));
        }

        assertThat(generatedStrings, hasSize(100)); //with length 50 no string should be the same
        assertThat(letters, greaterThan(5));
        assertThat(numbers, greaterThan(5));
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
