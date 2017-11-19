package net.pl3x.forge.item.custom;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.pl3x.forge.entity.EntityTrafficCone;
import net.pl3x.forge.item.ItemBase;
import net.pl3x.forge.item.ModItems;

public class ItemTrafficCone extends ItemBase {
    public ItemTrafficCone() {
        super("traffic_cone");

        setMaxStackSize(64);
        setCreativeTab(CreativeTabs.MISC);

        ModItems.items.add(this);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        return armorType == EntityEquipmentSlot.HEAD;
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

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            RayTraceResult rayTrace = rayTrace(world, player, true);
            if (rayTrace != null && rayTrace.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos pos = rayTrace.getBlockPos();
                if (!world.isBlockModifiable(player, pos) && player.canPlayerEdit(pos, rayTrace.sideHit, stack)) {
                    return new ActionResult<>(EnumActionResult.FAIL, stack);
                }
                EntityTrafficCone entity = new EntityTrafficCone(world);
                entity.setLocationAndAngles(rayTrace.hitVec.x, rayTrace.hitVec.y + 0.5, rayTrace.hitVec.z, player.getRotationYawHead(), player.rotationPitch);
                if (world.spawnEntity(entity)) {
                    if (!player.capabilities.isCreativeMode) {
                        stack.shrink(1);
                    }
                    player.addStat(StatList.getObjectUseStats(this));
                    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
                }
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }
}
