package net.pl3x.forge.core.item.tool;

import net.minecraft.creativetab.CreativeTabs;
import net.pl3x.forge.core.Pl3xCore;

public class ItemAxe extends net.minecraft.item.ItemAxe {
    private final String name;

    public ItemAxe(ToolMaterial material, String name) {
        super(material, 8f, -3.1f);
        setRegistryName(name);
        setUnlocalizedName(name);
        this.name = name;
    }

    public void registerItemModel() {
        Pl3xCore.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public ItemAxe setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
