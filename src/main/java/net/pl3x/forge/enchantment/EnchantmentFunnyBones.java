package net.pl3x.forge.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.capability.DeceptionTarget;
import net.pl3x.forge.capability.DeceptionTargetProvider;
import net.pl3x.forge.item.ModItems;

import java.util.concurrent.ThreadLocalRandom;

public class EnchantmentFunnyBones extends Enchantment {
    public EnchantmentFunnyBones() {
        super(Rarity.RARE, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST, EntityEquipmentSlot.FEET, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.HEAD});
        setName("funnyBones");
        setRegistryName(getName());

        ModEnchantments.enchantments.add(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 1 + 10 * (enchantmentLevel - 1);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 50;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        Item item = stack.getItem();
        return item == ModItems.BONE_BOOTS ||
                item == ModItems.BONE_CHESTPLATE ||
                item == ModItems.BONE_HELMET ||
                item == ModItems.BONE_LEGGINGS;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return canApply(stack);
    }

    @SubscribeEvent
    public void on(LivingSetAttackTargetEvent event) {
        EntityLivingBase target = event.getTarget();
        EntityLivingBase entity = event.getEntityLiving();
        if (!(target instanceof EntityPlayerMP)) {
            return; // not targeting player
        }

        if (!(entity instanceof EntitySkeleton)) {
            return; // not a skeleton
        }

        EntitySkeleton skeleton = (EntitySkeleton) entity;
        DeceptionTarget deceptionTarget = entity.getCapability(DeceptionTargetProvider.CAPABILITY, null);
        if (deceptionTarget == null) {
            return;
        }

        if (entity.getRevengeTarget() == target) {
            deceptionTarget.ignoreTarget(target, false);
            return; // player attacked
        }

        int level = ModEnchantments.getTotalEnchantmentLevel(ModEnchantments.FUNNY_BONES, target);
        if (level < 1) {
            deceptionTarget.ignoreTarget(target, false);
            return; // does not have funny bones enchantment
        }

        if (deceptionTarget.hasIgnoredTarget(target)) {
            if (deceptionTarget.shouldIgnoreTarget(target)) {
                // already rolled funny bones chance, ignored
                skeleton.setAttackTarget(null);
                return;
            }
            // already rolled funny bones chance, NOT ignored
            return;
        }

        if (ThreadLocalRandom.current().nextInt(100) < level * 5) {
            // blinded! ignore
            deceptionTarget.ignoreTarget(target, true);
            skeleton.setAttackTarget(null);
        }
    }
}
