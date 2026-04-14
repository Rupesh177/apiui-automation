package org.rupesh.app.performance;

import org.rupesh.app.exceptionNretry.FrameworkException;
import org.rupesh.app.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PerformanceRunner {

    private static final Logger log =
            LoggerFactory.getLogger(PerformanceRunner.class);

    private PerformanceRunner() {}

    public static void runJMeter() {

        String jmeterPath = Config.get("jmeter.path", "jmeter");
        String testPlan = Config.get("jmeter.testplan", "test-plan.jmx");
        String resultFile = Config.get("jmeter.result", "results.jtl");

        try {

            ProcessBuilder pb = new ProcessBuilder(
                    jmeterPath,
                    "-n",
                    "-t", testPlan,
                    "-l", resultFile
            );

            log.info("Starting JMeter execution...");
            Process process = pb.start();

            // Capture logs
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                log.debug("[JMETER] {}", line);
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                log.error("JMeter execution failed with exit code {}", exitCode);
                throw new FrameworkException("JMeter execution failed");
            }

            log.info("JMeter execution completed successfully");

        } catch (Exception e) {
            log.error("Error running JMeter", e);
            throw new FrameworkException("Performance execution failed", e);
        }
    }
}