package net.pl3x.forge.core.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.DimensionManager;
import net.pl3x.forge.core.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class PlayerDataImpl implements PlayerData {
    private TreeMap<String, Location> homes = new TreeMap<>();

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
    public TreeMap<String, Location> getMap() {
        return homes;
    }

    @Override
    public void setMap(TreeMap<String, Location> homes) {
        this.homes = homes;
    }

    @Override
    public NBTTagCompound getDataAsNBT() {
        NBTTagList nbtTagList = new NBTTagList();
        for (Map.Entry<String, Location> entry : homes.entrySet()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("name", entry.getKey());
            compound.setInteger("dimension", entry.getValue().getDimension());
            compound.setDouble("x", entry.getValue().getX());
            compound.setDouble("y", entry.getValue().getY());
            compound.setDouble("z", entry.getValue().getZ());
            compound.setDouble("pitch", entry.getValue().getPitch());
            compound.setDouble("yaw", entry.getValue().getYaw());
            nbtTagList.appendTag(compound);
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("homes", nbtTagList);
        return nbt;
    }

    @Override
    public void setDataFromNBT(NBTTagCompound nbt) {
        NBTTagList nbtTagList = nbt.getTagList("homes", 10);
        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            NBTTagCompound compound = nbtTagList.getCompoundTagAt(i);
            homes.put(compound.getString("name"), new Location(
                    DimensionManager.getWorld(compound.getInteger("dimension")),
                    compound.getInteger("dimension"),
                    compound.getDouble("x"),
                    compound.getDouble("y"),
                    compound.getDouble("z"),
                    compound.getDouble("pitch"),
                    compound.getDouble("yaw")));
        }
    }
}
