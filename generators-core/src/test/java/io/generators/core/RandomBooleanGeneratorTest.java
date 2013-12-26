package io.generators.core;

import org.junit.Test;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class RandomBooleanGeneratorTest {

    private RandomBooleanGenerator generator = new RandomBooleanGenerator();

    @Test
    public void shouldGenerateRandomBooleans() throws Exception {
        // Given
        Set<Boolean> generatedValues = newHashSet();

        // When
        for (int i = 0; i < 100; i++) {
            generatedValues.add(generator.next());
        }

        // Then
        // In principle this test could sometimes fail if 100 consecutive identical booleans were generated.
        assertThat(generatedValues, containsInAnyOrder(true, false));
    }
}
