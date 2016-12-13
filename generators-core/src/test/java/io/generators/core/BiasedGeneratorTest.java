package io.generators.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BiasedGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldGenerateAbout20PercentOfFirstGenerator() {
        //Given
        Generator<Boolean> firstGenerator = () -> true;
        Generator<Boolean> secondGenerator = () -> false;
        int biasTowardsFirst = 20;
        Generator<Boolean> biasedGenerator = new BiasedGenerator<>(biasTowardsFirst, firstGenerator, secondGenerator);
        int trueCount = 0;

        //When
        for (int i = 0; i < 10000; i++) {
            if (biasedGenerator.next()) {
                trueCount++;
            }
        }

        //Then
        assertThat(trueCount, greaterThan(1500));
        assertThat(trueCount, lessThan(2500));
    }

    @Test
    public void shouldGenerate100PercentOfFirstGenerator() {
        //Given
        Generator<Boolean> firstGenerator = () -> true;
        Generator<Boolean> secondGenerator = () -> false;
        int biasTowardsFirst = 100;
        Generator<Boolean> biasedGenerator = new BiasedGenerator<>(biasTowardsFirst, firstGenerator, secondGenerator);
        int trueCount = 0;

        //When
        for (int i = 0; i < 10000; i++) {
            if (biasedGenerator.next()) {
                trueCount++;
            }
        }

        //Then
        assertThat(trueCount, is(10000));
    }

    @Test
    public void shouldGenerate0PercentOfFirstGenerator() {
        //Given
        Generator<Boolean> firstGenerator = () -> true;
        Generator<Boolean> secondGenerator = () -> false;
        int biasTowardsFirst = 0;
        Generator<Boolean> biasedGenerator = new BiasedGenerator<>(biasTowardsFirst, firstGenerator, secondGenerator);
        int trueCount = 0;

        //When
        for (int i = 0; i < 10000; i++) {
            if (biasedGenerator.next()) {
                trueCount++;
            }
        }

        //Then
        assertThat(trueCount, is(0));
    }

    @Test
    public void shouldFailWhenBiasIsLessThanZero() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("bias must be between 0 and 100");

        new BiasedGenerator<>(-1, () -> 13, () -> 13);
    }

    @Test
    public void shouldFailWhenBiasIsGreaterThanHundred() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("bias must be between 0 and 100");

        new BiasedGenerator<>(101, () -> 13, () -> 13);
    }

    @Test
    public void shouldFailWhenFirstGeneratorIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("First generator must not be null");

        new BiasedGenerator<>(20, null, () -> 1);
    }

    @Test
    public void shouldFailWhenSecondGeneratorIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Second generator must not be null");

        new BiasedGenerator<>(20, () -> 1, null);
    }
}
