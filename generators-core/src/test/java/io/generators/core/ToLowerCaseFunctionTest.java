package io.generators.core;

import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class ToLowerCaseFunctionTest {

    private static final String STRING_IN_LOWER_CASE = "abcd";
    public static final String STRING_IN_UPPER_CASE = "AbCD";

    @Test
    public void shouldConvertToUpperCase() {
        ToLowerCaseFunction toLowerCaseFunction = new ToLowerCaseFunction(Locale.ENGLISH);

        String upperCased = toLowerCaseFunction.apply(STRING_IN_UPPER_CASE);

        assertThat(upperCased, is(STRING_IN_LOWER_CASE));
    }

    @Test
    public void shouldConvertToUpperCaseWhenNoLocaleIsProvided() {
        ToLowerCaseFunction toLowerCaseFunction = new ToLowerCaseFunction();

        String upperCased = toLowerCaseFunction.apply(STRING_IN_UPPER_CASE);

        assertThat(upperCased, is(STRING_IN_LOWER_CASE));
    }

    @Test
    public void shouldReturnNullIfInputIsNull() {
        ToLowerCaseFunction toLowerCaseFunction = new ToLowerCaseFunction(Locale.ENGLISH);

        String upperCased = toLowerCaseFunction.apply(null);

        assertThat(upperCased, nullValue());
    }
}
