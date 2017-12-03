package net.pl3x.forge.block.custom.stairs;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.pl3x.forge.block.BlockBase;
import net.pl3x.forge.block.ModBlocks;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BlockStairs extends BlockBase {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<EnumHalf> HALF = PropertyEnum.create("half", EnumHalf.class);
    public static final PropertyEnum<EnumShape> SHAPE = PropertyEnum.create("shape", EnumShape.class);
    protected static final AxisAlignedBB AABB_SLAB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_TOP_WEST = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_TOP_EAST = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_TOP_NORTH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
    protected static final AxisAlignedBB AABB_QTR_TOP_SOUTH = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_OCT_TOP_NW = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 0.5D);
    protected static final AxisAlignedBB AABB_OCT_TOP_NE = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
    protected static final AxisAlignedBB AABB_OCT_TOP_SW = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 0.5D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_OCT_TOP_SE = new AxisAlignedBB(0.5D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_BOT_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_BOT_EAST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_QTR_BOT_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
    protected static final AxisAlignedBB AABB_QTR_BOT_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_OCT_BOT_NW = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 0.5D);
    protected static final AxisAlignedBB AABB_OCT_BOT_NE = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
    protected static final AxisAlignedBB AABB_OCT_BOT_SW = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 0.5D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_OCT_BOT_SE = new AxisAlignedBB(0.5D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);

    public static final CreativeTabs TAB_STAIRS = new CreativeTabs("stairs") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.STAIRS_ICE_PACKED);
        }

        @Override
        public void displayAllRelevantItems(NonNullList<ItemStack> items) {
            //super.displayAllRelevantItems(items);

            //TreeMap<String, ItemStack> map = new TreeMap<>();
            //items.forEach(item -> map.put(item.getUnlocalizedName(), item));

            items.clear();

            items.add(new ItemStack(ModBlocks.STAIRS_DIRT));
            items.add(new ItemStack(ModBlocks.STAIRS_DIRT_COARSE));
            items.add(new ItemStack(ModBlocks.STAIRS_GRASS));
            items.add(new ItemStack(Blocks.OAK_STAIRS));
            items.add(new ItemStack(Blocks.BIRCH_STAIRS));
            items.add(new ItemStack(Blocks.JUNGLE_STAIRS));
            items.add(new ItemStack(Blocks.SPRUCE_STAIRS));
            items.add(new ItemStack(Blocks.ACACIA_STAIRS));
            items.add(new ItemStack(Blocks.DARK_OAK_STAIRS));
            items.add(new ItemStack(Blocks.STONE_STAIRS));
            items.add(new ItemStack(ModBlocks.STAIRS_COBBLESTONE_MOSSY));
            items.add(new ItemStack(ModBlocks.STAIRS_STONE));
            items.add(new ItemStack(ModBlocks.STAIRS_ANDESITE));
            items.add(new ItemStack(ModBlocks.STAIRS_ANDESITE_SMOOTH));
            items.add(new ItemStack(ModBlocks.STAIRS_DIORITE));
            items.add(new ItemStack(ModBlocks.STAIRS_DIORITE_SMOOTH));
            items.add(new ItemStack(ModBlocks.STAIRS_GRANITE));
            items.add(new ItemStack(ModBlocks.STAIRS_GRANITE_SMOOTH));
            items.add(new ItemStack(Blocks.STONE_BRICK_STAIRS));
            items.add(new ItemStack(ModBlocks.STAIRS_STONE_BRICK_CHISELED));
            items.add(new ItemStack(ModBlocks.STAIRS_STONE_BRICK_CRACKED));
            items.add(new ItemStack(ModBlocks.STAIRS_STONE_BRICK_MOSSY));
            items.add(new ItemStack(Blocks.BRICK_STAIRS));
            items.add(new ItemStack(Blocks.SANDSTONE_STAIRS));
            items.add(new ItemStack(ModBlocks.STAIRS_SANDSTONE_CHISELED));
            items.add(new ItemStack(ModBlocks.STAIRS_SANDSTONE_SMOOTH));
            items.add(new ItemStack(Blocks.RED_SANDSTONE_STAIRS));
            items.add(new ItemStack(ModBlocks.STAIRS_RED_SANDSTONE_CHISELED));
            items.add(new ItemStack(ModBlocks.STAIRS_RED_SANDSTONE_SMOOTH));
            items.add(new ItemStack(ModBlocks.STAIRS_NETHER_WART));
            items.add(new ItemStack(ModBlocks.STAIRS_NETHERRACK));
            items.add(new ItemStack(Blocks.NETHER_BRICK_STAIRS));
            items.add(new ItemStack(ModBlocks.STAIRS_RED_NETHER_BRICK));
            items.add(new ItemStack(Blocks.QUARTZ_STAIRS));
            items.add(new ItemStack(ModBlocks.STAIRS_QUARTZ_CHISELED));
            items.add(new ItemStack(ModBlocks.STAIRS_QUARTZ_PILLAR));
            items.add(new ItemStack(ModBlocks.STAIRS_GLOWSTONE));
            items.add(new ItemStack(ModBlocks.STAIRS_MAGMA));
            items.add(new ItemStack(ModBlocks.STAIRS_OBSIDIAN));
            items.add(new ItemStack(ModBlocks.STAIRS_COAL));
            items.add(new ItemStack(ModBlocks.STAIRS_IRON));
            items.add(new ItemStack(ModBlocks.STAIRS_GOLD));
            items.add(new ItemStack(ModBlocks.STAIRS_LAPIS));
            items.add(new ItemStack(ModBlocks.STAIRS_REDSTONE));
            items.add(new ItemStack(ModBlocks.STAIRS_DIAMOND));
            items.add(new ItemStack(ModBlocks.STAIRS_EMERALD));
            items.add(new ItemStack(ModBlocks.STAIRS_RUBY));
            items.add(new ItemStack(ModBlocks.STAIRS_PRISMARINE));
            items.add(new ItemStack(ModBlocks.STAIRS_PRISMARINE_BRICK));
            items.add(new ItemStack(ModBlocks.STAIRS_DARK_PRISMARINE));
            items.add(new ItemStack(ModBlocks.STAIRS_SEA_LANTERN));
            items.add(new ItemStack(ModBlocks.STAIRS_END_STONE));
            items.add(new ItemStack(ModBlocks.STAIRS_END_BRICKS));
            items.add(new ItemStack(Blocks.PURPUR_STAIRS));
            items.add(new ItemStack(ModBlocks.STAIRS_PURPUR_PILLAR));
            items.add(new ItemStack(ModBlocks.STAIRS_BONE));
            items.add(new ItemStack(ModBlocks.STAIRS_ICE_PACKED));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_BLACK));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_BLUE));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_BROWN));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_CYAN));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_GRAY));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_GREEN));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_LIGHT_BLUE));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_LIME));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_MAGENTA));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_ORANGE));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_PINK));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_PURPLE));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_RED));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_SILVER));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_WHITE));
            items.add(new ItemStack(ModBlocks.STAIRS_CONCRETE_YELLOW));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_BLACK));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_BLUE));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_BROWN));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_CYAN));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_GRAY));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_GREEN));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_LIGHT_BLUE));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_LIME));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_MAGENTA));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_ORANGE));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_PINK));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_PURPLE));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_RED));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_SILVER));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_WHITE));
            items.add(new ItemStack(ModBlocks.STAIRS_GLASS_YELLOW));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_BLACK));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_BLUE));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_BROWN));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_CYAN));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_GRAY));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_GREEN));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_LIGHT_BLUE));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_LIME));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_MAGENTA));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_ORANGE));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_PINK));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_PURPLE));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_RED));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_SILVER));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_WHITE));
            items.add(new ItemStack(ModBlocks.STAIRS_TERRACOTTA_YELLOW));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_BLACK));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_BLUE));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_BROWN));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_CYAN));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_GRAY));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_GREEN));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_LIGHT_BLUE));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_LIME));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_MAGENTA));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_ORANGE));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_PINK));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_PURPLE));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_RED));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_SILVER));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_WHITE));
            items.add(new ItemStack(ModBlocks.STAIRS_WOOL_YELLOW));

            //map.forEach((k, v) -> {
            //    System.out.println(k);
            //    items.add(v);
            //});
        }
    };

    public BlockStairs(Material material, String name) {
        super(material, name);
        setDefaultState(blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH)
                .withProperty(HALF, EnumHalf.BOTTOM)
                .withProperty(SHAPE, EnumShape.STRAIGHT));

        ModBlocks.blocks.add(this);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState) {
        if (!isActualState) {
            state = this.getActualState(state, world, pos);
        }
        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
    }

    private static List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
        List<AxisAlignedBB> list = Lists.newArrayList();
        boolean flag = state.getValue(HALF) == EnumHalf.TOP;
        list.add(flag ? AABB_SLAB_TOP : AABB_SLAB_BOTTOM);
        EnumShape shape = state.getValue(SHAPE);
        if (shape == EnumShape.STRAIGHT || shape == EnumShape.INNER_LEFT || shape == EnumShape.INNER_RIGHT) {
            list.add(getCollQuarterBlock(state));
        }
        if (shape != EnumShape.STRAIGHT) {
            list.add(getCollEighthBlock(state));
        }
        return list;
    }

    private static AxisAlignedBB getCollQuarterBlock(IBlockState state) {
        boolean flag = state.getValue(HALF) == EnumHalf.TOP;
        switch (state.getValue(FACING)) {
            case NORTH:
            default:
                return flag ? AABB_QTR_BOT_NORTH : AABB_QTR_TOP_NORTH;
            case SOUTH:
                return flag ? AABB_QTR_BOT_SOUTH : AABB_QTR_TOP_SOUTH;
            case WEST:
                return flag ? AABB_QTR_BOT_WEST : AABB_QTR_TOP_WEST;
            case EAST:
                return flag ? AABB_QTR_BOT_EAST : AABB_QTR_TOP_EAST;
        }
    }

    private static AxisAlignedBB getCollEighthBlock(IBlockState state) {
        EnumFacing facing = state.getValue(FACING);
        EnumFacing facing1;
        switch (state.getValue(SHAPE)) {
            case OUTER_LEFT:
            default:
                facing1 = facing;
                break;
            case OUTER_RIGHT:
                facing1 = facing.rotateY();
                break;
            case INNER_RIGHT:
                facing1 = facing.getOpposite();
                break;
            case INNER_LEFT:
                facing1 = facing.rotateYCCW();
        }
        boolean flag = state.getValue(HALF) == EnumHalf.TOP;
        switch (facing1) {
            case NORTH:
            default:
                return flag ? AABB_OCT_BOT_NW : AABB_OCT_TOP_NW;
            case SOUTH:
                return flag ? AABB_OCT_BOT_SE : AABB_OCT_TOP_SE;
            case WEST:
                return flag ? AABB_OCT_BOT_SW : AABB_OCT_TOP_SW;
            case EAST:
                return flag ? AABB_OCT_BOT_NE : AABB_OCT_TOP_NE;
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        state = getActualState(state, world, pos);
        if (face.getAxis() == EnumFacing.Axis.Y) {
            return face == EnumFacing.UP == (state.getValue(HALF) == EnumHalf.TOP) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
        } else {
            EnumShape shape = state.getValue(SHAPE);
            if (shape != EnumShape.OUTER_LEFT && shape != EnumShape.OUTER_RIGHT) {
                EnumFacing enumfacing = state.getValue(FACING);
                switch (shape) {
                    case INNER_RIGHT:
                        return enumfacing != face && enumfacing != face.rotateYCCW() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
                    case INNER_LEFT:
                        return enumfacing != face && enumfacing != face.rotateY() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
                    case STRAIGHT:
                        return enumfacing == face ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
                    default:
                        return BlockFaceShape.UNDEFINED;
                }
            } else {
                return BlockFaceShape.UNDEFINED;
            }
        }
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
    public boolean isTopSolid(IBlockState state) {
        return state.getValue(HALF) == EnumHalf.TOP;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        state = state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(SHAPE, EnumShape.STRAIGHT);
        return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? state.withProperty(HALF, EnumHalf.BOTTOM) : state.withProperty(HALF, EnumHalf.TOP);
    }

    @Override
    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        List<RayTraceResult> list = Lists.newArrayList();
        for (AxisAlignedBB aabb : getCollisionBoxList(getActualState(blockState, worldIn, pos))) {
            list.add(rayTrace(pos, start, end, aabb));
        }
        RayTraceResult result = null;
        double d1 = 0.0D;
        for (RayTraceResult raytraceresult : list) {
            if (raytraceresult != null) {
                double d0 = raytraceresult.hitVec.squareDistanceTo(end);
                if (d0 > d1) {
                    result = raytraceresult;
                    d1 = d0;
                }
            }
        }
        return result;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = getDefaultState().withProperty(HALF, (meta & 4) > 0 ? EnumHalf.TOP : EnumHalf.BOTTOM);
        state = state.withProperty(FACING, EnumFacing.getFront(5 - (meta & 3)));
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (state.getValue(HALF) == EnumHalf.TOP) {
            i |= 4;
        }
        i = i | 5 - state.getValue(FACING).getIndex();
        return i;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(SHAPE, getStairsShape(state, worldIn, pos));
    }

    private static EnumShape getStairsShape(IBlockState state, IBlockAccess world, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        IBlockState state1 = world.getBlockState(pos.offset(facing));
        if (isBlockStairs(state1) && state.getValue(HALF) == state1.getValue(HALF)) {
            EnumFacing facing1 = state1.getValue(FACING);
            if (facing1.getAxis() != state.getValue(FACING).getAxis() && isDifferentStairs(state, world, pos, facing1.getOpposite())) {
                if (facing1 == facing.rotateYCCW()) {
                    return EnumShape.OUTER_LEFT;
                }
                return EnumShape.OUTER_RIGHT;
            }
        }
        IBlockState state2 = world.getBlockState(pos.offset(facing.getOpposite()));
        if (isBlockStairs(state2) && state.getValue(HALF) == state2.getValue(HALF)) {
            EnumFacing facing2 = state2.getValue(FACING);
            if (facing2.getAxis() != state.getValue(FACING).getAxis() && isDifferentStairs(state, world, pos, facing2)) {
                if (facing2 == facing.rotateYCCW()) {
                    return EnumShape.INNER_LEFT;
                }
                return EnumShape.INNER_RIGHT;
            }
        }
        return EnumShape.STRAIGHT;
    }

    private static boolean isDifferentStairs(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        IBlockState iblockstate = world.getBlockState(pos.offset(facing));
        return !isBlockStairs(iblockstate) || iblockstate.getValue(FACING) != state.getValue(FACING) || iblockstate.getValue(HALF) != state.getValue(HALF);
    }

    public static boolean isBlockStairs(IBlockState state) {
        return state.getBlock() instanceof BlockStairs;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        EnumFacing enumfacing = state.getValue(FACING);
        EnumShape shape = state.getValue(SHAPE);
        switch (mirrorIn) {
            case LEFT_RIGHT:
                if (enumfacing.getAxis() == EnumFacing.Axis.Z) {
                    switch (shape) {
                        case OUTER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumShape.OUTER_LEFT);
                        case INNER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumShape.INNER_LEFT);
                        case INNER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumShape.INNER_RIGHT);
                        default:
                            return state.withRotation(Rotation.CLOCKWISE_180);
                    }
                }
                break;
            case FRONT_BACK:
                if (enumfacing.getAxis() == EnumFacing.Axis.X) {
                    switch (shape) {
                        case OUTER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumShape.OUTER_LEFT);
                        case INNER_RIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumShape.INNER_RIGHT);
                        case INNER_LEFT:
                            return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, EnumShape.INNER_LEFT);
                        case STRAIGHT:
                            return state.withRotation(Rotation.CLOCKWISE_180);
                    }
                }
        }
        return super.withMirror(state, mirrorIn);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, HALF, SHAPE);
    }

    @Override
    public boolean canEntitySpawn(IBlockState state, Entity entityIn) {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        if (ForgeModContainer.disableStairSlabCulling) {
            return super.doesSideBlockRendering(state, world, pos, face);
        }
        if (state.isOpaqueCube()) {
            return true;
        }
        state = getActualState(state, world, pos);
        EnumHalf half = state.getValue(HALF);
        EnumFacing side = state.getValue(FACING);
        EnumShape shape = state.getValue(SHAPE);
        if (face == EnumFacing.UP) {
            return half == EnumHalf.TOP;
        }
        if (face == EnumFacing.DOWN) {
            return half == EnumHalf.BOTTOM;
        }
        if (shape == EnumShape.OUTER_LEFT || shape == EnumShape.OUTER_RIGHT) {
            return false;
        }
        if (face == side) {
            return true;
        }
        if (shape == EnumShape.INNER_LEFT && face.rotateY() == side) {
            return true;
        }
        if (shape == EnumShape.INNER_RIGHT && face.rotateYCCW() == side) {
            return true;
        }
        return false;
    }

    public enum EnumHalf implements IStringSerializable {
        TOP("top"),
        BOTTOM("bottom");

        private final String name;

        EnumHalf(String name) {
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

    public enum EnumShape implements IStringSerializable {
        STRAIGHT("straight"),
        INNER_LEFT("inner_left"),
        INNER_RIGHT("inner_right"),
        OUTER_LEFT("outer_left"),
        OUTER_RIGHT("outer_right");

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
}
