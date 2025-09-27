package dev.aikyn;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClosestPairTest {

    private static final String METRICS_FILE_NAME = "target/metrics.csv";
    private static final Random RANDOM = new Random();

    @BeforeAll
    static void clearMetricsFile() {
        try {
            Files.deleteIfExists(Paths.get(METRICS_FILE_NAME));
        } catch (IOException e) {
            System.err.println("Error while trying to delete metrics file: " + e.getMessage());
        }
    }

    @BeforeAll
    static void warmUpJvm() {
        for (int i = 0; i < 5000; i++) {
            Point[] dummyPoints = generateRandomPoints(100);
            MetricsTracker dummyMetrics = new MetricsTracker();
            ClosestPair.findClosestPair(dummyPoints, dummyMetrics);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 10, 50, 100, 500, 1000, 2000})
    public void testClosestPairWithRandomData(int size) throws IOException {
        Point[] points = generateRandomPoints(size);

        MetricsTracker metrics = new MetricsTracker();

        long startTime = System.nanoTime();
        double actualDistance = ClosestPair.findClosestPair(points, metrics);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.printf("Finding closest pair for %d points took: %d nanoseconds.%n", size, duration);

        metrics.writeMetricsToCSV(duration, "ClosestPair_size_" + size);

        double expectedDistance = bruteForceChecker(points);
        assertEquals(expectedDistance, actualDistance, 1e-9, "The calculated distance should match the brute-force result.");
    }

    private static Point[] generateRandomPoints(int size) {
        if (size < 0) return new Point[0];
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            double x = RANDOM.nextDouble() * 10000;
            double y = RANDOM.nextDouble() * 10000;
            points[i] = new Point(x, y);
        }
        return points;
    }

    private double bruteForceChecker(Point[] points) {
        if (points == null || points.length < 2) {
            return Double.POSITIVE_INFINITY;
        }
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dx = points[i].x() - points[j].x();
                double dy = points[i].y() - points[j].y();
                minDistance = Math.min(minDistance, Math.sqrt(dx * dx + dy * dy));
            }
        }
        return minDistance;
    }
}