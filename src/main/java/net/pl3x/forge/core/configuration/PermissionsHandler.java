package net.pl3x.forge.core.configuration;

import net.pl3x.forge.core.Logger;

import java.io.File;
import java.io.IOException;

public class PermissionsHandler extends ConfigLoader {
    static final String FILE_NAME = "permissions.json";
    private static File configDir;
    private static Permissions permissions;

    public static Permissions getPermissions() {
        return permissions;
    }

    public static void reload(File dir) {
        configDir = dir;
        reload();
    }

    public static void reload() {
        Logger.info("Loading permissions from disk...");
        try {
            permissions = loadConfig(new Permissions(),
                    Permissions.class, new File(configDir, FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
