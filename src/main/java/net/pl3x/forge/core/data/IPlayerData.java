package net.pl3x.forge.core.data;

import net.minecraft.nbt.NBTTagCompound;
import net.pl3x.forge.core.Location;

import java.util.ArrayList;
import java.util.TreeMap;

public interface IPlayerData {
    void addHome(String name, Location location);

    void removeHome(String name);

    Location getHome(String name);

    ArrayList<String> getHomeNames();

    TreeMap<String, Location> getMap();

    void setMap(TreeMap<String, Location> homes);

    NBTTagCompound getDataAsNBT();

    void setDataFromNBT(NBTTagCompound nbt);
}
