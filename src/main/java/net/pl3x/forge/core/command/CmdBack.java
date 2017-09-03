package net.pl3x.forge.core.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.core.Location;
import net.pl3x.forge.core.util.Lang;
import net.pl3x.forge.core.util.Teleport;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CmdBack extends CommandBase {
    @Override
    public String getName() {
        return "back";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/back: Teleport to your last location";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        Location backLoc = Teleport.BACK_DB.get(player.getUniqueID());
        if (backLoc == null) {
            Lang.send(player, Lang.BACK_NO_LOCATION);
            return;
        }

        Teleport.teleport(player, backLoc, true);
        Lang.send(player, Lang.BACK_TO_PREVIOUS);
    }
}
