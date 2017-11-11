package net.pl3x.forge.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.pl3x.forge.block.custom.decoration.BlockTrafficLight;

public class TileEntityTrafficLight extends TileEntity implements ITickable {
    public final EnumFacing facing;

    public final int rot;
    public LightState lightState;
    public BlockTrafficLight.EnumPole pole;

    private int searchTick = 0;
    private int blinkTick = 0;
    private boolean blinkState = false;
    private int oldLightLevel = -1;

    public double xOffset;
    public double yOffset;
    public double zOffset;

    public TileEntityTrafficLightControlBox controlBox;

    public TileEntityTrafficLight() {
        this(EnumFacing.SOUTH);
    }

    public TileEntityTrafficLight(EnumFacing facing) {
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
    }

    public void updateOffsets() {
        if (!world.isRemote) {
            return;
        }
        BlockTrafficLight.EnumPole chkPole = world.getBlockState(pos).getActualState(world, pos).getValue(BlockTrafficLight.POLE);
        if (pole != chkPole) {
            pole = chkPole;
            switch (pole) {
                case VERTICAL:
                    xOffset = 0.5;
                    yOffset = 0.155;
                    zOffset = 0.7817;
                    break;
                case HORIZONTAL:
                    xOffset = 0.5;
                    yOffset = 0.14;
                    zOffset = 0.7505;
                    break;
                case NONE:
                default:
                    xOffset = 0.5;
                    yOffset = 0;
                    zOffset = 0.595;
            }
        }
    }

    public boolean isLightOn() {
        return lightState != null || blinkState;
    }

    public int getLightLevel() {
        return isLightOn() ? 5 : 0;
    }

    public void setLightState(LightState state) {
        if (lightState != state) {
            lightState = state;
            updateOffsets();
        }
    }

    public void resetLight() {
        setLightState(null);
        searchTick = 0;
        blinkTick = 0;
        blinkState = facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH;
    }

    @Override
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
                resetLight();
            }
            searchTick++;
            if (searchTick > 20) {
                searchTick = 0;
                int radius = 10;
                for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++) {
                    for (int y = pos.getY() - radius; y <= pos.getY() + radius; y++) {
                        for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++) {
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
                case NS_GREEN_EW_RED:
                    if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                        setLightState(LightState.RED);
                    } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                        setLightState(LightState.GREEN);
                    }
                    break;
                case NS_YELLOW_EW_RED:
                    if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                        setLightState(LightState.RED);
                    } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                        setLightState(LightState.YELLOW);
                    }
                    break;
                case NS_RED_EW_GREEN:
                    if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                        setLightState(LightState.GREEN);
                    } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                        setLightState(LightState.RED);
                    }
                    break;
                case NS_RED_EW_YELLOW:
                    if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
                        setLightState(LightState.YELLOW);
                    } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                        setLightState(LightState.RED);
                    }
            }
        } else if (lightState != null) {
            resetLight();
        }

        // blink red light if colorState is null
        if (lightState == null) {
            blinkTick++;
            if (blinkTick > 13) {
                blinkTick = 0;
                blinkState = !blinkState;
                updateOffsets();
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
        RED(-0.203, 0.532),
        YELLOW(0, 0.345),
        GREEN(0.203, 0.156);

        public final double x;
        public final double y;

        LightState(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
