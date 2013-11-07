package io.generators.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneratorOfInstanceTest {
    private static final String A = "A";
    private static final int INT = 15;
    private static final Object NULL = null;

    @Test
    public void shouldGenerateConstantValue() {
        assertThat(new GeneratorOfInstance<>(A).next(), is(A));
        assertThat(new GeneratorOfInstance<>(INT).next(), is(INT));
        assertThat(new GeneratorOfInstance<>(NULL).next(), nullValue());

        Object someObject = new Object();
        assertThat(new GeneratorOfInstance<>(someObject).next(), sameInstance(someObject));
    }
}
