package net.pl3x.forge.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.pl3x.forge.Location;

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
            FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
                    .transferPlayerToDimension(player, loc.getDimension(),
                            new SimpleTeleporter(loc.getWorldServer()));
        }
        if (center) {
            player.connection.setPlayerLocation(loc.getBlockX() + 0.5D, loc.getBlockY(), loc.getBlockZ() + 0.5D, (float) loc.getYaw(), (float) loc.getPitch());
            //player.setPositionAndUpdate(loc.getBlockX() + 0.5D, loc.getBlockY(), loc.getBlockZ() + 0.5D);
        } else {
            player.connection.setPlayerLocation(loc.getX(), loc.getY(), loc.getZ(), (float) loc.getYaw(), (float) loc.getPitch());
            //player.setPositionAndUpdate(loc.getX(), loc.getY(), loc.getZ());
        }
    }

    public static class SimpleTeleporter extends Teleporter {
        public SimpleTeleporter(WorldServer world) {
            super(world);
        }

        @Override
        public void placeInPortal(Entity entity, float yaw) {
            int i = MathHelper.floor(entity.posX);
            int j = MathHelper.floor(entity.posY) - 1;
            int k = MathHelper.floor(entity.posZ);
            entity.setLocationAndAngles(i, j, k, entity.rotationYaw, 0.0F);
        }

        @Override
        public void removeStalePortalLocations(long totalWorldTime) {
            /* do nothing */
        }

        @Override
        public boolean placeInExistingPortal(Entity entity, float yaw) {
            placeInPortal(entity, yaw);
            return true;
        }
    }
}
