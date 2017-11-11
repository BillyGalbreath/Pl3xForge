package net.pl3x.forge.item.custom.seed;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.block.ModBlocks;

public class ItemCornSeed extends ItemSeeds {
    public ItemCornSeed() {
        super(ModBlocks.CROP_CORN, Blocks.FARMLAND);
        setUnlocalizedName("corn_seed");
        setRegistryName("corn_seed");

        setCreativeTab(CreativeTabs.MISC);
    }

    public void registerItemModel() {
        Pl3x.proxy.registerItemRenderer(this, 0, "corn_seed");
    }
}

