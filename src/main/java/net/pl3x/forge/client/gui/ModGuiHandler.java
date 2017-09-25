package net.pl3x.forge.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.pl3x.forge.client.block.enchantmentsplitter.ContainerEnchantmentSplitter;
import net.pl3x.forge.client.block.enchantmentsplitter.GuiEnchantmentSplitter;

public class ModGuiHandler implements IGuiHandler {
    public static final int ENCHANTMENT_SPLITTER = 0;

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case ENCHANTMENT_SPLITTER:
                return new ContainerEnchantmentSplitter(player.inventory, world, new BlockPos(x, y, z));
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case ENCHANTMENT_SPLITTER:
                return new GuiEnchantmentSplitter(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            default:
                return null;
        }
    }
}
