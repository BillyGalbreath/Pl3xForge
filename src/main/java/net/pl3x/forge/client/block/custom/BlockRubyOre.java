package net.pl3x.forge.client.block.custom;

import net.minecraft.creativetab.CreativeTabs;
import net.pl3x.forge.client.block.BlockOre;

public class BlockRubyOre extends BlockOre {
    public BlockRubyOre() {
        super("ore_ruby", "oreRuby", 20f, 30f, 4, "pickaxe");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }
}
