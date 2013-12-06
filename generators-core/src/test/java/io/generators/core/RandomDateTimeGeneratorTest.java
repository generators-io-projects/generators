package io.generators.core;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

/**
 */
public class RandomDateTimeGeneratorTest {


    @Test
    public void shouldGenerateRandomDateTime() {
        //Given
        Generator<DateTime> dateTimeGenerator = new RandomDateTimeGenerator();
        Set<DateTime> dateTimeSet = newHashSet();

        //When & Then
        for (int i = 0; i < 100; i++) {
            DateTime next = dateTimeGenerator.next();
            dateTimeSet.add(next);
            System.out.println(next);
        }

        assertThat(dateTimeSet, hasSize(greaterThan(90)));
    }
}