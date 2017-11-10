package net.pl3x.forge.container;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerArmorstand extends Container {
    public final EntityArmorStand armorStand;
    private final EntityPlayer player;
    private final boolean allowInteraction;

    public ContainerArmorstand(EntityPlayer player, int entityId, boolean allowInteraction) {
        this.armorStand = (EntityArmorStand) player.world.getEntityByID(entityId);
        this.player = player;
        this.allowInteraction = allowInteraction;

        IInventory armorSlots = new InventoryBasic("Armor", true, 4);
        IInventory handSlots = new InventoryBasic("Hands", true, 2);

        if (armorStand != null && allowInteraction) {
            addSlotToContainer(new Slot(armorSlots, 3, 35, 25) {
                @SideOnly(Side.CLIENT)
                public String getSlotTexture() {
                    return "minecraft:items/empty_armor_slot_helmet";
                }

                @Override
                public boolean isItemValid(ItemStack stack) {
                    return allowInteraction() && EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD;
                }

                @Override
                public void putStack(ItemStack stack) {
                    if (allowInteraction()) {
                        armorStand.setItemStackToSlot(EntityEquipmentSlot.HEAD, stack);
                        super.putStack(stack);
                    }
                }

                @Override
                public boolean canTakeStack(EntityPlayer playerIn) {
                    return allowInteraction() && super.canTakeStack(playerIn);
                }
            });
            addSlotToContainer(new Slot(armorSlots, 2, 35, 45) {
                @SideOnly(Side.CLIENT)
                public String getSlotTexture() {
                    return "minecraft:items/empty_armor_slot_chestplate";
                }

                @Override
                public boolean isItemValid(ItemStack stack) {
                    return allowInteraction() && EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.CHEST;
                }

                @Override
                public void putStack(ItemStack stack) {
                    if (allowInteraction()) {
                        armorStand.setItemStackToSlot(EntityEquipmentSlot.CHEST, stack);
                        super.putStack(stack);
                    }
                }

                @Override
                public boolean canTakeStack(EntityPlayer playerIn) {
                    return allowInteraction() && super.canTakeStack(playerIn);
                }
            });
            addSlotToContainer(new Slot(armorSlots, 1, 35, 65) {
                @SideOnly(Side.CLIENT)
                public String getSlotTexture() {
                    return "minecraft:items/empty_armor_slot_leggings";
                }

                @Override
                public boolean isItemValid(ItemStack stack) {
                    return allowInteraction() && EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.LEGS;
                }

                @Override
                public void putStack(ItemStack stack) {
                    if (allowInteraction()) {
                        armorStand.setItemStackToSlot(EntityEquipmentSlot.LEGS, stack);
                        super.putStack(stack);
                    }
                }

                @Override
                public boolean canTakeStack(EntityPlayer playerIn) {
                    return allowInteraction() && super.canTakeStack(playerIn);
                }
            });
            addSlotToContainer(new Slot(armorSlots, 0, 35, 85) {
                @SideOnly(Side.CLIENT)
                public String getSlotTexture() {
                    return "minecraft:items/empty_armor_slot_boots";
                }

                @Override
                public boolean isItemValid(ItemStack stack) {
                    return allowInteraction() && EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.FEET;
                }

                @Override
                public void putStack(ItemStack stack) {
                    if (allowInteraction()) {
                        armorStand.setItemStackToSlot(EntityEquipmentSlot.FEET, stack);
                        super.putStack(stack);
                    }
                }

                @Override
                public boolean canTakeStack(EntityPlayer playerIn) {
                    return allowInteraction() && super.canTakeStack(playerIn);
                }
            });

            addSlotToContainer(new Slot(handSlots, 0, 15, 45) {
                @SideOnly(Side.CLIENT)
                public String getSlotTexture() {
                    return "minecraft:items/empty_armor_slot_mainhand";
                }

                @Override
                public boolean isItemValid(ItemStack stack) {
                    EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(stack);
                    return allowInteraction() && (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND);
                }

                @Override
                public void putStack(ItemStack stack) {
                    if (allowInteraction()) {
                        armorStand.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
                        super.putStack(stack);
                    }
                }

                @Override
                public boolean canTakeStack(EntityPlayer playerIn) {
                    return allowInteraction() && super.canTakeStack(playerIn);
                }
            });
            addSlotToContainer(new Slot(handSlots, 1, 55, 45) {
                @SideOnly(Side.CLIENT)
                public String getSlotTexture() {
                    return "minecraft:items/empty_armor_slot_shield";
                }

                @Override
                public boolean isItemValid(ItemStack stack) {
                    EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(stack);
                    return allowInteraction() && (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND);
                }

                @Override
                public void putStack(ItemStack stack) {
                    if (allowInteraction()) {
                        armorStand.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack);
                        super.putStack(stack);
                    }
                }

                @Override
                public boolean canTakeStack(EntityPlayer playerIn) {
                    return allowInteraction() && super.canTakeStack(playerIn);
                }
            });

            //noinspection unchecked
            NonNullList<ItemStack> armor = (NonNullList) armorStand.getArmorInventoryList();
            armorSlots.setInventorySlotContents(0, armor.get(0)); // feet
            armorSlots.setInventorySlotContents(1, armor.get(1)); // legs
            armorSlots.setInventorySlotContents(2, armor.get(2)); // chest
            armorSlots.setInventorySlotContents(3, armor.get(3)); // head

            //noinspection unchecked
            NonNullList<ItemStack> hands = (NonNullList) armorStand.getHeldEquipment();
            handSlots.setInventorySlotContents(0, hands.get(0)); // main hand
            handSlots.setInventorySlotContents(1, hands.get(1)); // off hand
        }

        // player inventory
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + (allowInteraction ? 0 : 38) + j * 18, 130 + i * 18));
            }
        }

        // player hotbar
        for (int k = 0; k < 9; ++k) {
            addSlotToContainer(new Slot(player.inventory, k, 8 + (allowInteraction ? 0 : 38) + k * 18, 188));
        }
    }

    private boolean allowInteraction() {
        return allowInteraction && armorStand != null && !armorStand.isDead && player.dimension == armorStand.dimension;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return allowInteraction();
    }

    // handle shift clicking
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        if (!allowInteraction()) {
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
            if (!mergeItemStack(stack, 7, 40, true)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChange(stack, copy);
        }

        // shift clicked player inventory slot
        else {
            if (index < 40 && !mergeItemStack(stack, 0, 6, false)) {
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
