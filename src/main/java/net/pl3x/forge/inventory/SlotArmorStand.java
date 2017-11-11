package net.pl3x.forge.inventory;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class SlotArmorStand extends Slot {
    private EntityEquipmentSlot slotType;
    private EntityArmorStand armorStand;
    private int dimension;

    public SlotArmorStand(IInventory inventory, int index, int xPos, int yPos, EntityEquipmentSlot slotType, EntityArmorStand armorStand) {
        super(inventory, index, xPos, yPos);
        this.slotType = slotType;
        this.armorStand = armorStand;
        this.dimension = armorStand.dimension;
    }

    private boolean isArmorStandStillThere() {
        return armorStand != null && !armorStand.isDead && armorStand.dimension == dimension;
    }

    public boolean isSameStack(ItemStack stack, EntityEquipmentSlot slot) {
        return armorStand.getItemStackFromSlot(slot).isItemEqual(stack);
    }

    public boolean isItemValid(ItemStack stack) {
        if (!isArmorStandStillThere()) {
            return false;
        }
        EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(stack);
        if (slotType == EntityEquipmentSlot.MAINHAND) {
            if (inventory.getStackInSlot(4).isEmpty()) {
                return slot == EntityEquipmentSlot.MAINHAND;
            }
            return slot == EntityEquipmentSlot.OFFHAND;
        } else if (slotType == EntityEquipmentSlot.OFFHAND) {
            if (inventory.getStackInSlot(5).isEmpty()) {
                return slot == EntityEquipmentSlot.OFFHAND;
            }
            return slot == EntityEquipmentSlot.MAINHAND;
        }
        return slot == slotType;
    }

    public ItemStack getStack() {
        ItemStack stack = inventory.getStackInSlot(getSlotIndex());
        return isArmorStandStillThere() && isSameStack(stack, slotType) ? stack : ItemStack.EMPTY;
    }

    public void putStack(ItemStack stack) {
        if (isArmorStandStillThere()) {
            armorStand.setItemStackToSlot(slotType, stack);
            inventory.setInventorySlotContents(getSlotIndex(), stack);
            onSlotChanged();
        }
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public String getSlotTexture() {
        switch (slotType) {
            case MAINHAND:
                return "minecraft:items/empty_armor_slot_mainhand";
            case OFFHAND:
                return "minecraft:items/empty_armor_slot_shield";
            case FEET:
                return "minecraft:items/empty_armor_slot_boots";
            case LEGS:
                return "minecraft:items/empty_armor_slot_leggings";
            case CHEST:
                return "minecraft:items/empty_armor_slot_chestplate";
            case HEAD:
                return "minecraft:items/empty_armor_slot_helmet";
        }
        return backgroundName;
    }

    public boolean canTakeStack(EntityPlayer playerIn) {
        return isArmorStandStillThere();
    }
}
