package io.generators.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.EnumSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class RandomEnumGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldGenerateEnumRandomly() {
        //Given
        Generator<SomeEnum> randomEnumGenerator = new RandomEnumGenerator<>(SomeEnum.class);

        EnumSet<SomeEnum> enumSet = EnumSet.noneOf(SomeEnum.class);

        //When
        for (int i = 0; i < 20; i++) {
            enumSet.add(randomEnumGenerator.next());
        }

        //Then
        assertThat(enumSet, contains(SomeEnum.FIRST, SomeEnum.SECOND, SomeEnum.THIRD));
    }

    @Test
    public void shouldFailWhenEnumClassIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("enumClass must not be null");

        new RandomEnumGenerator<SomeEnum>(null);
    }


    private enum SomeEnum {
        FIRST,
        SECOND,
        THIRD
    }
}
