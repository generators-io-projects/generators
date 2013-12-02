package io.generators.core;

/**
* Test type class
*/
public class WholeAmount {
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
