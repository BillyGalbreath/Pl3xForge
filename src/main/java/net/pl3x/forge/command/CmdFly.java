package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.util.Utils;
import org.apache.commons.lang3.BooleanUtils;

public class CmdFly extends CommandBase {
    public CmdFly() {
        super("fly", "Toggles survival fly mode");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        if (player.interactionManager.getGameType() != GameType.SURVIVAL) {
            Lang.send(player, Lang.INSTANCE.data.FLY_ONLY_SURVIVAL);
            return;
        }

        player.capabilities.allowFlying = !player.capabilities.allowFlying;
        Utils.setFlySpeed(player, 0.1F);

        if (!player.capabilities.allowFlying) {
            // drop out of sky
            player.capabilities.isFlying = false;
            if (!player.onGround) {
                // but prevent fall damage
                player.fallDistance = Integer.MIN_VALUE;
            }
        }

        // inform the client of the changes
        player.sendPlayerAbilities();

        Lang.send(player, Lang.INSTANCE.data.FLY_TOGGLED
                .replace("{state}",
                        BooleanUtils.toStringOnOff(player.capabilities.allowFlying)));
    }
}
