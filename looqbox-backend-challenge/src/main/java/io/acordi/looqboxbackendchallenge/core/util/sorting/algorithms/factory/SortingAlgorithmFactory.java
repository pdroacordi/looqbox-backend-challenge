package io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.factory;

import io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.implementation.BubbleSort;
import io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.implementation.MergeSort;
import io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.SortingAlgorithm;
import org.springframework.beans.factory.annotation.Value;

public class SortingAlgorithmFactory {

    @Value("${acordi.sorting.algorithm}")
    private static String algorithmType;

    public static <T> SortingAlgorithm<T> getAlgorithm() {
        if ("bubble".equalsIgnoreCase(algorithmType)) {
            return new BubbleSort<>();
        } else {
            return new MergeSort<>(); // Default to Merge Sort
        }
    }
}
