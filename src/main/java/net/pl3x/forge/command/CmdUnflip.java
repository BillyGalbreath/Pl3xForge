package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.util.PlayerUtil;

public class CmdUnflip extends CommandBase {
    public CmdUnflip() {
        super("unflip", "Unflip a table");
        addAlias("unfliptable");
        addAlias("tableunflip");
        addAlias("replace");
        addAlias("replacetable");
        addAlias("tablereplace");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        String message = "┬─┬ ノ( ゜-゜ノ)";
        if (args.length > 0) {
            message = String.join(" ", args) + " " + message;
        }

        PlayerUtil.chat(player, message);
    }
}
