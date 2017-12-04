package net.pl3x.forge.block.custom.decoration;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pl3x.forge.block.BlockDirectional;
import net.pl3x.forge.util.CollisionHelper;

import javax.annotation.Nullable;
import java.util.List;

public class BlockStairsSpiral extends BlockDirectional {
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0D, 0D, 0D, 1D, 1D, 1D);
    private static final AxisAlignedBB AABB_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.875, 0, -0.125, 1.125, 1.0, 0.125);
    private static final AxisAlignedBB AABB_SOUTH_1 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0, 0, -0.0925, 1, 0.125, 0.0925);
    private static final AxisAlignedBB AABB_SOUTH_2_1 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.75, 0.25, -0.035, 1, 0.375, 0.15);
    private static final AxisAlignedBB AABB_SOUTH_2_2 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.25, 0.05, 0.75, 0.375, 0.25);
    private static final AxisAlignedBB AABB_SOUTH_2_3 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.25, 0.25, 0.15, 0.5, 0.375, 0.35);
    private static final AxisAlignedBB AABB_SOUTH_2_4 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.05, 0.25, 0.25, 0.25, 0.375, 0.45);
    private static final AxisAlignedBB AABB_SOUTH_3_1 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.65, 0.5, 0.1, 0.9, 0.625, 0.35);
    private static final AxisAlignedBB AABB_SOUTH_3_2 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.5, 0.3, 0.75, 0.625, 0.55);
    private static final AxisAlignedBB AABB_SOUTH_3_3 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.35, 0.5, 0.45, 0.6, 0.625, 0.7);
    private static final AxisAlignedBB AABB_SOUTH_3_4 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.25, 0.5, 0.55, 0.5, 0.625, 0.8);
    private static final AxisAlignedBB AABB_SOUTH_4_1 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.775, 0.75, 0.15, 1.05, 0.875, 0.45);
    private static final AxisAlignedBB AABB_SOUTH_4_2 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.7, 0.75, 0.35, 0.9, 0.875, 0.65);
    private static final AxisAlignedBB AABB_SOUTH_4_3 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.6, 0.75, 0.5, 0.825, 0.875, 0.8);
    private static final AxisAlignedBB AABB_SOUTH_4_4 = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.525, 0.75, 0.7, 0.775, 0.875, 0.945);
    private static final AxisAlignedBB AABB_WEST = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.875, 0, -0.125, 1.125, 1.0, 0.125);
    private static final AxisAlignedBB AABB_WEST_1 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0, 0, -0.0925, 1, 0.125, 0.0925);
    private static final AxisAlignedBB AABB_WEST_2_1 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.75, 0.25, -0.035, 1, 0.375, 0.15);
    private static final AxisAlignedBB AABB_WEST_2_2 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.25, 0.05, 0.75, 0.375, 0.25);
    private static final AxisAlignedBB AABB_WEST_2_3 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.25, 0.25, 0.15, 0.5, 0.375, 0.35);
    private static final AxisAlignedBB AABB_WEST_2_4 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.05, 0.25, 0.25, 0.25, 0.375, 0.45);
    private static final AxisAlignedBB AABB_WEST_3_1 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.65, 0.5, 0.1, 0.9, 0.625, 0.35);
    private static final AxisAlignedBB AABB_WEST_3_2 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.5, 0.3, 0.75, 0.625, 0.55);
    private static final AxisAlignedBB AABB_WEST_3_3 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.35, 0.5, 0.45, 0.6, 0.625, 0.7);
    private static final AxisAlignedBB AABB_WEST_3_4 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.25, 0.5, 0.55, 0.5, 0.625, 0.8);
    private static final AxisAlignedBB AABB_WEST_4_1 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.775, 0.75, 0.15, 1.05, 0.875, 0.45);
    private static final AxisAlignedBB AABB_WEST_4_2 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.7, 0.75, 0.35, 0.9, 0.875, 0.65);
    private static final AxisAlignedBB AABB_WEST_4_3 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.6, 0.75, 0.5, 0.825, 0.875, 0.8);
    private static final AxisAlignedBB AABB_WEST_4_4 = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.525, 0.75, 0.7, 0.775, 0.875, 0.945);
    private static final AxisAlignedBB AABB_NORTH = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.875, 0, -0.125, 1.125, 1.0, 0.125);
    private static final AxisAlignedBB AABB_NORTH_1 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0, 0, -0.0925, 1, 0.125, 0.0925);
    private static final AxisAlignedBB AABB_NORTH_2_1 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.75, 0.25, -0.035, 1, 0.375, 0.15);
    private static final AxisAlignedBB AABB_NORTH_2_2 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.25, 0.05, 0.75, 0.375, 0.25);
    private static final AxisAlignedBB AABB_NORTH_2_3 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.25, 0.25, 0.15, 0.5, 0.375, 0.35);
    private static final AxisAlignedBB AABB_NORTH_2_4 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.05, 0.25, 0.25, 0.25, 0.375, 0.45);
    private static final AxisAlignedBB AABB_NORTH_3_1 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.65, 0.5, 0.1, 0.9, 0.625, 0.35);
    private static final AxisAlignedBB AABB_NORTH_3_2 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.5, 0.3, 0.75, 0.625, 0.55);
    private static final AxisAlignedBB AABB_NORTH_3_3 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.35, 0.5, 0.45, 0.6, 0.625, 0.7);
    private static final AxisAlignedBB AABB_NORTH_3_4 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.25, 0.5, 0.55, 0.5, 0.625, 0.8);
    private static final AxisAlignedBB AABB_NORTH_4_1 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.775, 0.75, 0.15, 1.05, 0.875, 0.45);
    private static final AxisAlignedBB AABB_NORTH_4_2 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.7, 0.75, 0.35, 0.9, 0.875, 0.65);
    private static final AxisAlignedBB AABB_NORTH_4_3 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.6, 0.75, 0.5, 0.825, 0.875, 0.8);
    private static final AxisAlignedBB AABB_NORTH_4_4 = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.525, 0.75, 0.7, 0.775, 0.875, 0.945);
    private static final AxisAlignedBB AABB_EAST = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.875, 0, -0.125, 1.125, 1.0, 0.125);
    private static final AxisAlignedBB AABB_EAST_1 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0, 0, -0.0925, 1, 0.125, 0.0925);
    private static final AxisAlignedBB AABB_EAST_2_1 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.75, 0.25, -0.035, 1, 0.375, 0.15);
    private static final AxisAlignedBB AABB_EAST_2_2 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.25, 0.05, 0.75, 0.375, 0.25);
    private static final AxisAlignedBB AABB_EAST_2_3 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.25, 0.25, 0.15, 0.5, 0.375, 0.35);
    private static final AxisAlignedBB AABB_EAST_2_4 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.05, 0.25, 0.25, 0.25, 0.375, 0.45);
    private static final AxisAlignedBB AABB_EAST_3_1 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.65, 0.5, 0.1, 0.9, 0.625, 0.35);
    private static final AxisAlignedBB AABB_EAST_3_2 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.5, 0.3, 0.75, 0.625, 0.55);
    private static final AxisAlignedBB AABB_EAST_3_3 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.35, 0.5, 0.45, 0.6, 0.625, 0.7);
    private static final AxisAlignedBB AABB_EAST_3_4 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.25, 0.5, 0.55, 0.5, 0.625, 0.8);
    private static final AxisAlignedBB AABB_EAST_4_1 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.775, 0.75, 0.15, 1.05, 0.875, 0.45);
    private static final AxisAlignedBB AABB_EAST_4_2 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.7, 0.75, 0.35, 0.9, 0.875, 0.65);
    private static final AxisAlignedBB AABB_EAST_4_3 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.6, 0.75, 0.5, 0.825, 0.875, 0.8);
    private static final AxisAlignedBB AABB_EAST_4_4 = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.525, 0.75, 0.7, 0.775, 0.875, 0.945);

    public BlockStairsSpiral() {
        super(Material.WOOD, "stairs_spiral");
        setSoundType(SoundType.METAL);
        setHardness(1);

        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (!isActualState) {
            state = getActualState(state, worldIn, pos);
        }
        switch (state.getValue(FACING)) {
            case NORTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_2_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_2_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_2_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_2_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_3_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_3_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_3_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_3_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_4_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_4_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_4_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH_4_4);
                break;
            case SOUTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_2_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_2_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_2_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_2_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_3_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_3_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_3_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_3_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_4_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_4_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_4_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH_4_4);
                break;
            case WEST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_2_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_2_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_2_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_2_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_3_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_3_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_3_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_3_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_4_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_4_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_4_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST_4_4);
                break;
            case EAST:
            default:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_2_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_2_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_2_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_2_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_3_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_3_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_3_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_3_4);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_4_1);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_4_2);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_4_3);
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST_4_4);
                break;
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.QUARTZ;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
