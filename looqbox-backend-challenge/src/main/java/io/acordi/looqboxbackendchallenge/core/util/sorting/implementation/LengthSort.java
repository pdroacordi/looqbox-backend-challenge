package io.acordi.looqboxbackendchallenge.core.util.sorting.implementation;

import io.acordi.looqboxbackendchallenge.core.domain.Pokemon;
import io.acordi.looqboxbackendchallenge.core.util.sorting.SortingStrategy;
import io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.SortingAlgorithm;
import io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.factory.SortingAlgorithmFactory;

import java.util.Comparator;
import java.util.List;

public class LengthSort implements SortingStrategy<Pokemon> {

    @Override
    public List<Pokemon> sort(List<Pokemon> items) {
        SortingAlgorithm<Pokemon> algorithm = SortingAlgorithmFactory.getAlgorithm();
        return algorithm.sort(items, (p1, p2) -> Integer.compare(p1.getName().length(), p2.getName().length()) );
    }

}
