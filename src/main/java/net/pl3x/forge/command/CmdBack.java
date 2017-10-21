package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.Location;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.util.Teleport;

public class CmdBack extends CommandBase {
    public CmdBack() {
        super("back", "Teleport to your last location");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        Location backLoc = Teleport.BACK_DB.get(player.getUniqueID());
        if (backLoc == null) {
            Lang.send(player, Lang.INSTANCE.data.BACK_NO_LOCATION);
            return;
        }

        Teleport.teleport(player, backLoc, true);
        Lang.send(player, Lang.INSTANCE.data.BACK_TO_PREVIOUS);
    }
}
