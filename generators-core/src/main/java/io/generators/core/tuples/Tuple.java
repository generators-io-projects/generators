package io.generators.core.tuples;


public abstract class Tuple {
    public static <T1, T2> Tuple2<T1, T2> tuple(T1 _1, T2 _2) {
        return new Tuple2<>(_1, _2);
    }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> tuple(T1 _1, T2 _2, T3 _3) {
        return new Tuple3<>(_1, _2, _3);
    }

    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> tuple(T1 _1, T2 _2, T3 _3, T4 _4) {
        return new Tuple4<>(_1, _2, _3, _4);
    }

    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> tuple(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        return new Tuple5<>(_1, _2, _3, _4, _5);
    }

    public abstract Iterable<?> iterable();

    public String toString() {
        return "Tuple" + iterable();
    }

    @Override
    public int hashCode() {
        return iterable().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
       return obj instanceof Tuple && ((Tuple) obj).iterable().equals(iterable());
    }
}
