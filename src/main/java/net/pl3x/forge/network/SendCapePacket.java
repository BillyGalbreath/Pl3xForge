package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.cape.CapeManager;

public class SendCapePacket implements IMessage {
    public String username;
    public String url;

    public SendCapePacket() {
    }

    public SendCapePacket(String username, String url) {
        this.username = username;
        this.url = url;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.username = ByteBufUtils.readUTF8String(buf);
        this.url = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.username);
        ByteBufUtils.writeUTF8String(buf, this.url);
    }

    public static class Handler implements IMessageHandler<SendCapePacket, IMessage> {
        @Override
        public IMessage onMessage(SendCapePacket message, MessageContext ctx) {
            CapeManager.addCape(message.username, message.url);
            return null;
        }
    }
}
