package net.pl3x.forge.core.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.core.Location;
import net.pl3x.forge.core.util.Lang;
import net.pl3x.forge.core.util.Teleport;

public class CmdSpawn extends CommandBase {
    public CmdSpawn() {
        super("spawn", "Go to server spawn");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        Teleport.teleport(player, new Location(player.world, player.world.getSpawnPoint()), true);
        Lang.send(player, Lang.SPAWN_TELEPORT);
    }
}
