package net.pl3x.forge.item.custom.tool;

import net.minecraft.creativetab.CreativeTabs;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.item.ModItems;

public class ItemHoe extends net.minecraft.item.ItemHoe {
    private final String name;

    public ItemHoe(ToolMaterial material, String name) {
        super(material);
        setRegistryName(name);
        setUnlocalizedName(name);
        this.name = name;

        ModItems.items.add(this);
    }

    public void registerItemModel() {
        Pl3x.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public ItemHoe setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
