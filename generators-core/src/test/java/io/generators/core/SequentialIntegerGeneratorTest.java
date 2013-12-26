package io.generators.core;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class SequentialIntegerGeneratorTest {

    private SequentialIntegerGenerator generator;

    @Test
    public void shouldGenerateIntegersInSequence() throws Exception {
        // Given
        generator = new SequentialIntegerGenerator();
        List<Integer> generatedValues = newArrayList();

        // When
        for (int i = 0; i < 10; i++) {
            generatedValues.add(generator.next());
        }

        // Then
        assertThat(generatedValues, contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Test
    public void shouldStartAtOffset() throws Exception {
        // Given
        generator = new SequentialIntegerGenerator(2);

        // When
        Integer generatedValue = generator.next();

        // Then
        assertThat(generatedValue, is(2));
    }
}
