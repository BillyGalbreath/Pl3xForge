package net.pl3x.forge.item.custom.food;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.item.ModItems;

public class ItemCorn extends ItemFood {
    public ItemCorn() {
        super(3, 0.6f, false);
        setUnlocalizedName("corn");
        setRegistryName("corn");

        setCreativeTab(CreativeTabs.FOOD);

        ModItems.items.add(this);
    }

    public void registerItemModel() {
        Pl3x.proxy.registerItemRenderer(this, 0, "corn");
    }
}
