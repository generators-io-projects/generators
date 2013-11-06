package io.generators;

import io.generators.core.Generators;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

public class GeneratorsTest {

    @Test
    public void shouldReturnPositiveIntegerGenerator() {
        Integer integer = Generators.positiveInts.next();
        assertThat(integer, greaterThanOrEqualTo(0));
    }

    @Test
    public void shouldReturnPositiveIntegerGeneratorWithRange() {
        Integer integer = Generators.positiveInts(25, 26).next();
        assertThat(integer, is(25));
    }

    @Test
    public void shouldReturnRandomAlphabeticStringGeneratorWithSpecifiedLength() {
        String stringOf5Characters = Generators.alphabetic(5).next();
        assertThat(stringOf5Characters.length(), is(5));
    }

    @Test
    public void shouldReturnRandomAlphabeticStringGeneratorWithLength10() {
        String stringOf5Characters = Generators.alphabetic10.next();
        assertThat(stringOf5Characters.length(), is(10));
    }
}
