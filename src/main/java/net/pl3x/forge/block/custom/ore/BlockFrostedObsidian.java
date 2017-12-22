package net.pl3x.forge.block.custom.ore;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.pl3x.forge.block.BlockOre;

import java.util.Random;

public class BlockFrostedObsidian extends BlockOre {
    private static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

    public BlockFrostedObsidian() {
        super("frosted_obsidian", 50, 6000, 3, "pickaxe");
        setDefaultState(blockState.getBaseState().withProperty(AGE, 0));
        setTickRandomly(true);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(AGE, MathHelper.clamp(meta, 0, 3));
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (rand.nextInt(3) == 0 || countNeighbors(worldIn, pos) < 4) {
            slightlyCrack(worldIn, pos, state, rand, true);
        } else {
            worldIn.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
        }
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (blockIn == this) {
            if (countNeighbors(worldIn, pos) < 2) {
                turnIntoLava(worldIn, pos);
            }
        }
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return ItemStack.EMPTY;
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    private int countNeighbors(World worldIn, BlockPos pos) {
        int count = 0;
        for (EnumFacing enumfacing : EnumFacing.values()) {
            if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this) {
                ++count;
                if (count >= 4) {
                    return count;
                }
            }
        }
        return count;
    }

    private void slightlyCrack(World worldIn, BlockPos pos, IBlockState state, Random rand, boolean crackNeighbors) {
        int age = state.getValue(AGE);

        if (age < 3) {
            worldIn.setBlockState(pos, state.withProperty(AGE, age + 1), 2);
            worldIn.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
        } else {
            turnIntoLava(worldIn, pos);

            if (crackNeighbors) {
                for (EnumFacing enumfacing : EnumFacing.values()) {
                    BlockPos blockpos = pos.offset(enumfacing);
                    IBlockState iblockstate = worldIn.getBlockState(blockpos);

                    if (iblockstate.getBlock() == this) {
                        slightlyCrack(worldIn, blockpos, iblockstate, rand, false);
                    }
                }
            }
        }
    }

    private void turnIntoLava(World worldIn, BlockPos pos) {
        dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
        worldIn.setBlockState(pos, Blocks.LAVA.getDefaultState(), 2);
        //worldIn.neighborChanged(pos, Blocks.LAVA, pos); // causes neighbors to immediately turn into lava
    }
}
