package org.rupesh.app.core.monitoring;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.rupesh.app.utils.Config;
import org.rupesh.app.exceptionNretry.FrameworkException;

import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MetricsServer {

    private static final Logger log =
            LoggerFactory.getLogger(MetricsServer.class);

    private static final int PORT = Config.getMetricsPort();

    private static boolean started = false;

    private MetricsServer() {
    }

    public static synchronized void start() {

        if (started) {
            return;
        }

        try {
            HttpServer server = HttpServer.create(
                    new InetSocketAddress(PORT), 0
            );

            server.createContext("/metrics", exchange -> {
                String response = MetricsCollector.expose();
                byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

                exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            });

            server.start();
            started = true;

            log.info("Metrics server started on port {}", PORT);

        } catch (Exception e) {
            log.error("Failed to start metrics server on port {}", PORT, e);
            throw new FrameworkException("Failed to start metrics server", e);
        }
    }
}