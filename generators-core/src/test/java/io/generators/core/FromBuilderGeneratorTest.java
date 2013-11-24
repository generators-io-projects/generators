package io.generators.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FromBuilderGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private Car car1;
    @Mock
    private Car car2;
    @Mock
    private Car car3;
    @Mock
    private Builder<Car> carBuilder;

    @Test
    public void shouldGenerateTypeUsingBuilder() {
        //Given
        given(carBuilder.build()).willReturn(car1, car2, car3);
        Generator<Car> fromBuilderGenerator = new FromBuilderGenerator<>(carBuilder);

        assertThat(fromBuilderGenerator.next(), sameInstance(car1));
        assertThat(fromBuilderGenerator.next(), sameInstance(car2));
        assertThat(fromBuilderGenerator.next(), sameInstance(car3));
    }

    @Test
    public void shouldThrowExceptionWhenBuilderIsNull() {
        //Given
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Builder can't be null");
        //When
        new FromBuilderGenerator<>(null);
    }

    private interface Car {
    }
}
