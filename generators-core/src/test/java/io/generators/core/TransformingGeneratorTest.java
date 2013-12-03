package io.generators.core;

import com.google.common.base.Function;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

public class TransformingGeneratorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldTransformGeneratedValueUsingSuppliedFunction() {
        //Given
        Generator<String> intAsStringGenerator = new TransformingGenerator<>(Generators.ofInstance(5), new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return input.toString();
            }
        });

        //When & Then
        assertThat(intAsStringGenerator.next(), is("5"));
    }

    @Test
    public void shouldFailWhenDelegateGeneratorIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Delegate generator can't be null");

        new TransformingGenerator<Object, Object>(null, mock(Function.class));
    }

    @Test
    public void shouldFailWhenFunctionIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Function can't be null");

        new TransformingGenerator<>(Generators.positiveInts, null);
    }

}
