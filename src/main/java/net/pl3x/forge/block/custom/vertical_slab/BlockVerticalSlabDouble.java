package net.pl3x.forge.block.custom.vertical_slab;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public abstract class BlockVerticalSlabDouble extends BlockVerticalSlab {
    protected BlockVerticalSlab singleSlab;

    public BlockVerticalSlabDouble(Material material, String name) {
        super(material, name);
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(singleSlab);
    }
}
