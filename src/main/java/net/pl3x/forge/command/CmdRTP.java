package net.pl3x.forge.command;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.scheduler.Pl3xRunnable;
import net.pl3x.forge.util.Location;
import net.pl3x.forge.util.teleport.Teleport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CmdRTP extends CommandBase {
    private final Map<UUID, Cooldown> cooldowns = new HashMap<>();

    public CmdRTP() {
        super("rtp", "Teleport to a random location");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        if (player.dimension != 0) {
            Lang.send(player, Lang.INSTANCE.data.RTP_OVERWORLD_ONLY);
            return;
        }

        Cooldown cooldown = cooldowns.get(player.getUniqueID());
        if (cooldown != null) {
            Lang.send(player, Lang.INSTANCE.data.COMMAND_ON_COOLDOWN
                    .replace("{timeleft}", Integer.toString(cooldown.seconds)));
            return;
        }

        Location destination;
        int failSafe = 100; // max number of tries
        while ((destination = checkSpot(player)) == null && failSafe > 0) {
            failSafe--;
        }
        if (destination == null) {
            Lang.send(player, Lang.INSTANCE.data.RTP_NO_SAFE_LOCATION);
            return;
        }

        cooldown = new Cooldown(player.getUniqueID());
        cooldown.runTaskTimer(20, 20);

        cooldowns.put(player.getUniqueID(), cooldown);

        Teleport.teleport(player, destination);
        Lang.send(player, Lang.INSTANCE.data.RTP_SUCCESS);
    }

    private Location checkSpot(EntityPlayerMP player) {
        BlockPos pos = calculatePos(player.dimension);
        if (!player.world.getWorldBorder().contains(pos)) {
            return null;
        }
        IBlockState ground = player.world.getBlockState(pos);
        IBlockState lower = player.world.getBlockState(pos.up());
        IBlockState upper = player.world.getBlockState(pos.up(2));
        // If the block we're standing on is air or liquid or if the two blocks
        // we take up are liquid then return null, check failed.
        if (isValid(ground, lower, upper)) {
            return new Location(player.world, pos).add(0.5, 0, 0.5);
        }
        if (ground.getMaterial().isLiquid()) {
            return null;
        }
        while (!isValid(ground, lower, upper) && pos.getY() > 0) {
            // Make a new position of y+1
            pos = pos.down();
            ground = player.world.getBlockState(pos);
            lower = player.world.getBlockState(pos.up());
            upper = player.world.getBlockState(pos.up(2));
            if (ground.getMaterial().isLiquid()) {
                return null;
            }
        }
        return new Location(player.world, pos.up(2)).add(0.5, 0, 0.5);
    }

    private BlockPos calculatePos(int dimension) {
        return new BlockPos(ThreadLocalRandom.current().nextInt(50000) - 25000,
                dimension == -1 ? 120 : 250, ThreadLocalRandom.current().nextInt(50000) - 25000);
    }

    private boolean isValid(IBlockState ground, IBlockState lower, IBlockState upper) {
        return ground.isNormalCube() &&
                validMaterial(lower.getMaterial()) &&
                validMaterial(upper.getMaterial());
    }

    private boolean validMaterial(Material material) {
        return !material.isSolid() && !material.isLiquid();
    }

    private class Cooldown extends Pl3xRunnable {
        private final UUID uuid;
        private int seconds;

        private Cooldown(UUID uuid) {
            this.uuid = uuid;
            this.seconds = 60 * 5; // 5 minutes
        }

        @Override
        public void run() {
            seconds--;

            if (seconds < 0) {
                cooldowns.remove(uuid);
                cancel();
            }
        }
    }
}
