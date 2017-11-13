package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.block.custom.decoration.BlockTV;
import net.pl3x.forge.tileentity.TileEntityTV;

public class TVUpdateChannelPacket implements IMessage {
    private int x, y, z;
    private int channel = 0;

    public TVUpdateChannelPacket() {
    }

    public TVUpdateChannelPacket(BlockPos pos, BlockTV.EnumChannel channel) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.channel = channel.ordinal();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(channel);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        channel = buf.readInt();
    }

    public static class Handler implements IMessageHandler<TVUpdateChannelPacket, IMessage> {
        @Override
        public IMessage onMessage(TVUpdateChannelPacket packet, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                BlockTV.EnumChannel channel;
                try {
                    channel = BlockTV.EnumChannel.values()[packet.channel];
                } catch (IllegalArgumentException e) {
                    return;
                }
                BlockPos pos = new BlockPos(packet.x, packet.y, packet.z);
                IBlockState state = Minecraft.getMinecraft().world.getBlockState(pos);
                TileEntityTV te = (TileEntityTV) Minecraft.getMinecraft().world.getTileEntity(pos);
                if (te == null || state.getBlock() != ModBlocks.TV) {
                    return; // missing tv or block
                }
                te.channel = channel;
                Minecraft.getMinecraft().world.setBlockState(pos, state.withProperty(BlockTV.CHANNEL, channel));
                Minecraft.getMinecraft().world.markBlockRangeForRenderUpdate(pos, pos);
            });
            return null;
        }
    }
}
