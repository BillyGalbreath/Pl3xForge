package net.pl3x.forge.block.custom.vertical_slab;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.pl3x.forge.block.ModBlocks;

public class BlockVerticalSlabAcaciaDouble extends BlockVerticalSlabDouble {
    public BlockVerticalSlabAcaciaDouble() {
        super(Material.WOOD, "vertical_slab_acacia_double");
        setHardness(2.0f);
        setResistance(5.0f);
        setSoundType(SoundType.WOOD);

        singleSlab = ModBlocks.VERTICAL_SLAB_ACACIA;

        ModBlocks.blocks.add(this);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.ADOBE;
    }
}
