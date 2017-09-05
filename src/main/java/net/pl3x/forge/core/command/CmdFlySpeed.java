package net.pl3x.forge.core.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.core.util.Lang;
import net.pl3x.forge.core.util.Utils;

public class CmdFlySpeed extends CommandBase {
    public CmdFlySpeed() {
        super("flyspeed", "Change your flight speed");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        float speed;
        try {
            speed = Float.valueOf(args[0]);
        } catch (NumberFormatException e1) {
            try {
                speed = Integer.valueOf(args[0]);
            } catch (NumberFormatException e2) {
                Lang.send(player, Lang.FLY_SPEED_INVALID);
                return;
            }
        }

        if (speed < 0F || speed > 10F) {
            Lang.send(player, Lang.FLY_SPEED_INVALID);
            return;
        }

        // set new fly speed
        Utils.setFlySpeed(player, speed / 10F);

        // inform the client of the changes
        player.sendPlayerAbilities();

        Lang.send(player, Lang.FLY_SPEED_SET
                .replace("{speed}", Float.toString(speed)));
    }
}
