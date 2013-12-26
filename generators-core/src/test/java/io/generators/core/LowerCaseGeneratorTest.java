package io.generators.core;

import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LowerCaseGeneratorTest {

    public static final String UPPERCASE = "ABC";

    private LowerCaseGenerator generator;

    @Test
    public void shouldConvertGeneratedValuesToLowerCase() throws Exception {
        // Given
        generator = new LowerCaseGenerator(new GeneratorOfInstance<>(UPPERCASE), Locale.ENGLISH);

        // When
        String lower = generator.next();

        // Then
        assertThat(lower, is("abc"));
    }

    @Test
    public void shouldUseDefaultLocaleIfNotProvided() throws Exception {
        // Given
        generator = new LowerCaseGenerator(new GeneratorOfInstance<>(UPPERCASE));

        // When
        String lower = generator.next();

        // Then
        assertThat(lower, is(UPPERCASE.toLowerCase()));
    }
}
