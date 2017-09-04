package net.pl3x.forge.core.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class Utils {
    public static MinecraftServer serverInstance = FMLCommonHandler.instance().getMinecraftServerInstance();
    public static PlayerList playerList = serverInstance.getPlayerList();

    public static Collection<String> getOnlinePlayerNames() {
        return Arrays.asList(playerList.getOnlinePlayerNames());
    }

    public static EntityPlayerMP getPlayer(UUID uuid) {
        return playerList.getPlayerByUUID(uuid);
    }

    public static EntityPlayerMP getPlayer(String name) {
        return getPlayer(name, false);
    }

    public static EntityPlayerMP getPlayer(String name, boolean ignoreCase) {
        for (String tempName : getOnlinePlayerNames()) {
            if (ignoreCase) {
                if (tempName.equalsIgnoreCase(name)) {
                    return playerList.getPlayerByUsername(tempName);
                }
            } else if (tempName.equals(name)) {
                return playerList.getPlayerByUsername(tempName);
            }
        }
        return null;
    }
}
