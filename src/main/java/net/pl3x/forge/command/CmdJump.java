package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.pl3x.forge.Location;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.util.Teleport;
import net.pl3x.forge.util.Utils;

public class CmdJump extends CommandBase {
    public CmdJump() {
        super("jump", "Teleport to block you're looking at");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        World world = sender.getEntityWorld();
        if (world.isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        RayTraceResult result = Utils.getLineOfSight(player, 120);
        if (result == null) {
            Lang.send(player, Lang.INSTANCE.data.JUMP_TOO_FAR);
            return;
        }

        Location location = new Location(player.world, result.getBlockPos());
        location.setPitch(player.rotationPitch);
        location.setYaw(player.rotationYaw);
        location = location.add(0.5, 1, 0.5);

        Teleport.teleport(player, location, true);
        Lang.send(player, Lang.INSTANCE.data.JUMP_TELEPORT);
    }
}
