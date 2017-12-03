package net.pl3x.forge.block.custom.vertical_slab;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
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

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.STONE;
    }
}
