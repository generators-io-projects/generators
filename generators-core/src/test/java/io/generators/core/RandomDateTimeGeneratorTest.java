package io.generators.core;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.junit.Test;

import java.util.NavigableSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newTreeSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.joda.time.DateTime.now;

public class RandomDateTimeGeneratorTest {

    @Test
    public void shouldGenerateRandomDateTime() {
        //Given
        Generator<DateTime> dateTimeGenerator = new RandomDateTimeGenerator();
        Set<DateTime> dateTimeSet = newHashSet();

        //When & Then
        for (int i = 0; i < 100; i++) {
            dateTimeSet.add(dateTimeGenerator.next());
        }

        assertThat(dateTimeSet, hasSize(100));
    }

    @Test
    public void shouldGenerateRandomDateTimeInTheSpecifiedRange() {
        //Given
        DateTime now = now();
        DateTime from = now.minusYears(100);
        DateTime to = now.plusYears(200);
        Generator<DateTime> dateTimeGenerator = new RandomDateTimeGenerator(from, to);
        NavigableSet<DateTime> dateTimeSet = newTreeSet();

        //When
        for (int i = 0; i < 1000; i++) {
            DateTime next = dateTimeGenerator.next();
            dateTimeSet.add(next);
        }

        //Then
        assertThat(dateTimeSet, hasSize(1000));
        assertThat(dateTimeSet.first(), greaterThanOrEqualTo((ReadableInstant) from));
        assertThat(dateTimeSet.last(), lessThanOrEqualTo((ReadableInstant) to));
    }

    @Test
    public void shouldGenerateRandomDateTimeInTheSpecifiedVerySmallRange() {
        //Given
        DateTime now = now();
        DateTime from = now;
        DateTime to = now.plus(1);
        Generator<DateTime> dateTimeGenerator = new RandomDateTimeGenerator(from, to);
        NavigableSet<DateTime> dateTimeSet = newTreeSet();

        //When
        UniqueGenerator<DateTime> dateTimeUniqueGenerator = new UniqueGenerator<>(dateTimeGenerator);
        dateTimeSet.add(dateTimeUniqueGenerator.next());
        dateTimeSet.add(dateTimeUniqueGenerator.next());

        //Then
        assertThat(dateTimeSet, hasItems(from, to));
    }

    @Test
    public void shouldGenerateNowDateTime() {
        //Given
        DateTime now = now();
        Generator<DateTime> dateTimeGenerator = new RandomDateTimeGenerator(now, now);

        //When & Then
        assertThat(dateTimeGenerator.next(), is(now));
    }
}
