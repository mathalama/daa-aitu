# Assignment 1 | Divide-and-Conquer Algorithms

## Step 1: Initialize the project (committed on 2025-09-26)
- The Git repository has been initialized.
- Configured **Maven** for dependency management.
- **JUnit 5** is connected for testing.
- The initial file has been created `README.md `.
- Configure **CI** using **GitHub Actions** to automatically build and test the project.

## Step 2: Implement Metrics Tracker and Integrate with MergeSort (committed on 2025-09-27)

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
