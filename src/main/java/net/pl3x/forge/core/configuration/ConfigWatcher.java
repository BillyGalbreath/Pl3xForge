package net.pl3x.forge.core.configuration;

import net.pl3x.forge.core.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class ConfigWatcher implements Runnable {
    private Path dir;

    public ConfigWatcher(Path dir) {
        this.dir = dir;
    }

    @Override
    public void run() {
        Logger.info("Config directory currently being monitored: " + dir);
        try (WatchService watcher = dir.getFileSystem().newWatchService()) {
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            while (true) {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == ENTRY_CREATE || event.kind() == ENTRY_MODIFY) {
                        if (event.context().toString().equals(PermissionsHandler.FILE_NAME)) {
                            Logger.warn("Detected changes in permissions file!");
                            PermissionsHandler.reload();
                            break;
                        }
                    } else if (event.kind() == ENTRY_DELETE) {
                        if (event.context().toString().equals(PermissionsHandler.FILE_NAME)) {
                            Logger.warn("Permissions file deleted!");
                            PermissionsHandler.reload();
                            break;
                        }
                    }
                }
                if (!key.reset()) {
                    Logger.error("Could not reset key!");
                    key.cancel();
                    watcher.close();
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            Logger.warn("ConfigWatcher has stopped");
            if (!(e instanceof InterruptedException)) {
                e.printStackTrace();
            }
        }
    }
}
