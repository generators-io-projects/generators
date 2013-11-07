package io.generators.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static io.generators.core.Generators.ofInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TypeGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldFailWhenTypeClassIsNull() {
        //Given
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("typeClass");

        //When
        Class<WholeAmount> typeClass = null;
        new TypeGenerator<>(typeClass, ofInstance(13));
    }

    @Test
    public void shouldFailWhenValueGeneratorIsNull() {
        //Given
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("valueGenerator");

        //When
        new TypeGenerator<>(WholeAmount.class, null);
    }

    @Test
    public void shouldGenerateTypeThatHasOneArgConstructor() {
        //Given
        Generator<WholeAmount> wholeAmountGenerator = new TypeGenerator<>(WholeAmount.class, Generators.positiveInts(12, 13));

        //When
        WholeAmount wholeAmount = wholeAmountGenerator.next();

        //Then
        assertThat(wholeAmount, is(new WholeAmount(12)));
    }

    @Test
    public void shouldGenerateTypeThatHasOneArgConstructorAndValueGeneratorIsGeneric() {
        //Given
        TypeGenerator<WholeAmount, Integer> wholeAmountGenerator = new TypeGenerator<>(WholeAmount.class, ofInstance(13));

        //When
        WholeAmount wholeAmount = wholeAmountGenerator.next();

        //Then
        WholeAmount expectedAmount = new WholeAmount(13);
        assertThat(wholeAmount, is(expectedAmount));
    }

    @Test
    public void shouldGenerateTypeThatHasOneArgConstructorAndValueGeneratorAnotherTypeGenerator() {
        //Given
        TypeGenerator<WholeAmount, Integer> wholeAmountGenerator = new TypeGenerator<>(WholeAmount.class, ofInstance(13));
        TypeGenerator<Salary, WholeAmount> salaryGenerator = new TypeGenerator<>(Salary.class, wholeAmountGenerator);

        //When
        Salary salary = salaryGenerator.next();

        //Then
        Salary expectedSalary = new Salary(new WholeAmount(13));
        assertThat(salary, is(expectedSalary));
    }

    public static class WholeAmount {
        private final Integer value;

        public WholeAmount(Integer value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "" + value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WholeAmount that = (WholeAmount) o;

            return value.equals(that.value);

        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }

    public static class Salary {
        private final WholeAmount wholeAmount;

        public Salary(WholeAmount wholeAmount) {
            this.wholeAmount = wholeAmount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Salary salary = (Salary) o;

            if (!wholeAmount.equals(salary.wholeAmount)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return wholeAmount.hashCode();
        }
    }
}
