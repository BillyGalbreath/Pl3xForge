package net.pl3x.forge;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.server.FMLServerHandler;

public class Location implements Cloneable {
    private final World world;
    private final int dimension;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    public Location(EntityPlayerMP player) {
        this(player.world, player.dimension, player.posX, player.posY, player.posZ, player.rotationPitch, player.rotationYaw);
    }

    public Location(World world, BlockPos pos) {
        this(world, 0, pos.getX(), pos.getY(), pos.getZ(), 0, 0);
    }

    public Location(World world, int dimension, double x, double y, double z, float pitch, float yaw) {
        this.world = world;
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public static int floor(double v) {
        final int floor = (int) v;
        return floor == v ? floor : floor - (int) (Double.doubleToRawLongBits(v) >>> 63);
    }

    public World getWorld() {
        return world;
    }

    public WorldServer getWorldServer() {
        return FMLServerHandler.instance().getServer().getWorld(dimension);
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

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
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

    public Location add(double x, double y, double z) {
        return x == 0.0D && y == 0.0D && z == 0.0D ? this : new Location(world, dimension, this.x + x, this.y + y, this.z + z, pitch, yaw);
    }

    @Override
    public Location clone() {
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false; // null
        }
        if (getClass() != obj.getClass()) {
            return false; // incompatible objects
        }
        final Location other = (Location) obj;
        if (this.world != other.world && (this.world == null || !this.world.equals(other.world))) {
            return false; // not same world
        }
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false; // not same x
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false; // not same y
        }
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
            return false; // not same z
        }
        if (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.pitch)) {
            return false; // not same pitch
        }
        if (Float.floatToIntBits(this.yaw) != Float.floatToIntBits(other.yaw)) {
            return false; // not same yaw
        }
        return true;
    }
}
