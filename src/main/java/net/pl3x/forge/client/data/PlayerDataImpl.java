package net.pl3x.forge.client.data;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.pl3x.forge.client.inventory.InventoryBanker;

import javax.annotation.Nullable;

public class PlayerDataImpl implements PlayerData {
    private long coins = 0;
    private long bankCoins = 0;
    private int bankExp = 0;
    private int bankSlots = 0;
    private InventoryBanker bankInventory = new InventoryBanker();

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
    public NBTTagCompound getDataAsNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("coins", coins);

        NBTTagCompound bank = new NBTTagCompound();
        bank.setLong("coins", bankCoins);
        bank.setInteger("exp", bankExp);
        bank.setInteger("slots", bankSlots);
        bank.setTag("inventory", bankInventory.serializeNBT());
        nbt.setTag("bank", bank);

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
