package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.pl3x.forge.Location;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.util.Teleport;

public class CmdTop extends CommandBase {
    public CmdTop() {
        super("top", "Teleport to the surface");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        World world = sender.getEntityWorld();
        if (world.isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        Location location = new Location(player);
        location.setY(player.world.getTopSolidOrLiquidBlock(player.getPosition()).getY());

        Teleport.teleport(player, location, true);
        Lang.send(player, Lang.INSTANCE.data.TOP_TELEPORT);
    }
}
