package net.pl3x.forge.core.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.core.data.PlayerData;
import net.pl3x.forge.core.data.PlayerDataProvider;
import net.pl3x.forge.core.util.Lang;

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
        return player.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null);
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
        return Lang.colorize("&e/&3" + name + "&3: &d" + usage);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }
}
