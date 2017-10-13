package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.data.PlayerData;
import org.apache.commons.lang3.BooleanUtils;

public class CmdTPToggle extends CommandBase {
    public CmdTPToggle() {
        super("tptoggle", "Toggle teleport requests");
        addAlias("tpt");
        addAlias("teleporttoggle");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        PlayerData playerData = getPlayerData(player);
        playerData.denyTeleports(!playerData.denyTeleports());

        Lang.send(player, Lang.getData().TELEPORT_REQUEST_TP_TOGGLE
                .replace("{state}", BooleanUtils.toStringOnOff(!playerData.denyTeleports())));
    }
}
