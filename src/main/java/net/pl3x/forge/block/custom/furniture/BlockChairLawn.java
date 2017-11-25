package net.pl3x.forge.block.custom.furniture;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.util.CollisionHelper;

public class BlockChairLawn extends BlockSeat {
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.625, 1);
    ;

    public BlockChairLawn() {
        super(Material.WOOD, "chair_lawn");

        AABB_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.85, 0, 0, 1, 1, 1);
        AABB_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.85, 0, 0, 1, 1, 1);
        AABB_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.85, 0, 0, 1, 1, 1);
        AABB_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.85, 0, 0, 1, 1, 1);
        AABB_BASE = new AxisAlignedBB(0, 0, 0, 1, 0.35, 1);

        yOffset = -0.35;

        ModBlocks.blocks.add(this);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }
}
