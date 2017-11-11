package net.pl3x.forge.block.custom.crops;

import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.pl3x.forge.item.ModItems;

public class BlockCropCorn extends BlockCrops {
    public BlockCropCorn() {
        setUnlocalizedName("crop_corn");
        setRegistryName("crop_corn");

        //setCreativeTab(CreativeTabs.FOOD);
    }

    @Override
    protected Item getSeed() {
        return ModItems.CORN_SEED;
    }

    @Override
    protected Item getCrop() {
        return ModItems.CORN;
    }
}
