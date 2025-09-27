package dev.aikyn;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricsTracker {
    private final AtomicInteger comparisonCounter = new AtomicInteger(0);
    private final AtomicInteger allocationCounter = new AtomicInteger(0);
    private final AtomicInteger currentRecursionDepth = new AtomicInteger(0);
    private final AtomicInteger maxRecursionDepth = new AtomicInteger(0);

    public void incrementComparisonCounter() {
        comparisonCounter.incrementAndGet();
    }

    public void incrementAllocationCounter() {
        allocationCounter.incrementAndGet();
    }

    public void increaseRecursionDepth() {
        int depth = currentRecursionDepth.incrementAndGet();
        maxRecursionDepth.updateAndGet(max -> Math.max(max, depth));
    }
    public void decreaseRecursionDepth() {
        currentRecursionDepth.decrementAndGet();
    }

    public void reset() {
        comparisonCounter.set(0);
        allocationCounter.set(0);
        currentRecursionDepth.set(0);
        maxRecursionDepth.set(0);
    }

    public void writeMetricsToCSV(long timeTaken, String algorithmName) throws IOException {
        try (FileWriter fileWriter = new FileWriter("target/metrics.csv", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.printf("%s, %d, %d, %d, %d\n",
                    algorithmName,
                    timeTaken,
                    comparisonCounter.get(),
                    allocationCounter.get(),
                    maxRecursionDepth.get());

        }
    }
}