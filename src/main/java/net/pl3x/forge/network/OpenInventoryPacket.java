package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class OpenInventoryPacket implements IMessage {
    private int i = 0;

    public OpenInventoryPacket() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(i);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        i = buf.readInt();
    }

    public static class Handler implements IMessageHandler<OpenInventoryPacket, IMessage> {
        @Override
        public IMessage onMessage(OpenInventoryPacket packet, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() ->
                    Minecraft.getMinecraft().displayGuiScreen(
                            new GuiInventory(Minecraft.getMinecraft().player)));
            return null;
        }

    }
}
