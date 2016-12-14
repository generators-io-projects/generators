package io.generators.core;

import com.google.common.base.Predicate;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static io.generators.core.MorePredicatesTest.PredicateDataPoint.create;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Theories.class)
public class MorePredicatesTest {

    private static final Predicate<Integer> IN_PREDICATE = MorePredicates.in(6, 7, 8);
    private static final Predicate<Integer> NOT_IN_PREDICATE = MorePredicates.notIn(6, 7, 8);


    @DataPoints
    public static final PredicateDataPoint[] DATA_POINTS = new PredicateDataPoint[]{
            create(IN_PREDICATE, 5, false),
            create(IN_PREDICATE, 6, true),
            create(IN_PREDICATE, 7, true),
            create(IN_PREDICATE, 8, true),
            create(IN_PREDICATE, 9, false),
            create(NOT_IN_PREDICATE, 5, true),
            create(NOT_IN_PREDICATE, 6, false),
            create(NOT_IN_PREDICATE, 7, false),
            create(NOT_IN_PREDICATE, 8, false),
            create(NOT_IN_PREDICATE, 9, true),
    };

    @Theory
    public void shouldMatchPredicate(PredicateDataPoint input) {
        // When
        boolean result = input.apply();

        // Then
        assertThat(input.value + " is accepted by predicate", result, is(input.result));
    }

    static class PredicateDataPoint<T> {

        final Predicate<T> predicate;
        final T value;
        final boolean result;

        PredicateDataPoint(Predicate<T> predicate, T value, boolean result) {
            this.predicate = predicate;
            this.value = value;
            this.result = result;
        }

        boolean apply() {
            return predicate.apply(value);
        }

        static <T> PredicateDataPoint<T> create(Predicate<T> predicate, T value, boolean result) {
            return new PredicateDataPoint<>(predicate, value, result);
        }
    }
}
