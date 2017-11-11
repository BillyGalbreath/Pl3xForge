package net.pl3x.forge.inventory;

import com.google.common.collect.HashMultimap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryBasic;

public class InventoryPlayer extends InventoryBasic {
    public static HashMultimap<EntityPlayer, InventoryPlayer> map = HashMultimap.create();

    public EntityPlayerMP viewer;
    public EntityPlayerMP target;
    public boolean allowUpdate;

    public InventoryPlayer(EntityPlayerMP target, EntityPlayerMP viewer) {
        super(target.getName() + "'s inventory", false, target.inventory.mainInventory.size() + 9);
        this.target = target;
        this.viewer = viewer;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        map.put(target, this);
        allowUpdate = false;
        getInv();
        allowUpdate = true;
        super.openInventory(player);
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        map.remove(target, this);
        if (allowUpdate) {
            saveInv();
        }
        markDirty();
        super.closeInventory(player);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (allowUpdate) {
            saveInv();
        }
    }

    public void update() {
        allowUpdate = false;
        getInv();
        allowUpdate = true;
        markDirty();
    }

    public static void tickStart(EntityPlayer player) {
        if (map.containsKey(player)) {
            map.get(player).forEach(InventoryPlayer::update);
        }
    }

    public void getInv() {
        for (int id = 0; id < 9; ++id) {
            setInventorySlotContents(id + 36, target.inventory.mainInventory.get(id));
        }
        for (int id = 9; id < 36; ++id) {
            setInventorySlotContents(id, target.inventory.mainInventory.get(id));
        }
        setInventorySlotContents(0, target.inventory.armorInventory.get(0));
        setInventorySlotContents(1, target.inventory.armorInventory.get(1));
        setInventorySlotContents(2, target.inventory.armorInventory.get(2));
        setInventorySlotContents(3, target.inventory.armorInventory.get(3));
        setInventorySlotContents(4, target.inventory.offHandInventory.get(0));
    }

    public void saveInv() {
        for (int id = 0; id < 9; ++id) {
            target.inventory.mainInventory.set(id, getStackInSlot(id + 36));
        }
        for (int id = 9; id < 36; ++id) {
            target.inventory.mainInventory.set(id, getStackInSlot(id));
        }
        target.inventory.armorInventory.set(0, getStackInSlot(0));
        target.inventory.armorInventory.set(1, getStackInSlot(1));
        target.inventory.armorInventory.set(2, getStackInSlot(2));
        target.inventory.armorInventory.set(3, getStackInSlot(3));
        target.inventory.offHandInventory.set(0, getStackInSlot(4));
    }
}
