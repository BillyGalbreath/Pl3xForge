package net.pl3x.forge.core.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.core.data.PlayerData;
import net.pl3x.forge.core.util.Lang;
import org.apache.commons.lang3.BooleanUtils;

public class CmdFly extends CommandBase {
    public CmdFly() {
        super("fly", "Toggles fly mode");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        PlayerData playerData = getPlayerData(player);
        playerData.flyEnabled(!playerData.flyEnabled());

        if (playerData.flyEnabled()) {
            player.capabilities.allowFlying = true;
            player.capabilities.setFlySpeed(playerData.flySpeed());
        } else {
            player.capabilities.setFlySpeed(0.1F);
            player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
            player.fallDistance = -256F; // prevent falling to death
        }

        Lang.send(player, Lang.FLY_TOGGLED
                .replace("{state}", BooleanUtils.toStringOnOff(playerData.flyEnabled())));
    }
}
