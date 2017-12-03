package net.pl3x.forge.block.custom.stairs;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.pl3x.forge.block.ModBlocks;

public class BlockStairsStone extends BlockStairs {
    public BlockStairsStone() {
        super(Material.ROCK, "stairs_stone");
        setSoundType(SoundType.STONE);
        setHardness(1.5F);
        setResistance(10F);

        setCreativeTab(TAB_STAIRS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.STONE;
    }
}
