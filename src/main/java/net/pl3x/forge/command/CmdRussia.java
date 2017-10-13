package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.util.Utils;

public class CmdRussia extends CommandBase {
    public CmdRussia() {
        super("russia", "In mother russia...");
        addAlias("russiaflip");
        addAlias("fliprussia");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        String message = "ノ┬─┬ノ ︵ ( \\o°o)\\";
        if (args.length > 0) {
            message = String.join(" ", args) + " " + message;
        }

        Utils.chat(player, message);
    }
}
