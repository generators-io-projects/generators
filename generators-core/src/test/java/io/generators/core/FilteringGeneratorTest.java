package io.generators.core;

import com.google.common.base.Predicate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isOneOf;
import static org.mockito.Mockito.mock;

public class FilteringGeneratorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldFilterGeneratedValueUsingSuppliedPredicate() {
        //Given
        Generator<Integer> oddNumbers = new FilteringGenerator<>(new RandomFromCollectionGenerator<>(1, 2, 3, 4, 5), new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 != 0;//only odd numbers
            }
        });

        //When & Then
        for (int i = 0; i < 10; i++) {
            assertThat(oddNumbers.next(), isOneOf(1, 3, 5));
        }
    }

    @Test
    public void shouldFailWhenDelegateGeneratorIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Delegate generator can't be null");

        new FilteringGenerator<Object>(null, mock(Predicate.class));
    }

    @Test
    public void shouldFailWhenFunctionIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Predicate can't be null");

        new FilteringGenerator<>(Generators.positiveInts, null);
    }

}
