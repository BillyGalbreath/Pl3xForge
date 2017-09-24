package net.pl3x.forge.client.data;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerDataImpl implements PlayerData {
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public NBTTagCompound getDataAsNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setDouble("balance", balance);
        return nbt;
    }

    @Override
    public void setDataFromNBT(NBTTagCompound nbt) {
        balance = nbt.getDouble("balance");
    }
}
