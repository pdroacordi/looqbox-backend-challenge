package io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms;

import java.util.List;

public interface SortingAlgorithm<T> {
    List<T> sort(List<T> items, Comparison<T> comparison);

    interface Comparison<T> {
        boolean isLessThan(T a, T b);
    }
}
