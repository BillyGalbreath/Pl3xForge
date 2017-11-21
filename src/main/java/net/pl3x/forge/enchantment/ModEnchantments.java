package net.pl3x.forge.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

public class ModEnchantments {
    public static final Set<Enchantment> enchantments = new HashSet<>();

    public static final Enchantment FUNNY_BONES = new EnchantmentFunnyBones();
    public static final Enchantment LAVA_WALKER = new EnchantmentLavaWalker();

    public static void register(IForgeRegistry<Enchantment> registry) {
        enchantments.forEach(registry::register);
    }

    public static int getTotalEnchantmentLevel(Enchantment enchantment, EntityLivingBase entity) {
        int lvl = 0;
        for (ItemStack itemstack : entity.getArmorInventoryList()) {
            if (enchantment.canApply(itemstack)) {
                lvl += EnchantmentHelper.getEnchantmentLevel(enchantment, itemstack);
            }
        }
        return lvl;
    }
}
