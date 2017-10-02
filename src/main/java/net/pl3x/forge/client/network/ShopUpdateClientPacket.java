package net.pl3x.forge.client.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.ItemStackHandler;
import net.pl3x.forge.client.tileentity.TileEntityShop;

public class ShopUpdateClientPacket implements IMessage {
    private BlockPos pos;
    private NBTTagCompound inventory;
    private int price;
    private int quantity;

    public ShopUpdateClientPacket() {
    }

    public ShopUpdateClientPacket(TileEntityShop te) {
        this(te.getPos(), te.inventory, te.price, te.quantity);
    }

    public ShopUpdateClientPacket(BlockPos pos, ItemStackHandler inventory, int price, int quantity) {
        this.inventory = inventory.serializeNBT();
        this.price = price;
        this.quantity = quantity;
        this.pos = pos;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeTag(buf, inventory);
        buf.writeInt(price);
        buf.writeInt(quantity);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        inventory = ByteBufUtils.readTag(buf);
        price = buf.readInt();
        quantity = buf.readInt();
    }

    public static class Handler implements IMessageHandler<ShopUpdateClientPacket, IMessage> {
        @Override
        public IMessage onMessage(ShopUpdateClientPacket packet, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityShop te = (TileEntityShop) Minecraft.getMinecraft().world.getTileEntity(packet.pos);
                if (te != null) {
                    te.inventory.deserializeNBT(packet.inventory);
                    te.price = packet.price;
                    te.quantity = packet.quantity;
                    te.updateStack();
                }
            });
            return null;
        }

    }
}
