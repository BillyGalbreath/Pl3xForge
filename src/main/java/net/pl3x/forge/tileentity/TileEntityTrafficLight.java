package net.pl3x.forge.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class TileEntityTrafficLight extends TileEntity implements ITickable {
    public final EnumFacing facing;

    public final int rot;
    public LightState lightState;

    private int searchTick = 0;
    private int blinkTick = 0;
    private boolean blinkState = false;
    private int oldLightLevel = -1;

    public final double xOffset;
    public final double yOffset;
    public final double zOffset;

    public TileEntityTrafficLightControlBox controlBox;

    public TileEntityTrafficLight() {
        this(EnumFacing.SOUTH);
    }

    public TileEntityTrafficLight(EnumFacing facing) {
        this(facing, 0.5, 0, 0.595);
    }

    public TileEntityTrafficLight(EnumFacing facing, double xOffset, double yOffset, double zOffset) {
        this.facing = facing;

        switch (facing) {
            case NORTH: // light faces to the EAST
                rot = 90;
                break;
            case SOUTH: // light faces to the WEST
                rot = -90;
                break;
            case WEST: // light faces to the NORTH
                rot = 180;
                break;
            case EAST: // light faces to the SOUTH
            default:
                rot = 0;
                break;
        }

        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }

    public boolean isLightOn() {
        return lightState != null || blinkState;
    }

    public int getLightLevel() {
        return isLightOn() ? 5 : 0;
    }

    public void update() {
        // check control box is still there
        if (controlBox != null) {
            TileEntity te = world.getTileEntity(controlBox.getPos());
            if (!(te instanceof TileEntityTrafficLightControlBox)) {
                controlBox = null;
            }
        }

        // if no control box set, look for one
        if (controlBox == null) {
            if (lightState != null) {
                lightState = null;
            }
            searchTick++;
            if (searchTick > 20) {
                searchTick = 0;
                for (int x = pos.getX() - 25; x <= pos.getX() + 25; x++) {
                    for (int y = pos.getY() - 25; y <= pos.getY() + 25; y++) {
                        for (int z = pos.getZ() - 25; z <= pos.getZ() + 25; z++) {
                            TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
                            if (tileEntity instanceof TileEntityTrafficLightControlBox) {
                                controlBox = (TileEntityTrafficLightControlBox) tileEntity;
                            }
                        }
                    }
                }
            }
        }

        // update state from control box
        if (controlBox != null) {
            switch (controlBox.intersectionState) {
                case EW_GREEN_NS_RED:
                    if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                        lightState = LightState.RED;
                    } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                        lightState = LightState.GREEN;
                    }
                    break;
                case EW_YELLOW_NS_RED:
                    if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                        lightState = LightState.RED;
                    } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                        lightState = LightState.YELLOW;
                    }
                    break;
                case EW_RED_NS_GREEN:
                    if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                        lightState = LightState.GREEN;
                    } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                        lightState = LightState.RED;
                    }
                    break;
                case EW_RED_NS_YELLOW:
                    if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                        lightState = LightState.YELLOW;
                    } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                        lightState = LightState.RED;
                    }
            }
        }

        // blink red light if colorState is null
        if (lightState == null) {
            blinkTick++;
            if (blinkTick > 13) {
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

    public enum LightState {
        RED(0.532),
        YELLOW(0.345),
        GREEN(0.156);

        public final double y;

        LightState(double y) {
            this.y = y;
        }
    }
}
