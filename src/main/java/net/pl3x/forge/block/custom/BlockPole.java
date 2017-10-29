package net.pl3x.forge.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.BlockBase;
import net.pl3x.forge.block.ModBlocks;

import javax.annotation.Nullable;
import java.util.List;

public class BlockPole extends BlockBase {
    public static final PropertyBool VERTICAL = PropertyBool.create("vertical");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool WEST = PropertyBool.create("west");
    private static final AxisAlignedBB AABB_VERTICAL = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D);
    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.41D, 0.41D, 0.0D, 0.59D, 0.59D, 0.59D);
    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.41D, 0.41D, 0.41D, 0.59D, 0.59D, 1.0D);
    private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.41D, 0.41D, 0.41D, 1D, 0.59D, 0.59D);
    private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.0D, 0.41D, 0.41D, 0.59D, 0.59D, 0.59D);
    private static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[]{
            // no data, vertical pole
            new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), // 0

            // no verticals
            new AxisAlignedBB(0.41D, 0.41D, 0.0D, 0.59D, 0.59D, 0.5D),     // 1  north
            new AxisAlignedBB(0.41D, 0.41D, 0.5D, 0.59D, 0.59D, 1.0D),     // 2  south
            new AxisAlignedBB(0.41D, 0.41D, 0.0D, 0.59D, 0.59D, 1.0D),     // 3  south + north
            new AxisAlignedBB(0.0D, 0.41D, 0.41D, 0.5D, 0.59D, 0.59D),     // 4  west
            new AxisAlignedBB(0.0D, 0.41D, 0.0D, 0.59D, 0.59D, 0.59D),     // 5  west + north
            new AxisAlignedBB(0.0D, 0.41D, 0.41D, 0.59D, 0.59D, 1.0D),     // 6  west + south
            new AxisAlignedBB(0.0D, 0.41D, 0.0D, 0.59D, 0.59D, 1.0D),      // 7  west + south + north
            new AxisAlignedBB(0.5D, 0.41D, 0.41D, 1.0D, 0.59D, 0.59D),     // 8  east
            new AxisAlignedBB(0.41D, 0.41D, 0.0D, 1.0D, 0.59D, 0.59D),     // 9  east + north
            new AxisAlignedBB(0.41D, 0.41D, 0.41D, 1.0D, 0.59D, 1.0D),     // 10 east + south
            new AxisAlignedBB(0.41D, 0.41D, 0.0D, 1.0D, 0.59D, 1.0D),      // 11 east + south + north
            new AxisAlignedBB(0.0D, 0.41D, 0.41D, 1.0D, 0.59D, 0.59D),     // 12 east + west
            new AxisAlignedBB(0.0D, 0.41D, 0.0D, 1.0D, 0.59D, 0.59D),      // 13 east + west + north
            new AxisAlignedBB(0.0D, 0.41D, 0.41D, 1.0D, 0.59D, 1.0D),      // 14 east + west + south
            new AxisAlignedBB(0.0D, 0.41D, 0.0D, 1.0D, 0.59D, 1.0D),       // 15 east + west + south + north

            // verticals
            new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), // 16 vert
            new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D),   // 17 vert + north
            new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D),   // 18 vert + south
            new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D),     // 19 vert + south + north
            new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D),   // 20 vert + west
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D),     // 21 vert + west + north
            new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D),     // 22 vert + west + south
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D),       // 23 vert + west + south + north
            new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D),   // 24 vert + east
            new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D),     // 25 vert + east + north
            new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D),     // 26 vert + east + south
            new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),       // 27 vert + east + south + north
            new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D),     // 28 vert + east + west
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D),       // 29 vert + east + west + north
            new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D),       // 30 vert + east + west + south
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),         // 31 vert + east + west + south + north
    };

    public BlockPole(String name) {
        super(Material.ROCK, name);
        setDefaultState(blockState.getBaseState()
                .withProperty(VERTICAL, false)
                .withProperty(NORTH, false)
                .withProperty(SOUTH, false)
                .withProperty(EAST, false)
                .withProperty(WEST, false));
        setHardness(2);

        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (!isActualState) {
            state = state.getActualState(worldIn, pos);
        }
        if (state.getValue(VERTICAL)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_VERTICAL);
        }
        if (state.getValue(NORTH)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH);
        }
        if (state.getValue(SOUTH)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH);
        }
        if (state.getValue(EAST)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST);
        }
        if (state.getValue(WEST)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        state = this.getActualState(state, source, pos);
        return BOUNDING_BOXES[getBoundingBoxIdx(state)];
    }

    private static int getBoundingBoxIdx(IBlockState state) {
        int i = 0;
        if (state.getValue(NORTH)) {
            i |= 1;
        }
        if (state.getValue(SOUTH)) {
            i |= 1 << 1;
        }
        if (state.getValue(WEST)) {
            i |= 1 << 2;
        }
        if (state.getValue(EAST)) {
            i |= 1 << 3;
        }
        if (state.getValue(VERTICAL)) {
            i |= 1 << 4;
        }
        System.out.println("i: " + i);
        return i;
    }

    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
        IBlockState state = worldIn.getBlockState(pos);
        BlockFaceShape shape = state.getBlockFaceShape(worldIn, pos, facing);
        Block block = state.getBlock();
        boolean flag = shape == BlockFaceShape.MIDDLE_POLE &&
                (state.getMaterial() == this.blockMaterial ||
                        block == ModBlocks.TRAFFIC_LIGHT_POLE_VERTICAL ||
                        block == ModBlocks.TRAFFIC_LIGHT_POLE_HORIZONTAL);
        return !isExcept(block) && shape == BlockFaceShape.SOLID || flag;
    }

    private static boolean isExcept(Block block) {
        return Block.isExceptBlockForAttachWithPiston(block) ||
                block == Blocks.BARRIER ||
                block == Blocks.MELON_BLOCK ||
                block == Blocks.PUMPKIN ||
                block == Blocks.LIT_PUMPKIN;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(VERTICAL, canPoleConnectTo(worldIn, pos, EnumFacing.UP) || canPoleConnectTo(worldIn, pos, EnumFacing.DOWN))
                .withProperty(NORTH, canPoleConnectTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(EAST, canPoleConnectTo(worldIn, pos, EnumFacing.EAST))
                .withProperty(SOUTH, canPoleConnectTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST, canPoleConnectTo(worldIn, pos, EnumFacing.WEST));
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        switch (rot) {
            case CLOCKWISE_180:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        switch (mirrorIn) {
            case LEFT_RIGHT:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
            default:
                return super.withMirror(state, mirrorIn);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VERTICAL, NORTH, EAST, WEST, SOUTH);
    }

    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return world.getBlockState(pos.offset(facing)).getBlock() instanceof BlockPole;
    }

    private boolean canPoleConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        BlockPos otherPos = pos.offset(facing);
        IBlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();
        if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
            return otherBlock == this || otherBlock.isSideSolid(otherState, world, otherPos, facing.getOpposite()) ||
                    otherBlock == ModBlocks.TRAFFIC_LIGHT_POLE_VERTICAL;
        }
        return otherBlock.canBeConnectedTo(world, otherPos, facing.getOpposite()) ||
                canConnectTo(world, otherPos, facing.getOpposite()) ||
                otherBlock == ModBlocks.TRAFFIC_LIGHT_POLE_VERTICAL ||
                otherBlock == ModBlocks.TRAFFIC_LIGHT_POLE_HORIZONTAL;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE : BlockFaceShape.CENTER;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
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
        return MapColor.CLAY;
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
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }
}
