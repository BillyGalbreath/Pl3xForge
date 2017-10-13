package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.util.Teleport;
import net.pl3x.forge.util.TeleportRequest;

public class CmdTPDeny extends CommandBase {
    public CmdTPDeny() {
        super("tpdeny", "Deny teleport request");
        addAlias("tpno");
        addAlias("teleportdeny");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        TeleportRequest request = Teleport.TELEPORT_REQUESTS.get(player.getUniqueID());
        if (request == null) {
            Lang.send(player, Lang.getData().TELEPORT_REQUEST_NONE_PENDING);
            return;
        }

        request.deny();
    }
}
