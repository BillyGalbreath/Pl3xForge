package net.pl3x.forge.item.tool;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.pl3x.forge.claims.Selection;
import net.pl3x.forge.item.ItemBase;

public class ItemClaimTool extends ItemBase {
    public ItemClaimTool() {
        super("claim_tool");

        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.TOOLS);
    }

    // Right click (block only) (fires first)
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (player.world.isRemote) {
            player.swingArm(EnumHand.MAIN_HAND);
        }
        player.resetActiveHand();
        return EnumActionResult.PASS;
    }

    // Right click (air or block) (fires second)
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (player.world.isRemote) {
            player.swingArm(EnumHand.MAIN_HAND);
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
        return false; // cancel creative block breaking
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        return true; // cancel block harvesting (breaking)
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        return false; // cancel block harvesting (breaking)
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return 0.0F; // cancel block crack animation
    }

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

    public static void processClaimToolClick(PlayerInteractEvent event, BlockPos pos, boolean isRightClick) {
        Item item = event.getEntityPlayer().getHeldItemMainhand().getItem();
        if (item instanceof ItemClaimTool) {
            ItemClaimTool claimTool = (ItemClaimTool) item;
            if (pos == null) {
                RayTraceResult result = event.getEntityPlayer().rayTrace(256, 1F);
                if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                    pos = result.getBlockPos();
                }
            }
            if (pos == null) {
                return; // still could not find a block in sight
            }
            if (isRightClick) {
                claimTool.processRightClick(event.getEntityPlayer(), pos);
            } else {
                claimTool.processLeftClick(event.getEntityPlayer(), pos);
            }
            event.setCanceled(true);
        }
    }
}
