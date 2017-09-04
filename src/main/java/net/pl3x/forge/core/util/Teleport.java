package net.pl3x.forge.core.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.pl3x.forge.core.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Teleport {
    public static final Map<UUID, Location> BACK_DB = new HashMap<>();
    public static final Map<UUID, TeleportRequest> TELEPORT_REQUESTS = new HashMap<>();

    public static void teleport(EntityPlayerMP player, Location loc) {
        teleport(player, loc, false);
    }

    public static void teleport(EntityPlayerMP player, Location loc, boolean center) {
        loc.getWorld().playSound(loc.getX(), loc.getY(), loc.getZ(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.BLOCKS, 1F, 1F, false);
        player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.BLOCKS, 1F, 1F, false);

        BACK_DB.put(player.getUniqueID(), new Location(player));
        if (loc.getDimension() != player.dimension) {
            player.changeDimension(loc.getDimension());
        }
        if (center) {
            player.connection.setPlayerLocation(loc.getBlockX() + 0.5D, loc.getBlockY(), loc.getBlockZ() + 0.5D, (float) loc.getYaw(), (float) loc.getPitch());
            //player.setPositionAndUpdate(loc.getBlockX() + 0.5D, loc.getBlockY(), loc.getBlockZ() + 0.5D);
        } else {
            player.connection.setPlayerLocation(loc.getX(), loc.getY(), loc.getZ(), (float) loc.getYaw(), (float) loc.getPitch());
            //player.setPositionAndUpdate(loc.getX(), loc.getY(), loc.getZ());
        }
    }
}
