package net.pl3x.forge.client.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.pl3x.forge.client.Pl3xForgeClient;

public class ItemBase extends Item {
    protected final String name;

    public ItemBase(String name) {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public void registerItemModel() {
        Pl3xForgeClient.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public ItemBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}