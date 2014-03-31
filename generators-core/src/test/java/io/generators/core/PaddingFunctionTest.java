package io.generators.core;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PaddingFunctionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private PaddingFunction leftPaddingFunction;
    private PaddingFunction rightPaddingFunction;

    @Before
    public void setUp() throws Exception {
        leftPaddingFunction = PaddingFunction.padStart(5, '0');
        rightPaddingFunction = PaddingFunction.padEnd(4, '0');
    }

    @Test
    public void shouldPadStartOfShorterStringWiZeros() {
        String padded = leftPaddingFunction.apply(123);
        assertThat(padded, is("00123"));
    }

    @Test
    public void shouldNotPadStringThatAlreadyHasRequiredLength() {
        String padded = leftPaddingFunction.apply(12345);
        assertThat(padded, is("12345"));
    }

    @Test
    public void shouldNotPadStringThatIsLongerThanRequiredMinimum() {
        String padded = leftPaddingFunction.apply(1234567);
        assertThat(padded, is("1234567"));
    }

    @Test
    public void shouldPadEndOfShorterStringWiZeros() {
        String padded = rightPaddingFunction.apply("AB");
        assertThat(padded, is("AB00"));
    }

    @Test
    public void shouldNotRightPadStringThatAlreadyHasRequiredLength() {
        String padded = rightPaddingFunction.apply("ABCD");
        assertThat(padded, is("ABCD"));
    }

    @Test
    public void shouldNotRightPadStringThatIsLongerThanRequiredMinimum() {
        String padded = rightPaddingFunction.apply("ABCDEFGH");
        assertThat(padded, is("ABCDEFGH"));
    }

    @Test
    public void shouldFailWhenNullIsPassedIn() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Can't pad null");
        rightPaddingFunction.apply(null);
    }

}
