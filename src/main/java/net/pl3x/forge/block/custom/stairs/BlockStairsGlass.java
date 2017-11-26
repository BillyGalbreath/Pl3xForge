package net.pl3x.forge.block.custom.stairs;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.ModBlocks;

public class BlockStairsGlass extends BlockStairs {
    public BlockStairsGlass() {
        super(Material.GLASS, "stairs_glass");
        setSoundType(SoundType.GLASS);
        setHardness(1);

        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        ModBlocks.blocks.add(this);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        Block block = world.getBlockState(pos.offset(face)).getBlock();
        return block == this || block == Blocks.GLASS;
    }
}
