package io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms;

import java.util.Comparator;
import java.util.List;

public interface SortingAlgorithm<T> {
    List<T> sort(List<T> items, Comparator<T> comparator);
}
