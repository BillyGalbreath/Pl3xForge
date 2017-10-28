package net.pl3x.forge.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityTrafficLight extends TileEntity implements ITickable {
    public final int rot;
    public State state;

    public float tick = 0;
    public boolean on = true;

    public TileEntityTrafficLight() {
        this(EnumFacing.SOUTH);
    }

    public TileEntityTrafficLight(EnumFacing facing) {
        switch (facing) {
            case NORTH: // sign faces to the EAST
                rot = 90;
                break;
            case SOUTH: // sign faces to the WEST
                rot = -90;
                break;
            case WEST: // sign faces to the NORTH
                rot = 180;
                break;
            case EAST: // sign faces to the SOUTH
            default:
                rot = 0;
                break;
        }
    }

    public void update() {
        //
        //
        //
    }

    public enum State {
        RED(0.532),
        YELLOW(0.345),
        GREEN(0.156);

        public double y;

        State(double y) {
            this.y = y;
        }
    }
}
