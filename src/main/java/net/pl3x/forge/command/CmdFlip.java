package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.util.Utils;

public class CmdFlip extends CommandBase {
    public CmdFlip() {
        super("flip", "Flip a table");
        addAlias("tableflip");
        addAlias("fliptable");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        String message = "(╯°□°）╯︵ ┻━┻";
        if (args.length > 0) {
            message = String.join(" ", args) + " " + message;
        }

        Utils.chat(player, message);
    }
}
