package net.pl3x.forge.data;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.pl3x.forge.inventory.InventoryBanker;
import net.pl3x.forge.util.Location;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class PlayerDataImpl implements PlayerData {
    private long coins = 0;
    private long bankCoins = 0;
    private int bankExp = 0;
    private int bankSlots = 0;
    private InventoryBanker bankInventory = new InventoryBanker();
    private TreeMap<String, Location> homes = new TreeMap<>();
    private boolean denyTeleports = false;

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public long getBankCoins() {
        return bankCoins;
    }

    public void setBankCoins(long coins) {
        this.bankCoins = coins;
    }

    public int getBankExp() {
        return bankExp;
    }

    public void setBankExp(int exp) {
        this.bankExp = exp;
    }

    public int getBankSlots() {
        return bankSlots;
    }

    public void setBankSlots(int slots) {
        this.bankSlots = slots;
    }

    public InventoryBanker getBankInventory() {
        return bankInventory;
    }

    public void setBankInventory(IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            bankInventory.setInventorySlotContents(i, inventory.getStackInSlot(i));
        }
    }

    @Override
    public void addHome(String name, Location location) {
        homes.put(name, location);
    }

    @Override
    public void removeHome(String name) {
        homes.remove(name);
    }

    @Override
    public Location getHome(String name) {
        return homes.get(name);
    }

    @Override
    public ArrayList<String> getHomeNames() {
        ArrayList<String> list = new ArrayList<>(homes.keySet());
        Collections.sort(list);
        return list;
    }

    @Override
    public TreeMap<String, Location> getHomes() {
        return homes;
    }

    @Override
    public void setHomes(TreeMap<String, Location> homes) {
        this.homes = homes;
    }

    @Override
    public boolean denyTeleports() {
        return this.denyTeleports;
    }

    @Override
    public void denyTeleports(boolean denyTeleports) {
        this.denyTeleports = denyTeleports;
    }

    @Override
    public NBTTagCompound getDataAsNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("coins", coins);

        NBTTagCompound bank = new NBTTagCompound();
        bank.setLong("coins", bankCoins);
        bank.setInteger("exp", bankExp);
        bank.setInteger("slots", bankSlots);
        bank.setTag("inventory", bankInventory.serializeNBT());
        nbt.setTag("bank", bank);

        NBTTagList homeList = new NBTTagList();
        for (Map.Entry<String, Location> home : homes.entrySet()) {
            NBTTagCompound homeNBT = new NBTTagCompound();
            homeNBT.setString("name", home.getKey());
            homeNBT.setInteger("dimension", home.getValue().getDimension());
            homeNBT.setDouble("x", home.getValue().getX());
            homeNBT.setDouble("y", home.getValue().getY());
            homeNBT.setDouble("z", home.getValue().getZ());
            homeNBT.setFloat("pitch", home.getValue().getPitch());
            homeNBT.setFloat("yaw", home.getValue().getYaw());
            homeList.appendTag(homeNBT);
        }

        nbt.setTag("homes", homeList);
        nbt.setBoolean("denyteleports", denyTeleports);

        return nbt;
    }

    @Override
    public void setDataFromNBT(NBTTagCompound nbt) {
        coins = nbt.getLong("coins");

        NBTTagCompound bank = nbt.getCompoundTag("bank");
        bankCoins = bank.getLong("coins");
        bankExp = bank.getInteger("exp");
        bankSlots = bank.getInteger("slots");
        bankInventory.deserializeNBT(bank.getCompoundTag("inventory"));

        denyTeleports = nbt.getBoolean("denyteleports");
        NBTTagList homesList = nbt.getTagList("homes", 10);
        for (int i = 0; i < homesList.tagCount(); i++) {
            NBTTagCompound home = homesList.getCompoundTagAt(i);
            homes.put(home.getString("name"), new Location(
                    DimensionManager.getWorld(home.getInteger("dimension")),
                    home.getInteger("dimension"),
                    home.getDouble("x"),
                    home.getDouble("y"),
                    home.getDouble("z"),
                    home.getFloat("pitch"),
                    home.getFloat("yaw")));
        }
    }

    public static class PlayerDataStorage implements Capability.IStorage<PlayerData> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side) {
            return instance.getDataAsNBT();
        }

        @Override
        public void readNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side, NBTBase nbt) {
            instance.setDataFromNBT((NBTTagCompound) nbt);
        }
    }
}
