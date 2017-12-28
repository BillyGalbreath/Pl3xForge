package net.pl3x.forge.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.capability.PlayerData;
import net.pl3x.forge.capability.PlayerDataProvider;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Pl3x.modId);

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(ArmorStandChangePacket.Handler.class, ArmorStandChangePacket.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(ArmorStandRefreshPacket.Handler.class, ArmorStandRefreshPacket.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(BankFailedPacket.Handler.class, BankFailedPacket.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(BankPacket.Handler.class, BankPacket.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(BedsideTableUpdatePacket.Handler.class, BedsideTableUpdatePacket.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(BedsideTableRequestUpdatePacket.Handler.class, BedsideTableRequestUpdatePacket.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(BigHeadPacket.Handler.class, BigHeadPacket.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(CuttingBoardUpdatePacket.Handler.class, CuttingBoardUpdatePacket.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(CuttingBoardRequestUpdatePacket.Handler.class, CuttingBoardRequestUpdatePacket.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(OpenInventoryPacket.Handler.class, OpenInventoryPacket.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(PlateUpdatePacket.Handler.class, PlateUpdatePacket.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(PlateRequestUpdatePacket.Handler.class, PlateRequestUpdatePacket.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(PlayerDataPacket.Handler.class, PlayerDataPacket.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(ShopChangePacket.Handler.class, ShopChangePacket.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(ShopUpdateClientPacket.Handler.class, ShopUpdateClientPacket.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(ShopClientRequestPacket.Handler.class, ShopClientRequestPacket.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(ShopPurchasePacket.Handler.class, ShopPurchasePacket.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(StoveUpdatePacket.Handler.class, StoveUpdatePacket.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(StoveRequestUpdatePacket.Handler.class, StoveRequestUpdatePacket.class, ++id, Side.SERVER);
        INSTANCE.registerMessage(TrafficLightControlBoxUpdatePacket.Handler.class, TrafficLightControlBoxUpdatePacket.class, ++id, Side.CLIENT);
        INSTANCE.registerMessage(TVUpdateChannelPacket.Handler.class, TVUpdateChannelPacket.class, ++id, Side.CLIENT);
    }

    public static void updatePlayerData(EntityPlayerMP player) {
        PlayerData data = player.getCapability(PlayerDataProvider.CAPABILITY, null);
        INSTANCE.sendTo(new PlayerDataPacket(data), player);
    }
}
