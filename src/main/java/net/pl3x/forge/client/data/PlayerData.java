package net.pl3x.forge.client.data;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.pl3x.forge.client.inventory.InventoryBanker;

public interface PlayerData {
    long getCoins();

    void setCoins(long coins);

    long getBankCoins();

    void setBankCoins(long coins);

    int getBankExp();

    void setBankExp(int exp);

    int getBankSlots();

    void setBankSlots(int slots);

    void setBankInventory(IInventory inventory);

    InventoryBanker getBankInventory();

    NBTTagCompound getDataAsNBT();

    void setDataFromNBT(NBTTagCompound nbt);
}
