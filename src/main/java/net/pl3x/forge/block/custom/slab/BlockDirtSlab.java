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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.block.ModBlocks;

import java.util.Random;

public abstract class BlockDirtSlab extends BlockSlab {
    private static final PropertyEnum<BlockDirtSlab.Variant> VARIANT = PropertyEnum.create("variant", BlockDirtSlab.Variant.class);

    public static final CreativeTabs TAB_SLABS = new CreativeTabs("slabs") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.CONCRETE_SLAB_LIME);
        }

        @Override
        public void displayAllRelevantItems(NonNullList<ItemStack> items) {
            items.clear();

            items.add(new ItemStack(ModBlocks.DIRT_SLAB));
            items.add(new ItemStack(ModBlocks.GRASS_SLAB));
            //items.add(new ItemStack(Blocks.STONE_SLAB, 1, 2)); // wood_old // no longer used
            items.add(new ItemStack(Blocks.WOODEN_SLAB, 1, 0)); // oak
            items.add(new ItemStack(Blocks.WOODEN_SLAB, 1, 1)); // spruce
            items.add(new ItemStack(Blocks.WOODEN_SLAB, 1, 2)); // birch
            items.add(new ItemStack(Blocks.WOODEN_SLAB, 1, 3)); // jungle
            items.add(new ItemStack(Blocks.WOODEN_SLAB, 1, 4)); // acacia
            items.add(new ItemStack(Blocks.WOODEN_SLAB, 1, 5)); // dark oak
            items.add(new ItemStack(Blocks.STONE_SLAB, 1, 3)); // cobblestone
            items.add(new ItemStack(Blocks.STONE_SLAB, 1, 0)); // stone
            items.add(new ItemStack(Blocks.STONE_SLAB, 1, 5)); // stone_brick
            items.add(new ItemStack(Blocks.STONE_SLAB, 1, 4)); // brick
            items.add(new ItemStack(Blocks.STONE_SLAB, 1, 1)); // sandstone
            items.add(new ItemStack(Blocks.STONE_SLAB2, 1, 0)); // red sandstone
            items.add(new ItemStack(Blocks.STONE_SLAB, 1, 6)); // nether_brick
            items.add(new ItemStack(Blocks.STONE_SLAB, 1, 7)); // quartz
            items.add(new ItemStack(Blocks.PURPUR_SLAB, 1, 0)); // purpur

            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_BLACK));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_BLUE));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_BROWN));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_CYAN));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_GRAY));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_GREEN));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_LIGHT_BLUE));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_LIME));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_MAGENTA));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_ORANGE));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_PINK));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_PURPLE));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_RED));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_SILVER));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_WHITE));
            items.add(new ItemStack(ModBlocks.CONCRETE_SLAB_YELLOW));

            items.add(new ItemStack(ModBlocks.CURB_SLAB_BLACK));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_BLUE));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_BROWN));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_CYAN));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_GRAY));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_GREEN));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_LIGHT_BLUE));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_LIME));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_MAGENTA));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_ORANGE));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_PINK));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_PURPLE));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_RED));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_SILVER));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_WHITE));
            items.add(new ItemStack(ModBlocks.CURB_SLAB_YELLOW));
        }
    };

    private final String name;

    public BlockDirtSlab(String name) {
        super(Material.GROUND);

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

        ModBlocks.__BLOCKS__.add(this);
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
