package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.scheduler.Pl3xRunnable;

public class CmdCountdown extends CommandBase {
    public CmdCountdown() {
        super("countdown", "Counts down");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        if (args.length < 1) {
            Lang.send(sender, "&cPlease specify a count");
            return;
        }

        int counter;
        try {
            counter = Integer.valueOf(args[0]);
        } catch (NumberFormatException e) {
            Lang.send(sender, "&cNot a valid number");
            return;
        }

        if (counter < 1 || counter > 10) {
            Lang.send(sender, "&cCount must be between 1 and 10");
            return;
        }

        new Countdown(sender, counter)
                .runTaskTimer(20, 20);
    }

    private class Countdown extends Pl3xRunnable {
        private final ICommandSender sender;
        private int counter;

        private Countdown(ICommandSender sender, int counter) {
            this.sender = sender;
            this.counter = counter;
        }

        @Override
        public void run() {
            Lang.send(sender, "&aCounter&e: &7" + counter);
            counter--;
            if (counter < 0) {
                cancel();
            }
        }

        @Override
        public void cancel() {
            super.cancel();
            Lang.send(sender, "&aCounter cancelled");
        }
    }
}
