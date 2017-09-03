package net.pl3x.forge.core.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pl3x.forge.core.Location;
import net.pl3x.forge.core.util.Lang;
import net.pl3x.forge.core.util.Teleport;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CmdTop extends CommandBase {
    @Override
    public String getName() {
        return "top";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/top: Teleport tp the surface";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName());
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        World world = sender.getEntityWorld();
        if (world.isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        Location location = new Location(player);
        location.setY(player.world.getTopSolidOrLiquidBlock(player.getPosition()).getY());

        Teleport.teleport(player, location, true);
        Lang.send(player, Lang.TELEPORT_TOP);
    }
}
