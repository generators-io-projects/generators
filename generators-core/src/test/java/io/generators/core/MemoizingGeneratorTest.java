package io.generators.core;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MemoizingGeneratorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldReturnSameObject() {
        //Given
        Generator<Integer> memoizingGenerator = new MemoizingGenerator<>(Generators.positiveInts);
        Integer first = memoizingGenerator.next();
        assertThat(first, notNullValue());

        //When
        //Then
        for (int i = 0; i < 20; i++) {
            assertThat(memoizingGenerator.next(), sameInstance(first));
        }
    }

    @Test
    public void shouldReturnForMultipleThreads() throws InterruptedException, ExecutionException {
        //Given
        final Generator<Integer> memoizingGenerator = new MemoizingGenerator<>(Generators.positiveInts);
        ExecutorService executorService = newFixedThreadPool(50);
        Collection<Callable<Integer>> generatorTasks = newArrayList();
        generatorTasks.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return memoizingGenerator.next();
            }
        });

        try {
            Set<Integer> singletonSet = newHashSet();
            for (Future<Integer> future : executorService.invokeAll(generatorTasks,10, TimeUnit.SECONDS)) {
                singletonSet.add(future.get());
            }
            assertThat(singletonSet, hasSize(1));
        } finally {
            executorService.shutdown();
        }
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenDelegateGeneratorIsNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Delegate generator can't be null");

        new MemoizingGenerator<>(null);
    }
}
