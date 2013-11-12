package io.generators.core;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.singleton;
import static java.util.Locale.getISOCountries;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.nullValue;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RandomFromCollectionGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldPickRandomItemFromCollection() {
        //Given
        Set<String> countries = newHashSet(getISOCountries());
        Generator<String> countriesGenerator = new RandomFromCollectionGenerator<>(countries);

        //When
        for (int i = 0; i < 1000; i++) {
            countries.remove(countriesGenerator.next());
        }

        //Then
        assertThat(countries, hasSize(lessThan(30)));
    }

    @Test
    public void shouldPickRandomItemFromArray() {
        //Given
        String[] countries = getISOCountries();
        Set<String> countriesSet = newHashSet(getISOCountries());
        Generator<String> countriesGenerator = new RandomFromCollectionGenerator<>(countries);

        //When
        for (int i = 0; i < 1000; i++) {
            countriesSet.remove(countriesGenerator.next());
        }

        //Then
        assertThat(countriesSet, hasSize(lessThan(30)));
    }

    @Test
    public void shouldFailIfCollectionPassedInIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Collection for generation can't be null");

        Collection<String> collection = null;
        new RandomFromCollectionGenerator<>(collection);
    }

    @Test
    public void shouldFailIfArraysPassedInIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Collection for generation can't be null");//array is "collection" as well

        String[] array = null;
        new RandomFromCollectionGenerator<>(array);
    }

    @Test
    public void shouldNotFailGenerationIfCollectionIsEmpty() {
        //Given
        Generator<String> someGenerator = new RandomFromCollectionGenerator<>(Collections.<String>emptyList());

        for (int i = 0; i < 3; i++) {
            //When
            String nullValue = someGenerator.next();
            //Then
            assertThat(nullValue, nullValue());
        }
    }

    @Test
    public void shouldAlwaysReturnSameElementForSingletonCollection() {
        //Given
        String someString = "Hi";
        Generator<String> someGenerator = new RandomFromCollectionGenerator<>(singleton(someString));

        for (int i = 0; i < 3; i++) {
            //When
            String generatedString = someGenerator.next();
            //Then
            assertThat(generatedString, is(someString));
        }
    }
}

