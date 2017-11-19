package net.pl3x.forge.block.custom.farmland;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.block.custom.crops.BlockCropEnderPearl;

import java.util.Random;

public class BlockTilledEndStone extends BlockFarmland {

    public BlockTilledEndStone() {
        super();
        setUnlocalizedName("tilled_end_stone");
        setRegistryName("tilled_end_stone");
        setSoundType(SoundType.GROUND);
        setLightOpacity(255);
        useNeighborBrightness = true;
        setHardness(0.6F);
        setHarvestLevel("pickaxe", 1);

        ModBlocks.blocks.add(this);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int moisture = state.getValue(MOISTURE);
        if (!hasDarkness(world, pos)) {
            if (moisture > 0) {
                world.setBlockState(pos, state.withProperty(MOISTURE, moisture - 1), 2);
            } else {
                turnToEndStone(world, pos);
            }
        } else if (moisture < 7) {
            world.setBlockState(pos, state.withProperty(MOISTURE, 7), 2);
        }
    }

    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isRemote && entity.canTrample(world, this, pos, fallDistance)) {
            turnToEndStone(world, pos);
        }
    }

    private void turnToEndStone(World world, BlockPos pos) {
        world.setBlockState(pos, Blocks.END_STONE.getDefaultState());
        AxisAlignedBB axisalignedbb = field_194405_c.offset(pos);
        for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb)) {
            double d0 = Math.min(axisalignedbb.maxY - axisalignedbb.minY, axisalignedbb.maxY - entity.getEntityBoundingBox().minY);
            entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.001D, entity.posZ);
        }
    }

    private boolean hasCrops(IBlockState state, World world, BlockPos pos) {
        Block block = world.getBlockState(pos.up()).getBlock();
        return block instanceof IPlantable && canSustainPlant(state, world, pos, EnumFacing.UP, (IPlantable) block);
    }

    private boolean hasDarkness(World world, BlockPos pos) {
        return world.getLightFromNeighbors(pos.up()) <= 7;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos pos2) {
        if (world.getBlockState(pos.up()).getMaterial().isSolid()) {
            turnToEndStone(world, pos);
        }
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (world.getBlockState(pos.up()).getMaterial().isSolid()) {
            turnToEndStone(world, pos);
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        switch (side) {
            case UP:
                return true;
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                IBlockState state1 = world.getBlockState(pos.offset(side));
                Block block = state1.getBlock();
                return !state1.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH && block != ModBlocks.TILLED_END_STONE;
            default:
                return super.shouldSideBeRendered(state, world, pos, side);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Blocks.END_STONE.getItemDropped(Blocks.END_STONE.getDefaultState(), rand, fortune);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return plantable.getPlant(world, pos.offset(direction)).getBlock() instanceof BlockCropEnderPearl;
    }

    @Override
    public boolean isFertile(World world, BlockPos pos) {
        return world.getBlockState(pos).getValue(BlockTilledEndStone.MOISTURE) > 0;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(Blocks.END_STONE));
    }
}
