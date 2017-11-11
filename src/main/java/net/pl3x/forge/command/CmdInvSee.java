package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.network.OpenInventoryPacket;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.inventory.InventoryPlayer;
import net.pl3x.forge.util.PlayerUtil;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CmdInvSee extends CommandBase {
    public CmdInvSee() {
        super("invsee", "View another player's inventory");
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

        if (args.length == 0) {
            Lang.send(sender, Lang.INSTANCE.data.MUST_SPECIFY_PLAYER);
            return;
        }

        EntityPlayerMP target = PlayerUtil.getPlayer(args[0]);
        if (target == null) {
            Lang.send(sender, Lang.INSTANCE.data.PLAYER_NOT_FOUND);
            return;
        }

        if (target == player) {
            PacketHandler.INSTANCE.sendTo(new OpenInventoryPacket(), player);
            return;
        }

        if (player.openContainer != player.inventoryContainer) {
            player.closeScreen();
        }
        player.getNextWindowId();
        player.displayGUIChest(new InventoryPlayer(target, player));
    }
}
