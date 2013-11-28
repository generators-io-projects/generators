package io.generators.core;

import com.google.common.collect.Range;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RandomClosedRangeGeneratorTest {

    public static final long TEN = 10L;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private Generator<Long> generator;
    private Long lower;
    private Long upper;
    private Generator<Range<Long>> randomRangeGenerator;

    @Before
    public void setUp() throws Exception {
        lower = 25L;
        upper = 500L;
        randomRangeGenerator = new RandomClosedRangeGenerator<>(generator);
    }

    @Test
    public void shouldGenerateClosedRangeOfLongsWhenFirstGeneratedIsHigher() {
        //Given
        given(generator.next()).willReturn(upper, lower);

        //When
        Range<Long> integerRange = randomRangeGenerator.next();

        //Then
        assertThat(integerRange, is(Range.closed(lower, upper)));
    }

    @Test
    public void shouldGenerateClosedRangeOfLongsWhenFirstGeneratedIsLower() {
        //Given
        given(generator.next()).willReturn(lower, upper);

        //When
        Range<Long> integerRange = randomRangeGenerator.next();

        //Then
        assertThat(integerRange, is(Range.closed(lower, upper)));
    }

    @Test
    public void shouldGenerateClosedRangeOfLongsWhenFirstIsSameAsSecond() {
        //Given
        given(generator.next()).willReturn(TEN, TEN);

        //When
        Range<Long> integerRange = randomRangeGenerator.next();

        //Then
        assertThat(integerRange, is(Range.singleton(TEN)));
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenDelegateGeneratorIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Delegate generator can't be null");

        new RandomClosedRangeGenerator<>((Generator<Integer>) null);
    }
}
