package net.pl3x.forge.item.tool.claimtool;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pl3x.forge.item.ItemBase;

public abstract class ItemClaimTool extends ItemBase {
    public ItemClaimTool(String name) {
        super(name);

        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public void registerItemModel() {
        super.registerItemModel();
    }

    // Right click (air or block) (fires second)
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (player.world.isRemote) {
            player.swingArm(EnumHand.MAIN_HAND);
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
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
        return 0.0F;
    }

    public abstract void processRightClick(EntityPlayer player, BlockPos pos);

    public abstract void processLeftClick(EntityPlayer player, BlockPos pos);
}
