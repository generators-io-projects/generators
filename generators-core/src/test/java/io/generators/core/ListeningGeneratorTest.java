package io.generators.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class ListeningGeneratorTest {

    @Test
    public void shouldGenerateConsumedValues() {
        //Given
        ListeningGenerator<Integer> listeningGenerator = new ListeningGenerator<>();
        Generator<Integer> original = new SequentialIntegerGenerator().peek(listeningGenerator);

        //When & Then
        assertThat(listeningGenerator.next(), nullValue());

        Integer firstInteger = original.next();
        assertThat(listeningGenerator.next(), is(firstInteger));

        Integer secondInteger = original.next();
        assertThat(listeningGenerator.next(), is(secondInteger));

        Integer thirdInteger = original.next();
        assertThat(listeningGenerator.next(), is(thirdInteger));
    }
}
