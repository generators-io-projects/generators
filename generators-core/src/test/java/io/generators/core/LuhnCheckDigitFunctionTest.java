package io.generators.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LuhnCheckDigitFunctionTest {

    @Test
    public void shouldGeneratedLuhnNumber() {
        //Given
        LuhnCheckDigitFunction luhnCheckDigitFunction = new LuhnCheckDigitFunction();

        //When & Then
        assertThat(luhnCheckDigitFunction.apply(7992739871L), is(79927398713L));
    }

    @Test
    public void shouldGeneratedLuhnNumberForOneDigitNumber() {
        //Given
        LuhnCheckDigitFunction luhnCheckDigitFunction = new LuhnCheckDigitFunction();

        //When & Then
        assertThat(luhnCheckDigitFunction.apply(5L), is(59L));
    }

    @Test
    public void shouldGeneratedLuhnNumberForTwoDigitNumber() {
        //Given
        LuhnCheckDigitFunction luhnCheckDigitFunction = new LuhnCheckDigitFunction();

        //When & Then
        assertThat(luhnCheckDigitFunction.apply(23L), is(232L));
    }

    @Test
    public void shouldGeneratedLuhnNumberForZero() {
        //Given
        LuhnCheckDigitFunction luhnCheckDigitFunction = new LuhnCheckDigitFunction();

        //When & Then
        assertThat(luhnCheckDigitFunction.apply(0L), is(0L));
    }
}
