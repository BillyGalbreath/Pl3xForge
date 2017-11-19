package net.pl3x.forge.block.custom.slab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.pl3x.forge.block.ModBlocks;

public class BlockConcreteSlabHalf extends BlockConcreteSlab {
    public BlockConcreteSlabHalf(EnumDyeColor color) {
        super("concrete_slab_" + color.getName(), color);

        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public boolean isDouble() {
        return false;
    }
}
