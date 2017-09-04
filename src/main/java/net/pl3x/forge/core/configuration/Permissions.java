package net.pl3x.forge.core.configuration;

import net.pl3x.forge.core.Logger;
import net.pl3x.forge.core.permissions.PermissionsHolder;

import java.io.File;
import java.io.IOException;

public class Permissions extends ConfigLoader {
    static final String FILE_NAME = "permissions.json";
    private static File configDir;
    private static PermissionsHolder permissionsHolder;

    public static PermissionsHolder getHolder() {
        return permissionsHolder;
    }

    public static void reload(File dir) {
        configDir = dir;
        reload();
    }

    public static void reload() {
        Logger.info("Loading permissions from disk...");
        try {
            permissionsHolder = loadConfig(new PermissionsHolder(),
                    PermissionsHolder.class, new File(configDir, FILE_NAME));
        } catch (IOException e) {
            permissionsHolder = null;
            e.printStackTrace();
        }
    }
}
