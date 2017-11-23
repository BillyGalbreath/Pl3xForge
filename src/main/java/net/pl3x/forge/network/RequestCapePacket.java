package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
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
            PacketHandler.INSTANCE.sendToServer(new ReplyCapePacket(
                    Minecraft.getMinecraft().getSession().getUsername(), ClientConfig.capeOptions.capeURL));
            return null;
        }
    }
}
