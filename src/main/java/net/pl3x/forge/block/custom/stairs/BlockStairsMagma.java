package net.pl3x.forge.block.custom.stairs;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.ModBlocks;

import java.util.Random;

public class BlockStairsMagma extends BlockStairs {
    public BlockStairsMagma() {
        super(Material.ROCK, "stairs_magma");
        setSoundType(SoundType.STONE);
        setHardness(1);
        setLightLevel(0.2F);
        setTickRandomly(true);

        setCreativeTab(TAB_STAIRS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.NETHERRACK;
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        if (!entity.isImmuneToFire() && entity instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase) entity)) {
            entity.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
        }
        super.onEntityWalk(world, pos, entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        return 15728880;
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        BlockPos blockpos = pos.up();
        IBlockState iblockstate = world.getBlockState(blockpos);
        if (iblockstate.getBlock() == Blocks.WATER || iblockstate.getBlock() == Blocks.FLOWING_WATER) {
            world.setBlockToAir(blockpos);
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
            if (world instanceof WorldServer) {
                ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.25D, (double) blockpos.getZ() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D);
            }
        }
    }
}
