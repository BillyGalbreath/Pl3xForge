package net.pl3x.forge.block.custom.slab;

import net.minecraft.creativetab.CreativeTabs;

public class BlockDirtSlabHalf extends BlockDirtSlab {
    public BlockDirtSlabHalf() {
        super("dirt_slab");

        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public boolean isDouble() {
        return false;
    }
}
