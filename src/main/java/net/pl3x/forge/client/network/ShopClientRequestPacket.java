package net.pl3x.forge.client.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.client.tileentity.TileEntityShop;

public class ShopClientRequestPacket implements IMessage {
    private BlockPos pos;
    private int dimension;

    public ShopClientRequestPacket() {
    }

    public ShopClientRequestPacket(TileEntityShop te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public ShopClientRequestPacket(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(dimension);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        dimension = buf.readInt();
    }

    public static class Handler implements IMessageHandler<ShopClientRequestPacket, IMessage> {
        @Override
        public IMessage onMessage(ShopClientRequestPacket packet, MessageContext context) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(packet.dimension);
            TileEntityShop te = (TileEntityShop) world.getTileEntity(packet.pos);
            if (te != null) {
                return new ShopUpdateClientPacket(te);
            }
            return null;
        }

    }
}
