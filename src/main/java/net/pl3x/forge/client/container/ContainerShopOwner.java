package net.pl3x.forge.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.pl3x.forge.client.block.ModBlocks;
import net.pl3x.forge.client.tileentity.TileEntityShop;

import javax.annotation.Nonnull;

public class ContainerShopOwner extends Container {
    public TileEntityShop shop;

    public ContainerShopOwner(InventoryPlayer playerInventory, TileEntityShop shop) {
        this.shop = shop;

        // shop inventory
        int rows = shop.inventory.getSlots() / 9;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new SlotItemHandler(shop.inventory, j + i * 9, 8 + j * 18, 8 + i * 18) {
                    @Override
                    public void onSlotChanged() {
                        shop.markDirty();
                        shop.updateStack();
                    }

                    @Override
                    public boolean isItemValid(@Nonnull ItemStack stack) {
                        stack = stack.copy();
                        stack.setCount(1);
                        return shop.stack.isEmpty() || ItemStack.areItemStacksEqual(shop.stack, stack);
                    }
                });
            }
        }

        // player inventory
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 37 + j * 18, 130 + i * 18));
            }
        }

        // player hotbar
        for (int k = 0; k < 9; ++k) {
            addSlotToContainer(new Slot(playerInventory, k, 37 + k * 18, 188));
        }
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return shop.getWorld().getBlockState(shop.getPos()).getBlock() == ModBlocks.shopBlock &&
                playerIn.getDistanceSq((double) shop.getPos().getX() + 0.5D,
                        (double) shop.getPos().getY() + 0.5D,
                        (double) shop.getPos().getZ() + 0.5D) <= 64.0D;
    }

    // handle shift clicking
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack copyStack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            copyStack = slotStack.copy();
            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!this.mergeItemStack(slotStack, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == copyStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return copyStack;
    }
}
