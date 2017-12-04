package net.pl3x.forge.block.custom.decoration;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.stairs.block.stairs.BlockStairsBasic;
import net.pl3x.stairs.tab.StairsTab;

public class BlockStairsRuby extends BlockStairsBasic {
    public BlockStairsRuby() {
        super(Material.IRON, "stairs_ruby", MapColor.RED);
        setHardness(20F);
        setResistance(30F);
        setSoundType(SoundType.METAL);

        ModBlocks.blocks.add(this);
        net.pl3x.stairs.block.ModBlocks.__BLOCKS__.remove(this);

        StairsTab.LIST.add(StairsTab.LIST.indexOf(net.pl3x.stairs.block.ModBlocks.STAIRS_EMERALD) + 1, this);
    }
}
