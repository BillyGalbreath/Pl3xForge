package net.pl3x.forge.client.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.client.ChatColor;
import net.pl3x.forge.client.ExperienceManager;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.data.CapabilityProvider;
import net.pl3x.forge.client.data.PlayerData;
import net.pl3x.forge.client.gui.ModGuiHandler;

public class BankPacket implements IMessage {
    public static final int DEPOSIT_COIN = 0;
    public static final int WITHDRAW_COIN = 1;
    public static final int DEPOSIT_EXP = 2;
    public static final int WITHDRAW_EXP = 3;
    public static final int SLOTS_INCREASE = 4;

    private int packetType;
    private long amount;

    public BankPacket() {
    }

    public BankPacket(long amount, int packetType) {
        this.amount = amount;
        this.packetType = packetType;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        amount = buf.readLong();
        packetType = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(amount);
        buf.writeInt(packetType);
    }

    public static class Handler implements IMessageHandler<BankPacket, IMessage> {
        @Override
        public IMessage onMessage(BankPacket packet, MessageContext context) {
            EntityPlayerMP player = context.getServerHandler().player;
            player.getServerWorld().addScheduledTask(() -> {
                PlayerData capability = player.getCapability(CapabilityProvider.CAPABILITY, null);
                if (capability == null) {
                    PacketHandler.INSTANCE.sendTo(new BankFailedPacket(), player);
                    System.out.println("No capability? (" + player.getName() + ")");
                    return;
                }

                long coins = capability.getCoins();
                long bankCoins = capability.getBankCoins();
                int bankExp = capability.getBankExp();
                int bankSlots = capability.getBankSlots();

                ExperienceManager expMan = new ExperienceManager(player);

                switch (packet.packetType) {
                    case DEPOSIT_COIN:
                        if (packet.amount > coins) {
                            PacketHandler.INSTANCE.sendTo(new BankFailedPacket(DEPOSIT_COIN), player);
                            return; // not enough funds
                        }
                        capability.setCoins(coins - packet.amount);
                        capability.setBankCoins(bankCoins + packet.amount);
                        player.sendMessage(new TextComponentString(ChatColor.colorize("&aDeposited " + packet.amount + " coins into bank")));
                        break;
                    case WITHDRAW_COIN:
                        if (packet.amount > bankCoins) {
                            PacketHandler.INSTANCE.sendTo(new BankFailedPacket(WITHDRAW_COIN), player);
                            return; // not enough funds
                        }
                        capability.setCoins(coins + packet.amount);
                        capability.setBankCoins(bankCoins - packet.amount);
                        player.sendMessage(new TextComponentString(ChatColor.colorize("&aWithdrew " + packet.amount + " coins from bank")));
                        break;
                    case DEPOSIT_EXP:
                        if (packet.amount > expMan.getCurrentExp()) {
                            PacketHandler.INSTANCE.sendTo(new BankFailedPacket(DEPOSIT_EXP), player);
                            return; // not enough exp
                        }
                        expMan.changeExp(-packet.amount);
                        capability.setBankExp(bankExp + (int) packet.amount);
                        player.sendMessage(new TextComponentString(ChatColor.colorize("&aDeposited " + packet.amount + " experience into bank")));
                        break;
                    case WITHDRAW_EXP:
                        if (packet.amount > bankExp) {
                            PacketHandler.INSTANCE.sendTo(new BankFailedPacket(WITHDRAW_EXP), player);
                            return; // not enough exp
                        }
                        expMan.changeExp(packet.amount);
                        capability.setBankExp(bankExp - (int) packet.amount);
                        player.sendMessage(new TextComponentString(ChatColor.colorize("&aWithdrew " + packet.amount + " experience from bank")));
                        break;
                    case SLOTS_INCREASE:
                        int cost = bankSlots * 10 + 10;
                        if (cost > coins) {
                            PacketHandler.INSTANCE.sendTo(new BankFailedPacket(SLOTS_INCREASE), player);
                            return; // not enough funds
                        }
                        capability.setCoins(coins - cost);
                        capability.setBankSlots(bankSlots + 1);
                        player.sendMessage(new TextComponentString(ChatColor.colorize("&aPurchased bank slot for " + cost + " coins")));
                        break;
                }

                PacketHandler.updatePlayerData(player);
                player.openGui(Pl3xForgeClient.instance, ModGuiHandler.BANKER, player.world, 0, 0, 0);
            });
            return null;
        }
    }
}
