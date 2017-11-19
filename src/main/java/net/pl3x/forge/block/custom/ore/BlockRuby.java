package net.pl3x.forge.block.custom.ore;

import net.minecraft.creativetab.CreativeTabs;
import net.pl3x.forge.block.BlockOre;
import net.pl3x.forge.block.ModBlocks;

public class BlockRuby extends BlockOre {
    public BlockRuby() {
        super("block_ruby", "blockRuby", 10f, 50f, 2, "pickaxe");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        ModBlocks.blocks.add(this);
    }
}
