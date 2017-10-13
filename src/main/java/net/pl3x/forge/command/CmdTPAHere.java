package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.util.Teleport;
import net.pl3x.forge.util.TeleportRequestTPAHere;
import net.pl3x.forge.util.Utils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CmdTPAHere extends CommandBase {
    public CmdTPAHere() {
        super("tpahere", "Request player to teleport to you");
        addAlias("tpah");
        addAlias("teleporthere");
        addAlias("teleporthererequest");
        addAlias("teleportrequesthere");
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

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        if (args.length < 1) {
            Lang.send(player, Lang.getData().MUST_SPECIFY_PLAYER);
            return;
        }

        EntityPlayerMP target = Utils.getPlayer(args[0]);
        if (target == null) {
            Lang.send(player, Lang.getData().PLAYER_NOT_ONLINE);
            return;
        }

        if (Teleport.TELEPORT_REQUESTS.get(target.getUniqueID()) != null) {
            Lang.send(player, Lang.getData().TELEPORT_REQUEST_TARGET_HAS_PENDING
                    .replace("{target}", target.getName()));
            return;
        }

        if (getPlayerData(target).denyTeleports()) {
            Lang.send(player, Lang.getData().TELEPORT_REQUEST_TARGET_TOGGLED_OFF
                    .replace("{target}", target.getName()));
            return;
        }

        Teleport.TELEPORT_REQUESTS.put(target.getUniqueID(),
                new TeleportRequestTPAHere(player, target));

        Lang.send(player, Lang.getData().TELEPORT_REQUEST_REQUESTER
                .replace("{target}", target.getName()));
        Lang.send(target, Lang.getData().TELEPORT_REQUEST_TPAHERE_TARGET
                .replace("{requester}", player.getName()));
    }
}
