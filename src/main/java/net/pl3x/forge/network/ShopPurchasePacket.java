package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.capability.PlayerDataProvider;
import net.pl3x.forge.capability.PlayerData;
import net.pl3x.forge.tileentity.TileEntityShop;

public class ShopPurchasePacket implements IMessage {
    private BlockPos pos;

    public ShopPurchasePacket() {
    }

    public ShopPurchasePacket(TileEntityShop te) {
        this(te.getPos());
    }

    public ShopPurchasePacket(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
    }

    public static class Handler implements IMessageHandler<ShopPurchasePacket, IMessage> {
        @Override
        public IMessage onMessage(ShopPurchasePacket packet, MessageContext context) {
            EntityPlayerMP player = context.getServerHandler().player;
            player.getServerWorld().addScheduledTask(() -> {
                TileEntityShop shop = (TileEntityShop) player.world.getTileEntity(packet.pos);
                if (shop == null) {
                    return;
                }
                if (shop.quantity > 0) {
                    PlayerData capability = player.getCapability(PlayerDataProvider.CAPABILITY, null);
                    long balance = capability.getCoins();
                    if (shop.price > balance) {
                        return;
                    }

                    shop.updateStack();
                    if (shop.quantity > shop.stackCount) {
                        // not enough items in shop to sell
                        PacketHandler.INSTANCE.sendTo(new ShopUpdateClientPacket(shop), player);
                        return;
                    }

                    ItemStack stack = shop.stack.copy();
                    stack.setCount(shop.quantity);

                    if (shop.processPurchase(stack, player) > 0) {
                        // not enough items in shop to sell
                        PacketHandler.INSTANCE.sendTo(new ShopUpdateClientPacket(shop), player);
                        return;
                    }

                    EntityItem item = player.dropItem(stack, false);
                    if (item != null) {
                        item.setNoPickupDelay();
                        item.setOwner(player.getName());
                    }

                    capability.setCoins(balance - shop.price);
                    shop.coins += shop.price;

                    PacketHandler.INSTANCE.sendTo(new ShopUpdateClientPacket(shop), player);
                    PacketHandler.updatePlayerData(player);
                }
            });
            return null;
        }
    }
}
