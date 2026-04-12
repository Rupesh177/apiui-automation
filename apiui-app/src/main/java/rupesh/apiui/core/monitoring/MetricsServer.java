package rupesh.apiui.core.monitoring;


import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rupesh.apiui.utils.Config;

import java.net.InetSocketAddress;

public class MetricsServer {

    private static boolean started = false;

    private static final Logger log =
            LoggerFactory.getLogger(MetricsServer.class);

    private static int port = Config.getMetricsPort();

    public static synchronized void start() {

        if (started) return;

        try {
            HttpServer server = HttpServer.create(
                    new InetSocketAddress(port), 0
            );

            server.createContext("/metrics", exchange -> {
                String response = MetricsCollector.expose();

                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
            });

            server.start();
            started = true;

            log.info("📊 Metrics server started on port 9090");

        } catch (Exception e) {
            throw new RuntimeException("Failed to start metrics server", e);
        }
    }
}
