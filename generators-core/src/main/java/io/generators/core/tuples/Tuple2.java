package io.generators.core.tuples;


import com.google.common.collect.ImmutableList;

public class Tuple2 <T1, T2> extends Tuple {
    private final T1 _1;
    private final T2 _2;

    public Tuple2(T1 _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }


    public T1 _1() {
        return _1;
    }

    public T2 _2() {
        return _2;
    }

    public Iterable<?> iterable() {
        return ImmutableList.builder().add(_1).add(_2).build();
    }


}

