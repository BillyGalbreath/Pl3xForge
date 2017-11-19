package net.pl3x.forge.block.custom.slab;

import net.minecraft.creativetab.CreativeTabs;
import net.pl3x.forge.block.ModBlocks;

public class BlockDirtSlabHalf extends BlockDirtSlab {
    public BlockDirtSlabHalf() {
        super("dirt_slab");

        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public boolean isDouble() {
        return false;
    }
}
