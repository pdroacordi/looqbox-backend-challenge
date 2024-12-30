package io.acordi.looqboxbackendchallenge.core.util.sorting.implementation;

import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.core.util.sorting.SortingStrategy;
import io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.SortingAlgorithm;
import io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.factory.SortingAlgorithmFactory;

import java.util.List;

public class AlphabeticalSort implements SortingStrategy<Pokemon> {

    @Override
    public List<Pokemon> sort(List<Pokemon> items) {
        SortingAlgorithm<Pokemon> algorithm = SortingAlgorithmFactory.getAlgorithm();
        return algorithm.sort(items, (p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()) );
    }
}
