package net.pl3x.forge.core.item.tool;

import net.minecraft.creativetab.CreativeTabs;
import net.pl3x.forge.core.Pl3xCore;

public class ItemHoe extends net.minecraft.item.ItemHoe {
    private final String name;

    public ItemHoe(ToolMaterial material, String name) {
        super(material);
        setRegistryName(name);
        setUnlocalizedName(name);
        this.name = name;
    }

    public void registerItemModel() {
        Pl3xCore.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public ItemHoe setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
