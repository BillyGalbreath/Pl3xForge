package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.configuration.BigHeadConfig;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.network.BigHeadPacket;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.util.Utils;

import java.util.Set;
import java.util.UUID;

public class CmdBigHead extends CommandBase {
    public CmdBigHead() {
        super("bighead", "Toggle BigHead mode for a player");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        if (args.length == 0) {
            Lang.send(sender, Lang.INSTANCE.data.MUST_SPECIFY_PLAYER);
            return;
        }

        EntityPlayerMP target = Utils.getPlayer(args[0]);
        UUID uuid;
        if (target == null) {
            uuid = Utils.getUUIDFromName(args[0]);
            if (uuid == null) {
                Lang.send(sender, Lang.INSTANCE.data.PLAYER_NOT_FOUND);
                return;
            }
        } else {
            uuid = target.getUniqueID();
        }

        Set<UUID> bigHeads = BigHeadConfig.INSTANCE.data.getBigHeads();
        if (bigHeads.contains(uuid)) {
            bigHeads.remove(uuid);
            Lang.send(sender, Lang.INSTANCE.data.BIGHEAD_DISABLED);
        } else {
            bigHeads.add(uuid);
            Lang.send(sender, Lang.INSTANCE.data.BIGHEAD_ENABLED);
        }

        BigHeadConfig.INSTANCE.data.setBighead(bigHeads);
        BigHeadConfig.INSTANCE.save();

        PacketHandler.INSTANCE.sendToAll(new BigHeadPacket(bigHeads));
    }
}
