package net.pl3x.forge.block.custom.ore;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.pl3x.forge.block.BlockOre;
import net.pl3x.forge.block.ModBlocks;

public class BlockRubyOre extends BlockOre {
    public BlockRubyOre() {
        super("ore_ruby", "oreRuby", 20f, 30f, 4, "pickaxe");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.RED;
    }
}
