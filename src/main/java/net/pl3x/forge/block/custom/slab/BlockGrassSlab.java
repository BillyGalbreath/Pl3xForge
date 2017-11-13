package net.pl3x.forge.block.custom.slab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.block.ModBlocks;

import java.util.Random;

public abstract class BlockGrassSlab extends BlockSlab {
    private static final PropertyEnum<BlockGrassSlab.Variant> VARIANT = PropertyEnum.create("variant", BlockGrassSlab.Variant.class);

    private final String name;

    public BlockGrassSlab(String name) {
        super(Material.GROUND);

        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
        setSoundType(SoundType.GROUND);
        setHardness(0.6F);
        setTickRandomly(true);
        useNeighborBrightness = true;

        IBlockState iblockstate = blockState.getBaseState();
        if (!isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, EnumBlockHalf.BOTTOM);
        }
        setDefaultState(iblockstate);
    }

    public void registerItemModel(Item item) {
        Pl3x.proxy.registerItemRenderer(item, 0, name);
    }

    public Item createItemBlock() {
        return new ItemSlab(ModBlocks.GRASS_SLAB, ModBlocks.GRASS_SLAB, ModBlocks.GRASS_SLAB_DOUBLE).setRegistryName(getRegistryName());
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.GRASS_SLAB);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.GRASS_SLAB);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.GRASS;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return true;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockGrassSlab.Variant.DEFAULT);
        if (!isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }
        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (!isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP) {
            i |= 8;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
    }

    public String getUnlocalizedName(int meta) {
        return this.getUnlocalizedName();
    }

    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    public Comparable<?> getTypeForItem(ItemStack stack) {
        return BlockGrassSlab.Variant.DEFAULT;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote) {
            return; // dont run on client
        }
        BlockPos posUp = pos.up();
        if (worldIn.getLightFromNeighbors(posUp) < 4 && worldIn.getBlockState(posUp).getLightOpacity(worldIn, posUp) > 2) {
            if (isDouble()) {
                worldIn.setBlockState(pos, ModBlocks.DIRT_SLAB_DOUBLE.getDefaultState());
            } else {
                worldIn.setBlockState(pos, ModBlocks.DIRT_SLAB.getDefaultState().withProperty(HALF, state.getValue(HALF)));
            }
            return; // grass slab turned to dirt slab
        }
        if (worldIn.getLightFromNeighbors(posUp) < 9) {
            return; // not bright enough
        }
        // look for dirt to change to grass
        for (int i = 0; i < 4; ++i) {
            BlockPos dirtpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
            if (dirtpos.getY() >= 0 && dirtpos.getY() < 256 && !worldIn.isBlockLoaded(dirtpos)) {
                return; // block not loaded (lazy chunk?)
            }
            IBlockState dirtUpState = worldIn.getBlockState(dirtpos.up());
            IBlockState dirtState = worldIn.getBlockState(dirtpos);
            Block dirtBlock = dirtState.getBlock();
            if (worldIn.getLightFromNeighbors(dirtpos.up()) >= 4 && dirtUpState.getLightOpacity(worldIn, pos.up()) <= 2) {
                if (dirtBlock == ModBlocks.DIRT_SLAB) {
                    worldIn.setBlockState(dirtpos, ModBlocks.GRASS_SLAB.getDefaultState().withProperty(HALF, dirtState.getValue(HALF)));
                } else if (dirtBlock == ModBlocks.DIRT_SLAB_DOUBLE) {
                    worldIn.setBlockState(dirtpos, ModBlocks.GRASS_SLAB_DOUBLE.getDefaultState());
                } else if (dirtBlock == Blocks.DIRT) {
                    worldIn.setBlockState(dirtpos, Blocks.GRASS.getDefaultState());
                }
            }
        }
    }

    public enum Variant implements IStringSerializable {
        DEFAULT;

        public String getName() {
            return "default";
        }
    }
}
