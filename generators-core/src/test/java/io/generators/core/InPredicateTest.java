package io.generators.core;

import com.google.common.base.Predicate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InPredicateTest {

    @Test
    public void shouldReturnTrueForSpecifiedObjects() {
        //Given
        String first = "1";
        String[] rest = {"a", "X"};
        Predicate<String> inPredicate = new InPredicate<>(first, rest);

        //When & Then
        assertThat(inPredicate.apply(first), is(true));
        assertThat(inPredicate.apply(rest[0]), is(true));
        assertThat(inPredicate.apply(rest[1]), is(true));
        assertThat(inPredicate.apply("H"), is(false));
    }
}
