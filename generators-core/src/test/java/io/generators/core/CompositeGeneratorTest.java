package io.generators.core;

import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.getLast;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CompositeGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldConcatenateGeneratedValues() {
        Generator<Object> stringGenerator = Generators.<Object>ofInstance("someString");
        Generator<Object> integerGenerator = Generators.<Object>ofInstance(78);

        Function<List<Object>, String> aggregationFunction = new Function<List<Object>, String>() {
            @Nullable
            @Override
            public String apply(@Nullable List<Object> input) {
                return Joiner.on(",").join(input);
            }
        };

        Generator<String> compositeGenerator = new CompositeGenerator<>(aggregationFunction, asList(stringGenerator, integerGenerator));

        String generatedString = compositeGenerator.next();

        assertThat(generatedString, is("someString,78"));
    }

    @Test
    public void shouldCreateRichObject() {
        String someString = "someString";
        Generator<Object> stringGenerator = Generators.<Object>ofInstance(someString);

        byte age = 25;
        Generator<Object> byteGenerator = Generators.<Object>ofInstance(age);

        Function<List<Object>, Person> aggregationFunction = new Function<List<Object>, Person>() {
            @Override
            public Person apply(List<Object> input) {
                return new Person(getFirst(input, null).toString(), (byte) getLast(input, null));
            }
        };

        Generator<Person> compositeGenerator = new CompositeGenerator<>(aggregationFunction, asList(stringGenerator, byteGenerator));

        Person generatedString = compositeGenerator.next();

        assertThat(generatedString, is(new Person(someString, age)));
    }

    @Test
    public void shouldFailWhenAggregationFunctionIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Aggregation function is mandatory");

        new CompositeGenerator<>(null, asList(Generators.alphabetic10));
    }

    @Test
    public void shouldFailWhenListOfGeneratorsIsNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("No generators provided");

        new CompositeGenerator<>(Functions.<List<Object>>identity(), null);
    }

    private static class Person {
        private final String name;
        private final byte age;

        Person(String name, byte age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Person person = (Person) o;

            if (age != person.age) {
                return false;
            }
            if (name != null ? !name.equals(person.name) : person.name != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (int) age;
            return result;
        }
    }


}
