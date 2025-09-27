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

## Step 3:
### Implementation MergeSort
- Hybrid Algorithm: A hybrid approach was implemented, using Merge Sort for arrays larger than 16 elements and Insertion Sort for smaller ones to improve performance on small datasets.
- Memory Optimization: A single buffer is used for the merge process, allocated only once to avoid repeated memory allocations within recursive calls.
### MergeSortTest Testing:
- Parameterized Tests: JUnit 5 tests were written to verify correctness and performance on arrays of various sizes (from 0 to 2000).
- JVM Warm-up: A dedicated method was added to warm up the JVM, ensuring that timing measurements are more accurate and stable.
### MetricsTracker:
- Fixes were implemented to ensure the correct collection of metrics (execution time, comparisons, allocations, recursion depth) and their logging to a CSV file.