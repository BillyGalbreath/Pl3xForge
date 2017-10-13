package net.pl3x.forge.client.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class PlayerUtil {
    private static MinecraftServer serverInstance = FMLCommonHandler.instance().getMinecraftServerInstance();

    public static boolean isSinglePlayer() {
        return serverInstance != null && serverInstance.isSinglePlayer();
    }

    public static boolean isOp(EntityPlayer player) {
        return isSinglePlayer() || serverInstance.getPlayerList().getOppedPlayers()
                .getPermissionLevel(player.getGameProfile()) > 0;
    }

    public static GameProfile getCachedProfile(UUID uuid) {
        return serverInstance.getPlayerProfileCache().getProfileByUUID(uuid);
    }
}
