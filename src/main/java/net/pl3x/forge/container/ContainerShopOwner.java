package net.pl3x.forge.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.tileentity.TileEntityShop;
import net.pl3x.forge.util.ItemStackUtil;

import javax.annotation.Nonnull;

public class ContainerShopOwner extends Container {
    public TileEntityShop shop;
    private boolean allowInteraction;

    public ContainerShopOwner(InventoryPlayer playerInventory, TileEntityShop shop, boolean allowInteraction) {
        this.shop = shop;
        this.allowInteraction = allowInteraction;

        // shop inventory
        int rows = shop.inventory.getSlots() / 9;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new SlotItemHandler(shop.inventory, j + i * 9, 8 + j * 18, 8 + i * 18) {
                    @Override
                    public void onSlotChanged() {
                        shop.markDirty();
                        shop.updateStack();
                        shop.updateClients();
                    }

                    @Override
                    public boolean isItemValid(@Nonnull ItemStack stack) {
                        return allowInteraction && (shop.stack.isEmpty() || ItemStackUtil.isItemStackEqualIgnoreCount(shop.stack, stack));
                    }

                    @Override
                    public void putStack(@Nonnull ItemStack stack) {
                        if (allowInteraction) {
                            super.putStack(stack);
                        }
                    }

                    @Override
                    public boolean canTakeStack(EntityPlayer playerIn) {
                        return allowInteraction && super.canTakeStack(playerIn);
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
        return allowInteraction && shop.getWorld().getBlockState(shop.getPos()).getBlock() == ModBlocks.SHOP &&
                playerIn.getDistanceSq((double) shop.getPos().getX() + 0.5D,
                        (double) shop.getPos().getY() + 0.5D,
                        (double) shop.getPos().getZ() + 0.5D) <= 64.0D;
    }

    // handle shift clicking
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        if (!allowInteraction) {
            return ItemStack.EMPTY;
        }
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
