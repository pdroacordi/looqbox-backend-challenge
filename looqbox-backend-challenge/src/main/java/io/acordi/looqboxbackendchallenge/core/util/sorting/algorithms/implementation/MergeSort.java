package io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.implementation;

import io.acordi.looqboxbackendchallenge.core.util.sorting.algorithms.SortingAlgorithm;

import java.util.Arrays;
import java.util.Comparator;
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

    // Here I have used a lot of generics (<T> and Comparator) so that this method can be used for both String comparison
    // And int comparison (alphabetical and length sort, as requested).
    @Override
    public List<T> sort(List<T> items, Comparator<T> comparator) {
        //If the list has one or no elements, it is already sorted
        if (items.size() < 2) {
            return items;
        }
        T[] array = (T[]) items.toArray((T[]) new Object[items.size()]);
        mergeSort( array, comparator );
        items = Arrays.asList(array);
        return items;
    }

    private void mergeSort(T[] array, Comparator<T> comparator) {
        int length = array.length;

        if (length < 2) {
            return;
        }

        // Split the array into left and right halves
        int mid = length / 2;
        T[] left = Arrays.copyOfRange(array, 0, mid);
        T[] right = Arrays.copyOfRange(array, mid, length);

        // Recursively sort both halves
        mergeSort(left, comparator);
        mergeSort(right, comparator);

        // Merge the sorted halves
        merge(array, left, right, comparator);
    }

    private void merge(T[] array, T[] left, T[] right, Comparator<T> comparator) {
        int i = 0, j = 0, k = 0;

        // Merge elements from left and right arrays in sorted order
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        // Copy any remaining elements from the left array
        while (i < left.length) {
            array[k++] = left[i++];
        }

        // Copy any remaining elements from the right array
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }


}
