package net.pl3x.forge.block.custom.decoration;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.stairs.block.stairs.StairsBasic;
import net.pl3x.stairs.tab.StairsTab;

public class BlockStairsRubyOre extends StairsBasic {
    public BlockStairsRubyOre() {
        super(Material.ROCK, "stairs_ruby_ore", MapColor.STONE);
        setHardness(20F);
        setResistance(30F);
        setSoundType(SoundType.STONE);

        ModBlocks.__BLOCKS__.add(this);
        net.pl3x.stairs.block.ModBlocks.__BLOCKS__.remove(this);

        StairsTab.LIST.add(StairsTab.LIST.indexOf(net.pl3x.stairs.block.ModBlocks.STAIRS_EMERALD_ORE) + 1, this);
    }
}
