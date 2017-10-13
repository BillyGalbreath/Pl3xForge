package net.pl3x.forge.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackUtil {
    public static boolean equalsIgnoreCount(ItemStack stack1, ItemStack stack2) {
        return stack1.isEmpty() && stack2.isEmpty() ||
                (!stack1.isEmpty() && !stack2.isEmpty()) &&
                        isItemStackEqualIgnoreCount(stack1, stack2);
    }

    public static boolean isItemStackEqualIgnoreCount(ItemStack stack1, ItemStack stack2) {
        if (stack1.getItem() != stack2.getItem()) {
            return false;
        } else if (stack1.getItemDamage() != stack2.getItemDamage()) {
            return false;
        } else if (!stack1.hasTagCompound() && stack2.hasTagCompound()) {
            return false;
        } else {
            return (!stack1.hasTagCompound() || stack1.getTagCompound().equals(stack2.getTagCompound())) && stack1.areCapsCompatible(stack2);
        }
    }

    public static ItemStack[] getContents(ItemStackHandler inventory) {
        ItemStack[] contents = new ItemStack[inventory.getSlots()];
        for (int i = 0; i < inventory.getSlots(); i++) {
            contents[i] = inventory.getStackInSlot(i);
        }
        return contents;
    }

    public static void setContents(ItemStackHandler inventory, ItemStack[] contents) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, contents[i]);
        }
    }
}
