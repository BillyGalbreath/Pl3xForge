package net.pl3x.forge.client.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.client.block.BlockBase;
import net.pl3x.forge.client.block.ModBlocks;

import java.util.Random;

public class BlockLamp extends BlockBase {
    private static final PropertyEnum<BlockLamp.Half> HALF = PropertyEnum.create("half", BlockLamp.Half.class);
    private static final PropertyBool ON = PropertyBool.create("on");
    private static final AxisAlignedBB AABB_UPPER = new AxisAlignedBB(0.25D, -1D, 0.25D, 0.75D, 1D, 0.75D);
    private static final AxisAlignedBB AABB_LOWER = new AxisAlignedBB(0.25D, 0D, 0.25D, 0.75D, 2D, 0.75D);

    public BlockLamp() {
        super(Material.GLASS, "lamp");
        setDefaultState(blockState.getBaseState()
                .withProperty(HALF, BlockLamp.Half.UPPER)
                .withProperty(ON, Boolean.TRUE));
        setHardness(1);
        setLightLevel(0);

        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(HALF) == BlockLamp.Half.LOWER ? AABB_LOWER : AABB_UPPER;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.GOLD;
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue(IBlockState state) {
        return 0.2F;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return isOn(state) ? 15 : 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == BlockLamp.Half.UPPER ? Items.AIR : Item.getItemFromBlock(ModBlocks.lamp);
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, ON);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return (meta & 2) > 0 ? getDefaultState().withProperty(HALF, BlockLamp.Half.UPPER).withProperty(ON, (meta & 1) > 0) : getDefaultState().withProperty(HALF, Half.LOWER);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (state.getValue(HALF) == Half.UPPER) {
            i = i | 2;
            if (state.getValue(ON)) {
                i |= 1;
            }
        }
        return i;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        if (state.getValue(HALF) == Half.LOWER) {
            IBlockState stateLower = worldIn.getBlockState(pos.down());
            if (stateLower.getBlock() == this) {
                state = state.withProperty(ON, stateLower.getValue(ON));
            }
        }
        return state;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (state.getValue(HALF) == Half.UPPER) {
            BlockPos posLower = pos.down();
            IBlockState stateLower = worldIn.getBlockState(posLower);
            if (stateLower.getBlock() != this) {
                worldIn.setBlockToAir(pos);
            } else if (blockIn != this) {
                stateLower.neighborChanged(worldIn, posLower, blockIn, fromPos);
            }
        } else {
            boolean dropItem = false;
            BlockPos posUpper = pos.up();
            IBlockState stateUpper = worldIn.getBlockState(posUpper);

            if (stateUpper.getBlock() != this) {
                worldIn.setBlockToAir(pos);
                dropItem = true;
            }
            if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
                worldIn.setBlockToAir(pos);
                dropItem = true;
                if (stateUpper.getBlock() == this) {
                    worldIn.setBlockToAir(posUpper);
                }
            }
            if (dropItem) {
                if (!worldIn.isRemote) {
                    dropBlockAsItem(worldIn, pos, state, 0);
                }
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return pos.getY() < worldIn.getHeight() - 1 &&
                worldIn.getBlockState(pos.down()).getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID &&
                super.canPlaceBlockAt(worldIn, pos) &&
                super.canPlaceBlockAt(worldIn, pos.up());
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos.up(), getDefaultState().withProperty(HALF, BlockLamp.Half.UPPER), 2);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        BlockPos posLower = pos.down();
        BlockPos posUpper = pos.up();
        if (player.capabilities.isCreativeMode &&
                state.getValue(HALF) == BlockLamp.Half.UPPER &&
                worldIn.getBlockState(posLower).getBlock() == this) {
            worldIn.setBlockToAir(posLower);
        }
        if (state.getValue(HALF) == BlockLamp.Half.LOWER && worldIn.getBlockState(posUpper).getBlock() == this) {
            if (player.capabilities.isCreativeMode) {
                worldIn.setBlockToAir(pos);
            }
            worldIn.setBlockToAir(posUpper);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        toggleLight(worldIn, pos, state);
        return true;
    }

    public void toggleLight(World worldIn, BlockPos pos) {
        toggleLight(worldIn, pos, worldIn.getBlockState(pos));
    }

    public void toggleLight(World worldIn, BlockPos pos, IBlockState state) {
        BlockPos posUpper = state.getValue(HALF) == BlockLamp.Half.UPPER ? pos : pos.up();
        IBlockState stateUpper = pos.equals(posUpper) ? state : worldIn.getBlockState(posUpper);
        if (stateUpper.getBlock() == this) {
            worldIn.setBlockState(posUpper, stateUpper.cycleProperty(ON), 2);
            worldIn.markBlockRangeForRenderUpdate(posUpper, pos);
            playClickSound(worldIn, pos);
        }
    }

    public static boolean isOn(IBlockAccess worldIn, BlockPos pos) {
        return isOn(worldIn.getBlockState(pos));
    }

    public static boolean isOn(IBlockState state) {
        return state.getValue(HALF) == Half.UPPER && state.getValue(ON);
    }

    public void playClickSound(World world, BlockPos pos) {
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    public enum Half implements IStringSerializable {
        UPPER,
        LOWER;

        public String toString() {
            return this.getName();
        }

        public String getName() {
            return this == UPPER ? "upper" : "lower";
        }
    }
}
