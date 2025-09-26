package dev.aikyn;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricsTracker {
    private final AtomicInteger comparisonCounter = new AtomicInteger(0);
    private final AtomicInteger allocationCounter = new AtomicInteger(0);
    private int recursionDepth = 0;

    public void incrementComparisonCounter() {
        comparisonCounter.incrementAndGet();
    }

    public void incrementAllocationCounter() {
        allocationCounter.incrementAndGet();
    }

    public void increaseRecursionDepth() {
        recursionDepth++;
    }

    public void decreaseRecursionDepth() {
        recursionDepth--;
    }

    public void writeMetricsToCSV(long timeTaken, String algorithmName) {
        try (FileWriter fileWriter = new FileWriter("target/metrics.csv", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.printf("%s, %d, %d, %d, %d, %d\n",
                    algorithmName,
                    timeTaken,
                    comparisonCounter.get(),
                    allocationCounter.get(),
                    recursionDepth,
                    recursionDepth);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}