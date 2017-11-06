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
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.block.ModBlocks;

import java.util.Random;

public abstract class BlockDirtSlab extends BlockSlab {
    private static final PropertyEnum<BlockDirtSlab.Variant> VARIANT = PropertyEnum.create("variant", BlockDirtSlab.Variant.class);

    private final String name;

    public BlockDirtSlab(String name) {
        super(Material.ROCK);

        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
        setSoundType(SoundType.GROUND);
        setHardness(0.5F);
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
        return new ItemSlab(ModBlocks.DIRT_SLAB, ModBlocks.DIRT_SLAB, ModBlocks.DIRT_SLAB_DOUBLE).setRegistryName(getRegistryName());
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.DIRT_SLAB);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.DIRT_SLAB);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.DIRT;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockDirtSlab.Variant.DEFAULT);
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
        return BlockDirtSlab.Variant.DEFAULT;
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote) {
            return; // dont run on client
        }
        if (worldIn.getLightFromNeighbors(pos.up()) < 9) {
            return; // not bright enough
        }
        // look for grass to change self into grass too
        for (int i = 0; i < 4; ++i) {
            BlockPos grassPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
            if (grassPos.getY() >= 0 && grassPos.getY() < 256 && !worldIn.isBlockLoaded(grassPos)) {
                return; // block not loaded (lazy chunk?)
            }
            IBlockState grassState = worldIn.getBlockState(grassPos);
            Block grassBlock = grassState.getBlock();
            if (grassBlock != Blocks.GRASS &&
                    grassBlock != ModBlocks.GRASS_SLAB &&
                    grassBlock != ModBlocks.GRASS_SLAB_DOUBLE) {
                continue; // not a grass block
            }
            if (worldIn.getLightFromNeighbors(pos.up()) >= 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, grassPos.up()) <= 2) {
                if (isDouble()) {
                    worldIn.setBlockState(pos, ModBlocks.GRASS_SLAB_DOUBLE.getDefaultState());
                } else {
                    worldIn.setBlockState(pos, ModBlocks.GRASS_SLAB.getDefaultState().withProperty(HALF, state.getValue(HALF)));
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
