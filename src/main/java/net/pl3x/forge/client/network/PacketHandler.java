package net.pl3x.forge.client.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.data.PlayerDataProvider;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Pl3xForgeClient.modId);

    public static void init() {
        INSTANCE.registerMessage(BalancePacket.BalanceHandler.class, BalancePacket.class, 0, Side.CLIENT);
    }

    public static void updateBalance(EntityPlayerMP player) {
        double balance = 0;
        try {
            balance = player.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null).getBalance();
        } catch (Exception ignore) {
        }

        INSTANCE.sendTo(new BalancePacket(balance), player);
    }
}
