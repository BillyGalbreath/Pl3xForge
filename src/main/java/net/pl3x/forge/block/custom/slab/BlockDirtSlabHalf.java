package net.pl3x.forge.block.custom.slab;

public class BlockDirtSlabHalf extends BlockDirtSlab {
    public BlockDirtSlabHalf() {
        super("dirt_slab");

        setCreativeTab(BlockDirtSlab.TAB_SLABS);
    }

    @Override
    public boolean isDouble() {
        return false;
    }
}
