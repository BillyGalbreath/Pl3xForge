package net.pl3x.forge.item.custom.seed;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.item.ModItems;

public class ItemCornSeeds extends ItemSeeds {
    public ItemCornSeeds() {
        super(ModBlocks.CROP_CORN, Blocks.FARMLAND);
        setUnlocalizedName("corn_seeds");
        setRegistryName("corn_seeds");

        setCreativeTab(CreativeTabs.MISC);

        ModItems.items.add(this);
    }

    public void registerItemModel() {
        Pl3x.proxy.registerItemRenderer(this, 0, "corn_seeds");
    }
}
