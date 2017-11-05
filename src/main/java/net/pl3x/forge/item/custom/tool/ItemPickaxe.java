package net.pl3x.forge.item.custom.tool;

import net.minecraft.creativetab.CreativeTabs;
import net.pl3x.forge.Pl3x;

public class ItemPickaxe extends net.minecraft.item.ItemPickaxe {
    private final String name;

    public ItemPickaxe(ToolMaterial material, String name) {
        super(material);
        setRegistryName(name);
        setUnlocalizedName(name);
        this.name = name;
    }

    public void registerItemModel() {
        Pl3x.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public ItemPickaxe setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
