package org.rupesh.app.tests.perf;

import org.rupesh.app.performance.PerformanceRunner;
import org.testng.annotations.Test;

public class TestBooking {

    @Test(groups = {"performance"})
    public void runPerfTest() throws Exception {
        PerformanceRunner.runJMeter();
    }
}
