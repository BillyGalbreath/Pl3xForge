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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.block.BlockTileEntity;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.network.TVUpdateChannelPacket;
import net.pl3x.forge.tileentity.TileEntityTV;

import javax.annotation.Nullable;

public class BlockTV extends BlockTileEntity<TileEntityTV> {
    private static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<EnumChannel> CHANNEL = PropertyEnum.create("channel", EnumChannel.class);
    private static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.375D, 0.125D, -0.4365D, 0.623D, 1.1875D, 1.4365D);
    private static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(-0.4365D, 0.125D, 0.375D, 1.4365D, 1.1875D, 0.623D);

    public BlockTV() {
        super(Material.WOOD, "tv");
        setDefaultState(blockState.getBaseState()
                .withProperty(FACING, EnumFacing.SOUTH)
                .withProperty(CHANNEL, EnumChannel.OFF));
        setSoundType(SoundType.METAL);
        setHardness(1);

        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(FACING).getAxis() == EnumFacing.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityTV te = getTileEntity(world, pos);
        return te == null ? state : state.withProperty(CHANNEL, te.channel);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        EnumFacing enumfacing = placer.getHorizontalFacing().getOpposite();
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
        return new BlockStateContainer(this, FACING, CHANNEL);
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
        return BlockRenderLayer.CUTOUT;
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

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return isOn(getActualState(state, world, pos)) ? 5 : 1;
    }

    private boolean isOn(IBlockState state) {
        return state != null && state.getValue(CHANNEL) != EnumChannel.OFF;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityTV te = getTileEntity(world, pos);
        if (player.isSneaking()) {
            return true;
        }
        te.channel = te.channel.next();
        te.markDirty();
        world.setBlockState(pos, state.withProperty(CHANNEL, te.channel));
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.checkLightFor(EnumSkyBlock.BLOCK, pos);
        if (!world.isRemote) {
            PacketHandler.INSTANCE.sendToAllAround(new TVUpdateChannelPacket(pos, te.channel),
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
        }
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public Class<TileEntityTV> getTileEntityClass() {
        return TileEntityTV.class;
    }

    @Nullable
    @Override
    public TileEntityTV createTileEntity(World world, IBlockState state) {
        return new TileEntityTV(state.getValue(CHANNEL), state.getValue(FACING));
    }

    public enum EnumChannel implements IStringSerializable {
        OFF("off"),
        CH1("ch1"),
        CH2("ch2"),
        CH3("ch3"),
        CH4("ch4"),
        CH5("ch5"),
        CH6("ch6"),
        CH7("ch7"),
        CH8("ch8"),
        CH9("ch9");

        private final String name;
        private final ResourceLocation resource;
        private int frames = 0;
        private int frame = 0;

        EnumChannel(String name) {
            this.name = name;
            this.resource = new ResourceLocation(Pl3x.modId, "textures/blocks/tv_" + name + ".png");
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public ResourceLocation getResource() {
            return resource;
        }

        @SideOnly(Side.CLIENT)
        public int getFrames() {
            return frames;
        }

        @SideOnly(Side.CLIENT)
        public int getFrame() {
            return frame;
        }

        @SideOnly(Side.CLIENT)
        public void setFrames(int frames) {
            if (this.frames == 0) {
                this.frames = frames;
                this.frame = 0;
            }
        }

        @SideOnly(Side.CLIENT)
        public static void tick() {
            for (EnumChannel channel : values()) {
                if (channel.frames <= 1) {
                    continue;
                }
                channel.frame++;
                if (channel.frame >= channel.frames) {
                    channel.frame = 0;
                }
            }
        }

        public EnumChannel next() {
            EnumChannel[] chs = EnumChannel.values();
            int i = ordinal() + 1;
            return i >= chs.length ? chs[0] : chs[i];
        }
    }
}
