package io.generators.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static io.generators.core.Generators.positiveInts;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BroadcastingGeneratorTest {

    public static final Generator<Integer> NULL_DELEGATE = null;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private Consumer<Integer> firstConsumer;
    @Mock
    private Consumer<Integer> secondConsumer;


    @Test
    public void shouldPublishGeneratedValuesToConsumers() {
        //Given
        Generator<Integer> generator = new BroadcastingGenerator<>(Generators.positiveInts, firstConsumer, secondConsumer);

        //When
        Integer integer = generator.next();

        //Then
        verify(firstConsumer).consume(integer);
        verify(secondConsumer).consume(integer);

        //When
        Integer integer2 = generator.next();

        //Then
        verify(firstConsumer).consume(integer2);
        verify(secondConsumer).consume(integer2);
    }

    @Test
    public void shouldPublishGeneratedValuesToConsumersList() {
        //Given
        Generator<Integer> generator = new BroadcastingGenerator<>(Generators.positiveInts, asList(firstConsumer, secondConsumer));

        //When
        Integer integer = generator.next();

        //Then
        verify(firstConsumer).consume(integer);
        verify(secondConsumer).consume(integer);

        //When
        Integer integer2 = generator.next();

        //Then
        verify(firstConsumer).consume(integer2);
        verify(secondConsumer).consume(integer2);
    }

    @Test
    public void shouldFailCreationWhenDelegateGeneratorIsNull() {
        //Given
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("delegate can't be null");

        //When
        new BroadcastingGenerator<>(NULL_DELEGATE, firstConsumer);
    }

    @Test
    public void shouldFailCreationWhenDelegateGeneratorIsNullSecondConstructor() {
        //Given
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("delegate can't be null");

        //When
        new BroadcastingGenerator<>(NULL_DELEGATE, asList(firstConsumer));
    }

}
