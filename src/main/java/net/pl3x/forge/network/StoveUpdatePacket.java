package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.ItemStackHandler;
import net.pl3x.forge.tileentity.TileEntityStove;

public class StoveUpdatePacket implements IMessage {
    private BlockPos pos;
    private NBTTagCompound inventory;

    public StoveUpdatePacket() {
    }

    public StoveUpdatePacket(TileEntityStove te) {
        this(te.getPos(), te.inventory);
    }

    public StoveUpdatePacket(BlockPos pos, ItemStackHandler inventory) {
        this.pos = pos;
        this.inventory = inventory.serializeNBT();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeTag(buf, inventory);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        inventory = ByteBufUtils.readTag(buf);
    }

    public static class Handler implements IMessageHandler<StoveUpdatePacket, IMessage> {
        @Override
        public IMessage onMessage(StoveUpdatePacket packet, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityStove te = (TileEntityStove) Minecraft.getMinecraft().world.getTileEntity(packet.pos);
                if (te != null) {
                    te.inventory.deserializeNBT(packet.inventory);
                }
            });
            return null;
        }
    }
}
