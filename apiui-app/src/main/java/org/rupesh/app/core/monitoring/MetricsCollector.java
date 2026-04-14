package org.rupesh.app.core.monitoring;

import java.util.concurrent.atomic.AtomicInteger;

public class MetricsCollector {

    private static final AtomicInteger passed = new AtomicInteger(0);
    private static final AtomicInteger failed = new AtomicInteger(0);
    private static final AtomicInteger skipped = new AtomicInteger(0);

    private MetricsCollector() {
    }

    public static void pass() {
        passed.incrementAndGet();
    }

    public static void fail() {
        failed.incrementAndGet();
    }

    public static void skip() {
        skipped.incrementAndGet();
    }

    public static void reset() {
        passed.set(0);
        failed.set(0);
        skipped.set(0);
    }

    public static String expose() {

        StringBuilder sb = new StringBuilder();

        sb.append("# HELP test_passed_total Total number of passed tests\n");
        sb.append("# TYPE test_passed_total counter\n");
        sb.append("test_passed_total ").append(passed.get()).append("\n");

        sb.append("# HELP test_failed_total Total number of failed tests\n");
        sb.append("# TYPE test_failed_total counter\n");
        sb.append("test_failed_total ").append(failed.get()).append("\n");

        sb.append("# HELP test_skipped_total Total number of skipped tests\n");
        sb.append("# TYPE test_skipped_total counter\n");
        sb.append("test_skipped_total ").append(skipped.get()).append("\n");

        return sb.toString();
    }
}