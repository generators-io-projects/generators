package io.generators.core;

import org.junit.Test;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static io.generators.core.Generators.positiveInts;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FluentGeneratorTest {

    @Test
    public void shouldCreateGenerator() {
        //Given
        Generator<Integer> generator = FluentGenerator.from(positiveInts);

        //When
        Integer integer = generator.next();

        //Then
        assertThat(integer, notNullValue());
    }

    @Test
    public void shouldCreateFluentUniqueGenerator() {
        //Given
        Generator<Integer> generator = FluentGenerator.from(positiveInts(1, 3)).unique();

        //When
        List<Integer> integers = asList(generator.next(), generator.next());

        //Then
        assertThat(integers, containsInAnyOrder(1, 2));
    }

    @Test
    public void shouldCreateFluentUniqueOfTypeGenerator() {
        //Given
        FluentGenerator<WholeAmount> generator = FluentGenerator.from(positiveInts)
                .unique()
                .ofType(WholeAmount.class);

        //When
        WholeAmount wholeAmount = generator.next();

        //Then
        assertThat(wholeAmount, notNullValue());
    }

    @Test
    public void shouldCreateFluentUniqueOfTypeIterableGenerator() {
        //Given
        Iterable<WholeAmount> amounts = FluentGenerator.from(positiveInts)
                .unique()
                .ofType(WholeAmount.class)
                .toIterable(5);

        Set<WholeAmount> amountsSet = newHashSet();

        //When
        for (WholeAmount amount : amounts) {
            amountsSet.add(amount);
        }
        assertThat(amountsSet, hasSize(5));
    }
}
