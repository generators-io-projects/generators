package io.generators.core.tuples;

import com.google.common.collect.ImmutableList;

public class Tuple4<T1, T2, T3,T4> extends Tuple {
    private final T1 _1;
    private final T2 _2;
    private final T3 _3;
    private final T4 _4;

    public Tuple4(T1 _1, T2 _2, T3 _3, T4 _4) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
    }


    public T1 _1() {
        return _1;
    }

    public T2 _2() {
        return _2;
    }

    public T3 _3() {
        return _3;
    }
    public T4 _4() {
        return _4;
    }

    public Iterable<?> iterable() {
        return ImmutableList.builder().add(_1).add(_2).add(_3).add(_4).build();
    }

}
