package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.permissions.Permissions;
import net.pl3x.forge.util.Utils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CmdGMAdventure extends CommandBase {
    public CmdGMAdventure() {
        super("adventure", "Change gamemode to adventure");
        addAlias("gma");
    }

    public String getPermissionNode() {
        return "command.gamemode." + getName();
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return Utils.getOnlinePlayerNames().stream()
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP target;
        if (args.length < 1) {
            if (!(sender instanceof EntityPlayerMP)) {
                Lang.send(sender, Lang.INSTANCE.data.MUST_SPECIFY_PLAYER);
                return;
            }
            target = getCommandSenderAsPlayer(sender);
        } else {
            target = Utils.getPlayer(args[0]);
            if (target == null) {
                Lang.send(sender, Lang.INSTANCE.data.PLAYER_NOT_ONLINE);
                return;
            }

            if (!Permissions.hasPermission(target, getPermissionNode())) {
                Lang.send(sender, Lang.INSTANCE.data.TARGET_NO_PERMISSION);
                return;
            }
        }

        target.setGameType(GameType.ADVENTURE);

        if (!target.equals(sender)) {
            Lang.send(sender, Lang.INSTANCE.data.GAMEMODE_SENDER
                    .replace("{target}", target.getName())
                    .replace("{gamemode}", getName()));
        }
        Lang.send(target, Lang.INSTANCE.data.GAMEMODE_TARGET
                .replace("{gamemode}", getName()));
    }
}
