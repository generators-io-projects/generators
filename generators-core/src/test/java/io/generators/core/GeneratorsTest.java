package io.generators.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSetWithExpectedSize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneratorsTest {
    public static final String UPPERCASE = "ABC";
    public static final String LOWERCASE = "abc";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

    @Test
    public void shouldReturnListPopulatedWithGenerator() {
        List<Integer> integers = Generators.listFrom(5, () -> 3);
        assertThat(integers, contains(3, 3, 3, 3, 3));
    }

    @Test
    public void shouldReturnGeneratorOfType() {
        Generator<BigDecimal> bigDecimalGenerator = Generators.ofType(BigDecimal.class, () -> "10");
        assertThat(bigDecimalGenerator.next(), comparesEqualTo(BigDecimal.TEN));
    }

    @Test
    public void shouldReturnRandomEnumGenerator() {
        Generator<TestEnum> testEnumGenerator = Generators.randomEnum(TestEnum.class);
        assertThat(testEnumGenerator.next(), isA(TestEnum.class));
    }

    @Test
    public void shouldReturnBiasedGenerator() {
        Generator<Integer> biasedGenerator = Generators.biased(50, () -> 5, () -> 6);
        Set<Integer> ints = newHashSetWithExpectedSize(2);
        for (int i = 0; i < 100; i++) {
            ints.add(biasedGenerator.next());
        }

        assertThat(ints, containsInAnyOrder(5, 6));
    }

    @Test
    public void shouldGenerateNumberNDigitsLong() {
        //Given
        Generator<Integer> integerGenerator = Generators.nDigitPositiveInteger(3);

        for (int i = 0; i < 20; i++) {
            //When
            Integer integerWith5Digits = integerGenerator.next();
            //Then
            assertThat(integerWith5Digits, allOf(greaterThan(99), lessThan(1000)));
        }
    }

    @Test
    public void shouldNotAllowZeroForNDigitsPositiveIntegerGenerator() {
        //Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Number of digits must be between 1  and 10");
        Generators.nDigitPositiveInteger(0);
    }

    @Test
    public void shouldNot11ForNDigitsPositiveIntegerGenerator() {
        //Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Number of digits must be between 1  and 10");
        Generators.nDigitPositiveInteger(11);//Integer.MAX_VALUE is 10 digit long
    }

    @Test
    public void shouldConvertGeneratedValuesToUpperCase() throws Exception {
        // Given
        Generator<String> generator = Generators.upperCase(() -> LOWERCASE, Locale.ENGLISH);

        // When
        String upper = generator.next();

        // Then
        assertThat(upper, is(LOWERCASE.toUpperCase()));
    }

    @Test
    public void shouldUseDefaultLocaleIfNotProvided() throws Exception {
        // Given
        Generator<String> generator = Generators.upperCase(() -> LOWERCASE);

        // When
        String upper = generator.next();

        // Then
        assertThat(upper, is(LOWERCASE.toUpperCase()));
    }


    @Test
    public void shouldConvertGeneratedValuesToLowerCase() throws Exception {
        // Given
        Generator<String> generator = Generators.lowerCase(() -> UPPERCASE, Locale.ENGLISH);

        // When
        String lower = generator.next();

        // Then
        assertThat(lower, is("abc"));
    }

    @Test
    public void shouldConvertToLowerCaseAndUseDefaultLocaleIfNotProvided() throws Exception {
        // Given
        Generator<String> generator = Generators.lowerCase(() -> UPPERCASE);

        // When
        String lower = generator.next();

        // Then
        assertThat(lower, is(UPPERCASE.toLowerCase()));
    }

    enum TestEnum {
        A,
        B
    }
}
