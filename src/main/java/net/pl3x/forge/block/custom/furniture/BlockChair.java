package net.pl3x.forge.block.custom.furniture;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.pl3x.forge.block.ModBlocks;

public class BlockChair extends BlockSeat {
    public BlockChair() {
        super(Material.WOOD, "chair");

        AABB = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 1D, 0.9375D);
        AABB_NORTH = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 1.437D, 0.1275D);
        AABB_SOUTH = new AxisAlignedBB(0.0625D, 0D, 0.8735D, 0.9375D, 1.437D, 0.9375D);
        AABB_WEST = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.1275D, 1.437D, 0.9375D);
        AABB_EAST = new AxisAlignedBB(0.8735D, 0D, 0.0625D, 0.9375D, 1.437D, 0.9375D);
        AABB_BASE = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 0.5D, 0.9375D);

        ModBlocks.blocks.add(this);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.RED;
    }
}
