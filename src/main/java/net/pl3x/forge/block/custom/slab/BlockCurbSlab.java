package net.pl3x.forge.block.custom.slab;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.BlockBase;
import net.pl3x.forge.block.ModBlocks;

public class BlockCurbSlab extends BlockBase {
    private static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final PropertyEnum<BlockCurbSlab.EnumShape> SHAPE = PropertyEnum.create("shape", BlockCurbSlab.EnumShape.class);
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

    private final EnumDyeColor color;

    public BlockCurbSlab(EnumDyeColor color) {
        super(Material.ROCK, "curb_slab_" + color.getName());
        this.color = color;

        setHardness(2.0F);
        setSoundType(SoundType.STONE);
        useNeighborBrightness = true;

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH).withProperty(SHAPE, BlockCurbSlab.EnumShape.STRAIGHT));

        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.getBlockColor(color);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState state;
        try {
            state = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        } catch (IllegalArgumentException var11) {
            state = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, placer);
        }
        return state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(SHAPE, BlockCurbSlab.EnumShape.STRAIGHT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | state.getValue(FACING).getHorizontalIndex();
        return i;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(SHAPE, getCurbShape(state, worldIn, pos));
    }

    private static BlockCurbSlab.EnumShape getCurbShape(IBlockState state, IBlockAccess iBlockAccess, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        IBlockState neighborState = iBlockAccess.getBlockState(pos.offset(facing));
        if (isBlockCurb(neighborState)) {
            EnumFacing neighborFacing = neighborState.getValue(FACING);
            if (neighborFacing.getAxis() != state.getValue(FACING).getAxis() &&
                    isDifferentCurb(state, iBlockAccess, pos, neighborFacing.getOpposite())) {
                if (neighborFacing == facing.rotateYCCW()) {
                    return BlockCurbSlab.EnumShape.CORNER_LEFT;
                }
                return BlockCurbSlab.EnumShape.CORNER_RIGHT;
            }
        }
        IBlockState oppositeState = iBlockAccess.getBlockState(pos.offset(facing.getOpposite()));
        if (isBlockCurb(oppositeState)) {
            EnumFacing oppositeFacing = oppositeState.getValue(FACING);
            if (oppositeFacing.getAxis() != state.getValue(FACING).getAxis() &&
                    isDifferentCurb(state, iBlockAccess, pos, oppositeFacing)) {
                if (oppositeFacing == facing.rotateYCCW()) {
                    return BlockCurbSlab.EnumShape.CORNER_LEFT;
                }
                return BlockCurbSlab.EnumShape.CORNER_RIGHT;
            }
        }
        return BlockCurbSlab.EnumShape.STRAIGHT;
    }

    private static boolean isDifferentCurb(IBlockState state, IBlockAccess iBlockAccess, BlockPos pos, EnumFacing facing) {
        IBlockState iblockstate = iBlockAccess.getBlockState(pos.offset(facing));
        return !isBlockCurb(iblockstate) || iblockstate.getValue(FACING) != state.getValue(FACING);
    }

    public static boolean isBlockCurb(IBlockState state) {
        return state.getBlock() instanceof BlockCurbSlab;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SHAPE);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.SOLID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        //this.modelState.neighborChanged(worldIn, pos, Blocks.AIR, pos);
    }

    public enum EnumShape implements IStringSerializable {
        STRAIGHT("straight"),
        CORNER_LEFT("corner_left"),
        CORNER_RIGHT("corner_right");

        private final String name;

        EnumShape(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    public static BlockCurbSlab getBlock(EnumDyeColor color) {
        switch (color) {
            case BLACK:
                return ModBlocks.CURB_SLAB_BLACK;
            case BLUE:
                return ModBlocks.CURB_SLAB_BLUE;
            case BROWN:
                return ModBlocks.CURB_SLAB_BROWN;
            case CYAN:
                return ModBlocks.CURB_SLAB_CYAN;
            case GRAY:
                return ModBlocks.CURB_SLAB_GRAY;
            case GREEN:
                return ModBlocks.CURB_SLAB_GREEN;
            case LIGHT_BLUE:
                return ModBlocks.CURB_SLAB_LIGHT_BLUE;
            case LIME:
                return ModBlocks.CURB_SLAB_LIME;
            case MAGENTA:
                return ModBlocks.CURB_SLAB_MAGENTA;
            case ORANGE:
                return ModBlocks.CURB_SLAB_ORANGE;
            case PINK:
                return ModBlocks.CURB_SLAB_PINK;
            case PURPLE:
                return ModBlocks.CURB_SLAB_PURPLE;
            case RED:
                return ModBlocks.CURB_SLAB_RED;
            case SILVER:
                return ModBlocks.CURB_SLAB_SILVER;
            case YELLOW:
                return ModBlocks.CURB_SLAB_YELLOW;
            case WHITE:
            default:
                return ModBlocks.CURB_SLAB_WHITE;
        }
    }
}
