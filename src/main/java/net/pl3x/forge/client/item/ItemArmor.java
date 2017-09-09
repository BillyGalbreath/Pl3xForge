package net.pl3x.forge.client.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.pl3x.forge.client.Pl3xForgeClient;

public class ItemArmor extends net.minecraft.item.ItemArmor {
    protected final String name;

    public ItemArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        super(material, 0, slot);
        setRegistryName(name);
        setUnlocalizedName(name);
        this.name = name;
    }

    public void registerItemModel() {
        Pl3xForgeClient.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public ItemArmor setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
