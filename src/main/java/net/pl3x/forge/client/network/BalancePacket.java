package net.pl3x.forge.client.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.client.gui.HUDBalance;

public class BalancePacket implements IMessage {
    protected double balance;

    public BalancePacket() {
        // Forge needs default constructor or it kicks client
    }

    public BalancePacket(double balance) {
        this.balance = balance;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        balance = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(balance);
    }

    public static class BalanceHandler implements IMessageHandler<BalancePacket, IMessage> {
        @Override
        public IMessage onMessage(BalancePacket packet, MessageContext context) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                HUDBalance.balance = packet.balance;
            });
            return null;
        }
    }
}
