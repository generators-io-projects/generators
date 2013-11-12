package io.generators.core;

import org.apache.commons.lang.NotImplementedException;

public class FromListGenerator<T> implements Generator<T> {

    @Override
    public T next() {
        throw new NotImplementedException();
    }
}
