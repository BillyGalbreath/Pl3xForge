package net.pl3x.forge.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockOre extends BlockBase {
    public BlockOre(String name, int harvestLevel, String harvestTool) {
        this(name, 3f, 5f, harvestLevel, harvestTool);
    }

    public BlockOre(String name, float hardness, float resistance, int harvestLevel, String harvestTool) {
        super(Material.ROCK, name);

        setHardness(hardness);
        setResistance(resistance);

        if (harvestTool != null && !harvestTool.isEmpty()) {
            setHarvestLevel(harvestTool, harvestLevel);
        }

        ModBlocks.__BLOCKS__.add(this);
    }

    @Override
    public BlockOre setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune < 1) {
            return quantityDropped(random);
        }
        if (Item.getItemFromBlock(this) != getItemDropped(getBlockState().getValidStates().iterator().next(), random, fortune)) {
            int i = random.nextInt(fortune + 2) - 1;
            if (i < 0) {
                i = 0;
            }
            return quantityDropped(random) * (i + 1);
        } else {
            return quantityDropped(random);
        }
    }
}
