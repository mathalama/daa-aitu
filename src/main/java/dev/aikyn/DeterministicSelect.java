package dev.aikyn;

import java.util.ArrayList;
import java.util.Arrays;

public class DeterministicSelect {
    public static MetricsTracker lastRunMetrics;

    public static int deterministicSelect(int[] arr, int k) {
        MetricsTracker metrics = new MetricsTracker();
        lastRunMetrics = metrics;

        if (arr == null || k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("Invalid input array or k");
        }

        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        metrics.incrementAllocationCounter();

        return deterministicSelectHelper(arrCopy, k, 0, arrCopy.length - 1, metrics);
    }

    private static int medianOfMedians(int[] arr, MetricsTracker metrics) {
        metrics.increaseRecursionDepth();

        ArrayList<Integer> medians = new ArrayList<>();
        metrics.incrementAllocationCounter();

        for (int i = 0; i < arr.length; i += 5) {
            int end = Math.min(i + 5, arr.length);
            int[] group = Arrays.copyOfRange(arr, i, end);
            metrics.incrementAllocationCounter();
            Arrays.sort(group);
            medians.add(group[group.length / 2]);
        }

        metrics.incrementComparisonCounter();
        if (medians.size() <= 1) {
            metrics.decreaseRecursionDepth();
            return medians.get(0);
        }

        int[] mediansArray = medians.stream().mapToInt(Integer::intValue).toArray();
        metrics.incrementAllocationCounter();

        int result = medianOfMedians(mediansArray, metrics);
        metrics.decreaseRecursionDepth();
        return result;
    }

    private static int deterministicSelectHelper(int[] arr, int k, int left, int right, MetricsTracker metrics) {
        metrics.increaseRecursionDepth();

        metrics.incrementComparisonCounter();
        if (left == right) {
            metrics.decreaseRecursionDepth();
            return arr[left];
        }

        int[] subarray = Arrays.copyOfRange(arr, left, right + 1);
        metrics.incrementAllocationCounter();
        int pivot = medianOfMedians(subarray, metrics);

        int pivotIndex = partition(arr, left, right, pivot, metrics);

        metrics.incrementComparisonCounter();
        if (k == pivotIndex) {
            metrics.decreaseRecursionDepth();
            return arr[k];
        } else if (k < pivotIndex) {
            metrics.incrementComparisonCounter();
            int result = deterministicSelectHelper(arr, k, left, pivotIndex - 1, metrics);
            metrics.decreaseRecursionDepth();
            return result;
        } else {
            int result = deterministicSelectHelper(arr, k, pivotIndex + 1, right, metrics);
            metrics.decreaseRecursionDepth();
            return result;
        }
    }

    private static int partition(int[] arr, int left, int right, int pivot, MetricsTracker metrics) {
        int pivotIndex = findPivotIndex(arr, left, right, pivot, metrics);
        if (pivotIndex == -1) return left;
        swap(arr, pivotIndex, right);

        int i = left;
        for (int j = left; j < right; j++) {
            metrics.incrementComparisonCounter();
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }

    private static int findPivotIndex(int[] arr, int left, int right, int pivot, MetricsTracker metrics) {
        for (int i = left; i <= right; i++) {
            metrics.incrementComparisonCounter();
            if (arr[i] == pivot) {
                return i;
            }
        }
        return -1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}