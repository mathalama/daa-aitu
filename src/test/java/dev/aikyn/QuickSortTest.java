package dev.aikyn;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    private static final String METRICS_FILE_NAME = "target/metrics.csv";
    @BeforeAll
    static void clearMetricsFile() {
        try {
            boolean deleted = Files.deleteIfExists(Paths.get(METRICS_FILE_NAME));
        } catch (IOException e) {
            System.err.println("Error while trying to delete metrics file: " + e.getMessage());
        }
    }

    @BeforeAll
    static void warmUpJvm() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int[] dummyArray = new int[1];
            for (int j = 0; j < dummyArray.length; j++) {
                dummyArray[j] = random.nextInt();
            }
            QuickSort.sort(dummyArray);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 10, 50, 100, 500, 1000, 2000})
    public void testLargeArraySorting(int size) throws IOException {

        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * 10000);
        }

        long startTime = System.nanoTime();

        QuickSort.sort(arr);

        MetricsTracker metrics = QuickSort.sort(arr);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Sorting " + size + " elements took: " + duration + " nanoseconds.");

        metrics.writeMetricsToCSV(duration, "QuickSort_size_" + size);

        for (int i = 1; i < arr.length; i++) {
            assertTrue(arr[i - 1] <= arr[i], "Array should be sorted in ascending order.");
        }
    }
}