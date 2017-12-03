package net.pl3x.forge.block.custom.vertical_slab;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.item.ItemVerticalSlab;

public class BlockVerticalSlabOak extends BlockVerticalSlab {
    public BlockVerticalSlabOak() {
        super(Material.WOOD, "vertical_slab_oak");
        setHardness(2.0f);
        setResistance(5.0f);
        setSoundType(SoundType.WOOD);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.WOOD;
    }

    @Override
    public Item createItemBlock() {
        return new ItemVerticalSlab(ModBlocks.VERTICAL_SLAB_OAK, ModBlocks.VERTICAL_SLAB_OAK, ModBlocks.VERTICAL_SLAB_OAK_DOUBLE).setRegistryName(getRegistryName());
    }
}
