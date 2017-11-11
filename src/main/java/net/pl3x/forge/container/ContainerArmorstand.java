package net.pl3x.forge.container;

import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.pl3x.forge.inventory.InventoryArmorStand;
import net.pl3x.forge.inventory.SlotArmorStand;

public class ContainerArmorstand extends Container {
    public final EntityArmorStand armorStand;
    private final EntityPlayer player;
    public final InventoryArmorStand inventory;
    private final boolean allowInteraction;

    public final int xOffset = 25;

    public ContainerArmorstand(EntityPlayer player, int entityId, boolean allowInteraction) {
        this.armorStand = (EntityArmorStand) player.world.getEntityByID(entityId);
        this.player = player;
        this.allowInteraction = allowInteraction;

        inventory = new InventoryArmorStand();

        if (armorStand != null && allowInteraction) {
            addSlotToContainer(new SlotArmorStand(inventory, 0, 35, 80, EntityEquipmentSlot.FEET, armorStand)); // feet
            addSlotToContainer(new SlotArmorStand(inventory, 1, 35, 60, EntityEquipmentSlot.LEGS, armorStand)); // legs
            addSlotToContainer(new SlotArmorStand(inventory, 2, 35, 40, EntityEquipmentSlot.CHEST, armorStand)); // chest
            addSlotToContainer(new SlotArmorStand(inventory, 3, 35, 20, EntityEquipmentSlot.HEAD, armorStand)); // head
            addSlotToContainer(new SlotArmorStand(inventory, 4, 15, 40, EntityEquipmentSlot.MAINHAND, armorStand)); // main hand
            addSlotToContainer(new SlotArmorStand(inventory, 5, 55, 40, EntityEquipmentSlot.OFFHAND, armorStand)); // off hand

            //noinspection unchecked
            NonNullList<ItemStack> armor = (NonNullList) armorStand.getArmorInventoryList();
            //noinspection unchecked
            NonNullList<ItemStack> hands = (NonNullList) armorStand.getHeldEquipment();

            inventory.setInventorySlotContents(0, armor.get(0)); // feet
            inventory.setInventorySlotContents(1, armor.get(1)); // legs
            inventory.setInventorySlotContents(2, armor.get(2)); // chest
            inventory.setInventorySlotContents(3, armor.get(3)); // head
            inventory.setInventorySlotContents(4, hands.get(0)); // main hand
            inventory.setInventorySlotContents(5, hands.get(1)); // off hand
        }

        // player inventory
        int doubleOffset = xOffset * 2;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + (allowInteraction ? 0 : doubleOffset) + j * 18, 120 + i * 18));
            }
        }

        // player hotbar
        for (int k = 0; k < 9; ++k) {
            addSlotToContainer(new Slot(player.inventory, k, 8 + (allowInteraction ? 0 : doubleOffset) + k * 18, 178));
        }
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return allowInteraction && armorStand != null && !armorStand.isDead && armorStand.dimension == player.dimension;
    }

    // handle shift clicking
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        if (!canInteractWith(player)) {
            return ItemStack.EMPTY;
        }
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();
        ItemStack copy = stack.copy();

        // shift clicked an armor slot
        if (index <= 6) {
            if (!mergeItemStack(stack, 7, 42, true)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChange(stack, copy);
        }

        // shift clicked player inventory slot
        else {
            if (index < 42 && !mergeItemStack(stack, 0, 6, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }

        if (stack.getCount() == copy.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);
        return copy;
    }
}
