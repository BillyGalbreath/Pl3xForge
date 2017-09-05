package net.pl3x.forge.core.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.core.Pl3xCore;
import net.pl3x.forge.core.util.Lang;

public class CmdTPS extends CommandBase {
    public CmdTPS() {
        super("tps", "Check server TPS");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        double tps = Pl3xCore.getTpsTracker().getTPS();

        String color;
        if (tps > 19D) {
            color = "&a";
        } else if (tps > 18D) {
            color = "&e";
        } else if (tps > 15D) {
            color = "&6";
        } else {
            color = "&c";
        }

        Lang.send(player, Lang.TPS_TRACKER
                .replace("{tps}", color + String.format("%.2f", tps)));
    }
}
