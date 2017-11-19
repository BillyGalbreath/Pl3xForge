package net.pl3x.forge.block.custom.slab;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.pl3x.forge.block.ModBlocks;

public class BlockConcreteSlabDouble extends BlockConcreteSlab {
    public BlockConcreteSlabDouble(EnumDyeColor color) {
        super("concrete_slab_" + color.getName() + "_double", color);

        ModBlocks.blocks.add(this);
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.SOLID;
    }
}
