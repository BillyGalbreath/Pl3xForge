package net.pl3x.forge.client.block.custom;

import net.minecraft.creativetab.CreativeTabs;
import net.pl3x.forge.client.block.BlockOre;

public class BlockRuby extends BlockOre {
    public BlockRuby() {
        super("block_ruby", "blockRuby", 10f, 50f, 2, "pickaxe");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }
}
