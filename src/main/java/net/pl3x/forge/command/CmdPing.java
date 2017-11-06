package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.util.PlayerUtil;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CmdPing extends CommandBase {
    public CmdPing() {
        super("ping", "View player ping");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return PlayerUtil.getOnlinePlayerNames().stream()
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
            target = PlayerUtil.getPlayer(args[0]);
            if (target == null) {
                Lang.send(sender, Lang.INSTANCE.data.PLAYER_NOT_ONLINE);
                return;
            }
        }

        int ping = target.ping;
        String color = "&a"; // green
        if (ping > 500) {
            color = "&c"; // red
        } else if (ping > 200) {
            color = "&6"; // gold/orange
        } else if (ping > 100) {
            color = "&e"; // yellow
        }

        Lang.send(sender, Lang.INSTANCE.data.PING
                .replace("{target}", target.getName())
                .replace("{ping}", color + ping));
    }
}
