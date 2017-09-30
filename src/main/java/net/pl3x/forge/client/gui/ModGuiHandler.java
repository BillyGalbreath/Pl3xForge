package net.pl3x.forge.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.pl3x.forge.client.container.ContainerBanker;
import net.pl3x.forge.client.container.ContainerEnchantmentSplitter;
import net.pl3x.forge.client.network.BankPacket;

public class ModGuiHandler implements IGuiHandler {
    public static final int ENCHANTMENT_SPLITTER = 0;
    public static final int BANKER = 1;
    public static final int BANKER_DEPOSIT_COIN = 2;
    public static final int BANKER_WITHDRAW_COIN = 3;
    public static final int BANKER_DEPOSIT_EXP = 4;
    public static final int BANKER_WITHDRAW_EXP = 5;

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case ENCHANTMENT_SPLITTER:
                return new ContainerEnchantmentSplitter(player.inventory, world, new BlockPos(x, y, z));
            case BANKER:
                return new ContainerBanker(player);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case ENCHANTMENT_SPLITTER:
                return new GuiEnchantmentSplitter(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            case BANKER:
                return new GuiBanker(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            case BANKER_DEPOSIT_COIN:
                return new GuiBankerAction(getServerGuiElement(BANKER, player, world, x, y, z), player, BankPacket.DEPOSIT_COIN);
            case BANKER_WITHDRAW_COIN:
                return new GuiBankerAction(getServerGuiElement(BANKER, player, world, x, y, z), player, BankPacket.WITHDRAW_COIN);
            case BANKER_DEPOSIT_EXP:
                return new GuiBankerAction(getServerGuiElement(BANKER, player, world, x, y, z), player, BankPacket.DEPOSIT_EXP);
            case BANKER_WITHDRAW_EXP:
                return new GuiBankerAction(getServerGuiElement(BANKER, player, world, x, y, z), player, BankPacket.WITHDRAW_EXP);
            default:
                return null;
        }
    }
}
