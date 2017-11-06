package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.util.PlayerUtil;
import net.pl3x.forge.util.teleport.Teleport;
import net.pl3x.forge.util.teleport.TeleportRequestTPA;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CmdTPA extends CommandBase {
    public CmdTPA() {
        super("tpa", "Request to teleport to a player");
        addAlias("tprequest");
        addAlias("teleportrequest");
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

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        if (args.length < 1) {
            Lang.send(player, Lang.INSTANCE.data.MUST_SPECIFY_PLAYER);
            return;
        }

        EntityPlayerMP target = PlayerUtil.getPlayer(args[0]);
        if (target == null) {
            Lang.send(player, Lang.INSTANCE.data.PLAYER_NOT_ONLINE);
            return;
        }

        if (Teleport.TELEPORT_REQUESTS.get(target.getUniqueID()) != null) {
            Lang.send(player, Lang.INSTANCE.data.TELEPORT_REQUEST_TARGET_HAS_PENDING
                    .replace("{target}", target.getName()));
            return;
        }

        if (getPlayerData(target).denyTeleports()) {
            Lang.send(player, Lang.INSTANCE.data.TELEPORT_REQUEST_TARGET_TOGGLED_OFF
                    .replace("{target}", target.getName()));
            return;
        }

        Teleport.TELEPORT_REQUESTS.put(target.getUniqueID(),
                new TeleportRequestTPA(player, target));

        Lang.send(player, Lang.INSTANCE.data.TELEPORT_REQUEST_REQUESTER
                .replace("{target}", target.getName()));
        Lang.send(target, Lang.INSTANCE.data.TELEPORT_REQUEST_TPA_TARGET
                .replace("{requester}", player.getName()));
    }
}
