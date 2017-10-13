package net.pl3x.forge.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.registries.IForgeRegistry;

public class ModEnchantments {
    public static final Enchantment LAVA_WALKER = new EnchantmentLavaWalker();

    public static void register(IForgeRegistry<Enchantment> registry) {
        registry.registerAll(
                LAVA_WALKER
        );
    }
}
