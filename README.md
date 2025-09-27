# Assignment 1 | Divide-and-Conquer Algorithms

## Step 1: Initialize the project (committed on 2025-09-26)
- The Git repository has been initialized.
- Configured **Maven** for dependency management.
- **JUnit 5** is connected for testing.
- The initial file has been created `README.md `.
- Configure **CI** using **GitHub Actions** to automatically build and test the project.

## Step 2: Implement Metrics Tracker and Integrate with MergeSort (committed on 2025-09-27)
### Implementation MetricsTracker:
- Comparison Counter: Increases each time items are compared during the sorting process.
- Memory Allocation Counter: Increases with each allocation of memory (for example, when creating temporary arrays).
- Recursion Depth Tracker: Tracks the current depth of recursion during sorting.
### CSV Logging:
- After sorting, the following metrics are recorded:
- The name of the algorithm.
- Time of execution.
- The number of comparisons.
- The number of memory allocations.
- The depth of recursion.

## Step 3: Implementation MergeSort and MergeSortTest Testing (committed on 2025-09-27)
### Implementation MergeSort
- Hybrid Algorithm: A hybrid approach was implemented,using Merge Sort for arrays larger than 16 elements and Insertion Sort for smaller ones to improve performance on small datasets.
- Memory Optimization: A single buffer is used for the merge process, allocated only once to avoid repeated memory allocations within recursive calls.
### MergeSortTest Testing:
- Parameterized Tests: JUnit 5 tests were written to verify correctness and performance on arrays of various sizes (from 0 to 2000).
- JVM Warm-up: A dedicated method was added to warm up the JVM, ensuring that timing measurements are more accurate and stable.
### MetricsTracker:
- Fixes were implemented to ensure the correct collection of metrics (execution time, comparisons, allocations, recursion depth) and their logging to a CSV file.

## Step 4: Implement QuickSort and QuickSortTest (committed on 2025-09-27)
### Implementation QuickSort
- Randomized Pivot Selection: A random index is chosen as the pivot to reduce the chances of encountering worst-case performance (e.g., sorted or reverse-sorted arrays).
- Tail Recursion Optimization: To minimize stack usage, the algorithm chooses the smaller subarray for recursive calls, reducing the recursion depth.
- The partitioning process ensures elements are rearranged such that all values smaller than the pivot are on the left and those larger are on the right.
- The algorithm then recursively sorts the two subarrays.

### QuickSortTest
- JUnit 5 Testing: A dedicated test class QuickSortTest was created to verify the correctness and performance of the QuickSort algorithm.
- Parameterized Tests: The tests cover various array sizes, ranging from 0 to 2000 elements, ensuring the algorithm handles both small and large datasets.
- JVM Warm-up: A warm-up method was introduced, running dummy sorting operations before the actual tests to stabilize JVM performance and improve the accuracy of benchmarking.
- Correctness Check: After sorting, assertions verify that the array is correctly sorted, ensuring no errors in the sorting logic.
- Performance Logging: Each test logs sorting time and other metrics (comparisons, memory allocations, recursion depth) to a CSV file for analysis.

## Step 5: Implement Deterministic Select Algorithm (committed on 2025-09-27)

- Implemented the Deterministic Select (Median-of-Medians) algorithm, which guarantees worst-case O(n) time complexity.
The method uses a carefully chosen pivot from groups of 5 to prevent the O(n²) performance degradation seen in basic Quickselect.
- The implementation was validated using JUnit 5 parameterized tests.
Metrics like comparisons, allocations, and recursion depth are captured via a public static field and logged to a CSV file for analysis.

## Step 6: Implement Closest Pair of Points Algorithm (Committed on 2025-09-27)

- Implemented the O(n log n) Divide and Conquer algorithm to find the closest pair of points.
- Core Logic: Efficiency is achieved by presorting the points and using a linear-time strip optimization when merging the recursive calls.
- Validation: The algorithm's correctness was verified via JUnit 5 tests against a brute-force solution.  
  All performance metrics were collected using MetricsTracker.