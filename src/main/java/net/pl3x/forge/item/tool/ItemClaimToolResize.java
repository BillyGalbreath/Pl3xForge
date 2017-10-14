package net.pl3x.forge.item.tool;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.pl3x.forge.item.ItemBase;
import net.pl3x.forge.item.ModItems;

public class ItemClaimToolResize extends ItemBase {
    public ItemClaimToolResize(String name) {
        super(name);

        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public void registerItemModel() {
        super.registerItemModel();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, ModItems.CLAIM_TOOL_VISUAL.getDefaultInstance());
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}
