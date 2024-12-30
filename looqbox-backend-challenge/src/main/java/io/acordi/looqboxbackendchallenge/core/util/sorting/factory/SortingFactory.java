package io.acordi.looqboxbackendchallenge.core.util.sorting.factory;

import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.core.util.sorting.SortingStrategy;
import io.acordi.looqboxbackendchallenge.core.util.sorting.implementation.AlphabeticalSort;
import io.acordi.looqboxbackendchallenge.core.util.sorting.implementation.LengthSort;

public class SortingFactory {
    public static SortingStrategy<Pokemon> getSortingStrategy(String sortType) {
        if ("LENGTH".equalsIgnoreCase(sortType)) {
            return new LengthSort();
        }
        return new AlphabeticalSort();
    }
}
