package io.generators.core;

import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UpperCaseGeneratorTest {

    public static final String LOWERCASE = "abc";
    private UpperCaseGenerator generator;

    @Test
    public void shouldConvertGeneratedValuesToUpperCase() throws Exception {
        // Given
        generator = new UpperCaseGenerator(new GeneratorOfInstance<>(LOWERCASE), Locale.ENGLISH);

        // When
        String upper = generator.next();

        // Then
        assertThat(upper, is("ABC"));
    }

    @Test
    public void shouldUseDefaultLocaleIfNotProvided() throws Exception {
        // Given
        generator = new UpperCaseGenerator(new GeneratorOfInstance<>(LOWERCASE));

        // When
        String upper = generator.next();

        // Then
        assertThat(upper, is(LOWERCASE.toUpperCase()));
    }
}
