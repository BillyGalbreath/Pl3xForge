package net.pl3x.forge.block.custom.slab;

import net.minecraft.creativetab.CreativeTabs;

public class BlockGrassSlabHalf extends BlockGrassSlab {
    public BlockGrassSlabHalf() {
        super("grass_slab");

        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public boolean isDouble() {
        return false;
    }
}
