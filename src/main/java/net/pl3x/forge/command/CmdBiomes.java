package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.util.BiomeHelper;

import java.util.List;

public class CmdBiomes extends CommandBase {
    public CmdBiomes() {
        super("biomes", "List biomes you've visited");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        List<String> list = BiomeHelper.getVisitedBiomes(player);
        String biomes = "";
        for (String biome : list) {
            biomes += "&7, " + biome;
        }

        Lang.send(player, "&aAdventuring Time Biomes&7:");
        Lang.send(player, " " + biomes.substring(4));
    }
}
