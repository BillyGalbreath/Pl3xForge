package net.pl3x.forge.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class TileEntityTrafficLight extends TileEntity implements ITickable {
    public final int rot;
    public ColorState colorState;

    private int blinkTick = 0;
    private boolean blinkState = false;
    private int oldLightLevel = -1;

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

    public boolean isLightOn() {
        return colorState != null || blinkState;
    }

    public int getLightLevel() {
        return isLightOn() ? 8 : 0;
    }

    public void update() {
        // blink red light if colorState is null
        if (colorState == null) {
            blinkTick++;
            if (blinkTick > 20) {
                blinkTick = 0;
                blinkState = !blinkState;
            }
        }

        // check light level on client
        if (world.isRemote) {
            int newLevel = getLightLevel();
            if (oldLightLevel != newLevel) {
                oldLightLevel = newLevel;
                world.checkLightFor(EnumSkyBlock.BLOCK, pos);
            }
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    public enum ColorState {
        RED(0.532),
        YELLOW(0.345),
        GREEN(0.156);

        public static final double x = 0.5;
        public static final double z = 0.595;

        public final double y;

        ColorState(double y) {
            this.y = y;
        }
    }
}
