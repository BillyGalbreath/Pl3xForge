package net.pl3x.forge.block.custom.stairs;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.pl3x.forge.block.ModBlocks;

public class BlockStairsIcePacked extends BlockStairs {
    public BlockStairsIcePacked() {
        super(Material.PACKED_ICE, "stairs_ice_packed");
        setSoundType(SoundType.GLASS);
        setHardness(1);
        setDefaultSlipperiness(0.98F);

        setCreativeTab(TAB_STAIRS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.ICE;
    }
}
