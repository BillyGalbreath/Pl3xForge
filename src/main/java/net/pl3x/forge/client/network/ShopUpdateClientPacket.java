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
    private long coins;
    private double display_scale;
    private double display_yOffset;
    private int display_rotateX;
    private int display_rotateY;
    private int display_rotateZ;

    public ShopUpdateClientPacket() {
    }

    public ShopUpdateClientPacket(TileEntityShop te) {
        this(te.getPos(), te.inventory, te.price, te.quantity, te.coins, te.display_scale, te.display_yOffset, te.display_rotateX, te.display_rotateY, te.display_rotateZ);
    }

    public ShopUpdateClientPacket(BlockPos pos, ItemStackHandler inventory, int price, int quantity, long coins,
                                  double display_scale, double display_yOffset, int display_rotateX,
                                  int display_rotateY, int display_rotateZ) {
        this.inventory = inventory.serializeNBT();
        this.pos = pos;
        this.price = price;
        this.quantity = quantity;
        this.coins = coins;
        this.display_scale = display_scale;
        this.display_yOffset = display_yOffset;
        this.display_rotateX = display_rotateX;
        this.display_rotateY = display_rotateY;
        this.display_rotateZ = display_rotateZ;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeTag(buf, inventory);
        buf.writeInt(price);
        buf.writeInt(quantity);
        buf.writeLong(coins);
        buf.writeDouble(display_scale);
        buf.writeDouble(display_yOffset);
        buf.writeInt(display_rotateX);
        buf.writeInt(display_rotateY);
        buf.writeInt(display_rotateZ);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        inventory = ByteBufUtils.readTag(buf);
        price = buf.readInt();
        quantity = buf.readInt();
        coins = buf.readLong();
        display_scale = buf.readDouble();
        display_yOffset = buf.readDouble();
        display_rotateX = buf.readInt();
        display_rotateY = buf.readInt();
        display_rotateZ = buf.readInt();
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
                    te.coins = packet.coins;
                    te.display_scale = packet.display_scale;
                    te.display_yOffset = packet.display_yOffset;
                    te.display_rotateX = packet.display_rotateX;
                    te.display_rotateY = packet.display_rotateY;
                    te.display_rotateZ = packet.display_rotateZ;
                    te.updateStack();
                }
            });
            return null;
        }

    }
}
