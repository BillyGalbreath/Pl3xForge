package net.pl3x.forge.block.custom.furniture;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCouch extends BlockSeat {
    private static final PropertyEnum<EnumShape> SHAPE = PropertyEnum.create("shape", EnumShape.class);

    public BlockCouch() {
        super(Material.WOOD, "couch");
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH).withProperty(SHAPE, EnumShape.SINGLE));
        setSoundType(SoundType.CLOTH);

        AABB = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 1D, 0.9375D);
        AABB_NORTH = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 1D, 0.315D);
        AABB_SOUTH = new AxisAlignedBB(0.0625D, 0D, 0.685D, 0.9375D, 1D, 0.9375D);
        AABB_WEST = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.315D, 1D, 0.9375D);
        AABB_EAST = new AxisAlignedBB(0.685D, 0D, 0.0625D, 0.9375D, 1D, 0.9375D);
        AABB_BASE = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 0.4375D, 0.9375D);

        yOffset = -0.25;
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.RED_STAINED_HARDENED_CLAY;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState state = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return state.withProperty(SHAPE, EnumShape.SINGLE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(SHAPE, getCouchShape(state, worldIn, pos));
    }

    private static EnumShape getCouchShape(IBlockState state, IBlockAccess iBlockAccess, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);

        IBlockState leftState = iBlockAccess.getBlockState(pos.offset(facing.rotateY().getOpposite()));
        IBlockState rightState = iBlockAccess.getBlockState(pos.offset(facing.rotateY()));

        if (isBlockCouch(leftState) && isBlockCouch(rightState)) {
            // has a couch on both side, is an extension piece
            return EnumShape.EXTENSION;
        }

        if (isBlockCouch(leftState)) {
            // has couch to left but not right, this is a right end (or corner)
            if (isDifferentCouch(iBlockAccess, pos.offset(facing.getOpposite()), facing)) {
                return EnumShape.CORNER_RIGHT;
            }
            if (isDifferentCouch(iBlockAccess, pos.offset(facing), facing)) {
                return EnumShape.CORNER_OUTER_LEFT;
            }
            return EnumShape.END_RIGHT;
        }

        if (isBlockCouch(rightState)) {
            // has couch to right but not left, this is a left end (or corner)
            if (isDifferentCouch(iBlockAccess, pos.offset(facing.getOpposite()), facing)) {
                return EnumShape.CORNER_LEFT;
            }
            if (isDifferentCouch(iBlockAccess, pos.offset(facing), facing)) {
                return EnumShape.CORNER_OUTER_RIGHT;
            }
            return EnumShape.END_LEFT;
        }
        // does NOT have couch on either side, this is a single piece
        return EnumShape.SINGLE;
    }

    private static boolean isDifferentCouch(IBlockAccess iBlockAccess, BlockPos pos, EnumFacing facing) {
        IBlockState state = iBlockAccess.getBlockState(pos);
        return isBlockCouch(state) && state.getValue(FACING) != facing;
    }

    private static boolean isBlockCouch(IBlockState state) {
        return state.getBlock() instanceof BlockCouch;
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SHAPE);
    }

    public enum EnumShape implements IStringSerializable {
        SINGLE("single"),
        EXTENSION("extension"),
        CORNER_LEFT("corner_left"),
        CORNER_RIGHT("corner_right"),
        CORNER_OUTER_LEFT("corner_outer_left"),
        CORNER_OUTER_RIGHT("corner_outer_right"),
        END_LEFT("end_left"),
        END_RIGHT("end_right");

        private final String name;

        EnumShape(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
