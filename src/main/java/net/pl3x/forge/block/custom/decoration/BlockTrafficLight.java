package net.pl3x.forge.block.custom.decoration;

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
import net.minecraft.tileentity.TileEntity;
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
import net.pl3x.forge.block.BlockTileEntity;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.tileentity.TileEntityTrafficLight;

import javax.annotation.Nullable;

public class BlockTrafficLight extends BlockTileEntity<TileEntityTrafficLight> {
    public static final PropertyEnum<EnumPole> POLE = PropertyEnum.create("pole", EnumPole.class);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final AxisAlignedBB AABB_VERT_NS = new AxisAlignedBB(0.41D, 0.0D, 0.375D, 0.59D, 0.6905D, 0.625D);
    private static final AxisAlignedBB AABB_VERT_WE = new AxisAlignedBB(0.375D, 0.0D, 0.41D, 0.625D, 0.6905D, 0.59D);
    private static final AxisAlignedBB AABB_VERT_NORTH_HORIZ_POLE = new AxisAlignedBB(0.41D, 0.14D, 0D, 0.75D, 0.83D, 1D);
    private static final AxisAlignedBB AABB_VERT_SOUTH_HORIZ_POLE = new AxisAlignedBB(0.25D, 0.14D, 0D, 0.59D, 0.83D, 1D);
    private static final AxisAlignedBB AABB_VERT_EAST_HORIZ_POLE = new AxisAlignedBB(0D, 0.14D, 0.41D, 1D, 0.83D, 0.75D);
    private static final AxisAlignedBB AABB_VERT_WEST_HORIZ_POLE = new AxisAlignedBB(0D, 0.14D, 0.25D, 1D, 0.83D, 0.59D);
    private static final AxisAlignedBB AABB_VERT_NORTH_VERT_POLE = new AxisAlignedBB(0.37D, 0D, 0.37D, 0.78D, 1D, 0.63D);
    private static final AxisAlignedBB AABB_VERT_SOUTH_VERT_POLE = new AxisAlignedBB(0.22D, 0D, 0.37D, 0.63D, 1D, 0.63D);
    private static final AxisAlignedBB AABB_VERT_EAST_VERT_POLE = new AxisAlignedBB(0.37D, 0D, 0.37D, 0.63D, 1D, 0.78D);
    private static final AxisAlignedBB AABB_VERT_WEST_VERT_POLE = new AxisAlignedBB(0.37D, 0D, 0.22D, 0.63D, 1D, 0.63D);

    public BlockTrafficLight() {
        super(Material.WOOD, "traffic_light");
        setDefaultState(blockState.getBaseState()
                .withProperty(FACING, EnumFacing.SOUTH)
                .withProperty(POLE, EnumPole.NONE));
        setSoundType(SoundType.METAL);
        setHardness(1);

        setCreativeTab(CreativeTabs.DECORATIONS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getActualState(source, pos).getValue(POLE)) {
            case VERTICAL:
                switch (state.getValue(FACING)) {
                    case WEST:
                        return AABB_VERT_WEST_VERT_POLE;
                    case EAST:
                        return AABB_VERT_EAST_VERT_POLE;
                    case NORTH:
                        return AABB_VERT_NORTH_VERT_POLE;
                    case SOUTH:
                    default:
                        return AABB_VERT_SOUTH_VERT_POLE;
                }
            case HORIZONTAL:
                switch (state.getValue(FACING)) {
                    case WEST:
                        return AABB_VERT_WEST_HORIZ_POLE;
                    case EAST:
                        return AABB_VERT_EAST_HORIZ_POLE;
                    case NORTH:
                        return AABB_VERT_NORTH_HORIZ_POLE;
                    case SOUTH:
                    default:
                        return AABB_VERT_SOUTH_HORIZ_POLE;
                }
            case NONE:
            default:
                switch (state.getValue(FACING)) {
                    case WEST:
                    case EAST:
                        return AABB_VERT_WE;
                    case NORTH:
                    case SOUTH:
                    default:
                        return AABB_VERT_NS;
                }
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        EnumFacing enumfacing = placer.getHorizontalFacing().rotateY();
        try {
            return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, enumfacing);
        } catch (IllegalArgumentException var11) {
            return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, placer).withProperty(FACING, enumfacing);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | state.getValue(FACING).getHorizontalIndex();
        return i;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(POLE, attachPole(worldIn, pos));
    }

    private EnumPole attachPole(IBlockAccess world, BlockPos pos) {
        for (EnumFacing facing : EnumFacing.values()) {
            if (world.getBlockState(pos.offset(facing)).getBlock() == ModBlocks.METAL_POLE) {
                if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
                    return EnumPole.VERTICAL;
                }
                return EnumPole.HORIZONTAL;
            }
        }
        return EnumPole.NONE;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, POLE);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityTrafficLight te = this.getTileEntity(world, pos);
        return te != null ? te.getLightLevel() : 0;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isTranslucent(IBlockState state) {
        return false;
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.YELLOW;
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
        return false;
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
    public Class<TileEntityTrafficLight> getTileEntityClass() {
        return TileEntityTrafficLight.class;
    }

    @Nullable
    public TileEntityTrafficLight getTileEntity(IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        return te instanceof TileEntityTrafficLight ? (TileEntityTrafficLight) te : null;
    }

    @Nullable
    @Override
    public TileEntityTrafficLight createTileEntity(World world, IBlockState state) {
        return new TileEntityTrafficLight(state.getValue(FACING));
    }

    public enum EnumPole implements IStringSerializable {
        NONE("none"),
        VERTICAL("vertical"),
        HORIZONTAL("horizontal");

        private final String name;

        EnumPole(String name) {
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
}
