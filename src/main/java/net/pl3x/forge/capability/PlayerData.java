package net.pl3x.forge.capability;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.pl3x.forge.inventory.InventoryBanker;
import net.pl3x.forge.util.Location;

import java.util.ArrayList;
import java.util.TreeMap;

public interface PlayerData {
    long getCoins();

    void setCoins(long coins);

    long getBankCoins();

    void setBankCoins(long coins);

    int getBankExp();

    void setBankExp(int exp);

    int getBankSlots();

    void setBankSlots(int slots);

    InventoryBanker getBankInventory();

    void setBankInventory(IInventory inventory);

    NBTTagCompound getDataAsNBT();

    void setDataFromNBT(NBTTagCompound nbt);

    void addHome(String name, Location location);

    void removeHome(String name);

    Location getHome(String name);

    ArrayList<String> getHomeNames();

    TreeMap<String, Location> getHomes();

    void setHomes(TreeMap<String, Location> homes);

    boolean denyTeleports();

    void denyTeleports(boolean denyTeleports);
}
