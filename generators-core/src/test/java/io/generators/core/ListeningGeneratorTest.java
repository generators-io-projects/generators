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
        BroadcastingGenerator<Integer> broadcastingGenerator = new BroadcastingGenerator<>(new SequentialIntegerGenerator(), listeningGenerator);

        //When & Then
        assertThat(listeningGenerator.next(), nullValue());

        Integer firstInteger = broadcastingGenerator.next();
        assertThat(listeningGenerator.next(), is(firstInteger));

        Integer secondInteger = broadcastingGenerator.next();
        assertThat(listeningGenerator.next(), is(secondInteger));

        Integer thirdInteger = broadcastingGenerator.next();
        assertThat(listeningGenerator.next(), is(thirdInteger));
    }
}
