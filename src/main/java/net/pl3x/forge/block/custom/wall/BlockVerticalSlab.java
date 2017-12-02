package net.pl3x.forge.block.custom.wall;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
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

import java.util.Random;

public abstract class BlockVerticalSlab extends BlockBase {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 1.0, 1.0); // east
    protected static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5); // west
    protected static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 1.0, 1.0); // south
    protected static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 1.0, 1.0); // north

    public BlockVerticalSlab(Material material, String name) {
        super(material, name);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (isDouble()) {
            return FULL_BLOCK_AABB;
        }
        switch (state.getValue(FACING)) {
            case NORTH:
                return AABB_NORTH;
            case SOUTH:
                return AABB_SOUTH;
            case WEST:
                return AABB_WEST;
            case EAST:
                return AABB_EAST;
        }
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return isDouble();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return isDouble();
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return isDouble();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, placer.getHorizontalFacing());
        if (isDouble()) {
            return state;
        }
        switch (state.getValue(FACING)) {
            case SOUTH:
                return facing != EnumFacing.WEST && (facing == EnumFacing.EAST || hitX <= 0.5) ? state : state.withProperty(FACING, state.getValue(FACING).getOpposite());
            case WEST:
                return facing != EnumFacing.NORTH && (facing == EnumFacing.SOUTH || hitZ <= 0.5) ? state : state.withProperty(FACING, state.getValue(FACING).getOpposite());
            case EAST:
                return facing != EnumFacing.SOUTH && (facing == EnumFacing.NORTH || hitZ >= 0.5) ? state : state.withProperty(FACING, state.getValue(FACING).getOpposite());
            case NORTH:
            default:
                return facing != EnumFacing.EAST && (facing == EnumFacing.WEST || hitX >= 0.5) ? state : state.withProperty(FACING, state.getValue(FACING).getOpposite());
        }
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror) {
        return state.withRotation(mirror.toRotation(state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int quantityDropped(Random random) {
        return isDouble() ? 2 : 1;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return isDouble();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return !isDouble() || super.shouldSideBeRendered(blockState, world, pos, facing);
    }

    public boolean isDouble() {
        return false;
    }
}
