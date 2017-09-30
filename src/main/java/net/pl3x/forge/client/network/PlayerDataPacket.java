package net.pl3x.forge.client.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.client.data.CapabilityProvider;
import net.pl3x.forge.client.data.PlayerData;
import net.pl3x.forge.client.gui.HUDBalance;

public class PlayerDataPacket implements IMessage {
    protected NBTTagCompound nbt;

    public PlayerDataPacket() {
    }

    public PlayerDataPacket(PlayerData playerData) {
        this.nbt = playerData.getDataAsNBT();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, nbt);
    }

    public static class Handler implements IMessageHandler<PlayerDataPacket, IMessage> {
        @Override
        public IMessage onMessage(PlayerDataPacket packet, MessageContext context) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                PlayerData capability = Minecraft.getMinecraft().player.getCapability(CapabilityProvider.CAPABILITY, null);
                capability.setDataFromNBT(packet.nbt);
                HUDBalance.balance = capability.getCoins();
            });
            return null;
        }
    }
}
