package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.tileentity.TileEntityTrafficLightControlBox;

public class TrafficLightControlBoxUpdatePacket implements IMessage {
    private BlockPos pos;
    private int tick;
    private TileEntityTrafficLightControlBox.IntersectionState intersectionState;

    public TrafficLightControlBoxUpdatePacket() {
    }

    public TrafficLightControlBoxUpdatePacket(BlockPos pos, int tick, TileEntityTrafficLightControlBox.IntersectionState intersectionState) {
        this.pos = pos;
        this.tick = tick;
        this.intersectionState = intersectionState;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(tick);
        buf.writeInt(intersectionState.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        tick = buf.readInt();
        intersectionState = TileEntityTrafficLightControlBox.IntersectionState.values()[buf.readInt()];
    }

    public static class Handler implements IMessageHandler<TrafficLightControlBoxUpdatePacket, IMessage> {
        @Override
        public IMessage onMessage(TrafficLightControlBoxUpdatePacket packet, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityTrafficLightControlBox te = (TileEntityTrafficLightControlBox) Minecraft.getMinecraft().world.getTileEntity(packet.pos);
                if (te != null) {
                    te.tick = packet.tick;
                    te.intersectionState = packet.intersectionState;
                }
            });
            return null;
        }

    }
}
