package net.pl3x.forge.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.data.CapabilityProvider;
import net.pl3x.forge.data.PlayerData;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Pl3x.modId);

    public static void init() {
        INSTANCE.registerMessage(PlayerDataPacket.Handler.class, PlayerDataPacket.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(BankPacket.Handler.class, BankPacket.class, 2, Side.SERVER);
        INSTANCE.registerMessage(BankFailedPacket.Handler.class, BankFailedPacket.class, 3, Side.CLIENT);
        INSTANCE.registerMessage(ShopChangePacket.Handler.class, ShopChangePacket.class, 4, Side.SERVER);
        INSTANCE.registerMessage(ShopUpdateClientPacket.Handler.class, ShopUpdateClientPacket.class, 5, Side.CLIENT);
        INSTANCE.registerMessage(ShopClientRequestPacket.Handler.class, ShopClientRequestPacket.class, 6, Side.SERVER);
        INSTANCE.registerMessage(ShopPurchasePacket.Handler.class, ShopPurchasePacket.class, 7, Side.SERVER);
    }

    public static void updatePlayerData(EntityPlayerMP player) {
        PlayerData data = player.getCapability(CapabilityProvider.CAPABILITY, null);
        INSTANCE.sendTo(new PlayerDataPacket(data), player);
    }
}
