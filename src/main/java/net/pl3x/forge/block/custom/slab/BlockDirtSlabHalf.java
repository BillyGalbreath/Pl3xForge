package net.pl3x.forge.block.custom.slab;

import net.pl3x.forge.block.ModBlocks;

public class BlockDirtSlabHalf extends BlockDirtSlab {
    public BlockDirtSlabHalf() {
        super("dirt_slab");

        setCreativeTab(BlockDirtSlab.TAB_SLABS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public boolean isDouble() {
        return false;
    }
}
