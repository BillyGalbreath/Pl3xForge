package net.pl3x.forge.client.block.enchantmentsplitter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class InventorySplitResult implements IInventory {
    private final NonNullList<ItemStack> stackResult = NonNullList.withSize(2, ItemStack.EMPTY);

    public int getSizeInventory() {
        return 2;
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : stackResult) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public ItemStack getStackInSlot(int index) {
        if (index != 0 && index != 1) {
            return ItemStack.EMPTY;
        }
        return stackResult.get(index);
    }

    public String getName() {
        return "result";
    }

    public boolean hasCustomName() {
        return false;
    }

    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndRemove(stackResult, index);
    }

    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(stackResult, index);
    }

    public void setInventorySlotContents(int index, ItemStack stack) {
        stackResult.set(index, stack);
    }

    public int getInventoryStackLimit() {
        return 1;
    }

    public void markDirty() {
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    public void openInventory(EntityPlayer player) {
    }

    public void closeInventory(EntityPlayer player) {
    }

    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    public int getField(int id) {
        return 0;
    }

    public void setField(int id, int value) {
    }

    public int getFieldCount() {
        return 0;
    }

    public void clear() {
        stackResult.clear();
    }
}
