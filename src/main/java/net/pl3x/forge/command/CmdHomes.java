package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.data.PlayerData;

import java.util.ArrayList;

public class CmdHomes extends CommandBase {
    public CmdHomes() {
        super("homes", "List your homes");
        addAlias("listhomes");
        addAlias("listhome");
        addAlias("homeslist");
        addAlias("homelist");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        World world = sender.getEntityWorld();
        if (world.isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);
        PlayerData playerData = getPlayerData(player);

        ArrayList<String> homeNames = playerData.getHomeNames();
        if (homeNames == null || homeNames.isEmpty()) {
            Lang.send(player, Lang.INSTANCE.data.HOME_NONE_SET);
            return;
        }

        Lang.send(player, Lang.INSTANCE.data.HOME_LIST
                .replace("{homes}", String.join("&7, &d", homeNames)));
    }
}
