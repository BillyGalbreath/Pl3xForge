package net.pl3x.forge.client.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.data.CapabilityProvider;
import net.pl3x.forge.client.data.PlayerData;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Pl3xForgeClient.modId);

    public static void init() {
        INSTANCE.registerMessage(PlayerDataPacket.Handler.class, PlayerDataPacket.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(BankPacket.Handler.class, BankPacket.class, 2, Side.SERVER);
        INSTANCE.registerMessage(BankFailedPacket.Handler.class, BankFailedPacket.class, 3, Side.CLIENT);
    }

    public static void updatePlayerData(EntityPlayerMP player) {
        PlayerData data = player.getCapability(CapabilityProvider.CAPABILITY, null);
        INSTANCE.sendTo(new PlayerDataPacket(data), player);
    }
}
