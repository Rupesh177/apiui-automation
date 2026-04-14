package org.rupesh.app.performance;

public class PerformanceRunner {

    public static void runJMeter() throws Exception {

        ProcessBuilder pb = new ProcessBuilder(
                "jmeter",
                "-n",
                "-t", "test-plan.jmx",
                "-l", "results.jtl"
        );

        pb.start();
    }
}