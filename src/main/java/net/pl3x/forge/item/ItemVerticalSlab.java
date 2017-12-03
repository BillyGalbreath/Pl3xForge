package net.pl3x.forge.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlab;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabDouble;

public class ItemVerticalSlab extends ItemBlock {
    private final BlockVerticalSlab singleSlab;
    private final BlockVerticalSlabDouble doubleSlab;

    public ItemVerticalSlab(Block block, BlockVerticalSlab singleSlab, BlockVerticalSlabDouble doubleSlab) {
        super(block);
        this.singleSlab = singleSlab;
        this.doubleSlab = doubleSlab;

        setMaxDamage(0);
        setHasSubtypes(false);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        BlockPos sidePos = pos.offset(side);
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.isEmpty() || !player.canPlayerEdit(sidePos, side, itemstack)) {
            return EnumActionResult.FAIL;
        }

        IBlockState clickedState = world.getBlockState(pos);
        IBlockState sideState = world.getBlockState(sidePos);
        if (clickedState.getBlock() == singleSlab) {
            EnumFacing clickedFacing = clickedState.getValue(BlockVerticalSlab.FACING);
            if (clickedFacing.rotateYCCW() == side) {
                setDouble(clickedState, world, pos, side, hitX, hitY, hitZ, player, hand, itemstack);
                return EnumActionResult.SUCCESS;
            }
        }

        if (sideState.getBlock() == singleSlab) {
            setDouble(sideState, world, sidePos, side, hitX, hitY, hitZ, player, hand, itemstack);
            return EnumActionResult.SUCCESS;
        }

        return super.onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == singleSlab) {
            EnumFacing facing = state.getValue(BlockVerticalSlab.FACING);
            if ((side == EnumFacing.NORTH && (facing == EnumFacing.NORTH || facing == EnumFacing.EAST)) ||
                    (side == EnumFacing.SOUTH && (facing == EnumFacing.SOUTH || facing == EnumFacing.WEST)) ||
                    (side == EnumFacing.EAST && (facing == EnumFacing.EAST || facing == EnumFacing.SOUTH)) ||
                    (side == EnumFacing.WEST && (facing == EnumFacing.WEST || facing == EnumFacing.NORTH))) {
                return true;
            }
        }
        return world.getBlockState(pos.offset(side)).getBlock() == singleSlab ||
                super.canPlaceBlockOnSide(world, pos, side, player, stack);
    }

    private void setDouble(IBlockState oldState, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EntityPlayer player, EnumHand hand, ItemStack itemStack) {
        IBlockState newState = doubleSlab.getStateForPlacement(world, pos, side, hitX, hitY, hitZ, 0, player, hand);
        newState = newState.withProperty(BlockVerticalSlab.FACING, oldState.getValue(BlockVerticalSlab.FACING));
        AxisAlignedBB aabb = newState.getCollisionBoundingBox(world, pos);
        if (aabb != null && world.checkNoEntityCollision(aabb.offset(pos)) && world.setBlockState(pos, newState, 11)) {
            SoundType soundtype = doubleSlab.getSoundType(newState, world, pos, player);
            world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
            itemStack.shrink(1);
        }
    }
}
