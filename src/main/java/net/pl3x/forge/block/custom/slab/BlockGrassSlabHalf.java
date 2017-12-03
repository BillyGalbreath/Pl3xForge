package net.pl3x.forge.block.custom.slab;

import net.pl3x.forge.block.ModBlocks;

public class BlockGrassSlabHalf extends BlockGrassSlab {
    public BlockGrassSlabHalf() {
        super("grass_slab");

        setCreativeTab(BlockDirtSlab.TAB_SLABS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public boolean isDouble() {
        return false;
    }
}
