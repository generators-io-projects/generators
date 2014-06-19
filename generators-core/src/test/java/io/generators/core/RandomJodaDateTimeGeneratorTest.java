package io.generators.core;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newTreeSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.joda.time.DateTime.now;

import java.util.NavigableSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableInstant;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RandomJodaDateTimeGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldGenerateRandomDateTime() {
        //Given
        Generator<DateTime> dateTimeGenerator = new RandomJodaDateTimeGenerator();
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
        Generator<DateTime> dateTimeGenerator = new RandomJodaDateTimeGenerator(from, to);
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
        DateTime from = now();
        DateTime to = from.plus(1);
        Generator<DateTime> dateTimeGenerator = new RandomJodaDateTimeGenerator(from, to);
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
        Generator<DateTime> dateTimeGenerator = new RandomJodaDateTimeGenerator(now, now);

        //When & Then
        assertThat(dateTimeGenerator.next(), is(now));
    }

    @Test
    public void shouldGenerateDatesInFuture() {
        //Given
        Generator<DateTime> dateTimeGenerator = RandomJodaDateTimeGenerator.futureDates();

        //When & Then
        for (int i = 0; i < 100; i++) {
            ReadableInstant now = now();
            assertThat(dateTimeGenerator.next(), greaterThanOrEqualTo(now));
        }
    }

    @Test
    public void shouldGenerateDatesInFutureUpToSpecifiedDate() {
        //Given
        DateTime upperBound = now().plusSeconds(20);
        Generator<DateTime> dateTimeGenerator = RandomJodaDateTimeGenerator.futureDates(upperBound);

        //When & Then
        for (int i = 0; i < 100; i++) {
            ReadableInstant now = now();
            DateTime generated = dateTimeGenerator.next();
            assertThat(generated, greaterThanOrEqualTo(now));
            assertThat(generated, lessThanOrEqualTo((ReadableInstant) upperBound));
        }
    }

    @Test
    public void shouldGenerateDatesInPast() {
        //Given
        Generator<DateTime> dateTimeGenerator = RandomJodaDateTimeGenerator.pastDates();

        //When & Then
        for (int i = 0; i < 100; i++) {
            ReadableInstant now = now();
            assertThat(dateTimeGenerator.next(), lessThanOrEqualTo(now));
        }
    }

    @Test
    public void shouldGenerateDatesInPastFromSpecifiedDate() {
        //Given
        DateTime lowerBound = now().minusSeconds(20);
        Generator<DateTime> dateTimeGenerator = RandomJodaDateTimeGenerator.pastDates(lowerBound);

        //When & Then
        for (int i = 0; i < 100; i++) {
            DateTime generated = dateTimeGenerator.next();
            assertThat(generated, lessThanOrEqualTo((ReadableInstant) now()));
            assertThat(generated, greaterThanOrEqualTo((ReadableInstant) lowerBound));
        }
    }

    @Test
    public void shouldFailIfTheDateFromIsGreaterThanDateTo() {
        //Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Date 'from' can't be after date 'to'");

        DateTime from = now();
        DateTime to = from.minusDays(1);
        //When & Then
        new RandomJodaDateTimeGenerator(from, to);
    }

    @Test
    public void shouldGenerateDateInTheSameTimeZoneAsFromDate() {
        //Given
        DateTimeZone specificTimeZone = DateTimeZone.forOffsetHours(5);
        DateTime from = now(specificTimeZone);

        DateTimeZone otherTimeZone = DateTimeZone.forOffsetHours(4);
        DateTime to = now().plusDays(1).withZone(otherTimeZone);

        Generator<DateTime> randomJodaDateTimeGenerator = new RandomJodaDateTimeGenerator(from, to);

        //When
        DateTime generated = randomJodaDateTimeGenerator.next();

        //Then
        assertThat(generated.getZone(), is(specificTimeZone));
    }

    @Test
    public void shouldGenerateDateInTheSameTimeZoneAsFromDates() {
        //Given
        Generator<DateTime> from = new Generator<DateTime>() {
            public int timeZoneHours = 0;

            @Override
            public DateTime next() {
                return now().withZone(DateTimeZone.forOffsetHours(timeZoneHours++));
            }
        };

        Generator<DateTime> to = Generators.ofInstance(now().plusDays(10));

        Generator<DateTime> randomJodaDateTimeGenerator = new RandomJodaDateTimeGenerator(from, to);

        //When & Then
        assertThat(randomJodaDateTimeGenerator.next().getZone(),is(DateTimeZone.forOffsetHours(0)));
        assertThat(randomJodaDateTimeGenerator.next().getZone(),is(DateTimeZone.forOffsetHours(1)));
        assertThat(randomJodaDateTimeGenerator.next().getZone(),is(DateTimeZone.forOffsetHours(2)));
    }

    @Test
    public void shouldUseProvidedTimeZoneWhenGeneratingTheDate() {
        //Given
        DateTimeZone specificTimeZone = DateTimeZone.forOffsetHours(5);
        Generator<DateTime> randomJodaDateTimeGenerator = new RandomJodaDateTimeGenerator().withTimeZone(specificTimeZone);

        //When
        DateTime generated = randomJodaDateTimeGenerator.next();

        //Then
        assertThat(generated.getZone(), is(specificTimeZone));
    }

    @Test
    public void shouldFailIfTheDateFromIsNull() {
        //Given
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Date 'from' can't be null");

        //When & Then
        new RandomJodaDateTimeGenerator(null, now());
    }



    @Test
    public void shouldFailIfTheDateToIsNull() {
        //Given
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Date 'to' can't be null");

        //When & Then
        new RandomJodaDateTimeGenerator(now(), null);
    }


}
