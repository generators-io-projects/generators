package io.generators.core.tuples;

import com.google.common.collect.Lists;
import io.generators.core.Generators;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static io.generators.core.tuples.Tuple.tuple;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class TupleTest {
    Tuple t2 = tuple(1, 2);
    Tuple t3 = tuple(1, 2, 3);
    Tuple t4 = tuple(1, 2, 3, "a");
    Tuple t5 = tuple(1, 2, 3, "a", "b");

    @Test
    public void shouldEqual() {
        assertThat(t2, is(tuple(1, 2)));
        assertThat(t3, is(tuple(1, 2, 3)));
        assertThat(t4, is(tuple(1, 2, 3, "a")));
        assertThat(t5, is(tuple(1, 2, 3, "a", "b")));

        assertThat(t2, not(tuple(1, 1)));
    }

    @Test
    public void shouldReturnSameHash() {
        assertThat(t2.hashCode(), is(tuple(1, 2).hashCode()));
        assertThat(t3.hashCode(), is(tuple(1, 2, 3).hashCode()));
        assertThat(t4.hashCode(), is(tuple(1, 2, 3, "a").hashCode()));
        assertThat(t5.hashCode(), is(tuple(1, 2, 3, "a", "b").hashCode()));

        assertThat(t2.hashCode(), not(tuple(1, 1).hashCode()));
    }

    @Test
    public void shouldReturnStringIncludingAllElements() {
        assertThat(t2.toString(), is("Tuple[1, 2]"));
        assertThat(t3.toString(), is("Tuple[1, 2, 3]"));
        assertThat(t4.toString(), is("Tuple[1, 2, 3, a]"));
        assertThat(t5.toString(), is("Tuple[1, 2, 3, a, b]"));
    }

    @Test
    public void shouldReturnIterableIncludingAllElements() {
        assertThat(t2.iterable(), is(newArrayList(1, 2)));
        assertThat(t3.iterable(), is(newArrayList(1, 2, 3)));
        assertThat(t4.iterable(), is(newArrayList(1, 2, 3, "a")));
        assertThat(t5.iterable(), is(newArrayList(1, 2, 3, "a", "b")));
    }
}
