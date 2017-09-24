package net.pl3x.forge.client.data;

import net.minecraft.nbt.NBTTagCompound;

public interface PlayerData {
    double getBalance();

    void setBalance(double balance);

    NBTTagCompound getDataAsNBT();

    void setDataFromNBT(NBTTagCompound nbt);
}
