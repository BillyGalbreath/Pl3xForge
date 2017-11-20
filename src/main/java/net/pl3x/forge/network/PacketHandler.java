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
        INSTANCE.registerMessage(TrafficLightControlBoxUpdatePacket.Handler.class, TrafficLightControlBoxUpdatePacket.class, 8, Side.CLIENT);
        INSTANCE.registerMessage(BigHeadPacket.Handler.class, BigHeadPacket.class, 9, Side.CLIENT);
        INSTANCE.registerMessage(ArmorStandChangePacket.Handler.class, ArmorStandChangePacket.class, 10, Side.SERVER);
        INSTANCE.registerMessage(ArmorStandRefreshPacket.Handler.class, ArmorStandRefreshPacket.class, 11, Side.CLIENT);
        INSTANCE.registerMessage(OpenInventoryPacket.Handler.class, OpenInventoryPacket.class, 12, Side.CLIENT);
        INSTANCE.registerMessage(TVUpdateChannelPacket.Handler.class, TVUpdateChannelPacket.class, 13, Side.CLIENT);
        INSTANCE.registerMessage(BedsideTableUpdatePacket.Handler.class, BedsideTableUpdatePacket.class, 14, Side.CLIENT);
        INSTANCE.registerMessage(CuttingBoardUpdatePacket.Handler.class, CuttingBoardUpdatePacket.class, 15, Side.CLIENT);
        INSTANCE.registerMessage(PlateUpdatePacket.Handler.class, PlateUpdatePacket.class, 16, Side.CLIENT);
        INSTANCE.registerMessage(StoveUpdatePacket.Handler.class, StoveUpdatePacket.class, 17, Side.CLIENT);
        INSTANCE.registerMessage(BedsideTableRequestUpdatePacket.Handler.class, BedsideTableRequestUpdatePacket.class, 18, Side.SERVER);
        INSTANCE.registerMessage(CuttingBoardRequestUpdatePacket.Handler.class, CuttingBoardRequestUpdatePacket.class, 19, Side.SERVER);
        INSTANCE.registerMessage(PlateRequestUpdatePacket.Handler.class, PlateRequestUpdatePacket.class, 20, Side.SERVER);
        INSTANCE.registerMessage(StoveRequestUpdatePacket.Handler.class, StoveRequestUpdatePacket.class, 21, Side.SERVER);
    }

    public static void updatePlayerData(EntityPlayerMP player) {
        PlayerData data = player.getCapability(CapabilityProvider.CAPABILITY, null);
        INSTANCE.sendTo(new PlayerDataPacket(data), player);
    }
}
