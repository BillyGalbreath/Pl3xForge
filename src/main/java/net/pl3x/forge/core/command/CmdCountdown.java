package net.pl3x.forge.core.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.core.scheduler.Pl3xRunnable;
import net.pl3x.forge.core.util.Lang;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CmdCountdown extends CommandBase {
    @Override
    public String getName() {
        return "countdown";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/countdown: Counts down";
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

    public class Countdown extends Pl3xRunnable {
        private EntityPlayerMP player;
        private int counter;

        public Countdown(EntityPlayerMP player, int counter) {
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
