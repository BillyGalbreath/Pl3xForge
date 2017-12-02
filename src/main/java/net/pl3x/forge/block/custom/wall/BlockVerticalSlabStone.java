package net.pl3x.forge.block.custom.wall;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.item.ItemVerticalSlab;

public class BlockVerticalSlabStone extends BlockVerticalSlab {
    public BlockVerticalSlabStone() {
        super(Material.ROCK, "vertical_slab_stone");
        setHardness(2.0f);
        setResistance(10.0f);
        setSoundType(SoundType.STONE);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public Item createItemBlock() {
        return new ItemVerticalSlab(ModBlocks.VERTICAL_SLAB_STONE, ModBlocks.VERTICAL_SLAB_STONE, ModBlocks.VERTICAL_SLAB_STONE_DOUBLE).setRegistryName(getRegistryName());
    }
}
