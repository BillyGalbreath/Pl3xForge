package net.pl3x.forge.block.custom.crops;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.item.ModItems;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockCropEnderPearl extends BlockCrops {
    public BlockCropEnderPearl() {
        super();
        setUnlocalizedName("crop_enderpearl");
        setRegistryName("crop_enderpearl");

        ModBlocks.blocks.add(this);
    }

    private boolean isOnEndstone(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).getBlock() == ModBlocks.TILLED_END_STONE;
    }

    @Override
    @Nonnull
    protected Item getSeed() {
        return ModItems.ENDER_PEARL_SEEDS;
    }

    @Override
    @Nonnull
    protected Item getCrop() {
        return Items.ENDER_PEARL;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == ModBlocks.TILLED_END_STONE;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return false;
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return !isMaxAge(state) && canBlockStay(world, pos, state);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return isOnEndstone(world, pos) && world.getLightFromNeighbors(pos.up()) <= 7;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        if (world.getLightFromNeighbors(pos.up()) <= 7) {
            int age = getAge(state);
            if (age < getMaxAge()) {
                float chance = getGrowthChance(this, world, pos);
                if (ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt((int) (25.0F / chance) + 1) == 0)) {
                    world.setBlockState(pos, withAge(age + 1), 2);
                    ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
                }
            }
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.getValue(AGE) == 7) {
            Random rand = ((World) world).rand;
            drops.add(new ItemStack(getSeed(), rand.nextInt(10) > 5 ? 2 : 1));
            drops.add(new ItemStack(getCrop(), rand.nextInt(10) > 5 ? 2 : 1));
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        super.harvestBlock(world, player, pos, state, te, stack);
        if (isOnEndstone(world, pos) && isMaxAge(state) && world.rand.nextInt(10) == 0) {
            EntityEndermite mite = new EntityEndermite(world);
            mite.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
            world.spawnEntity(mite);
            mite.setAttackTarget(player);
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return ModItems.ENDER_PEARL_SEEDS.getDefaultInstance();
    }
}
