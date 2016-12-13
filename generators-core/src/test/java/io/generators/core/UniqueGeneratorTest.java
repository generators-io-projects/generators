package io.generators.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UniqueGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private Generator<String> stringGenerator;

    @Test
    public void shouldGenerateOnlyUniqueValues() {
        //Given
        Generator<Integer> uniqueGenerator = new UniqueGenerator<>(Generators.positiveInts(1, 6), 100);

        //When
        List<Integer> generatedIntegers = newArrayList();

        for (int i = 1; i <= 5; i++) {
            generatedIntegers.add(uniqueGenerator.next());
        }

        //Then
        assertThat(generatedIntegers, hasSize(5));
        assertThat(newHashSet(generatedIntegers), hasSize(5));
    }

    @Test
    public void shouldFailAfter10UnsuccessfulAttemptsToGenerateUniqueValue() {
        //Given
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Exhausted 10 retries trying to generate unique value");
        Generator<String> uniqueGenerator = new UniqueGenerator<>(Generators.ofInstance("One"));

        try {
            uniqueGenerator.next();
        } catch (IllegalStateException e) {
            fail("Exception thrown earlier than expected");
        }

        //When
        uniqueGenerator.next();
        verify(stringGenerator, times(12)).next();

    }

    @Test
    public void shouldFailAfterNCustomRetries() {
        //Given
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Exhausted 2 retries trying to generate unique value");
        Generator<String> uniqueGenerator = new UniqueGenerator<>(Generators.ofInstance("One"), 2);
        try {
            uniqueGenerator.next();
        } catch (IllegalStateException e) {
            fail("Exception thrown earlier than expected");
        }

        //When
        uniqueGenerator.next();
        verify(stringGenerator, times(4)).next();
    }

    @Test
    public void shouldFailWhenDelegateGeneratorIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Delegate generator can't be null");

        new UniqueGenerator<>(null);
    }

    @Test
    public void shouldFailWhenNumberOfRetriesIsLessThan1() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Number of retries must be at least 1");

        new UniqueGenerator<>(Generators.positiveInts, 0);
    }
}
