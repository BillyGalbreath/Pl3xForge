package net.pl3x.forge.client.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.client.ChatColor;
import net.pl3x.forge.client.container.ContainerBanker;

public class BankFailedPacket implements IMessage {
    private byte packetType;

    public BankFailedPacket() {
    }

    public BankFailedPacket(byte packetType) {
        this.packetType = packetType;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        packetType = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(packetType);
    }

    public static class Handler implements IMessageHandler<BankFailedPacket, IMessage> {
        @Override
        public IMessage onMessage(BankFailedPacket packet, MessageContext context) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayerSP player = Minecraft.getMinecraft().player;
                Container container = player.openContainer;
                if (!(container instanceof ContainerBanker)) {
                    return;
                }

                switch (packet.packetType) {
                    case BankPacket.DEPOSIT_COIN:
                    case BankPacket.DEPOSIT_EXP:
                        player.sendMessage(new TextComponentString(ChatColor.colorize("&cBank deposit failed")));
                        player.closeScreen();
                        break;
                    case BankPacket.WITHDRAW_COIN:
                    case BankPacket.WITHDRAW_EXP:
                        player.sendMessage(new TextComponentString(ChatColor.colorize("&cBank withdraw failed")));
                        player.closeScreen();
                        break;
                    case BankPacket.SLOTS_INCREASE:
                        ((ContainerBanker) container).increaseBankSlotsFailed();
                        // do not close screen
                        break;
                }
            });
            return null;
        }
    }
}
