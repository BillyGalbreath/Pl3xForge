package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.configuration.ClientConfig;

public class RequestCapePacket implements IMessage {
    public RequestCapePacket() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<RequestCapePacket, IMessage> {
        @Override
        public IMessage onMessage(RequestCapePacket message, MessageContext ctx) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            ItemStack cape = player.inventory.capeInventory.get(0);
            if (!cape.isEmpty()) {
                PacketHandler.INSTANCE.sendToServer(new ReplyCapePacket(player.getName(), cape));
            } else {
                PacketHandler.INSTANCE.sendToServer(new ReplyCapePacket(player.getName(), ClientConfig.capeOptions.capeURL));
            }
            return null;
        }
    }
}
