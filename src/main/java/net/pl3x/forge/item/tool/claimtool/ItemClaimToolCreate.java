package net.pl3x.forge.item.tool.claimtool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pl3x.forge.claims.Selection;
import net.pl3x.forge.item.ModItems;

public class ItemClaimToolCreate extends ItemClaimTool {
    public ItemClaimToolCreate(String name) {
        super(name);
    }

    @Override
    public void registerItemModel() {
        super.registerItemModel();
    }

    // Right click (air or block) (fires second)
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, ModItems.CLAIM_TOOL_RESIZE.getDefaultInstance());
        }
        return super.onItemRightClick(world, player, hand);
    }

    // Right click (block only) (fires first)
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (player.isSneaking()) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, ModItems.CLAIM_TOOL_RESIZE.getDefaultInstance());
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    @Override
    public void processRightClick(EntityPlayer player, BlockPos pos) {
        if (player.isSneaking()) {
            return;
        }

        if (pos == null) {
            return;
        }

        if (Selection.CURRENT_SELECTION == null || Selection.CURRENT_SELECTION.getDimension() != player.dimension) {
            // no current selection, or selection is in another dimension, start a new selection for this dimension
            Selection.CURRENT_SELECTION = new Selection(null, null, player.dimension);
        }

        // update the right click position
        Selection.CURRENT_SELECTION.setRightClickPos(pos);
    }

    @Override
    public void processLeftClick(EntityPlayer player, BlockPos pos) {
        if (player.isSneaking()) {
            Selection.CURRENT_SELECTION = new Selection();
            return; // player is sneaking, reset selection
        }

        if (pos == null) {
            return;
        }

        if (Selection.CURRENT_SELECTION == null || Selection.CURRENT_SELECTION.getDimension() != player.dimension) {
            // no current selection, or selection is in another dimension, start a new selection for this dimension
            Selection.CURRENT_SELECTION = new Selection(null, null, player.dimension);
        }

        // update the left click position
        Selection.CURRENT_SELECTION.setLeftClickPos(pos);
    }
}
