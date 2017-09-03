package net.pl3x.forge.core.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.core.scheduler.Pl3xRunnable;
import net.pl3x.forge.core.util.Lang;

public class CmdCountdown extends CommandBase {
    public CmdCountdown() {
        super("countdown", "Counts down");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        if (args.length < 1) {
            Lang.send(player, "&cPlease specify a count");
            return;
        }

        int counter;
        try {
            counter = Integer.valueOf(args[0]);
        } catch (NumberFormatException e) {
            Lang.send(player, "&cNot a valid number");
            return;
        }

        if (counter < 1 || counter > 10) {
            Lang.send(player, "&cCount must be between 1 and 10");
            return;
        }

        new Countdown(player, counter)
                .runTaskTimer(20, 20);
    }

    private class Countdown extends Pl3xRunnable {
        private EntityPlayerMP player;
        private int counter;

        private Countdown(EntityPlayerMP player, int counter) {
            this.player = player;
            this.counter = counter;
        }

        @Override
        public void run() {
            Lang.send(player, "&aCounter&e: &7" + counter);
            counter--;
            if (counter < 0) {
                cancel();
            }
        }

        @Override
        public void cancel() {
            super.cancel();
            Lang.send(player, "&aCounter cancelled");
        }
    }
}
