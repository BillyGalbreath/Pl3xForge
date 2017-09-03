package net.pl3x.forge.core.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pl3x.forge.core.Location;
import net.pl3x.forge.core.data.IPlayerData;
import net.pl3x.forge.core.data.PlayerDataProvider;
import net.pl3x.forge.core.util.Lang;
import net.pl3x.forge.core.util.Teleport;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CmdHome extends CommandBase {
    @Override
    public String getName() {
        return "home";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/home: Teleport to a home";
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
        World world = sender.getEntityWorld();
        if (world.isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        IPlayerData playerData = player.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null);
        String homeName = args.length > 0 ? args[0].toLowerCase() : "home";
        Location homeLoc = playerData.getHome(homeName);
        if (homeLoc == null) {
            Lang.send(player, Lang.HOME_NOT_FOUND);
            return;
        }

        Teleport.teleport(player, homeLoc);
        Lang.send(player, Lang.HOME_TELEPORTED);
    }
}
