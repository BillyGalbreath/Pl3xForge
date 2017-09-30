package net.pl3x.forge.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.pl3x.forge.client.data.CapabilityProvider;
import net.pl3x.forge.client.data.PlayerData;
import net.pl3x.forge.client.inventory.InventoryBanker;
import net.pl3x.forge.client.inventory.SlotBanker;

import java.util.concurrent.TimeUnit;

public class ContainerBanker extends Container {
    private final IInventory bankSlots;
    public final EntityPlayer player;
    private long coins = 0;
    private int exp = 0;
    private PlayerData playerData;
    public boolean increaseBankSlotsFailed = false;

    public ContainerBanker(EntityPlayer player) {
        this.player = player;

        playerData = player.getCapability(CapabilityProvider.CAPABILITY, null);
        if (playerData == null) {
            System.out.println("No capability found. Creating new banker inventory");
            bankSlots = new InventoryBanker();
        } else {
            bankSlots = playerData.getBankInventory();
            coins = playerData.getBankCoins();
            exp = playerData.getBankExp();
        }

        // bank inventory
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 5; ++j) {
                addSlotToContainer(new SlotBanker(this, bankSlots, j + i * 5, 80 + j * 18, 8 + i * 18));
            }
        }

        // player inventory
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 130 + i * 18));
            }
        }

        // player hotbar
        for (int k = 0; k < 9; ++k) {
            addSlotToContainer(new Slot(player.inventory, k, 8 + k * 18, 188));
        }
    }

    public long getCoins() {
        return coins;
    }

    public int getExp() {
        return exp;
    }

    public int getSlots() {
        return playerData.getBankSlots();
    }

    public void increaseBankSlotsFailed() {
        increaseBankSlotsFailed = true;
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException ignore) {
            }
            FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(player.dimension)
                    .addScheduledTask(() -> increaseBankSlotsFailed = false);
        }).start();
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return player != null && player.equals(playerIn);
    }

    public void addListener(IContainerListener listener) {
        super.addListener(listener);
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

        // shift clicked a bank item
        if (index < 30) {
            if (!mergeItemStack(stack, 30, inventorySlots.size(), true)) {
                return ItemStack.EMPTY;
            }
        }

        // shift clicked player inventory slot
        else if (!mergeItemStack(stack, 0, 29, false)) {
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
