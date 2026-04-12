package rupesh.apiui.core.monitoring;

import java.util.concurrent.atomic.AtomicInteger;

public class MetricsCollector {

    private static final AtomicInteger passed = new AtomicInteger(0);
    private static final AtomicInteger failed = new AtomicInteger(0);
    private static final AtomicInteger skipped = new AtomicInteger(0);

    public static void pass() {
        passed.incrementAndGet();
    }

    public static void fail() {
        failed.incrementAndGet();
    }

    public static void skip() {
        skipped.incrementAndGet();
    }

    public static String expose() {
        return "tests_passed " + passed.get() + "\n" +
                "tests_failed " + failed.get() + "\n" +
                "tests_skipped " + skipped.get();
    }
}