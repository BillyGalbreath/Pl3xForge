package net.pl3x.forge.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.oredict.OreDictionary;

public class ItemOre extends ItemBase {
    private final String oreName;

    public ItemOre(String name, String oreName) {
        super(name);

        this.oreName = oreName;

        ModItems.items.add(this);
    }

    public void initOreDict() {
        OreDictionary.registerOre(oreName, this);
    }

    @Override
    public ItemOre setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
