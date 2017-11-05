package net.pl3x.forge.configuration;

import net.pl3x.forge.Logger;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.network.BigHeadPacket;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.scheduler.Pl3xRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BigHeadConfig extends ConfigLoader implements ConfigBase {
    public static final BigHeadConfig INSTANCE = new BigHeadConfig();
    public static final String FILE_NAME = "bighead.json";

    public Data data;

    public void init() {
        reload();

        new Pl3xRunnable() {
            @Override
            public void run() {
                PacketHandler.INSTANCE.sendToAll(new BigHeadPacket(data.getBigHeads()));
            }
        }.runTaskTimer(100, 100); // every 5 seconds
    }

    public String file() {
        return FILE_NAME;
    }

    public void reload() {
        Logger.info("Loading " + FILE_NAME + " from disk...");
        try {
            data = loadConfig(new Data(), Data.class, new File(Pl3x.configDir, FILE_NAME));
        } catch (IOException e) {
            data = null;
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            saveConfig(data, Data.class, new File(Pl3x.configDir, FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Data {
        private List<String> bighead = new ArrayList<>();

        public Set<UUID> getBigHeads() {
            return bighead.stream()
                    .map(UUID::fromString)
                    .collect(Collectors.toSet());
        }

        public void setBighead(Set<UUID> bighead) {
            this.bighead = bighead.stream()
                    .map(UUID::toString)
                    .collect(Collectors.toList());
        }
    }
}
