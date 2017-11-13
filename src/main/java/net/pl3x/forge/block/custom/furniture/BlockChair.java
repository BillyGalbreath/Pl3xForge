package net.pl3x.forge.block.custom.furniture;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.BlockBase;
import net.pl3x.forge.entity.EntityChairSeat;

import javax.annotation.Nullable;
import java.util.List;

public class BlockChair extends BlockBase {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 1D, 0.9375D);

    public BlockChair() {
        super(Material.WOOD, "chair");
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
        setSoundType(SoundType.WOOD);
        setHardness(1);

        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (!isActualState) {
            state = getActualState(state, worldIn, pos);
        }

        AxisAlignedBB aabb;
        switch (state.getValue(FACING)) {
            case NORTH:
                aabb = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.1275D, 1.437D, 0.9375D);
                break;
            case SOUTH:
                aabb = new AxisAlignedBB(0.8735D, 0D, 0.0625D, 0.9375D, 1.437D, 0.9375D);
                break;
            case WEST:
                aabb = new AxisAlignedBB(0.0625D, 0D, 0.8735D, 0.9375D, 1.437D, 0.9375D);
                break;
            case EAST:
            default:
                aabb = new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 1.437D, 0.1275D);
                break;
        }
        addCollisionBoxToList(pos, entityBox, collidingBoxes, aabb);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.0625D, 0D, 0.0625D, 0.9375D, 0.5D, 0.9375D));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
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
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
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
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.RED;
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

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        sit(world, pos, player);
        return true;
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        // handle tile entity setup
    }

    public void sit(World world, BlockPos pos, EntityPlayer player) {
        EntityChairSeat entity = new EntityChairSeat(world);
        entity.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        world.spawnEntity(entity);
        player.setPosition(entity.posX, entity.posY, entity.posZ);
        player.rotationYaw = entity.rotationYaw;
        player.rotationPitch = entity.rotationPitch;
        player.startRiding(entity);
    }
}
