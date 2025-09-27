package dev.aikyn;

import java.util.Random;

public class QuickSort {
    private static final Random RANDOM = new Random();

    public static MetricsTracker sort(int[] arr) {
        MetricsTracker metrics = new MetricsTracker();
        if (arr != null && arr.length >= 2) {
            quickSortLoop(arr, 0, arr.length - 1, metrics);
        }
        return metrics;
    }

    private static void quickSortLoop(int[] arr, int low, int high, MetricsTracker metrics) {
        metrics.incrementAllocationCounter();
        metrics.increaseRecursionDepth();

        while (low < high) {
            int pivotIndex = partition(arr, low, high, metrics);

            if (pivotIndex - low < high - pivotIndex) {
                quickSortLoop(arr, low, pivotIndex - 1, metrics);
                low = pivotIndex + 1;
            } else {
                quickSortLoop(arr, pivotIndex + 1, high, metrics);
                high = pivotIndex - 1;
            }
        }
        metrics.decreaseRecursionDepth();
    }

    private static int partition(int[] arr, int low, int high, MetricsTracker metrics) {
        int randomIndex = low + RANDOM.nextInt(high - low + 1);
        swap(arr, randomIndex, high, metrics);

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            metrics.incrementComparisonCounter();
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j, metrics);
            }
        }
        swap(arr, i + 1, high, metrics);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j, MetricsTracker metrics) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}