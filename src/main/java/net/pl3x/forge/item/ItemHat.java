package net.pl3x.forge.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;

public class ItemHat extends ItemBase {
    private static final CreativeTabs tabHats = new CreativeTabs("Hats") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.HAT_HARDHAT_ON);
        }
    };

    public ItemHat(String name) {
        super(name);
        setCreativeTab(tabHats);
        setMaxStackSize(1);
        setNoRepair();
        setMaxDamage(0);

        ModItems.items.add(this);
    }

    public void registerItemModel() {
        Pl3x.proxy.registerItemRenderer(this, 0, getName());
    }

    @Override
    public ItemHat setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        return armorType == EntityEquipmentSlot.HEAD;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand enumHand) {
        ItemStack handItem = player.getHeldItem(enumHand);
        ItemStack slotItem = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        player.setItemStackToSlot(EntityEquipmentSlot.HEAD, handItem.copy());
        if (slotItem.isEmpty()) {
            handItem.setCount(0);
        } else {
            player.setHeldItem(enumHand, slotItem.copy());
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, handItem);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    public int getItemEnchantability(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
        return false;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, net.minecraft.client.gui.ScaledResolution resolution, float partialTicks) {
        // HUD
    }
}
