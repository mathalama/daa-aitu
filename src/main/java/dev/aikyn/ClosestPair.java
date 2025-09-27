package dev.aikyn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClosestPair {
    public static double findClosestPair(Point[] points, MetricsTracker metrics) {
        if (points == null || points.length < 2) {
            return Double.POSITIVE_INFINITY;
        }

        Point[] pointsByX = Arrays.copyOf(points, points.length);
        metrics.incrementAllocationCounter();
        Arrays.sort(pointsByX, Comparator.comparingDouble(Point::x));

        Point[] pointsByY = Arrays.copyOf(points, points.length);
        metrics.incrementAllocationCounter();
        Arrays.sort(pointsByY, Comparator.comparingDouble(Point::y));

        return findRecursive(pointsByX, pointsByY, metrics);
    }

    private static double findRecursive(Point[] pointsByX, Point[] pointsByY, MetricsTracker metrics) {
        metrics.increaseRecursionDepth();
        metrics.incrementAllocationCounter();

        int n = pointsByX.length;

        if (n <= 3) {
            double result = bruteForce(pointsByX, metrics);
            metrics.decreaseRecursionDepth();
            return result;
        }

        int mid = n / 2;
        Point midPoint = pointsByX[mid];

        Point[] leftHalfByX = Arrays.copyOfRange(pointsByX, 0, mid);
        Point[] rightHalfByX = Arrays.copyOfRange(pointsByX, mid, n);
        metrics.incrementAllocationCounter();

        List<Point> leftHalfByY = new ArrayList<>();
        List<Point> rightHalfByY = new ArrayList<>();
        metrics.incrementAllocationCounter();

        for (Point p : pointsByY) {
            if (p.x() <= midPoint.x()) {
                leftHalfByY.add(p);
            } else {
                rightHalfByY.add(p);
            }
        }

        double deltaLeft = findRecursive(leftHalfByX, leftHalfByY.toArray(new Point[0]), metrics);
        double deltaRight = findRecursive(rightHalfByX, rightHalfByY.toArray(new Point[0]), metrics);

        metrics.incrementComparisonCounter();
        double delta = Math.min(deltaLeft, deltaRight);

        List<Point> stripPoints = new ArrayList<>();
        metrics.incrementAllocationCounter();

        for (Point p : pointsByY) {
            metrics.incrementComparisonCounter();
            if (Math.abs(p.x() - midPoint.x()) < delta) {
                stripPoints.add(p);
            }
        }

        for (int i = 0; i < stripPoints.size(); i++) {
            for (int j = i + 1; j < stripPoints.size() && (stripPoints.get(j).y() - stripPoints.get(i).y()) < delta; j++) {
                metrics.incrementComparisonCounter();
                double d = distance(stripPoints.get(i), stripPoints.get(j));
                metrics.incrementComparisonCounter();
                if (d < delta) {
                    delta = d;
                }
            }
        }

        metrics.decreaseRecursionDepth();
        return delta;
    }

    private static double bruteForce(Point[] points, MetricsTracker metrics) {
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                metrics.incrementComparisonCounter();
                double d = distance(points[i], points[j]);
                metrics.incrementComparisonCounter();
                if (d < minDistance) {
                    minDistance = d;
                }
            }
        }
        return minDistance;
    }

    private static double distance(Point p1, Point p2) {
        double dx = p1.x() - p2.x();
        double dy = p1.y() - p2.y();
        return Math.sqrt(dx * dx + dy * dy);
    }
}