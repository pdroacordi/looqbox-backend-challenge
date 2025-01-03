package io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.implementation;

import io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.SortingAlgorithm;

import java.util.List;

public class BubbleSort<T> implements SortingAlgorithm<T> {

    // I am not actually using bubble sort, merge sort is just superior.
    // I just wanted to show that it would be pretty easy to swap between sorting algorithms if asked.
    // If it is of your interest to try bubble sort, you may change the config at application.yml
    //
    // Bubble Sort is a simple and straightforward sorting algorithm that repeatedly steps through the list,
    // compares adjacent elements, and swaps them if they are in the wrong order.
    // It "bubbles" the largest unsorted element to the end of the array after each pass.
    //
    // Time Complexity:
    // - Best case: O(n) (when the array is already sorted, with an optimization check).
    // - Average case: O(n²).
    // - Worst case: O(n²).
    // This makes Bubble Sort inefficient for large datasets compared to algorithms like Merge Sort or Quick Sort.
    //
    // Space Complexity:
    // - O(1): Bubble Sort is an in-place algorithm, meaning it does not require additional space
    //         proportional to the input size.
    //
    // How it works:
    // 1. Traverse the array from the beginning to the end.
    // 2. Compare each pair of adjacent elements and swap them if they are out of order.
    // 3. Repeat the process for each element, reducing the range after every pass,
    //    as the largest element is "bubbled" to its correct position.

    @Override
    public List<T> sort(List<T> items, Comparison<T> comparison) {
        if (items.size() < 2) {
            return items; // Already sorted
        }

        // Convert List to Array
        @SuppressWarnings("unchecked")
        T[] array = items.toArray((T[]) new Object[items.size()]);

        // Perform Bubble Sort
        bubbleSort(array, comparison);

        // Convert Array back to List
        return List.of(array);
    }

    private void bubbleSort(T[] array, Comparison<T> comparison) {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (!comparison.isLessThan(array[j], array[j + 1])) {
                    // Swap elements
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

}
