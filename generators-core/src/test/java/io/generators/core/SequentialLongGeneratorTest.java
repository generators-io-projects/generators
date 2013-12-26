package io.generators.core;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class SequentialLongGeneratorTest {

    private SequentialLongGenerator generator;

    @Test
    public void shouldGenerateLongsInSequence() throws Exception {
        // Given
        generator = new SequentialLongGenerator();
        List<Long> generatedValues = newArrayList();

        // When
        for (int i = 0; i < 10; i++) {
            generatedValues.add(generator.next());
        }

        // Then
        assertThat(generatedValues, contains(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L));
    }

    @Test
    public void shouldStartAtOffset() throws Exception {
        // Given
        generator = new SequentialLongGenerator(2L);

        // When
        Long generatedValue = generator.next();

        // Then
        assertThat(generatedValue, is(2L));
    }
}
