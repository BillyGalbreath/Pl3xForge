package net.pl3x.forge.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMoney extends ItemBase {
    public ItemMoney(String name) {
        super(name);

        setMaxStackSize(1); // money does not stack
    }

    public static boolean isMoney(EntityItem entityItem) {
        return entityItem != null && isMoney(entityItem.getItem());
    }

    public static boolean isMoney(ItemStack stack) {
        return stack != null && !stack.isEmpty() && isMoney(stack.getItem());
    }

    public static boolean isMoney(Item item) {
        return item != null && item instanceof ItemMoney;
    }

    @Override
    public ItemMoney setCreativeTab(CreativeTabs tab) {
        return this; // money items never go into creative tabs
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        if (entityItem.isDead) {
            return true; // stop processing
        }
        if (entityItem.getItem().isEmpty()) {
            entityItem.setDead();
            return true; // stop processing
        }

        if (!(entityItem.getItem().getItem() instanceof ItemMoney)) {
            return false; // not money
        }

        // do stuffs maybe?

        return false; // return false to let EntityItem#onUpdate finish running
    }

    public int getEntityLifespan(ItemStack itemStack, World world) {
        return 1200; // 1 minute
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false; // not enchantable
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
        return false; // not enchantable
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean isBeaconPayment(ItemStack stack) {
        return false; // not a beacon payment
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 0; // not a furnace fuel
    }
}
