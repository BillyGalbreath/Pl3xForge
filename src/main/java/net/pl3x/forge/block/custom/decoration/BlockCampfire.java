package net.pl3x.forge.block.custom.decoration;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.BlockBase;
import net.pl3x.forge.block.ModBlocks;

public class BlockCampfire extends BlockBase {
    private static final PropertyBool ON = PropertyBool.create("on");
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);

    public BlockCampfire() {
        super(Material.WOOD, "campfire");
        setDefaultState(blockState.getBaseState().withProperty(ON, Boolean.FALSE));
        setSoundType(SoundType.WOOD);
        setHardness(1);
        setLightLevel(0);

        setCreativeTab(CreativeTabs.DECORATIONS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
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
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.WOOD;
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue(IBlockState state) {
        return 1.0F;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return isOn(state) ? 15 : 0;
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ON);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(ON, (meta & 1) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (state.getValue(ON)) {
            i |= 1;
        }
        return i;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        toggleOnOff(worldIn, pos, state);
        return true;
    }

    public void toggleOnOff(World worldIn, BlockPos pos) {
        toggleOnOff(worldIn, pos, worldIn.getBlockState(pos));
    }

    public void toggleOnOff(World world, BlockPos pos, IBlockState state) {
        world.setBlockState(pos, state.cycleProperty(ON), 3);
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.checkLight(pos);
        playClickSound(world, pos);
    }

    public static boolean isOn(IBlockAccess worldIn, BlockPos pos) {
        return isOn(worldIn.getBlockState(pos));
    }

    public static boolean isOn(IBlockState state) {
        return state.getValue(ON);
    }

    private void playClickSound(World world, BlockPos pos) {
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
}
