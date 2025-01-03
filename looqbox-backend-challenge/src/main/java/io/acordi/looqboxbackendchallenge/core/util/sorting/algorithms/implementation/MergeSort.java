package io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.implementation;

import io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.SortingAlgorithm;

import java.util.Arrays;
import java.util.List;

public class MergeSort<T> implements SortingAlgorithm<T> {

    // Merge Sort is a stable and efficient sorting algorithm that uses a divide-and-conquer approach.
    // It is particularly well-suited for medium-sized datasets due to its consistent time complexity.
    //
    // Time Complexity:
    // - O(n log n): This makes Merge Sort more efficient than algorithms like Bubble Sort or Insertion Sort for larger datasets.
    //
    // Space Complexity:
    // - O(n): The algorithm requires additional space proportional to the input size for temporary arrays.
    //
    // How it works:
    // 1. The array is recursively divided into halves until each subarray contains a single element.
    // 2. These subarrays are then merged in a sorted manner, step by step, to form the final sorted array.
    //
    // Advantages:
    // - Consistent performance regardless of the input.
    // - Stable: It preserves the relative order of equal elements.
    // - Suitable for linked lists, where the merge step can be done in-place.
    //
    // Disadvantages:
    // - Requires additional memory for the merging process.
    // - Slower than in-place sorting algorithms like Quick Sort for very large datasets.
    //
    // Usage:
    // Merge Sort is ideal for applications where stability is important and additional memory is not a constraint.

    // The Comparison interface allows dynamic sorting logic to be injected,
    // making the algorithm reusable for various data types and criteria.
    // This approach decouples sorting logic from comparison logic.
    // Originally, I was going to use the interface Comparator, which I later saw it was out of scope.
    // Then I decided to create my own interface.

    @Override
    public List<T> sort(List<T> items, Comparison<T> comparison) {
        //If the list has one or no elements, it is already sorted
        if (items.size() < 2) {
            return items;
        }
        // Convert List to Array
        @SuppressWarnings("unchecked")
        T[] array = items.toArray((T[]) new Object[items.size()]);

        // Perform Merge Sort
        mergeSort(array, 0, array.length - 1, comparison);

        // Convert Array back to List
        return List.of(array);
    }

    private void mergeSort(T[] array, int left, int right, Comparison<T> comparison) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        // Recursively sort left and right halves
        mergeSort(array, left, mid, comparison);
        mergeSort(array, mid + 1, right, comparison);

        // Merge sorted halves
        merge(array, left, mid, right, comparison);
    }

    private void merge(T[] array, int left, int mid, int right, Comparison<T> comparison) {
        // Copy the subarrays
        T[] leftPart = Arrays.copyOfRange(array, left, mid + 1);
        T[] rightPart = Arrays.copyOfRange(array, mid + 1, right + 1);

        // Indexes into the left, right, and merged subarrays
        int i = 0, j = 0;
        int k = left;

        // Merge subarrays
        while (i < leftPart.length && j < rightPart.length) {
            if (comparison.isLessThan(leftPart[i], rightPart[j])) {
                array[k++] = leftPart[i++];
            } else {
                array[k++] = rightPart[j++];
            }
        }

        // Copy any leftover elements from the left subarray
        while (i < leftPart.length) {
            array[k++] = leftPart[i++];
        }

        // Copy any leftover elements from the right subarray
        while (j < rightPart.length) {
            array[k++] = rightPart[j++];
        }
    }



}
