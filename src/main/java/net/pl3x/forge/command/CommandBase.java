package net.pl3x.forge.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.color.ChatColor;
import net.pl3x.forge.capability.PlayerDataProvider;
import net.pl3x.forge.capability.PlayerData;
import net.pl3x.forge.permissions.Permissions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CommandBase extends net.minecraft.command.CommandBase {
    private final String name;
    private final String usage;
    private final List<String> aliases = new ArrayList<>();

    CommandBase(String name, String usage) {
        this.name = name;
        this.usage = usage;
    }

    void addAlias(String alias) {
        aliases.add(alias);
    }

    PlayerData getPlayerData(EntityPlayerMP player) {
        return player.getCapability(PlayerDataProvider.CAPABILITY, null);
    }

    public String getPermissionNode() {
        return "command." + name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return ChatColor.colorize("&e/&3" + name + "&3: &d" + usage);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(getRequiredPermissionLevel(), getName()) ||
                (sender instanceof EntityPlayerMP &&
                        Permissions.hasPermission((EntityPlayerMP) sender, getPermissionNode()));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }
}
