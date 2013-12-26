package io.generators.core;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class CyclicGeneratorTest {
    private CyclicGenerator<Integer> generator;

    @Before
    public void setUp() throws Exception {
        generator = new CyclicGenerator<>(3, 4, 5);
    }

    @Test
    public void shouldCycle() throws Exception {
        // Given
        List<Integer> generatedValues = newArrayList();

        // When
        for (int i = 0; i < 10; i++) {
            generatedValues.add(generator.next());
        }

        // Then
        assertThat(generatedValues, contains(3, 4, 5, 3, 4, 5, 3, 4, 5, 3));
    }
}
