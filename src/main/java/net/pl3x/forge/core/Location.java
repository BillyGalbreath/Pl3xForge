package net.pl3x.forge.core;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Location implements Cloneable {
    private final World world;
    private final int dimension;
    private double x;
    private double y;
    private double z;
    private double pitch;
    private double yaw;

    public Location(EntityPlayerMP player) {
        this(player.world, player.dimension, player.posX, player.posY, player.posZ, player.rotationPitch, player.rotationYaw);
    }

    public Location(World world, int dimension, double x, double y, double z, double pitch, double yaw) {
        this.world = world;
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public World getWorld() {
        return world;
    }

    public int getDimension() {
        return dimension;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public BlockPos getBlockPos() {
        return new BlockPos(x, y, z);
    }

    public int getBlockX() {
        return floor(x);
    }

    public int getBlockY() {
        return floor(y);
    }

    public int getBlockZ() {
        return floor(z);
    }

    @Override
    public Location clone() {
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public static int floor(double v) {
        final int floor = (int) v;
        return floor == v ? floor : floor - (int) (Double.doubleToRawLongBits(v) >>> 63);
    }
}
