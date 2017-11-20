package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.tileentity.TileEntityBedsideTable;

public class BedsideTableRequestUpdatePacket implements IMessage {
    private BlockPos pos;
    private int dimension;

    public BedsideTableRequestUpdatePacket() {
    }

    public BedsideTableRequestUpdatePacket(TileEntityBedsideTable te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public BedsideTableRequestUpdatePacket(BlockPos pos, int dimension) {
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

    public static class Handler implements IMessageHandler<BedsideTableRequestUpdatePacket, IMessage> {
        @Override
        public IMessage onMessage(BedsideTableRequestUpdatePacket packet, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(packet.dimension);
            TileEntityBedsideTable te = (TileEntityBedsideTable) world.getTileEntity(packet.pos);
            if (te != null) {
                return new BedsideTableUpdatePacket(te);
            }
            return null;
        }
    }
}
