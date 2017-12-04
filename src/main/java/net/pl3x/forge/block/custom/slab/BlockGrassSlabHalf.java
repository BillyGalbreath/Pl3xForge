package net.pl3x.forge.block.custom.slab;

public class BlockGrassSlabHalf extends BlockGrassSlab {
    public BlockGrassSlabHalf() {
        super("grass_slab");

        setCreativeTab(BlockDirtSlab.TAB_SLABS);
    }

    @Override
    public boolean isDouble() {
        return false;
    }
}
