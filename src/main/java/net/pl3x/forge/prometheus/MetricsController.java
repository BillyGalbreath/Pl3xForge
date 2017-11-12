package net.pl3x.forge.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.common.TextFormat;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.pl3x.forge.Logger;
import net.pl3x.forge.scheduler.Pl3xScheduler;
import net.pl3x.forge.util.PlayerUtil;
import net.pl3x.forge.util.task.TPSTracker;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MetricsController extends AbstractHandler {
    private Gauge players;
    private Gauge loadedChunksPerWorld;
    private Gauge playersPerWorld;
    private Gauge entitiesPerWorld;
    private Gauge livingEntitiesPerWorld;
    private Gauge memory;
    private Gauge tps;

    MetricsController() {
        CollectorRegistry.defaultRegistry.clear();

        players = Gauge.build()
                .name("mc_players_total")
                .help("Online and offline players")
                .labelNames("state")
                .create().register();
        loadedChunksPerWorld = Gauge.build()
                .name("mc_loaded_chunks_total")
                .help("Chunks loaded per world")
                .labelNames("world")
                .create().register();
        playersPerWorld = Gauge.build()
                .name("mc_players_online_total")
                .help("Players currently online per world")
                .labelNames("world")
                .create().register();
        entitiesPerWorld = Gauge.build()
                .name("mc_entities_total")
                .help("Entities loaded per world")
                .labelNames("world")
                .create().register();
        livingEntitiesPerWorld = Gauge.build()
                .name("mc_living_entities_total")
                .help("Living entities loaded per world")
                .labelNames("world")
                .create().register();
        memory = Gauge.build()
                .name("mc_jvm_memory")
                .help("JVM memory usage")
                .labelNames("type")
                .create().register();
        tps = Gauge.build()
                .name("mc_tps")
                .help("Server TPS (ticks per second)")
                .create().register();
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (PrometheusController.INSTANCE.isStopped()) {
            return;
        }

        if (!target.equals("/metrics")) {
            Logger.debug("Invalid url requested: " + target);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Logger.debug("Metrics requested.");

        tps.set(TPSTracker.INSTANCE.getTPS());

        /*
        * API calls have to be made from the main thread.
        * That's why we use the scheduler to retrieve the server stats.
        * */
        Future<Object> future = Pl3xScheduler.INSTANCE.callSyncMethod(() -> {
            players.labels("online").set(PlayerUtil.getOnlinePlayerNames().size());
            players.labels("offline").set(PlayerUtil.getOfflinePlayerNames().size());
            for (WorldServer world : FMLCommonHandler.instance().getMinecraftServerInstance().worlds) {
                String worldName = getWorldName(world);
                loadedChunksPerWorld.labels(worldName).set(world.getChunkProvider().getLoadedChunks().size());
                playersPerWorld.labels(worldName).set(world.playerEntities.size());
                entitiesPerWorld.labels(worldName).set(world.loadedEntityList.size());
                livingEntitiesPerWorld.labels(worldName).set(world.loadedEntityList.stream().filter(e -> e instanceof EntityLiving).count());
            }
            memory.labels("max").set(Runtime.getRuntime().maxMemory());
            memory.labels("free").set(Runtime.getRuntime().freeMemory());
            return null;
        });

        try {
            future.get();

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(TextFormat.CONTENT_TYPE_004);

            TextFormat.write004(response.getWriter(), CollectorRegistry.defaultRegistry.metricFamilySamples());

            baseRequest.setHandled(true);
        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {
            Logger.warn("Failed to read server statistics");
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getWorldName(WorldServer world) {
        switch (world.provider.getDimension()) {
            case 1:
                return "The End";
            case -1:
                return "The Nether";
            case 0:
                return "World";
            default:
                return world.getWorldInfo().getWorldName();
        }
    }
}
