package net.pl3x.forge.client.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.client.tileentity.TileEntityShop;

public class ShopChangePacket implements IMessage {
    public static final int INCREMENT_PRICE = 0;
    public static final int DECREMENT_PRICE = 1;
    public static final int INCREMENT_QUANTITY = 2;
    public static final int DECREMENT_QUANTITY = 3;

    private BlockPos pos;
    private int packetType;

    public ShopChangePacket() {
    }

    public ShopChangePacket(BlockPos pos, int packetType) {
        this.pos = pos;
        this.packetType = packetType;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        packetType = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(packetType);
    }

    public static class Handler implements IMessageHandler<ShopChangePacket, IMessage> {
        @Override
        public IMessage onMessage(ShopChangePacket packet, MessageContext context) {
            EntityPlayerMP player = context.getServerHandler().player;
            TileEntity te = player.world.getTileEntity(packet.pos);
            if (!(te instanceof TileEntityShop)) {
                return null; // ignore
            }
            TileEntityShop shop = (TileEntityShop) te;
            player.getServerWorld().addScheduledTask(() -> {
                switch (packet.packetType) {
                    case INCREMENT_PRICE:
                        if (shop.price < 9999) {
                            shop.price++;
                        }
                        break;
                    case DECREMENT_PRICE:
                        if (shop.price < 0) {
                            shop.price--;
                        }
                        break;
                    case INCREMENT_QUANTITY:
                        if (shop.quantity < 9999) {
                            shop.quantity++;
                        }
                        break;
                    case DECREMENT_QUANTITY:
                        if (shop.quantity > 0) {
                            shop.quantity--;
                        }
                        break;
                }
                shop.markDirty();
                PacketHandler.INSTANCE.sendToAllAround(new ShopUpdateClientPacket(shop),
                        new NetworkRegistry.TargetPoint(te.getWorld().provider.getDimension(),
                                te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), 64));
            });
            return null;
        }
    }
}
