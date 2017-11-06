package net.pl3x.forge.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class PlayerUtil {
    private static MinecraftServer serverInstance = FMLCommonHandler.instance().getMinecraftServerInstance();
    private static PlayerList playerList = serverInstance.getPlayerList();

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

    public static Collection<String> getOnlinePlayerNames() {
        return Arrays.asList(playerList.getOnlinePlayerNames());
    }

    public static Collection<EntityPlayerMP> getPlayers() {
        return new ArrayList<>(playerList.getPlayers());
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

    public static void chat(EntityPlayerMP player, String message) {
        ServerChatEvent event = new ServerChatEvent(player, message,
                new TextComponentTranslation("chat.type.text",
                        player.getDisplayName(), ForgeHooks.newChatWithLinks(message)));
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return; // event cancelled
        }
        playerList.sendMessage(event.getComponent(), false);
    }

    public static UUID getUUIDFromName(String name) {
        for (Map.Entry<UUID, String> entry : UsernameCache.getMap().entrySet()) {
            if (entry.getValue().toLowerCase().equalsIgnoreCase(name.toLowerCase())) {
                return entry.getKey();
            }
        }
        return null;
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

    // borrowed from ForgeEssentials
    public static RayTraceResult getLineOfSight(EntityPlayerMP player, double maxDistance) {
        Vec3d lookAt = player.getLook(1);
        Vec3d playerPos = new Vec3d(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ);
        Vec3d pos1 = playerPos.addVector(0, player.getEyeHeight(), 0);
        Vec3d pos2 = pos1.addVector(lookAt.x * maxDistance, lookAt.y * maxDistance, lookAt.z * maxDistance);
        return player.world.rayTraceBlocks(pos1, pos2);
    }
}
