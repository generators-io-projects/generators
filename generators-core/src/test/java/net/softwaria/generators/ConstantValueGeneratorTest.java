package net.softwaria.generators;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ConstantValueGeneratorTest {
    public static final String A = "A";
    public static final int INT = 15;
    public static final Object NULL = null;

    @Test
    public void shouldGenerateConstantValue() {
        assertThat(new ConstantValueGenerator<String>(A).next(), is(A));
        assertThat(new ConstantValueGenerator<Integer>(INT).next(), is(INT));
        assertThat(new ConstantValueGenerator<Object>(NULL).next(), nullValue());

        Object someObject = new Object();
        assertThat(new ConstantValueGenerator<Object>(someObject).next(), sameInstance(someObject));
    }
}
