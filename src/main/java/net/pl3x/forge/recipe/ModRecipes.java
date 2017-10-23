package net.pl3x.forge.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.item.ModItems;

public class ModRecipes {
    public static void init() {
        // Ore Dict
        ModBlocks.RUBY_BLOCK.initOreDict();
        ModBlocks.RUBY.initOreDict();
        ModItems.RUBY.initOreDict();

        // Smelting
        GameRegistry.addSmelting(ModBlocks.RUBY, new ItemStack(ModItems.RUBY), 0.7f);
    }
}
