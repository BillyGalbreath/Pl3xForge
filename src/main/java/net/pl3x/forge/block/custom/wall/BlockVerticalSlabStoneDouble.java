package net.pl3x.forge.block.custom.wall;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.pl3x.forge.block.ModBlocks;

public class BlockVerticalSlabStoneDouble extends BlockVerticalSlabDouble {
    public BlockVerticalSlabStoneDouble() {
        super(Material.ROCK, "vertical_slab_stone_double");
        setHardness(2.0f);
        setResistance(10.0f);
        setSoundType(SoundType.STONE);

        singleSlab = ModBlocks.VERTICAL_SLAB_STONE;

        ModBlocks.blocks.add(this);
    }
}
