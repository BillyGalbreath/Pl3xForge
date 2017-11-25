package net.pl3x.forge.block.custom.decoration;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.block.custom.furniture.BlockSeat;
import net.pl3x.forge.util.CollisionHelper;

public class BlockToilet extends BlockSeat {
    public BlockToilet() {
        super(Material.WOOD, "toilet");
        setSoundType(SoundType.GLASS);

        AABB = new AxisAlignedBB(0.3, 0.0, 0.275, 0.82, 0.645, 0.725);
        AABB_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.67, 0.0, 0.275, 0.815, 0.645, 0.725);
        AABB_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.67, 0.0, 0.275, 0.815, 0.645, 0.725);
        AABB_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.67, 0.0, 0.275, 0.815, 0.645, 0.725);
        AABB_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.67, 0.0, 0.275, 0.815, 0.645, 0.725);
        AABB_BASE = new AxisAlignedBB(0.3, 0.0, 0.3, 0.7, 0.4, 0.7);

        yOffset = -0.25;

        ModBlocks.blocks.add(this);
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
