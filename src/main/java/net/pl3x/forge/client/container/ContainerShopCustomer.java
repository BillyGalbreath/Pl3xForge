package net.pl3x.forge.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.pl3x.forge.client.block.ModBlocks;
import net.pl3x.forge.client.tileentity.TileEntityShop;

public class ContainerShopCustomer extends Container {
    public TileEntityShop shop;

    public ContainerShopCustomer(InventoryPlayer playerInventory, TileEntityShop shop) {
        this.shop = shop;

        // player inventory
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 113 + i * 18));
            }
        }

        // player hotbar
        for (int k = 0; k < 9; ++k) {
            addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 171));
        }
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return shop.getWorld().getBlockState(shop.getPos()).getBlock() == ModBlocks.shopBlock &&
                playerIn.getDistanceSq((double) shop.getPos().getX() + 0.5D,
                        (double) shop.getPos().getY() + 0.5D,
                        (double) shop.getPos().getZ() + 0.5D) <= 64.0D;
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        return ItemStack.EMPTY;
    }
}
