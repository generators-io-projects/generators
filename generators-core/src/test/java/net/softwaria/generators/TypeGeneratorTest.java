package net.softwaria.generators;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class TypeGeneratorTest {

    private static class WholeAmount {
        private final Integer value;

        public WholeAmount(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    @Test
    public void shouldGenerateTinyTypeThatHasOneArgConstructor() {
        //Given
        Generator<WholeAmount> tinyTypeGenerator = new TypeGenerator<>(WholeAmount.class, Generators.positiveInts);

        //When
        WholeAmount wholeAmount = tinyTypeGenerator.next();

        //Then
        assertThat(wholeAmount, notNullValue());
        assertThat(wholeAmount.getValue(), Matchers.any(Integer.class));
    }
}
