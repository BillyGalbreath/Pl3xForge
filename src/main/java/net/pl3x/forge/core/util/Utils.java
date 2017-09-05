package net.pl3x.forge.core.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class Utils {
    public static MinecraftServer serverInstance = FMLCommonHandler.instance().getMinecraftServerInstance();
    public static PlayerList playerList = serverInstance.getPlayerList();

    public static Collection<String> getOnlinePlayerNames() {
        return Arrays.asList(playerList.getOnlinePlayerNames());
    }

    public static Collection<EntityPlayerMP> getPlayers() {
        return playerList.getPlayers();
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

    public static boolean isOp(EntityPlayerMP player) {
        return playerList.getOppedPlayers().getEntry(player.getGameProfile()) != null;
    }

    public static void setFlySpeed(EntityPlayerMP player, float speed) {
        PlayerCapabilities capabilities = player.capabilities;
        try {
            Field flySpeed = capabilities.getClass().getDeclaredField("field_75096_f");
            flySpeed.setAccessible(true);
            flySpeed.set(capabilities, speed / 2F);
        } catch (IllegalAccessException | NoSuchFieldException ignore) {
        }
    }
}
