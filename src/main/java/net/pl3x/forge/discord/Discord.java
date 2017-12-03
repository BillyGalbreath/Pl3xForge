package net.pl3x.forge.discord;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Discord {
    @SideOnly(Side.CLIENT)
    public static void loadLibs() {
        String OS = System.getProperty("os.name").toLowerCase();
        try {
            if (OS.contains("win")) {
                String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
                if (System.getenv("PROCESSOR_ARCHITECTURE").endsWith("64") ||
                        (wow64Arch != null && wow64Arch.endsWith("64"))) {
                    loadFile("win32-x86-64/discord-rpc.dll");
                } else {
                    loadFile("win32-x86/discord-rpc.dll");
                }
            }
            if (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0) {
                loadFile("linux-x86-64/libdiscord-rpc.so");
            }
            if (OS.contains("mac")) {
                loadFile("darwin/libdiscord-rpc.dylib");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SideOnly(Side.CLIENT)
    private static void loadFile(final String name) throws IOException {
        Pl3x.configDir.mkdirs();
        final File lib = new File(Pl3x.configDir, new File(MinecraftServer.class.getResource("/" + name).getPath()).getName());
        if (!lib.exists()) {
            lib.createNewFile();
            final InputStream in = MinecraftServer.class.getResourceAsStream("/" + name);
            final FileOutputStream fos = new FileOutputStream(lib);
            IOUtils.copy(in, fos);
            fos.close();
            in.close();
        }
        System.load(lib.getAbsolutePath());
    }
}
