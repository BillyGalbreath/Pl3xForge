package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.util.TPSTracker;

public class CmdTPS extends CommandBase {
    public CmdTPS() {
        super("tps", "Check server TPS");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("now")) {
            Lang.send(sender, Lang.INSTANCE.data.TPS_TRACKER_NOW
                    .replace("{tps}", colorTPS(TPSTracker.INSTANCE.getTPS())));
            return;
        }

        double tps1 = TPSTracker.INSTANCE.tps1.getAverage();
        double tps5 = TPSTracker.INSTANCE.tps5.getAverage();
        double tps15 = TPSTracker.INSTANCE.tps15.getAverage();
        Lang.send(sender, Lang.INSTANCE.data.TPS_TRACKER_AVERAGES
                .replace("{tps1}", colorTPS(tps1))
                .replace("{tps5}", colorTPS(tps5))
                .replace("{tps15}", colorTPS(tps15)));
    }

    private String colorTPS(double tps) {
        String color = "&a"; // green
        if (tps < 15D) {
            color = "&c"; // red
        } else if (tps < 18D) {
            color = "&6"; // gold/orange
        } else if (tps < 19D) {
            color = "&e"; // yellow
        }
        return color + String.format("%.2f", tps);
    }
}
