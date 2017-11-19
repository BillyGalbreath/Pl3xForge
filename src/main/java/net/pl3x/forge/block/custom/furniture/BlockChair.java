package net.pl3x.forge.block.custom.furniture;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pl3x.forge.block.ModBlocks;

import javax.annotation.Nullable;
import java.util.List;

public class BlockChair extends BlockSeat {
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 1D, 0.9375D);
    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 1.437D, 0.1275D);
    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.0625D, 0D, 0.8735D, 0.9375D, 1.437D, 0.9375D);
    private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.1275D, 1.437D, 0.9375D);
    private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.8735D, 0D, 0.0625D, 0.9375D, 1.437D, 0.9375D);
    private static final AxisAlignedBB AABB_BASE = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 0.5D, 0.9375D);

    public BlockChair() {
        super(Material.WOOD, "chair");

        ModBlocks.blocks.add(this);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (!isActualState) {
            state = getActualState(state, worldIn, pos);
        }
        switch (state.getValue(FACING)) {
            case NORTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH);
                break;
            case SOUTH:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH);
                break;
            case WEST:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST);
                break;
            case EAST:
            default:
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST);
                break;
        }
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BASE);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }
}
