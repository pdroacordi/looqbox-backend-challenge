package io.acordi.looqboxbackendchallenge.core.util.sorting;

import java.util.List;

public interface SortingStrategy<T> {

    List<T> sort(List<T> items);
}
