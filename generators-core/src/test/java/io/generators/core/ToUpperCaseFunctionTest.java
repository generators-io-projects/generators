package io.generators.core;

import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class ToUpperCaseFunctionTest {

    private static final String STRING_IN_LOWER_CASE = "aBcd";
    public static final String STRING_IN_UPPER_CASE = "ABCD";

    @Test
    public void shouldConvertToUpperCase() {
        ToUpperCaseFunction toUpperCaseFunction = new ToUpperCaseFunction(Locale.ENGLISH);


        String upperCased = toUpperCaseFunction.apply(STRING_IN_LOWER_CASE);

        assertThat(upperCased, is(STRING_IN_UPPER_CASE));
    }

    @Test
    public void shouldConvertToUpperCaseWhenNoLocaleIsProvided() {
        ToUpperCaseFunction toUpperCaseFunction = new ToUpperCaseFunction();


        String upperCased = toUpperCaseFunction.apply(STRING_IN_LOWER_CASE);

        assertThat(upperCased, is(STRING_IN_UPPER_CASE));
    }

    @Test
    public void shouldReturnNullIfInputIsNull() {
        ToUpperCaseFunction toUpperCaseFunction = new ToUpperCaseFunction(Locale.ENGLISH);

        String upperCased = toUpperCaseFunction.apply(null);

        assertThat(upperCased, nullValue());
    }
}
