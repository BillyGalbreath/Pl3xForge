package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pl3x.forge.Location;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.data.PlayerData;
import net.pl3x.forge.util.Teleport;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CmdHome extends CommandBase {
    public CmdHome() {
        super("home", "Teleport to a home");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            try {
                return getPlayerData(getCommandSenderAsPlayer(sender))
                        .getHomeNames().stream()
                        .filter(name -> name.startsWith(args[0].toLowerCase()))
                        .collect(Collectors.toList());
            } catch (PlayerNotFoundException ignore) {
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        World world = sender.getEntityWorld();
        if (world.isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);
        PlayerData playerData = getPlayerData(player);

        String homeName = args.length > 0 ? args[0].toLowerCase() : "home";
        Location homeLoc = playerData.getHome(homeName);
        if (homeLoc == null) {
            Lang.send(player, Lang.INSTANCE.data.HOME_NOT_FOUND);
            return;
        }

        if (homeName.equals("bed") && homeLoc.getWorld().getBlockState(homeLoc.getBlockPos()).getBlock() != Blocks.BED) {
            Lang.send(player, Lang.INSTANCE.data.HOME_BED_MISSING);
            return;
        }

        Teleport.teleport(player, homeLoc);
        Lang.send(player, Lang.INSTANCE.data.HOME_TELEPORTED);
    }
}
