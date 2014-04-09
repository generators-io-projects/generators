package io.generators.core;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.annotation.Nullable;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
        Generator<String> stringGenerator = Generators.ofInstance("someString");
        Generator<String> otherStringGenerator = Generators.ofInstance("xyz");
        Function<List<String>, String> aggregationFunction = new Function<List<String>, String>() {
            @Nullable
            @Override
            public String apply(@Nullable List<String> input) {
                return Joiner.on(",").join(input);
            }
        };

        Generator<String> compositeGenerator = new CompositeGenerator<>(aggregationFunction, asList(stringGenerator, otherStringGenerator));

        String generatedString = compositeGenerator.next();

        assertThat(generatedString, is("someString,xyz"));
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


}
