package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.configuration.Lang;

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

        if (args.length == 0) {
            Lang.send(player, Lang.INSTANCE.data.FLY_SPEED_INVALID);
            return;
        }

        float speed;
        try {
            speed = Float.valueOf(args[0]);
        } catch (NumberFormatException e1) {
            try {
                speed = Integer.valueOf(args[0]);
            } catch (NumberFormatException e2) {
                Lang.send(player, Lang.INSTANCE.data.FLY_SPEED_INVALID);
                return;
            }
        }

        if (speed < 0F || speed > 10F) {
            Lang.send(player, Lang.INSTANCE.data.FLY_SPEED_INVALID);
            return;
        }

        // set new fly speed (/ 10 / 2)
        player.capabilities.flySpeed = speed / 20F;

        // inform the client of the changes
        player.sendPlayerAbilities();

        Lang.send(player, Lang.INSTANCE.data.FLY_SPEED_SET
                .replace("{speed}", Float.toString(speed)));
    }
}
