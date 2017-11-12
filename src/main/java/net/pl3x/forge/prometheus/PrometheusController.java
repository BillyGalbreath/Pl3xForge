package net.pl3x.forge.prometheus;

import net.pl3x.forge.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ErrorHandler;

public class PrometheusController {
    public static final PrometheusController INSTANCE = new PrometheusController();

    private Server server;

    public void start() {

        server = new Server(9225);
        server.setHandler(new MetricsController());
        server.setErrorHandler(new ErrorHandler());

        try {
            server.start();

            Logger.info("Started Prometheus metrics endpoint on port 9225");
        } catch (Exception e) {
            Logger.error("Could not start embedded Jetty server");
            e.printStackTrace();
        }
    }

    public void stop() {
        if (server != null) {
            try {
                server.stop();
                server = null;

                Logger.info("Stopped Prometheus metrics endpoint");
            } catch (Exception e) {
                Logger.error("Could not stop embedded Jetty server");
                e.printStackTrace();
            }
        }
    }

    public void restart() {
        stop();
        start();
    }

    public boolean isStopped() {
        return server == null || server.isStopped();
    }
}
