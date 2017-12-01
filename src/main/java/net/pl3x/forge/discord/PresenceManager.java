package net.pl3x.forge.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class PresenceManager {
    public static final PresenceManager INSTANCE = new PresenceManager();

    public void init() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = this::ready;
        handlers.errored = this::error;
        handlers.disconnected = this::disconnected;
        DiscordRPC.INSTANCE.Discord_Initialize("386105933794902017", handlers, true, null);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                DiscordRPC.INSTANCE.Discord_RunCallbacks();
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException ex) {
                    DiscordRPC.INSTANCE.Discord_Shutdown();
                }
            }
        }, "CallbackHandler").start();
    }

    private void error(final int code, @Nullable final String text) {
        System.out.println("Error while connecting to DiscordRPC, Code: " + code + " Error: " + text);
    }

    private void disconnected(final int code, @Nullable final String text) {
        System.out.println("Disconnected from DiscordRPC, Code: " + code + " Error: " + text);
    }

    private void ready() {
        System.out.println("Connected to DiscordRPC");
    }

    @SideOnly(Side.CLIENT)
    public void update(boolean isLocal) {
        final DiscordRichPresence presence = new DiscordRichPresence();
        presence.largeImageKey = "minecraft";
        presence.largeImageText = "Minecraft " + ForgeVersion.mcVersion;
        ServerData data = Minecraft.getMinecraft().getCurrentServerData();
        if (data != null) {
            if (data.serverIP.contains("pl3x")) {
                presence.smallImageKey = "pl3x";
                presence.smallImageText = "Pl3x";
                presence.details = "Playing on Pl3x";
            } else {
                presence.smallImageKey = "multi_player";
                presence.smallImageText = data.serverName;
                presence.details = data.serverName;
            }
            presence.startTimestamp = System.currentTimeMillis() / 1000L;
        } else {
            if (isLocal) {
                presence.details = "Single Player";
                presence.smallImageKey = "single_player";
                presence.smallImageText = "Single Player";
                presence.startTimestamp = System.currentTimeMillis() / 1000L;
            } else {
                presence.details = "AFK";
            }
        }
        updatePresence(presence);
    }

    public static void updatePresence(final DiscordRichPresence p) {
        DiscordRPC.INSTANCE.Discord_UpdatePresence(p);
    }
}
