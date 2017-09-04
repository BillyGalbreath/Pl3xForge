package net.pl3x.forge.core.data;

import net.minecraft.nbt.NBTTagCompound;
import net.pl3x.forge.core.Location;

import java.util.ArrayList;
import java.util.TreeMap;

public interface PlayerData {
    void addHome(String name, Location location);

    void removeHome(String name);

    Location getHome(String name);

    ArrayList<String> getHomeNames();

    TreeMap<String, Location> getHomes();

    void setHomes(TreeMap<String, Location> homes);

    boolean denyTeleports();

    void denyTeleports(boolean denyTeleports);

    NBTTagCompound getDataAsNBT();

    void setDataFromNBT(NBTTagCompound nbt);
}
