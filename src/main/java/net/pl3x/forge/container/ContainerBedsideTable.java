package net.pl3x.forge.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.pl3x.forge.tileentity.TileEntityBedsideTable;

public class ContainerBedsideTable extends Container {
    public final TileEntityBedsideTable te;
    public final int startSlot;

    public ContainerBedsideTable(InventoryPlayer playerInv, TileEntityBedsideTable te, int startSlot) {
        this.te = te;
        this.startSlot = startSlot;

        for (int i = startSlot; i < startSlot + 5; ++i) {
            addSlotToContainer(new SlotItemHandler(te.inventory, i, 44 + (i - startSlot) * 18, 20) {
                @Override
                public void onSlotChanged() {
                    te.markDirty();
                }
            });
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 109));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    // handle shift clicking
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();
        ItemStack copy = stack.copy();

        // shift clicked a table item
        if (index < 5) {
            if (!mergeItemStack(stack, 5, inventorySlots.size(), true)) {
                return ItemStack.EMPTY;
            }
        }

        // shift clicked player inventory slot
        else if (!mergeItemStack(stack, 0, 4, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }

        return copy;
    }
}
